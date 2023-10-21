package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.AppState
import data.Notes
import data.TypeNotes

@Composable
@Preview
fun App() {
    with(AppState.state.collectAsState()) {

        val newNotes: List<Notes>? = this.value.notes

        LaunchedEffect(true) {
            AppState.loadNotes(this)

        }
        MaterialTheme {
            Scaffold(topBar = { Toolbar() }) { padding ->
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center
                ) {
                    if (data.AppState.state.value.loading) CircularProgressIndicator()
                    newNotes?.let { notesList(it) } ?: emptyList<Notes>()
                }
            }
        }

    }
}


@Composable
private fun notesList(notes: List<Notes>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(notes) {
            Card(
                modifier = Modifier.padding(8.dp).fillMaxWidth(0.8f)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row {
                        Text(
                            text = it.title, style = MaterialTheme.typography.h6, modifier = Modifier.weight(1f)
                        )
                        if (it.type == TypeNotes.AUDIO_NOTE) {
                            Icon(
                                imageVector = Icons.Default.Mic, contentDescription = null
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )
                    Text(
                        text = it.description, style = MaterialTheme.typography.body1
                    )
                }
            }
        }

    }
}