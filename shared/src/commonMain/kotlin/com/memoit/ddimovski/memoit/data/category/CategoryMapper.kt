package com.memoit.ddimovski.memoit.data.category

import com.memoit.ddimovski.memoit.domain.category.Category
import database.Category_entity

fun Category_entity.toCategory() : Category{
    return Category(
        id = id,
        name = name
    )
}
