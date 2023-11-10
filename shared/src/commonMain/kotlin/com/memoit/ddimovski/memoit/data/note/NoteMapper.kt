package com.memoit.ddimovski.memoit.data.note

import com.memoit.ddimovski.memoit.domain.category.Category
import com.memoit.ddimovski.memoit.domain.note.Note
import database.Note_entity
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Note_entity.toNote(categories: List<Category>): Note {
    return Note(
        id = id,
        title = title,
        description = content,
        created = Instant
            .fromEpochMilliseconds(created)
            .toLocalDateTime(TimeZone.currentSystemDefault()),
        categories = categories,
        dueDate = if (dueDate != null) Instant
            .fromEpochMilliseconds(dueDate)
            .toLocalDateTime(TimeZone.currentSystemDefault()) else null,
        notifications = notifications,
        isCompleted = isCompleted
    )
}
