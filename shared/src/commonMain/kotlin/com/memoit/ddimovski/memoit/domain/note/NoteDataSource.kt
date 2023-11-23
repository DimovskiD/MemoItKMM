package com.memoit.ddimovski.memoit.domain.note

import kotlinx.coroutines.flow.Flow

interface NoteDataSource {

    fun getNotes(): Flow<List<Note>>
    suspend fun upsertNote(note: Note) : Long
    suspend fun deleteNote(id: Long)
    fun getNoteById(existingNoteId: Long): Note?
}

