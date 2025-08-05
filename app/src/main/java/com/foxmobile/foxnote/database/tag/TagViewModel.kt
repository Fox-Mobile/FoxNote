package com.foxmobile.foxnote.database.tag

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foxmobile.foxnote.database.note.NoteState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TagViewModel(
    private val tagDao: TagDao
): ViewModel() {
    private val _state = MutableStateFlow(TagState())
    private val _tags = tagDao.getAllTags()

    val state = combine(_state, _tags){ state, tags ->
        state.copy(
            tags = tags
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TagState())

    fun onEvent(event: TagEvent) {
        when(event) {
            is TagEvent.DeleteTag -> {
                viewModelScope.launch {
                    tagDao.deleteTag(event.tag)
                }
            }

            TagEvent.SaveTag -> {
                val id: Int? = _state.value.id
                val name: String = _state.value.name

                val tag = Tag(
                    id = id,
                    name = name
                )

                viewModelScope.launch {
                    tagDao.upsertTag(tag)
                }

                _state.update {
                    it.copy(
                        id = null,
                        name = ""
                    )
                }
            }

            is TagEvent.SetID -> {
                _state.value = _state.value.copy(
                    id = event.id
                )
            }

            is TagEvent.SetName -> {
                _state.value = _state.value.copy(
                    name = event.name
                )
            }
        }
    }
}