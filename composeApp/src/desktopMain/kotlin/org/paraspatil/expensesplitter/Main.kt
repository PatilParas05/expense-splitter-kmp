package org.paraspatil.expensesplitter

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Expense Splitter",
    ) {
        App()
    }
}
