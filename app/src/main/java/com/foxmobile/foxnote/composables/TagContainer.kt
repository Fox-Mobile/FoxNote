package com.foxmobile.foxnote.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Label
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foxmobile.foxnote.database.tag.Tag
import com.foxmobile.foxnote.database.tag.TagEvent
import com.foxmobile.foxnote.database.tag.TagViewModel
import com.foxmobile.foxnote.ui.theme.FoxNoteTheme
import com.foxmobile.foxnote.ui.theme.Orange
import org.koin.androidx.compose.getViewModel

@Composable
fun TagContainer(
    modifier: Modifier = Modifier,
    tag: Tag,
    tagViewModel: TagViewModel
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(corner = CornerSize(30.dp)),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary)
    ) {
        Box(
            modifier = modifier
                .padding(4.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ){
            Row {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Label,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "Tag",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(48.dp)
                )
                Text(
                    text = tag.name,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                    fontSize = 25.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                )

                IconButton(
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.CenterVertically),
                    onClick = {
                        tagViewModel.onEvent(TagEvent.DeleteTag(tag))
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        tint = Color.White,
                        contentDescription = "Delete",
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TagContainerPreview(modifier: Modifier = Modifier) {
    FoxNoteTheme {
        TagContainer(
            modifier = modifier,
            tag = Tag(id = 1, name = "work"),
            tagViewModel = getViewModel()
        )
    }
}