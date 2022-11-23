package ge.bobokvadze.usersapp

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ge.bobokvadze.usersapp.model.Data
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class MainViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    private val communication: Communication,
    private val saveRead: SaveStateAndRead
) : ViewModel() {

    init {
        viewModelScope.launch {
            communication.map(usersRepository.users().apply())
        }
    }

    fun collect(
        collector: FlowCollector<UsersUi>
    ) = viewModelScope.launch {
        communication.collect(collector)
    }

    fun save(item: Data) = viewModelScope.launch {
        saveRead.save(item)
    }
}

interface Communication {

    fun map(data: UsersUi)
    suspend fun collect(collector: FlowCollector<UsersUi>)

    class Base : Communication {

        private val state = MutableStateFlow<UsersUi>(UsersUi.Empty)

        override fun map(data: UsersUi) {
            state.value = data
        }

        override suspend fun collect(collector: FlowCollector<UsersUi>) {
            state.collect(collector)
        }
    }
}

interface SaveStateAndRead {

    suspend fun save(data: Data)

    fun read(): Data

    @Singleton
    class Base : SaveStateAndRead {

        private val state = mutableListOf<Data>()

        override fun read(): Data = state[0]

        override suspend fun save(data: Data) {
            state.clear()
            state.add(data)
        }
    }
}

