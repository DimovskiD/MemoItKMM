package com.memoit.ddimovski.memoit.data.local

import com.memoit.ddimovski.memoit.database.Task
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(Task.Schema, "Task.db")
    }
}
