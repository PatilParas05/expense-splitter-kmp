package org.paraspatil.expensesplitter

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import di.webModule
import kotlinx.browser.document
import org.koin.core.context.GlobalContext.startKoin
import org.paraspatil.expensesplitter.di.commonModule

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    startKoin {
        modules(webModule, commonModule)
    }
    ComposeViewport(viewportContainerId = "compose-output") {
        App()
    }
}
