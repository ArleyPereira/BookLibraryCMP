package br.com.hellodev.presenter.features.manage

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.hellodev.core.navigation.route.BookRoutes
import br.com.hellodev.data.database.BookDatabase
import br.com.hellodev.domain.Book
import kotlinx.coroutines.launch

const val IMAGE_URL = "https://r2.erweima.ai/imgcompressed/compressed_c29235854ffdfec1257c539ecfc35783.webp"

class ManageViewModel(
    private val database: BookDatabase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val selectedBookId = savedStateHandle.toRoute<BookRoutes.Manage>().id ?: -1
    var imageField = mutableStateOf(IMAGE_URL)
    var titleField = mutableStateOf("")
    var summaryField = mutableStateOf("")
    var categoryField = mutableStateOf("")
    var tagsField = mutableStateOf("")
    var authorField = mutableStateOf("")

    init {
        viewModelScope.launch {
            if (selectedBookId != -1) {
                val selectedBook = database.bookDao()
                    .getBookById(selectedBookId)
                imageField.value = selectedBook.image
                titleField.value = selectedBook.title
                summaryField.value = selectedBook.summary
                categoryField.value = selectedBook.category
                tagsField.value = selectedBook.tags.joinToString()
                authorField.value = selectedBook.author
            }
        }
    }

    fun insertBook(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                if (
                    titleField.value.isNotEmpty() &&
                    summaryField.value.isNotEmpty() &&
                    categoryField.value.isNotEmpty() &&
                    tagsField.value.isNotEmpty() &&
                    authorField.value.isNotEmpty()
                ) {
                    database.bookDao()
                        .insertBook(
                            book = Book(
                                image = imageField.value,
                                title = titleField.value,
                                summary = summaryField.value,
                                category = categoryField.value,
                                tags = tagsField.value.split(","),
                                author = authorField.value,
                                isFavorite = false
                            ),
                        )
                    onSuccess()
                } else {
                    onError("Fields cannot be empty.")
                }
            } catch (e: Exception) {
                onError(e.toString())
            }
        }
    }

    fun updateBook(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                if (titleField.value.isNotEmpty() &&
                    summaryField.value.isNotEmpty() &&
                    categoryField.value.isNotEmpty() &&
                    tagsField.value.isNotEmpty() &&
                    authorField.value.isNotEmpty()
                ) {
                    database.bookDao()
                        .updateBook(
                            book = Book(
                                id = selectedBookId,
                                image = imageField.value,
                                title = titleField.value,
                                summary = summaryField.value,
                                category = categoryField.value,
                                tags = tagsField.value.split(","),
                                author = authorField.value,
                                isFavorite = database.bookDao()
                                    .getBookById(selectedBookId).isFavorite
                            )
                        )
                    onSuccess()
                } else {
                    onError("Fields cannot be empty.")
                }
            } catch (e: Exception) {
                onError(e.toString())
            }
        }
    }
}