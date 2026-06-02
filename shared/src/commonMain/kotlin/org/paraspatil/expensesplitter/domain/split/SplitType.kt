package org.paraspatil.expensesplitter.domain.split

data class SplitType(
    val type: String,
    val displayName: String,
    val description: String,
    val requiresInput: Boolean,
    val inputLabel: String = "",
    val hint: String ="",
)
