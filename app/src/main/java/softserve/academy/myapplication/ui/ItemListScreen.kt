package softserve.academy.myapplication.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import softserve.academy.myapplication.ItemViewModel

@Composable
fun ItemListScreen(
    viewModel: ItemViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val items by viewModel.items.collectAsState()
    var text by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp).then(modifier)) {
        Row {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("New item") },
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                if (text.isNotBlank()) {
                    viewModel.addItem(text)
                    text = ""
                }
            }) {
                Text("Add")
            }
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(items) { item ->
                Text("• ${item.name}", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}