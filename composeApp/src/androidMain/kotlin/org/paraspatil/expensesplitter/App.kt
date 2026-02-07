package org.paraspatil.expensesplitter

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.paraspatil.expensesplitter.ui.expense.ExpenseRoute

@Composable
fun App() {
    MaterialTheme {
        ExpenseRoute()
    }
}
