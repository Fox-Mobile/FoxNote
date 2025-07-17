package com.foxmobile.foxnote.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.foxmobile.foxnote.composables.NoteScreen
import com.foxmobile.foxnote.composables.OverviewScreen
import com.foxmobile.foxnote.database.Note
import com.foxmobile.foxnote.database.NoteViewModel
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.getViewModel

@Serializable
data object OverviewScreenKey: NavKey

@Serializable
data class NoteScreenKey(@Contextual val note: Note?): NavKey

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(OverviewScreenKey)
    val noteViewModel = getViewModel<NoteViewModel>()

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
            rememberSceneSetupNavEntryDecorator()
        ),
        entryProvider = { key  ->
            when(key) {
                OverviewScreenKey -> NavEntry(
                    key = key,
                ) {
                    OverviewScreen(
                        onNoteClick = { note ->
                            backStack.add(NoteScreenKey(note))
                        },
                        onAddButtonClick = {
                            backStack.add(NoteScreenKey(null))
                        },
                        noteViewModel = noteViewModel
                    )
                }

                is NoteScreenKey -> NavEntry(
                    key = key,
                ) {
                    NoteScreen(noteParam = key.note, noteViewModel = noteViewModel)
                }

                else -> throw IllegalArgumentException("Unknown key: $key")
            }
        }
    )
}