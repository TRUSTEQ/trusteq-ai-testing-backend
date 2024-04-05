package de.trusteq.usermanager.dto

data class IdNameTypeResponse(
    val id: Int,
    val name: String,
    val type: ResultType,
)

enum class ResultType {
    USER, COMPANY
}
