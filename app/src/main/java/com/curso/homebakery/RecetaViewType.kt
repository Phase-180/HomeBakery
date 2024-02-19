package com.curso.homebakery

enum class RecetaViewType(val value: String) {
    TYPE_UPDATE("update"),
    TYPE_READ_ONLY("read_only"),
    TYPE_CREATE("create");

    companion object {

        fun fromString(value: String): RecetaViewType? {
            return values().find { it.value.equals(value, ignoreCase = true) }
        }
    }
}