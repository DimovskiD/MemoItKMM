package com.memoit.ddimovski.memoit.domain.category

import kotlinx.coroutines.flow.Flow

interface CategoryDataSource {
    fun getCategories(): Flow<List<Category>>
    fun insertCategory(category: Category)
    fun getNotesCountForCategory(categoryId: Long): Int
}
