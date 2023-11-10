package com.memoit.ddimovski.memoit.data.category

import com.memoit.ddimovski.memoit.database.Task
import com.memoit.ddimovski.memoit.domain.category.Category
import com.memoit.ddimovski.memoit.domain.category.CategoryDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SqlDelightCategoryDataSource(db: Task): CategoryDataSource {

    private val categoryQueries = db.categoryQueries
    private val noteCategoryQueries = db.note_categoryQueries
    override fun getCategories(): Flow<List<Category>> {
        return flowOf(categoryQueries.getAllCategories().executeAsList().map { it.toCategory() })
    }

    override fun insertCategory(category: Category) {
        categoryQueries.insertCategory(category.id, category.name)
    }
    override fun getNotesCountForCategory(categoryId: Long): Int {
        return noteCategoryQueries.getNotesCountForCategory(categoryId).executeAsOneOrNull()?.toInt() ?: 0
    }

}
