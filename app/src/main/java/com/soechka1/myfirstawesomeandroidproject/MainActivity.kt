package com.soechka1.myfirstawesomeandroidproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.soechka1.myfirstawesomeandroidproject.model.NoteDataModel
import com.soechka1.myfirstawesomeandroidproject.navScreens.addnotepage.AddNoteScreen

import com.soechka1.myfirstawesomeandroidproject.navScreens.notespage.NotesScreen
import com.soechka1.myfirstawesomeandroidproject.navScreens.loginpage.LoginScreen
import com.soechka1.myfirstawesomeandroidproject.navigation.AddNotePageDataObject
import com.soechka1.myfirstawesomeandroidproject.navigation.LoginPageObject
import com.soechka1.myfirstawesomeandroidproject.navigation.NotesPageDataObject
import com.soechka1.myfirstawesomeandroidproject.ui.AppTheme
import com.soechka1.myfirstawesomeandroidproject.ui.theme.MyFirstAwesomeAndroidProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val currentTheme = remember { mutableStateOf(AppTheme.PURPLE) }

            MyFirstAwesomeAndroidProjectTheme(theme = currentTheme.value) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()
                    val notes = remember { mutableStateListOf<NoteDataModel>() }


                    NavHost(
                        navController = navController,
                        startDestination = LoginPageObject
                    ) {
                        composable<LoginPageObject> {
                            LoginScreen(navController = navController)
                        }

                        composable<NotesPageDataObject> { entry ->
                            val args = entry.toRoute<NotesPageDataObject>()
                            NotesScreen(
                                navController = navController,
                                onThemeChange = { idx -> currentTheme.value = AppTheme.entries[idx] },
                                // drag pass via a link is not safe and is unnecessary, and we create notes at the top level for lifetime the application cycle
                                email = args.email,
                                notes = notes
                            )
                        }

                        composable<AddNotePageDataObject> {
                            AddNoteScreen(
                                navController = navController,
                                onNoteSave = { note -> notes.add(note) })
                        }
                    }

                }
            }
        }
    }
}
