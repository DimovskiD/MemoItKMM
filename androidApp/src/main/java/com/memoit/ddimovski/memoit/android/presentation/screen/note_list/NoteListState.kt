package com.memoit.ddimovski.memoit.android.presentation.screen.note_list

import com.memoit.ddimovski.memoit.domain.category.Category
import com.memoit.ddimovski.memoit.domain.note.Note

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val categories: List<Category> = emptyList(),
    val totalNotesCount: Int = 0,
    val longPressedNotes: List<Note> = emptyList(),
    val selectedFilter: Category? = null,
    val searchText: String = "",
    val isSearchActive: Boolean = false
) {
    val isMultiSelectModeEnabled = longPressedNotes.isNotEmpty()
    val isEditEnabled = longPressedNotes.size == 1
}

