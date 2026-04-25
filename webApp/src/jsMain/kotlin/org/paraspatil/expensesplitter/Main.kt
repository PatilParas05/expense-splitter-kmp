package org.paraspatil.expensesplitter

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import org.paraspatil.expensesplitter.ui.screens.ExpenseScreen
import org.paraspatil.expensesplitter.presentation.ExpenseViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow("Expense Splitter") {
        val viewModel = getViewModel(
            Unit,
            viewModelFactory { ExpenseViewModel() }
        )
        ExpenseScreen(viewModel = viewModel)
    }
}
