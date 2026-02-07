package org.paraspatil.expensesplitter.ui.expense

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import org.paraspatil.expensesplitter.presentation.ExpenseViewModel

@Composable
fun ExpenseRoute(
    viewModel: ExpenseViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    ExpenseScreen(
        settlements = state.settlements,
        onCalculate = { viewModel.calculate() }
    )
}

