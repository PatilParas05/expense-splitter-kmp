package org.paraspatil.expensesplitter.ui.expense

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.paraspatil.expensesplitter.domain.settlement.Settlements

@Composable
fun ExpenseScreen(
    settlements: List<Settlements>,
    onCalculate: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = onCalculate,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate Settlements")
        }

        Spacer(Modifier.height(16.dp))

        settlements.forEach {
            Text("${it.from} pays ${it.to} ₹${it.amount}")
        }
    }
}
