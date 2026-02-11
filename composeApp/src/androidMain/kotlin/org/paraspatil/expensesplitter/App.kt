package org.paraspatil.expensesplitter

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.paraspatil.expensesplitter.domain.usecase.CalculateExpenseUseCase
import org.paraspatil.expensesplitter.presentation.ExpenseViewModel
import org.paraspatil.expensesplitter.ui.expense.ExpenseRoute

@Composable
fun App() {
    MaterialTheme {
        val viewModel = remember {
            ExpenseViewModel(
                useCase = CalculateExpenseUseCase()
            )
        }
        ExpenseRoute(viewModel = viewModel)
    }
}
