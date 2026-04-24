package org.paraspatil.expensesplitter.presentation

import org.paraspatil.expensesplitter.domain.model.Expense
import org.paraspatil.expensesplitter.domain.model.Person
import org.paraspatil.expensesplitter.domain.settlement.Settlements

data class ExpenseUiState(
    val people: List<Person> = emptyList(),
    val expenses: List<Expense> = emptyList(),
    val settlements: List<Settlements> = emptyList(),
    val balances: Map<String, Double> = emptyMap(),
    val newPersonName: String = "",
    val expenseAmount: String = "",
    val expenseDescription: String = "",
    val selectedPaidBy: String = "",
    val isLoadings: Boolean = false,
    val errorMessages: String? = null
)
