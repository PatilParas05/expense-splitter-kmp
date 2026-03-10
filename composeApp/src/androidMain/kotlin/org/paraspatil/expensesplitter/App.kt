package org.paraspatil.expensesplitter

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import org.paraspatil.expensesplitter.domain.usecase.CalculateExpenseUseCase
import org.paraspatil.expensesplitter.presentation.ExpenseViewModel
import org.paraspatil.expensesplitter.ui.expense.ExpenseRoute

@Composable
fun App() {
    MaterialTheme {
        val viewModel: ExpenseViewModel = viewModel {
            ExpenseViewModel(
                useCase = CalculateExpenseUseCase()
            )
        }
        ExpenseRoute(viewModel = viewModel)
    }
}
