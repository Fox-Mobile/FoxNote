package com.foxmobile.foxnote.database.tag

data class TagState(
    val id: Int? = null,
    val name: String = "",
    val tags: List<Tag> = emptyList()
)
