package org.paraspatil.expensesplitter

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.koin.compose.koinInject
import org.paraspatil.expensesplitter.presentation.ExpenseViewModel
import org.paraspatil.expensesplitter.ui.screens.ExpenseScreen
@Composable
fun App() {
    MaterialTheme {
        val viewModel : ExpenseViewModel = koinInject()
        ExpenseScreen(viewModel = viewModel)
    }
}