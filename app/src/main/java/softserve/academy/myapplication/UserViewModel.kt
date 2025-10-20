package softserve.academy.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import softserve.academy.myapplication.UserField.*
import softserve.academy.myapplication.room.*
import java.util.regex.Pattern


data class UserState(
    val firstName: String = "",
    val lastName: String = "",
    val emailAddress: String = "",
    val phoneNumber: String = "",
    val country: String = "",
    val city: String = "",
    val street: String = "",
    val building: String = "",
    val apartment: String = "",
    val zipCode: String = "",
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val emailAddressError: String? = null,
    val phoneNumberError: String? = null
)

enum class UserField {
    FIRST_NAME, LAST_NAME, EMAIL_ADDRESS, PHONE_NUMBER, COUNTRY, CITY, STREET, BUILDING, APARTMENT, ZIP_CODE
}


class UserViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        val PHONE_NUMBER_PATTERN = "\\+38\\(\\d{3}\\)\\d{7}".toRegex()
    }

    private val _uiState = MutableStateFlow(UserState())
    val uiState: StateFlow<UserState> = _uiState.asStateFlow()

    private val userDao: UserDao

    init {
        val userDb = UserDatabase.getDatabase(application)
        userDao = userDb.userDao()
    }

    fun onFieldChange(value: String, field: UserField) {
        when (field) {
            FIRST_NAME -> _uiState.update { it.copy(firstName = value) }
            LAST_NAME -> _uiState.update { it.copy(lastName = value) }
            EMAIL_ADDRESS -> _uiState.update { it.copy(emailAddress = value) }
            else -> _uiState.update { it.copy(emailAddress = value) }
            // TODO: add dispatching for all other fields
        }
    }

    fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()

    fun updateUser(user: User) = viewModelScope.launch {
        userDao.updateUser(user)
    }

    fun deleteUser(user: User) = viewModelScope.launch {
        userDao.deleteUser(user)
    }

    fun saveUser() {
        if (validate()) {
            viewModelScope.launch {
                val user = User(
                    firstName = _uiState.value.firstName,
                    lastName = _uiState.value.lastName,
                    emailAddress = _uiState.value.emailAddress,
                    phoneNumber = _uiState.value.phoneNumber,
                    homeAddress = HomeAddress(
                        country = _uiState.value.country,
                        city = _uiState.value.city,
                        street = _uiState.value.street,
                        building = _uiState.value.building,
                        apartment = _uiState.value.apartment,
                        zipCode = _uiState.value.zipCode
                    )
                )
                userDao.insertUser(user)
            }
        }
    }

    private fun validate(): Boolean {
        var isValid = true
        _uiState.update { it.copy(
            firstNameError = if (it.firstName.isBlank()) {
                isValid = false
                "First name cannot be empty."
            } else null,
            lastNameError = if (it.lastName.isBlank()) {
                isValid = false
                "Last name cannot be empty."
            } else null,
            emailAddressError = if (!android.util.Patterns.EMAIL_ADDRESS.matcher(it.emailAddress).matches()) {
                isValid = false
                "Invalid email address."
            } else null,
            phoneNumberError = if (PHONE_NUMBER_PATTERN.matches(it.phoneNumber)) {
                isValid = false
                "Phone number should be in format +38(066)111-11-11"
            } else null
        ) }
        return isValid
    }
}