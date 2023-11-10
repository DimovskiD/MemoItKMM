package com.memoit.ddimovski.memoit.domain

import com.memoit.ddimovski.memoit.domain.category.Category
import com.memoit.ddimovski.memoit.domain.note.Note
import com.memoit.ddimovski.memoit.domain.time.DateTimeUtil

class SearchNotes {

    fun execute(notes: List<Note>, query: String, filter: Category?): List<Note> {
        if (query.isBlank()) {
            return if (filter == null) notes else notes.filter {
                it.categories.contains(
                    filter
                )
            }
        }
        val filteredNotes = notes.filter {
            it.title.trim().lowercase().contains(query.lowercase()) ||
                    it.description.trim().lowercase().contains(query.lowercase())
        }.sortedBy {
            DateTimeUtil.toEpochMillis(it.created)
        }
        return if (filter == null) filteredNotes else filteredNotes.filter {
            it.categories.contains(
                filter
            )
        }
    }
}
