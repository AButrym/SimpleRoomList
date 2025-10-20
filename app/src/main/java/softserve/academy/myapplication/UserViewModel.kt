package softserve.academy.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import softserve.academy.myapplication.room.*

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao: UserDao

    init {
        val userDb = UserDatabase.getDatabase(application)
        userDao = userDb.userDao()
    }

    fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()

    fun insertUser(user: User) = viewModelScope.launch {
        userDao.insertUser(user)
    }

    fun updateUser(user: User) = viewModelScope.launch {
        userDao.updateUser(user)
    }

    fun deleteUser(user: User) = viewModelScope.launch {
        userDao.deleteUser(user)
    }
}