package com.memoit.ddimovski.memoit.domain.category

import kotlinx.coroutines.flow.Flow

interface CategoryDataSource {
    fun getCategories(): Flow<List<Category>>
}
