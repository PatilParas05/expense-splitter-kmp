package org.paraspatil.expensesplitter

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.paraspatil.expensesplitter.presentation.ExpenseViewModel
import org.paraspatil.expensesplitter.ui.screens.ExpenseScreen
@Composable
fun App() {
    MaterialTheme {
        val viewModel = remember { ExpenseViewModel() }
        ExpenseScreen(viewModel = viewModel)
    }
}