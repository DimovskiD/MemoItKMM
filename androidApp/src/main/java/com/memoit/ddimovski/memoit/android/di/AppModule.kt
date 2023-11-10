package com.memoit.ddimovski.memoit.android.di

import android.app.Application
import com.memoit.ddimovski.memoit.data.category.SqlDelightCategoryDataSource
import com.memoit.ddimovski.memoit.data.local.DatabaseDriverFactory
import com.memoit.ddimovski.memoit.data.note.SqlDelightNoteDataSource
import com.memoit.ddimovski.memoit.database.Task
import com.memoit.ddimovski.memoit.domain.category.CategoryDataSource
import com.memoit.ddimovski.memoit.domain.note.NoteDataSource
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).createDriver()
    }

    @Provides
    @Singleton
    fun provideNoteDataSource(driver: SqlDriver): NoteDataSource {
        return SqlDelightNoteDataSource(Task(driver))
    }

    @Provides
    @Singleton
    fun provideCategoryDataSource(driver: SqlDriver): CategoryDataSource {
        return SqlDelightCategoryDataSource(Task(driver))
    }
}
