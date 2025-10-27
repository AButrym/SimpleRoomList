package softserve.academy.myapplication.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PhoneNumberField(
    modifier: Modifier = Modifier,
    onNumberComplete: (String) -> Unit = {}
) {
    var rawNumber by remember { mutableStateOf("") }
    val maxDigits = 10

    val formatted = remember(rawNumber) {
        buildString {
            append("+38 (0")
            append(rawNumber.take(2).padEnd(2, '_'))
            append(") ")
            append(rawNumber.drop(2).take(3).padEnd(3, '_'))
            append('-')
            append(rawNumber.drop(5).take(2).padEnd(2, '_'))
            append('-')
            append(rawNumber.drop(7).take(2).padEnd(2, '_'))
        }
    }

    val isComplete = rawNumber.length == maxDigits

    OutlinedTextField(
        value = formatted,
        onValueChange = { newValue ->
            val digits = newValue.filter { it.isDigit() }

            if (digits.length <= maxDigits) {
                rawNumber = digits
                if (digits.length == maxDigits) {
                    onNumberComplete(digits)
                }
            }
        },
        label = { Text("Phone number") },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        textStyle = LocalTextStyle.current.copy(
            letterSpacing = 1.5.sp
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isComplete) Color(0xFF4CAF50) else Color.Red,
            unfocusedBorderColor = if (isComplete) Color(0xFF4CAF50) else Color.Red,
        ),
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun PhoneFormDemo() {
    var result by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        PhoneNumberField(
            onNumberComplete = { phone ->
                result = phone
            }
        )
        if (result.isNotBlank()) {
            Text(result)
        }
    }
}