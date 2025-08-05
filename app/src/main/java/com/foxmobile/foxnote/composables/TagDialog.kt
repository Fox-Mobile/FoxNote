package com.foxmobile.foxnote.composables

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.foxmobile.foxnote.database.tag.TagEvent
import com.foxmobile.foxnote.database.tag.TagViewModel
import com.foxmobile.foxnote.ui.theme.Black
import com.foxmobile.foxnote.ui.theme.FoxNoteTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun TagDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onTagClicked: (tagID: Int) -> Unit,
    tagViewModel: TagViewModel
) {
    val uiState by tagViewModel.state.collectAsState()
    var newTagName by remember { mutableStateOf("") }
    val createNewTag = remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)

                ) {
                    uiState.tags.forEach { tag ->
                        item {
                            Box(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .clickable {
                                        tag.id?.let { onTagClicked(it) }
                                    }
                            ) {
                                TagContainer(
                                    modifier = Modifier,
                                    tag = tag,
                                    tagViewModel = getViewModel()
                                )
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    when {
                        !createNewTag.value -> {
                            TextButton(
                                onClick = {
                                    createNewTag.value = true
                                }
                            ) {
                                Text(
                                    text = "Create tag +",
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }

                    when {
                        createNewTag.value -> {
                            TextField(
                                value = newTagName,
                                onValueChange = { newTitle ->
                                    newTagName = newTitle
                                },

                                modifier = Modifier
                                    .weight(1f),

                                textStyle = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.White
                                ),
                                placeholder = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Outlined.Info,
                                            contentDescription = "Hint",
                                            tint = Color.Gray,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(Modifier.width(4.dp))
                                        Text(
                                            text = "name",
                                            fontSize = 20.sp,
                                            color = Color.Gray,
                                            fontStyle = FontStyle.Italic
                                        )
                                    }
                                }
                            )

                            IconButton(
                                onClick = {
                                    tagViewModel.onEvent(TagEvent.SetID(null))
                                    tagViewModel.onEvent(TagEvent.SetName(newTagName))
                                    tagViewModel.onEvent(TagEvent.SaveTag)
                                    newTagName = ""
                                    createNewTag.value = false
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Check,
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = "Save tag",
                                    modifier = Modifier.size(35.dp)
                                )
                            }
                        }
                    }

                    TextButton(
                        onClick = {
                            onDismissRequest()
                        }) {
                        Text(
                            text = "Cancel", color = Color.White, fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DialogPreview(modifier: Modifier = Modifier) {
    FoxNoteTheme {
        TagDialog(
            onDismissRequest = {}, onTagClicked = {}, tagViewModel = getViewModel()
        )
    }
}