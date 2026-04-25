package org.paraspatil.expensesplitter

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.paraspatil.expensesplitter.presentation.ExpenseViewModel
import org.paraspatil.expensesplitter.ui.screens.ExpenseScreen

@Composable
fun App() {
    MaterialTheme {
        val viewModel = getViewModel(
            Unit,
            viewModelFactory { ExpenseViewModel() }
        )
        ExpenseScreen(viewModel = viewModel)
    }
}
