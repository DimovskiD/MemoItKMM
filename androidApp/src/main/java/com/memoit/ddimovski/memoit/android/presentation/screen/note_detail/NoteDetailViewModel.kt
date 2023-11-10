package com.memoit.ddimovski.memoit.android.presentation.screen.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memoit.ddimovski.memoit.domain.category.Category
import com.memoit.ddimovski.memoit.domain.category.CategoryDataSource
import com.memoit.ddimovski.memoit.domain.note.Note
import com.memoit.ddimovski.memoit.domain.note.NoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
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
    private val noteCategories = savedStateHandle.getStateFlow("noteCategories", emptyList<Category>())

    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved = _hasNoteBeenSaved.asStateFlow()

    val state = combine(
        noteTitle, noteDescription, noteCategories
    ) {
        title, description, categories ->
        NoteDetailState(
            noteTitle = title,
            noteDescription = description,
            noteCategories = categories,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteDetailState())

    private var existingNoteId: Long? = null

    init {
        savedStateHandle.get<Long>("noteId")?.let { existingNoteId ->
            if(existingNoteId == -1L) {
                return@let
            }
            this.existingNoteId = existingNoteId
            viewModelScope.launch {
                noteDataSource.getNoteById(existingNoteId)?.let { note ->
                    savedStateHandle["noteTitle"] = note.title
                    savedStateHandle["noteDescription"] = note.description
                    savedStateHandle["noteCategories"] = note.categories
                }
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
        viewModelScope.launch {
            noteDataSource.updateNote(
                Note(
                    id = existingNoteId,
                    title = noteTitle.value,
                    description = noteDescription.value,
                    categories = noteCategories.value,
                )
            )
            _hasNoteBeenSaved.value = true
        }
    }
}
