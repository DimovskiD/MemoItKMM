package com.memoit.ddimovski.memoit.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.memoit.ddimovski.memoit.android.presentation.screen.note_detail.NoteDetailScreen
import com.memoit.ddimovski.memoit.android.presentation.screen.note_list.NoteListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "note_list") {
                    composable(route = "note_list") {
                        NoteListScreen(
                            navController = navController,
                        )
                    }
                    composable(
                        route = "note_detail/{noteId}?{isInEditMode}",
                        arguments = listOf(
                            navArgument(name = "noteId") {
                                type = NavType.LongType
                                defaultValue = -1L
                            },
                            navArgument(name = "isInEditMode") {
                                type = NavType.BoolType
                                defaultValue = false
                            }
                        )
                    ) { backStackEntry ->
                        NoteDetailScreen(navController = navController)
                    }
                }
            }
        }
    }
}
