package softserve.academy.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.flow.SharingStarted
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

    val items = dao.getAll()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addItem(name: String) {
        viewModelScope.launch { dao.insert(Item(name = name)) }
    }
}