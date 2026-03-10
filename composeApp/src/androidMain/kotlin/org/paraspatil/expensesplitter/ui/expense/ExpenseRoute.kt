package org.paraspatil.expensesplitter.ui.expense

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.paraspatil.expensesplitter.presentation.ExpenseViewModel

@Composable
fun ExpenseRoute(
    viewModel: ExpenseViewModel
) {
    val state by viewModel.uiState.collectAsState()

    ExpenseScreen(
        persons = state.persons,
        expenses = state.expenses,
        settlements = state.settlements,
        onAddPerson = { viewModel.addPerson(it) },
        onAddExpense = { amount, paidBy -> viewModel.addExpense(amount, paidBy) },
        onCalculate = { viewModel.calculate() }
    )
}

