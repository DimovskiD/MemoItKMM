package com.memoit.ddimovski.memoit.android.presentation.screen.note_detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.memoit.ddimovski.memoit.android.presentation.components.ActionIcon
import com.memoit.ddimovski.memoit.android.presentation.components.MainFAB

@Composable
fun NoteDetailScreen(
    navController: NavController,
    viewModel: NoteDetailViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    val hasNoteBeenSaved by viewModel.hasNoteBeenSaved.collectAsState()

    LaunchedEffect(key1 = hasNoteBeenSaved) {
        if (hasNoteBeenSaved) {
            navController.popBackStack()
        }
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            Icons.Outlined.Edit,
                            contentDescription = "Edit task",
                        )
                    }
                    IconButton(onClick = {
                        viewModel.deleteNote()
                        navController.popBackStack()
                    }) {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = "Delete task",
                        )
                    }
                },
                floatingActionButton = {
                    MainFAB(Icons.Rounded.Check, "Save task") {
                        viewModel.saveNote()
                    }
                }
            )
        },
    ) { contentPadding ->

        val max = 60
        val min = 25
        val scrollState = rememberScrollState()

        var headerSize by remember { mutableStateOf(max) }
        headerSize = (max - scrollState.value / 5).coerceIn(min, max)

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier
                .fillMaxSize()
                .padding(paddingValues = contentPadding)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ActionIcon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Go back",
                    onClick = {
                        navController.popBackStack()
                    }
                )
                ActionIcon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Add time and date",
                    onClick = {
                        //TODO
                    }
                )
            }
            Text(
                state.noteTitle,
                style = TextStyle(
                    fontSize = headerSize.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {
                LazyRow(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(state.noteCategories.size) {
                        SuggestionChip(
                            icon = {
                                if (state.noteCategories.size > 1) {
                                    Icon(
                                        Icons.Default.Close,
                                        "Remove from category",
                                        modifier = Modifier
                                            .clip(
                                                RoundedCornerShape(4.dp)
                                            )
                                            .clickable {
                                                viewModel.removeFromCategory(state.noteCategories[it])
                                            })
                                }
                            },
                            label = { Text(state.noteCategories[it].name) },
                            enabled = false,
                            onClick = {
                            })
                    }
                    item {
                        SuggestionChip(
                            icon = { Icon(Icons.Default.Add, "Add to category") },
                            shape = CircleShape,
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            ),
                            onClick = {
                                //TODO
                            }, label = { Text("ADD") })
                    }
                }
                Column(modifier = Modifier.padding(16.dp)) {
                    TextField(
                        value = state.noteDescription,
                        onValueChange = { viewModel.onDescriptionChanged(it) },
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }


    }
}
