package com.memoit.ddimovski.memoit.android.presentation.screen.note_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.memoit.ddimovski.memoit.android.presentation.components.MainFAB
import com.memoit.ddimovski.memoit.android.presentation.components.NoteListItem
import com.memoit.ddimovski.memoit.android.presentation.components.SearchTextField


@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun NoteListScreen(
    navController: NavController,
    viewModel: NoteListViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    if (state.isMultiSelectModeEnabled) {
                        if (state.isEditEnabled) {
                            IconButton(onClick = { /* do something */ }) {
                                Icon(
                                    Icons.Outlined.Edit,
                                    contentDescription = "Edit selected task",
                                )
                            }
                        }
                        IconButton(onClick = {
                            viewModel.onEvent(NoteListEvent.DeleteNotes(state.longPressedNotes))
                        }) {
                            Icon(
                                Icons.Outlined.Delete,
                                contentDescription = "Delete selected",
                            )
                        }
                    }
                },
                floatingActionButton = {
                    MainFAB(
                        Icons.Rounded.Add,
                        "Add task"
                    ) {
                        navController.navigate("note_detail/-1L")
                    }

                }
            )
        },
    ) { padding ->
        val haptics = LocalHapticFeedback.current
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 0.dp),
            ) {
                if (!state.isSearchActive) {
                    Text(
                        text = "My Memos",
                        style = TextStyle(fontSize = 50.sp, fontWeight = FontWeight.SemiBold),
                    )
                }

                SearchTextField(
                    text = state.searchText,
                    isSearchActive = state.isSearchActive,
                    onTextChange = { viewModel.onEvent(NoteListEvent.OnSearchTextChange(it)) },
                    onSearchClick = { viewModel.onEvent(NoteListEvent.OnToggleSearch) },
                    onCloseClick = { viewModel.onEvent(NoteListEvent.OnToggleSearch) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }

            LazyRow(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                item {
                    FilterChip(
                        label = {
                            Text("ALL (${state.totalNotesCount})")
                        },
                        selected = state.selectedFilter == null,
                        onClick = {
                            viewModel.onEvent(NoteListEvent.SelectFilter(null))
                        })
                }
                items(state.categories.size) {
                    FilterChip(label = { Text(state.categories[it].name) }, onClick = {
                        viewModel.onEvent(NoteListEvent.SelectFilter(state.categories[it]))

                    }, selected = state.selectedFilter == state.categories[it])
                }
                item {
                    SuggestionChip(
                        icon = { Icon(Icons.Default.Add, "Add to category") },
                        shape = CircleShape,
                        colors = SuggestionChipDefaults.suggestionChipColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        onClick = {
                            //TODO
                        }, label = { Text("ADD") })
                }
            }
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                contentPadding = padding,
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(state.notes.size) { index ->
                    val note = state.notes[index]
                    val isLongPressed = state.longPressedNotes.contains(note)
                    NoteListItem(
                        note = state.notes[index],
                        isLongPressed = isLongPressed,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = if (index % 2 == 0) 16.dp else 0.dp,
                                end = if (index % 2 == 1) 16.dp else 0.dp,
                                bottom = if (index >= state.notes.size - 1) 16.dp else 0.dp
                            )
                            .combinedClickable(
                                onClick = {
                                    if (isLongPressed || state.isMultiSelectModeEnabled) {
                                        viewModel.onEvent(NoteListEvent.OnNoteLongClicked(note))
                                    } else {
                                        navController.navigate("note_detail/${note.id}")
                                    }
                                }, onLongClick = {
                                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                    viewModel.onEvent(NoteListEvent.OnNoteLongClicked(note))
                                }, onLongClickLabel = "Edit"
                            )
                            .animateItemPlacement(),
                        onCompleteChanged = { checked ->
                            viewModel.onEvent(
                                NoteListEvent.OnCompleteChanged(
                                    note,
                                    checked
                                )
                            )
                        })
                }
            }
        }
    }
}
