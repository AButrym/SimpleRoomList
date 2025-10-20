package softserve.academy.myapplication.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import softserve.academy.myapplication.UserField
import softserve.academy.myapplication.UserState
import softserve.academy.myapplication.UserViewModel

@Composable
fun UserForm(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = viewModel()
) {
    val uiState by userViewModel.uiState.collectAsState()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .then(modifier)
    ) {
        if (isLandscape) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    UserInfoFields(uiState, userViewModel)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    HomeAddressFields(uiState, userViewModel)
                }
            }
        } else {
            UserInfoFields(uiState, userViewModel)
            Spacer(modifier = Modifier.height(16.dp))
            HomeAddressFields(uiState, userViewModel)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { userViewModel.saveUser() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save User")
        }
    }
}

@Composable
fun UserInfoFields(uiState: UserState, userViewModel: UserViewModel) {
    Text("User Information", style = MaterialTheme.typography.headlineSmall)
    Spacer(modifier = Modifier.height(16.dp))

    ValidatedOutlinedTextField(
        value = uiState.firstName,
        onValueChange = { userViewModel.onFieldChange(it, UserField.FIRST_NAME) },
        label = "First Name",
        errorMessage = uiState.firstNameError
    )
    ValidatedOutlinedTextField(
        value = uiState.lastName,
        onValueChange = { userViewModel.onFieldChange(it, UserField.LAST_NAME) },
        label = "Last Name",
        errorMessage = uiState.lastNameError
    )
    ValidatedOutlinedTextField(
        value = uiState.phoneNumber,
        onValueChange = { userViewModel.onFieldChange(it, UserField.PHONE_NUMBER) },
        label = "PhoneNumber",
        errorMessage = uiState.phoneNumberError
    )
    // TODO: add rest of the fields
}

@Composable
fun HomeAddressFields(uiState: UserState, userViewModel: UserViewModel) {
    Text("Home Address", style = MaterialTheme.typography.headlineSmall)
    Spacer(modifier = Modifier.height(16.dp))
    ValidatedOutlinedTextField(
        value = uiState.phoneNumber,
        onValueChange = { userViewModel.onFieldChange(it, UserField.CITY) },
        label = "City",
        errorMessage = null
    )
    // TODO: rest of the Address fields
    // ... OutlinedTextFields for all address fields
}

@Composable
fun ValidatedOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    errorMessage: String?,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = errorMessage != null,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions
    )
    errorMessage?.let {
        Text(
            text = it,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}