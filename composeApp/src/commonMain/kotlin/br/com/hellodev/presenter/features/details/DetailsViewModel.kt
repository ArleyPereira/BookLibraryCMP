package br.com.hellodev.presenter.features.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.hellodev.core.navigation.route.BookRoutes
import br.com.hellodev.data.database.BookDatabase
import br.com.hellodev.domain.Book
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val database: BookDatabase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    var selectedBook: MutableState<Book?> = mutableStateOf(null)
        private set
    var isFavorite = mutableStateOf(false)
        private set
    private val selectedBookId = savedStateHandle.toRoute<BookRoutes.Details>().id

    init {
        viewModelScope.launch {
            database.bookDao()
                .getBookByIdFlow(selectedBookId)
                .collectLatest {
                    selectedBook.value = it
                    isFavorite.value = it?.isFavorite ?: false
                }
        }
    }

    fun setFavoriteBook() {
        viewModelScope.launch {
            if(selectedBook.value?.id != null) {
                database.bookDao()
                    .setFavoriteBook(
                        bookId = selectedBook.value!!.id,
                        isFavorite = !isFavorite.value
                    )
            }
        }
    }

    fun deleteBook() {
        viewModelScope.launch {
            database.bookDao()
                .deleteBookById(selectedBookId)
        }
    }
}