package com.memoit.ddimovski.memoit.data.local

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
//    actual fun createDriver(): SqlDriver {
//        return AndroidSqliteDriver(AppDatabase.Schema, context, "Task.db") //TODO uncomment when schema is created
//    }
}
