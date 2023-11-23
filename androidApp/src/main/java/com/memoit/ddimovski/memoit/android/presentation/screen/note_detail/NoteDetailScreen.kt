package com.memoit.ddimovski.memoit.android.presentation.screen.note_detail

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.memoit.ddimovski.memoit.android.R
import com.memoit.ddimovski.memoit.android.presentation.components.ActionIcon
import com.memoit.ddimovski.memoit.android.presentation.components.MainFAB
import com.memoit.ddimovski.memoit.android.presentation.components.StyledTextField
import com.memoit.ddimovski.memoit.domain.category.Category

@Composable
fun NoteDetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: NoteDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val errors by viewModel.errors.collectAsState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    AnimatedVisibility(visible = !state.isInEditMode) {
                        Row {
                            IconButton(onClick = {
                                viewModel.onEditClicked()
                            }) {
                                Icon(
                                    Icons.Outlined.Edit,
                                    contentDescription = "Edit task",
                                )
                            }
                            IconButton(onClick = {
                                viewModel.onDeleteClicked(true)
                            }) {
                                Icon(
                                    Icons.Outlined.Delete,
                                    contentDescription = "Delete task",
                                )
                            }
                        }
                    }

                    AnimatedVisibility(visible = state.isInEditMode) {
                        Row {
                            IconButton(onClick = {
                                focusManager.clearFocus()
                                if (viewModel.onCancelEditMode()) navController.popBackStack()
                            }) {
                                Icon(
                                    Icons.Outlined.Close,
                                    contentDescription = "Stop editing",
                                )
                            }
                        }
                    }
                },
                floatingActionButton = {
                    if (state.isInEditMode) {
                        MainFAB(ImageVector.vectorResource(R.drawable.ic_save), "Save task") {
                            viewModel.saveNote()
                        }
                    } else {
                        if (state.isCompleted) {
                            MainFAB(Icons.Filled.CheckCircle, "Reopen task") {
                                viewModel.reopenTask()
                            }
                        } else {
                            MainFAB(Icons.Outlined.CheckCircle, "Complete task") {
                                viewModel.completeTask()
                            }
                        }
                    }
                }
            )
        },
    ) { contentPadding ->
        Crossfade(
            targetState = state.isInEditMode,
            label = "crossfade",
            animationSpec = tween(500)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                Header(
                    onBackClicked = { navController.popBackStack() }
                )

                if (it) {
                    EditMode(
                        state = state,
                        onTitleChanged = viewModel::onNoteTitleChanged,
                        onDescriptionChanged = viewModel::onDescriptionChanged,
                        titleError = errors.titleError,
                        descError = errors.descriptionError
                    )
                } else {
                    ViewMode(
                        state = state,
                        onRemoveFromCategory = viewModel::removeFromCategory
                    )
                }
            }
            if (state.isInEditMode) {
                BackHandler {
                    focusManager.clearFocus()
                }
            }
            if (state.isDeleteDialogShown) {
                DeleteDialog(
                    onDismiss = { viewModel.onDeleteClicked(false) },
                    onDeleteClicked = {
                        viewModel.onDeleteClicked(false)
                        viewModel.deleteNote()
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@Composable
fun DeleteDialog(onDismiss: () -> Unit, onDeleteClicked: () -> Unit) {
    AlertDialog(
        icon = {
            Icon(Icons.Default.Warning, contentDescription = null)
        },
        title = {
            Text(text = "Delete task")
        },
        text = {
            Text(text = "Are you sure you want to delete this task?")
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDeleteClicked()
                }
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Cancel")
            }
        }
    )

}

@Composable
fun ColumnScope.ViewMode(
    state: NoteDetailState,
    onRemoveFromCategory: (category: Category) -> Unit
) {

    val max = 60
    val min = 25
    val scrollState = rememberScrollState()

    var headerSize by remember { mutableStateOf(max) }
    headerSize = (max - scrollState.value / 5).coerceIn(min, max)
    Text(
        state.noteTitle,
        style = TextStyle(
            fontSize = headerSize.sp,
            fontWeight = FontWeight.SemiBold
        ),
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    NoteCategories(state.noteCategories, onRemoveFromCategory)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Text(
            state.noteDescription,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}


@Composable
fun ColumnScope.EditMode(
    state: NoteDetailState,
    titleError: String?,
    descError: String?,
    onTitleChanged: (title: String) -> Unit = {},
    onDescriptionChanged: (description: String) -> Unit = {},
) {
    StyledTextField(
        value = state.noteTitle,
        onValueChange = { onTitleChanged(it) },
        textStyle = MaterialTheme.typography.titleLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        hint = "Title",
        error = titleError,
    )
    NoteCategories(state.noteCategories, {})
    StyledTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .weight(1f),
        value = state.noteDescription,
        onValueChange = { onDescriptionChanged(it) },
        textStyle = MaterialTheme.typography.bodyMedium,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        hint = "Description",
        error = descError,
    )
    Text(
        text = descError ?: "", style = MaterialTheme.typography.bodySmall,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .padding(start = 16.dp, bottom = 16.dp), color = MaterialTheme.colorScheme.error
    )
}

@Composable
fun ColumnScope.Header(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit = {},
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
                onBackClicked()
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
}

@Composable
fun NoteCategories(
    noteCategories: List<Category>,
    onRemoveFromCategory: (category: Category) -> Unit,
) {
    LazyRow(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(noteCategories.size) {
            SuggestionChip(
                icon = {
                    if (noteCategories.size > 1) {
                        Icon(
                            Icons.Default.Close,
                            "Remove from category",
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(4.dp)
                                )
                                .clickable {
                                    onRemoveFromCategory(noteCategories[it])
                                })
                    }
                },
                label = { Text(noteCategories[it].name) },
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
}
