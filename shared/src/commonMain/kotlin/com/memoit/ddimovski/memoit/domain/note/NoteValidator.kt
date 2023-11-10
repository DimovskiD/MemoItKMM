package com.memoit.ddimovski.memoit.domain.note

object NoteValidator {

    fun validateNote(note: Note): ValidationResult {
        var result = ValidationResult()

        if(note.title.isBlank()) {
            result = result.copy(titleError = "The title can't be empty.")
        }

        if(note.description.length > 500) {
            result = result.copy(descriptionError = "Description cannot be longer than 500 characters.")
        }

        //todo validate date

        return result
    }

    data class ValidationResult(
        val titleError: String? = null,
        val descriptionError: String? = null,
        val dateError: String? = null,
    )
}

