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
    modifier: Modifier = Modifier,
    viewModel: ItemViewModel = viewModel()
) {
//    val items by viewModel.items.collectAsState()
//    var text by remember { mutableStateOf("") }

    var showColdFlow by remember { mutableStateOf(false) }
    var showHotFlow by remember { mutableStateOf(false) }

    Column(Modifier
        .padding(16.dp)
        .then(modifier)) {
//        Row {
//            OutlinedTextField(
//                value = text,
//                onValueChange = { text = it },
//                label = { Text("New item") },
//                modifier = Modifier.weight(1f)
//            )
//            Spacer(Modifier.width(8.dp))
//            Button(onClick = {
//                if (text.isNotBlank()) {
//                    viewModel.addItem(text)
//                    text = ""
//                }
//            }) {
//                Text("Add")
//            }
//        }
//
//        Spacer(Modifier.height(16.dp))
//
//        LazyColumn {
//            items(items) { item ->
//                Text("- ${item.name}", style = MaterialTheme.typography.bodyLarge)
//            }
//        }

        Row {
            Button(onClick = { viewModel.addItem("Item #${(0..999).random()}") }) {
                Text("Add Random Item")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { viewModel.clearAll() }) {
                Text("Clear All")
            }
        }

        Spacer(Modifier.height(16.dp))

        Row {
            Button(onClick = { showColdFlow = !showColdFlow }) {
                Text(if (showColdFlow) "Stop Cold Flow" else "Start Cold Flow")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { showHotFlow = !showHotFlow }) {
                Text(if (showHotFlow) "Stop Hot Flow" else "Start Hot Flow")
            }
        }

        if (showColdFlow) {
            val coldItems by viewModel.coldFlow.collectAsState(initial = emptyList())
            Text("Cold flow count: ${coldItems.size}")
        }

        if (showHotFlow) {
            val hotItems by viewModel.hotFlow.collectAsState()
            Text("Hot flow count: ${hotItems.size}")
        }
    }
}