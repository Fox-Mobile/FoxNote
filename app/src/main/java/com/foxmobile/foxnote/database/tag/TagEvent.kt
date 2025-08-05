package com.foxmobile.foxnote.database.tag

sealed interface TagEvent {
    data object SaveTag: TagEvent
    data class DeleteTag(val tag: Tag): TagEvent
    data class SetID(val id: Int?): TagEvent
    data class SetName(val name: String): TagEvent
}