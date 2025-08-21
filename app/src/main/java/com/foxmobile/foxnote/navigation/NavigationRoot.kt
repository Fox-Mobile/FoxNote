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
import com.foxmobile.foxnote.database.note.Note
import com.foxmobile.foxnote.database.note.NoteEvent
import com.foxmobile.foxnote.database.note.NoteViewModel
import com.foxmobile.foxnote.database.tag.TagViewModel
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.getViewModel

@Serializable
data object OverviewScreenKey: NavKey

@Serializable
data object NoteScreenKey: NavKey


@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    noteFromWidget: Note? = null,
) {
    val backStack = rememberNavBackStack(OverviewScreenKey)
    val noteViewModel = getViewModel<NoteViewModel>()
    val tagViewModel = getViewModel<TagViewModel>()


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
                            noteViewModel.onEvent(NoteEvent.SetTitle(note.title))
                            noteViewModel.onEvent(NoteEvent.SetContent(note.content))
                            noteViewModel.onEvent(NoteEvent.SetID(note.id))
                            noteViewModel.onEvent(NoteEvent.SetTagID(note.tagId))
                            noteViewModel.onEvent(NoteEvent.SetIsPinned(note.isPinned))

                            backStack.add(NoteScreenKey)
                        },
                        onAddButtonClick = {
                            backStack.add(NoteScreenKey)
                        },
                        noteViewModel = noteViewModel
                    )
                }

                is NoteScreenKey -> NavEntry(
                    key = key,
                ) {
                    NoteScreen (noteViewModel = noteViewModel, tagViewModel = tagViewModel)
                }

                else -> throw IllegalArgumentException("Unknown key: $key")
            }
        }
    )
}