package com.memoit.ddimovski.memoit.data.local

import com.memoit.ddimovski.memoit.domain.category.Category
import com.memoit.ddimovski.memoit.domain.category.CategoryDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class TemporaryCategoryDataSource : CategoryDataSource {

    private val categories = listOf(Category("1", "DEFAULT"), Category("2", "IMPORTANT"), Category("3", "WORK"), Category("4", "SCHOOL"))
    override fun getCategories(): Flow<List<Category>> {
        return flowOf(categories)
    }

    fun getRandomCategories(numberOfCategories: Int): List<Category> {
        val list: MutableList<Category> = mutableListOf()
        repeat(numberOfCategories.coerceAtMost(categories.size)) {
            list.add(categories.get(it))
        }
        return list
    }
}

