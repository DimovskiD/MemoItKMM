package com.memoit.ddimovski.memoit.android.presentation.screen.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memoit.ddimovski.memoit.android.combine
import com.memoit.ddimovski.memoit.domain.category.Category
import com.memoit.ddimovski.memoit.domain.note.Note
import com.memoit.ddimovski.memoit.domain.note.NoteDataSource
import com.memoit.ddimovski.memoit.domain.note.NoteValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val noteTitle = savedStateHandle.getStateFlow("noteTitle", "")
    private val noteDescription = savedStateHandle.getStateFlow("noteDescription", "")
    private val noteCategories =
        savedStateHandle.getStateFlow("noteCategories", emptyList<Category>())
    private val isNoteCompleted = savedStateHandle.getStateFlow("isNoteCompleted", false)
    private val isInEditMode = savedStateHandle.getStateFlow("isInEditMode", false)
    private val isDeleteDialogVisible = savedStateHandle.getStateFlow("isDeleteDialogVisible", false)

    private val titleError: StateFlow<String?> = savedStateHandle.getStateFlow(
        "titleError",
        initialValue = null
    )
    private val descError: StateFlow<String?> = savedStateHandle.getStateFlow(
        "descError",
        initialValue = null
    )

    val state = combine(
        noteTitle, noteDescription, noteCategories, isInEditMode, isNoteCompleted, isDeleteDialogVisible
    ) { title, description, categories, isInEditMode, isNoteCompleted, isDeleteDialogVisible ->
            NoteDetailState(
                noteTitle = title,
                noteDescription = description,
                noteCategories = categories,
                isInEditMode = isInEditMode,
                isCompleted = isNoteCompleted,
                isDeleteDialogShown = isDeleteDialogVisible
            )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteDetailState())

    val errors = combine(titleError, descError) { titleError, descError ->
        NoteValidator.ValidationResult(titleError, descError)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        NoteValidator.ValidationResult()
    )

    private var existingNoteId: Long? = null

    init {
        savedStateHandle.get<Long>("noteId")?.let { existingNoteId ->
            if (existingNoteId == -1L) {
                savedStateHandle["isInEditMode"] = true
                return@let
            }
            this.existingNoteId = existingNoteId
            getNoteDetails(existingNoteId)
        }
    }

    private fun getNoteDetails(existingNoteId: Long) {
        viewModelScope.launch {
            noteDataSource.getNoteById(existingNoteId)?.let { note ->
                savedStateHandle["noteTitle"] = note.title
                savedStateHandle["noteDescription"] = note.description
                savedStateHandle["noteCategories"] = note.categories
                savedStateHandle["isNoteCompleted"] = note.isCompleted
            }
        }
    }

    fun deleteNote() = viewModelScope.launch {
        existingNoteId?.let { noteId ->
            noteDataSource.deleteNote(noteId)
        }
    }

    fun removeFromCategory(category: Category) {
        savedStateHandle["noteCategories"] = noteCategories.value.filter { it.id != category.id }
    }

    fun onNoteTitleChanged(text: String) {
        savedStateHandle["noteTitle"] = text
    }

    fun onDescriptionChanged(description: String) {
        savedStateHandle["noteDescription"] = description
    }

    fun saveNote() {
        val note = Note(
            id = existingNoteId,
            title = noteTitle.value,
            description = noteDescription.value,
            categories = noteCategories.value,
        )

        val result = NoteValidator.validateNote(note)
        savedStateHandle["titleError"] = result.titleError
        savedStateHandle["descError"] = result.descriptionError

        if (result.isValid()) {
            savedStateHandle["isInEditMode"] = false
            viewModelScope.launch {
                val noteId = noteDataSource.upsertNote(
                    note
                )
                existingNoteId = noteId
            }
        }
    }

    fun onEditClicked() {
        savedStateHandle["isInEditMode"] = true
    }

    fun completeTask() {
        updateNoteCompleted(true)
    }

    fun reopenTask() {
        updateNoteCompleted(false)
    }

    private fun updateNoteCompleted(isCompleted: Boolean) {
        savedStateHandle["isNoteCompleted"] = isCompleted
        viewModelScope.launch {
            noteDataSource.upsertNote(
                Note(
                    id = existingNoteId,
                    title = noteTitle.value,
                    description = noteDescription.value,
                    categories = noteCategories.value,
                    isCompleted = isCompleted
                )
            )
        }
    }

    fun onCancelEditMode(): Boolean {
        savedStateHandle["isInEditMode"] = false
        savedStateHandle["titleError"] = null
        savedStateHandle["descError"] = null

        val noteId = existingNoteId
        return if (noteId == null) true else {
            getNoteDetails(noteId)
            false
        }
    }

    fun onDeleteClicked(isClicked: Boolean) {
        savedStateHandle["isDeleteDialogVisible"] = isClicked
    }
}
