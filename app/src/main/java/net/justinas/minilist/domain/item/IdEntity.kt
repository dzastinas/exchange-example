package net.justinas.minilist.domain.item

import java.io.Serializable

data class IdEntity(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val text1: String = "",
    val text2: String = "",
    val url: String = "",
    val list: List<String> = emptyList()
) :
    Serializable