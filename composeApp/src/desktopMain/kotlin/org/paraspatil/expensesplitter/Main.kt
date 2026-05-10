package org.paraspatil.expensesplitter

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.desktopModule
import kotlinx.coroutines.delay
import org.koin.core.context.startKoin
import org.paraspatil.expensesplitter.di.commonModule


fun main() {
    startKoin {
        modules(desktopModule, commonModule)
    }

    application {
        var showSplash by remember { mutableStateOf(true) }

        if (showSplash) {
            Window(
                onCloseRequest = ::exitApplication,
                title = "Expense Splitter",
                undecorated = true
            ) {
                SplashScreen(onFinished = { showSplash = false })
            }
        } else {
            Window(
                onCloseRequest = ::exitApplication,
                title = "Expense Splitter"
            ) {
                App()
            }
        }
    }
}
@Composable
fun SplashScreen(onFinished: () -> Unit){
    var visible by remember { mutableStateOf(true) }
    val alpha by animateFloatAsState(
        targetValue = if (visible)1f else 0f,
        animationSpec = tween(durationMillis = 500),
        finishedListener = {if (it == 0f) onFinished()}
    )
    LaunchedEffect(Unit){
        delay(2000)
        visible = false
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .alpha(alpha),
        contentAlignment = Alignment.Center
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                text = "💸",
                fontSize = 64.sp
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = "Expense Splitter",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }

}
