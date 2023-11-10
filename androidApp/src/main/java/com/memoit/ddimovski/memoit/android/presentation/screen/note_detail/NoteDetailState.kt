package com.memoit.ddimovski.memoit.android.presentation.screen.note_detail

import com.memoit.ddimovski.memoit.domain.category.Category

data class NoteDetailState(
    val noteTitle: String = "",
    val noteDescription: String = "",
    val noteCategories: List<Category> = emptyList(),
    val titleError: String? = null,
    val descError: String? = null,
    val dueDateError: String? = null,
)
