package com.foxmobile.foxnote.widgets

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxHeight
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.wrapContentHeight
import androidx.glance.layout.wrapContentWidth
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import com.foxmobile.foxnote.R
import com.foxmobile.foxnote.database.note.Note
import com.foxmobile.foxnote.database.note.NoteDao
import com.foxmobile.foxnote.database.note.NoteViewModel
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import kotlin.math.log

class NotesWidget() : GlanceAppWidget(), KoinComponent {
    val noteViewModel: NoteViewModel by inject()
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val notes = try {
            noteViewModel.getNotesForWidget()
        } catch (e: Exception) {
            emptyList<Note>()
        }

        provideContent {
            GlanceTheme {
                WidgetContent(notes)
            }
        }
    }

    @Composable
    private fun WidgetContent(
        notes: List<Note>
    ) {
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(GlanceTheme.colors.tertiary)
        ) {
            Row(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    ImageProvider(R.mipmap.ic_launcher_foreground), contentDescription = null,
                    GlanceModifier.size(70.dp)
                )
                Text(
                    text = "FoxNote",
                    style = TextStyle(
                        color = GlanceTheme.colors.onTertiary,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Column(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalAlignment = Alignment.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (notes.isEmpty()) {
                    Box(
                        modifier = GlanceModifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "No notes to display.",
                            style = TextStyle(color = GlanceTheme.colors.onTertiary)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = GlanceModifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        items(
                            items = notes,
                            itemId = { note -> note.id?.toLong()!!}) { note ->
                            NoteCard(note)
                            Spacer(GlanceModifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun NoteCard(note: Note) {
        Box(
            modifier = GlanceModifier
                .fillMaxWidth()
                .background(GlanceTheme.colors.secondaryContainer)
                .cornerRadius(12.dp)
                .clickable {
                    Log.d("NotesWidget", "Clicked on note: ${note.title}")
                }
        ) {
            Column {
                Text(
                    note.title,
                    style = TextStyle(
                        color = GlanceTheme.colors.primary,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )

                Text(
                    note.content,
                    style = TextStyle(
                        color = GlanceTheme.colors.onSecondaryContainer,
                        fontSize = 22.sp
                    ),
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
            }
        }
    }
}