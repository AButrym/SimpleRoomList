package softserve.academy.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import softserve.academy.myapplication.room.AppDatabase
import softserve.academy.myapplication.room.Item

class ItemViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "items.db"
    ).build()

    private val dao = db.itemDao()

    val coldFlow = dao.getAll()
        .onStart { println("Cold Flow: started collecting from DB") }
        .onEach { println("Cold Flow: emitted ${it.size} items") }

    val hotFlow = dao.getAll()
        .onStart { println("Hot Flow (source): started collecting from DB") }
        .onEach { println("Hot Flow (source): emitted ${it.size} items") }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

//    val items = dao.getAll()
//        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addItem(name: String) {
        viewModelScope.launch(Dispatchers.IO)
        {
            println("Inserting '$name'")
            dao.insert(Item(name = name))
        }
    }

    fun clearAll() {
        viewModelScope.launch(Dispatchers.IO)
        {
            dao.deleteAll()
        }
    }
}