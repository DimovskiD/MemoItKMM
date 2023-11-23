package com.memoit.ddimovski.memoit.data.note

import com.memoit.ddimovski.memoit.database.Task
import com.memoit.ddimovski.memoit.domain.category.Category
import com.memoit.ddimovski.memoit.domain.note.Note
import com.memoit.ddimovski.memoit.domain.note.NoteDataSource
import com.memoit.ddimovski.memoit.domain.time.DateTimeUtil
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import database.Note_entity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapNotNull

class SqlDelightNoteDataSource(db: Task) : NoteDataSource {

    private val noteQueries = db.noteQueries
    private val noteCategoryQueries = db.note_categoryQueries
    override fun getNotes(): Flow<List<Note>> {
        return noteQueries
            .getAllNotes { id,
                           title,
                           content,
                           created,
                           dueDate,
                           notifications,
                           isCompleted ->
                val categories = noteCategoryQueries.getCategoriesForNote(id).executeAsList()
                    .map { category -> Category(category.id, category.name) }
                Note_entity(
                    id,
                    title,
                    content,
                    created,
                    dueDate,
                    notifications,
                    isCompleted
                ).toNote(categories)
            }.asFlow().mapToList()
    }

    override suspend fun upsertNote(note: Note): Long {
        noteQueries.insertNote(
            id = note.id,
            title = note.title,
            content = note.description,
            created = DateTimeUtil.toEpochMillis(note.created),
            dueDate = if (note.dueDate != null) DateTimeUtil.toEpochMillis(note.dueDate) else null,
            notifications = note.notifications,
            isCompleted = note.isCompleted
        )
        note.categories.forEach {
            noteCategoryQueries.insertNoteCategory(note.id, it.id)
        }
        return noteQueries.lastInsertRowId().executeAsOne()
    }

    override suspend fun deleteNote(id: Long) {
        noteQueries.deleteNoteById(id)
    }

    override fun getNoteById(existingNoteId: Long): Note? {
        val note = noteQueries.getNoteById(existingNoteId).executeAsOneOrNull()
        return note?.let {
            val categories = noteCategoryQueries.getCategoriesForNote(note.id).executeAsList()
                .map { category -> Category(category.id, category.name) }
            it.toNote(categories)
        }
    }
}
