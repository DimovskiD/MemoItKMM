package com.memoit.ddimovski.memoit.android.presentation.screen.note_detail

import com.memoit.ddimovski.memoit.domain.category.Category
import com.memoit.ddimovski.memoit.domain.note.Note

sealed interface NoteDetailEvent {
    object DismissNote: NoteDetailEvent
    object SaveNote: NoteDetailEvent
    data class DeleteNote(val id: Long): NoteDetailEvent
    data class OnTitleChanged(val value: String): NoteDetailEvent
    data class OnDescriptionChanged(val value: String): NoteDetailEvent
    data class OnDateChanged(val value: Long): NoteDetailEvent
    data class OnNotificationChanged(val value: Boolean): NoteDetailEvent
    data class EditNote(val note: Note): NoteDetailEvent
    data class OnCompleteChanged(val note: Note, val isCompleted: Boolean) : NoteDetailEvent
    data class RemoveNoteFromCategory(val note: Note, val category: Category) : NoteDetailEvent
}

