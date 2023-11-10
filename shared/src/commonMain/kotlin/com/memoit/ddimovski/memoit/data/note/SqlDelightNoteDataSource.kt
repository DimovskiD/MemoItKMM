package com.memoit.ddimovski.memoit.data.note

import com.memoit.ddimovski.memoit.database.Task
import com.memoit.ddimovski.memoit.domain.category.Category
import com.memoit.ddimovski.memoit.domain.note.Note
import com.memoit.ddimovski.memoit.domain.note.NoteDataSource
import com.memoit.ddimovski.memoit.domain.time.DateTimeUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SqlDelightNoteDataSource(db: Task) : NoteDataSource {

    private val noteQueries = db.noteQueries
    private val noteCategoryQueries = db.note_categoryQueries
    override fun getNotes(): Flow<List<Note>> {
        val notes = noteQueries
            .getAllNotes().executeAsList().map {
                val categories = noteCategoryQueries.getCategoriesForNote(it.id).executeAsList()
                    .map { category -> Category(category.id, category.name) }
                it.toNote(categories)
            }
        return flowOf(notes)
    }

    override suspend fun insertNote(note: Note) {
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
    }

    override suspend fun deleteNote(id: Long) {
        noteQueries.deleteNoteById(id)
    }

    override suspend fun updateNote(note: Note) {
        insertNote(note)
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
