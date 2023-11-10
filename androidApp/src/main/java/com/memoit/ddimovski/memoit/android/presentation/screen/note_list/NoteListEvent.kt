package com.memoit.ddimovski.memoit.android.presentation.screen.note_list

import com.memoit.ddimovski.memoit.domain.category.Category
import com.memoit.ddimovski.memoit.domain.note.Note


sealed interface NoteListEvent {
    data class SelectFilter(val filter: Category?): NoteListEvent
    data class OnNoteLongClicked(val note: Note): NoteListEvent
    data class DeleteNotes(val notesToDelete: List<Note>): NoteListEvent
    data class OnCompleteChanged(val note: Note, val isCompleted: Boolean) : NoteListEvent
    data class OnSearchTextChange(val text: String) : NoteListEvent
    object OnToggleSearch: NoteListEvent
}

