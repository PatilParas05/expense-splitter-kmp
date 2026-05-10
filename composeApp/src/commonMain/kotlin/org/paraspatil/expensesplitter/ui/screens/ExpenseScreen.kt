package org.paraspatil.expensesplitter.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import org.paraspatil.expensesplitter.presentation.ExpenseViewModel
import org.paraspatil.expensesplitter.presentation.ExpenseUiState
import org.paraspatil.expensesplitter.domain.model.Person
import org.paraspatil.expensesplitter.domain.model.Expense

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseScreen(viewModel: ExpenseViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxWidth()) {

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 4.dp
        ) {
            Text(
                text = "Expense Splitter",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .defaultMinSize(minHeight = 800.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AddPersonCard(viewModel, uiState)

            if (uiState.people.isNotEmpty()) {
                Text("People (${uiState.people.size})", style = MaterialTheme.typography.titleMedium)
                uiState.people.forEach { person ->
                    PersonCard(person, uiState.balances, onDelete = { viewModel.removePerson(person.id) })
                }
            }

            if (uiState.people.isNotEmpty()) {
                HorizontalDivider()
                AddExpenseCard(viewModel, uiState)
            }

            if (uiState.expenses.isNotEmpty()) {
                Text("Expenses (${uiState.expenses.size})", style = MaterialTheme.typography.titleMedium)
                uiState.expenses.forEach { expense ->
                    ExpenseCard(expense, uiState.people)
                }
            }

            if (uiState.settlements.isNotEmpty()) {
                HorizontalDivider()
                Text("Settlements", style = MaterialTheme.typography.titleMedium)
                uiState.settlements.forEach { settlement ->
                    val fromPerson = uiState.people.find { it.id == settlement.fromPersonId }
                    val toPerson = uiState.people.find { it.id == settlement.toPersonId }
                    if (fromPerson != null && toPerson != null) {
                        SettlementCard(fromPerson.name, toPerson.name, settlement.amount)
                    }
                }
            }

            uiState.errorMessages?.let { error ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Text(
                        error,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            if (uiState.people.isNotEmpty() || uiState.expenses.isNotEmpty()) {
                Button(
                    onClick = { viewModel.reset() },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Reset All")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun AddPersonCard(viewModel: ExpenseViewModel, uiState: ExpenseUiState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Add Person", style = MaterialTheme.typography.headlineSmall)

            OutlinedTextField(
                value = uiState.newPersonName,
                onValueChange = { viewModel.updatePersonName(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Person Name") },
                singleLine = true
            )

            Button(
                onClick = { viewModel.addPerson(uiState.newPersonName) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Add Person")
            }
        }
    }
}

@Composable
private fun PersonCard(
    person: Person,
    balances: Map<String, Double>,
    onDelete: () -> Unit
) {
    val balance = balances[person.id] ?: 0.0

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (balance > 0.1) MaterialTheme.colorScheme.tertiaryContainer
            else if (balance < -0.1) MaterialTheme.colorScheme.errorContainer
            else MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(person.name, style = MaterialTheme.typography.bodyLarge)
                Text(
                    "Balance: ₹${((balance * 100).toInt() / 100.0)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable
private fun AddExpenseCard(viewModel: ExpenseViewModel, uiState: ExpenseUiState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Add Expense", style = MaterialTheme.typography.headlineSmall)

            OutlinedTextField(
                value = uiState.expenseAmount,
                onValueChange = { viewModel.updateExpenseAmount(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Amount") },
                singleLine = true
            )

            OutlinedTextField(
                value = uiState.expenseDescription,
                onValueChange = { viewModel.updateExpenseDescription(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Description (optional)") },
                singleLine = true
            )

            Text("Who Paid?", style = MaterialTheme.typography.bodyMedium)

            Column(modifier = Modifier.fillMaxWidth()) {
                uiState.people.forEach { person ->
                    RadioButtonItem(
                        text = person.name,
                        selected = uiState.selectedPaidBy == person.id,
                        onSelect = { viewModel.selectedPaidBy(person.id) }
                    )
                }
            }

            Button(
                onClick = { viewModel.addExpense() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Add Expense")
            }
        }
    }
}

@Composable
private fun RadioButtonItem(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = onSelect)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text)
    }
}

@Composable
private fun ExpenseCard(expense: Expense, people: List<Person>) {
    val paidByPerson = people.find { it.id == expense.paidBy }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Paid by: ${paidByPerson?.name ?: "Unknown"}", style = MaterialTheme.typography.bodyMedium)
                    if (expense.description.isNotEmpty()) {
                        Text(expense.description, style = MaterialTheme.typography.bodySmall)
                    }
                }
                Text("₹${((expense.amount * 100).toInt() / 100.0)}", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
private fun SettlementCard(fromName: String, toName: String, amount: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(fromName, style = MaterialTheme.typography.bodyLarge)
                Text("↓ owes", style = MaterialTheme.typography.bodySmall)
                Text(toName, style = MaterialTheme.typography.bodyLarge)
            }
            Text("₹${((amount * 100).toInt() / 100.0)}", style = MaterialTheme.typography.headlineSmall)
        }
    }
}

fun Double.format(decimals: Int): String {
    val parts = this.toString().split(".")
    if (parts.size == 1) return "${parts[0]}.${"0".repeat(decimals)}"
    val decimalPart = parts[1].padEnd(decimals, '0').take(decimals)
    return "${parts[0]}.$decimalPart"
}