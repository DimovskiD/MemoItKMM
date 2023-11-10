package com.memoit.ddimovski.memoit.data.local

import com.memoit.ddimovski.memoit.domain.category.Category
import com.memoit.ddimovski.memoit.domain.note.Note
import com.memoit.ddimovski.memoit.domain.note.NoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TemporaryNoteDataSource : NoteDataSource {

    private val categoryDataSource = TemporaryCategoryDataSource()  //TODO remove

    private val notes: MutableList<Note> = (0..11).map {
        Note(
            it.toLong(), "Something very important $it",
            categories = categoryDataSource.getRandomCategories(it)
        )
    }.toMutableList()

    override fun getNotes(): Flow<List<Note>> = flowOf(notes)

    override suspend fun insertNote(note: Note) {
        notes.add(note)
    }

    override suspend fun deleteNote(id: Long) {
        notes.remove(notes.find { it.id == id })
    }

    override suspend fun updateNote(note: Note) {
        val existingNote = notes.find { it.id == note.id }
        val index = notes.indexOf(existingNote)
        notes[index] = note
    }

    override fun countNotesInCategory(category: Category): Int {
        return notes.count { it.categories.contains(category) }
    }

    override fun getNoteById(existingNoteId: Long): Note? {
        return notes.find { it.id == existingNoteId }
    }
}
