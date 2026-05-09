package org.paraspatil.expensesplitter

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.desktopModule
import org.koin.core.context.startKoin
import org.paraspatil.expensesplitter.di.commonModule

fun main() = application {
    startKoin {
        modules(desktopModule, commonModule)
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Expense Splitter",
    ) {
        App()
        }
}
