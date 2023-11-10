package com.memoit.ddimovski.memoit.domain.note

import com.memoit.ddimovski.memoit.domain.category.Category
import kotlinx.coroutines.flow.Flow

interface NoteDataSource {

    fun getNotes(): Flow<List<Note>>
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(id: Long)
    suspend fun updateNote(note: Note)
    fun countNotesInCategory(category: Category) : Int
    fun getNoteById(existingNoteId: Long): Note?
}

