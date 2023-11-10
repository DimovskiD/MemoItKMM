package com.memoit.ddimovski.memoit.data.local

import android.content.Context
import com.memoit.ddimovski.memoit.database.Task
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(Task.Schema, context, "Task.db")
    }
}
