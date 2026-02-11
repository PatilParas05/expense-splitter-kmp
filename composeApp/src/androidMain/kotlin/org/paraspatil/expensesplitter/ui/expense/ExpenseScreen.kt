package org.paraspatil.expensesplitter.ui.expense

import android.R.attr.name
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.paraspatil.expensesplitter.domain.settlement.Settlements

@Composable
fun ExpenseScreen(
    settlements: List<Settlements>,
    balances: Map<String, Double>,
    onAddBalance: (String, Double) -> Unit,
    onCalculate: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Spacer(modifier = Modifier.padding(top = 30.dp))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Add Balance", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.padding(top = 10.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Person Name") },
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            {
            val value = amount.toDoubleOrNull()
            if (name.isNotBlank() && value != null) {
                onAddBalance(name,value)
                name=""
                amount=""
            }
        }
        ){
            Text("Add Balance")
        }

        Spacer(modifier = Modifier.padding(top = 16.dp))

        LazyColumn {
            items(balances.toList()) { (person,balances) ->
                Text("$person: $balances")

            }
        }

        Spacer(modifier = Modifier.padding(top = 10.dp))

        Text("Settlements", style = MaterialTheme.typography.titleMedium)

        LazyColumn {
            items(settlements) {
                Text("${it.from} owes ${it.to} ${it.amount}")
            }
        }

    }
}
