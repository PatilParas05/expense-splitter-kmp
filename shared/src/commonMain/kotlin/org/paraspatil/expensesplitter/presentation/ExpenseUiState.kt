package org.paraspatil.expensesplitter.presentation

import org.paraspatil.expensesplitter.domain.model.Expense
import org.paraspatil.expensesplitter.domain.model.ExpenseGroup
import org.paraspatil.expensesplitter.domain.model.Person
import org.paraspatil.expensesplitter.domain.settlement.Settlements
import org.paraspatil.expensesplitter.domain.split.SplitType

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
    val errorMessages: String? = null,
    val isSplashScreenFinished: Boolean = false,
    val historyGroups: List<ExpenseGroup> = emptyList(),
    val currentGroupID: String = "default",
    val currentGroupName: String = "Current Split",
    val showResetDialog: Boolean = false,
    val renameTextFieldValue: String = "",
    val splitType: String = "EQUAL",
    val splitValues: Map<String, String> = emptyMap()
)