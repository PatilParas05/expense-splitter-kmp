package org.paraspatil.expensesplitter.domain.model

data class ExpenseGroup(
    val id: String,
    val name: String,
    val timestamp: Long= 0L
)
