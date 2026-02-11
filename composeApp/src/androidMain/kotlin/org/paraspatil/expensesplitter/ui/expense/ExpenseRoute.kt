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
        settlements = state.settlements,
        balances = state.balances,
        onAddBalance = { name, amount ->
            viewModel.addBalances(name,amount)
            },
        onCalculate = {
            viewModel.calculate()
        }
    )
}
