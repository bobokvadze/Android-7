package ge.bobokvadze.usersapp

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val saveStateAndRead: SaveStateAndRead
) : ViewModel() {

    fun read() = saveStateAndRead.read()
}