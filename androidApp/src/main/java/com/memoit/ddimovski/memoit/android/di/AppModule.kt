package com.memoit.ddimovski.memoit.android.di

import android.app.Application
import com.memoit.ddimovski.memoit.data.local.TemporaryCategoryDataSource
import com.memoit.ddimovski.memoit.data.local.TemporaryNoteDataSource
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

//    @Provides
//    @Singleton
//    fun provideSqlDriver(app: Application): SqlDriver {
//        return DatabaseDriverFactory(app).createDriver()
//    }

    @Provides
    @Singleton
    fun provideNoteDataSource(): NoteDataSource {
//        return SqlDelightNoteDataSource(NoteDatabase(driver)) TODO
        return TemporaryNoteDataSource()
    }

    @Provides
    @Singleton
    fun provideCategoryDataSource(): CategoryDataSource {
//        return SqlDelightNoteDataSource(NoteDatabase(driver)) TODO
        return TemporaryCategoryDataSource()
    }
}
