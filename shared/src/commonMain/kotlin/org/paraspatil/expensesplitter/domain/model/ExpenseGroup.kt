package org.paraspatil.expensesplitter.domain.model

data class ExpenseGroup(
    val id: String,
    val name: String,
    val timestamp: Long= Clock.System.now().toEpochMilliseconds(),
)
