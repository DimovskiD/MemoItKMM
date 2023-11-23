package com.memoit.ddimovski.memoit.android.presentation.screen.note_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memoit.ddimovski.memoit.domain.SearchNotes
import com.memoit.ddimovski.memoit.domain.category.Category
import com.memoit.ddimovski.memoit.domain.category.CategoryDataSource
import com.memoit.ddimovski.memoit.domain.note.Note
import com.memoit.ddimovski.memoit.domain.note.NoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val categoryDataSource: CategoryDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchNotes: SearchNotes = SearchNotes()

    private val searchText = savedStateHandle.getStateFlow("searchText", "")
    private val isSearchActive = savedStateHandle.getStateFlow("isSearchActive", false)
    private val categories = listOf(Category(1L, "DEFAULT"), Category(2L, "IMPORTANT"), Category(3L, "WORK"), Category(4L, "SCHOOL")) //todo remove

    private val _state = MutableStateFlow(NoteListState())
    val state = combine(
        _state,
        noteDataSource.getNotes(),
        categoryDataSource.getCategories(),
        searchText,
        isSearchActive
    ) { state: NoteListState, notes: List<Note>, categories: List<Category>, searchText, isSearchActive ->
        state.copy(
            notes = searchNotes.execute(notes, searchText, state.selectedFilter),
            totalNotesCount = notes.size,
            categories = categories,
            isSearchActive = isSearchActive,
            searchText = searchText,
            longPressedNotes = if (isSearchActive) emptyList() else state.longPressedNotes,
            selectedFilter = state.selectedFilter
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), NoteListState())


    init {
        categories.forEach {
            categoryDataSource.insertCategory(it)
        }
    }
    fun onEvent(noteListEvent: NoteListEvent) {
        when (noteListEvent) {
            is NoteListEvent.DeleteNotes -> {
                viewModelScope.launch {
                    noteListEvent.notesToDelete.forEach {
                        it.id?.let { id ->
                            noteDataSource.deleteNote(id)

                        }
                    }
                    delay(300L) // Animation delay
                    _state.update {
                        it.copy(
                            longPressedNotes = emptyList()
                        )
                    }
                }
            }
            is NoteListEvent.SelectFilter -> {
                _state.update {
                    it.copy(
                        selectedFilter = noteListEvent.filter
                    )
                }
            }
            is NoteListEvent.OnNoteLongClicked -> {
                _state.update {
                    val notes = _state.value.longPressedNotes.toMutableList()
                    if (notes.contains(noteListEvent.note)) notes.remove(noteListEvent.note)
                    else notes.add(noteListEvent.note)
                    it.copy(
                        longPressedNotes = notes
                    )
                }
            }
            is NoteListEvent.OnCompleteChanged -> {
                val note = noteListEvent.note.copy(isCompleted = noteListEvent.isCompleted)
                viewModelScope.launch {
                    noteDataSource.upsertNote(note)
                }
            }
            is NoteListEvent.OnToggleSearch -> {
                savedStateHandle["isSearchActive"] = !isSearchActive.value
                if(!isSearchActive.value) {
                    savedStateHandle["searchText"] = ""
                }
            }
            is NoteListEvent.OnSearchTextChange -> {
                savedStateHandle["searchText"] = noteListEvent.text
            }
        }
    }
}

