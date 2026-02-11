package org.paraspatil.expensesplitter.presentation

import org.paraspatil.expensesplitter.domain.model.Expense
import org.paraspatil.expensesplitter.domain.model.Person
import org.paraspatil.expensesplitter.domain.settlement.Settlements

data class ExpenseUiState(
    val persons: List<Person> = emptyList(),
    val expenses: List<Expense> = emptyList(),
    val settlements: List<Settlements> = emptyList(),
    val balances: Map<String, Double> = emptyMap()
)
