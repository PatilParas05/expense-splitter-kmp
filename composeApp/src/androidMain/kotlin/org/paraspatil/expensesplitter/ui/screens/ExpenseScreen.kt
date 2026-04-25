package org.paraspatil.expensesplitter.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.paraspatil.expensesplitter.presentation.ExpenseViewModel
import org.paraspatil.expensesplitter.presentation.ExpenseUiState
import org.paraspatil.expensesplitter.domain.model.Person
import org.paraspatil.expensesplitter.domain.model.Expense

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseScreen(viewModel: ExpenseViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Expense Splitter") }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Add Person Section
            item {
                AddPersonCard(viewModel, uiState)
            }

            // People List
            if (uiState.people.isNotEmpty()) {
                item {
                    Text("People (${uiState.people.size})", style = MaterialTheme.typography.titleMedium)
                }
                items(uiState.people) { person ->
                    PersonCard(person, uiState.balances, onDelete = { viewModel.removePerson(person.id) })
                }
            }

            // Add Expense Section
            if (uiState.people.isNotEmpty()) {
                item {
                    HorizontalDivider()
                }
                item {
                    AddExpenseCard(viewModel, uiState)
                }
            }

            // Expenses List
            if (uiState.expenses.isNotEmpty()) {
                item {
                    Text("Expenses (${uiState.expenses.size})", style = MaterialTheme.typography.titleMedium)
                }
                items(uiState.expenses) { expense ->
                    ExpenseCard(expense, uiState.people)
                }
            }

            // Settlements Section
            if (uiState.settlements.isNotEmpty()) {
                item {
                    HorizontalDivider()
                }
                item {
                    Text("Settlements", style = MaterialTheme.typography.titleMedium)
                }
                items(uiState.settlements) { settlement ->
                    val fromPerson = uiState.people.find { it.id == settlement.fromPersonId }
                    val toPerson = uiState.people.find { it.id == settlement.toPersonId }

                    if (fromPerson != null && toPerson != null) {
                        SettlementCard(fromPerson.name, toPerson.name, settlement.amount)
                    }
                }
            }

            // Error Message
            uiState.errorMessages?.let { error ->
                item {
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
            }

            // Reset Button
            if (uiState.people.isNotEmpty() || uiState.expenses.isNotEmpty()) {
                item {
                    Button(
                        onClick = { viewModel.reset() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Reset All")
                    }
                }
            }
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
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Add Person", style = MaterialTheme.typography.headlineSmall)

            TextField(
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
                    "Balance: ₹${String.format("%.2f", balance)}",
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
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Add Expense", style = MaterialTheme.typography.headlineSmall)

            TextField(
                value = uiState.expenseAmount,
                onValueChange = { viewModel.updateExpenseAmount(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Amount") },
                singleLine = true
            )

            TextField(
                value = uiState.expenseDescription,
                onValueChange = { viewModel.updateExpenseDescription(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Description (optional)") },
                singleLine = true
            )

            Text("Who Paid?", style = MaterialTheme.typography.bodyMedium)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
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
private fun ExpenseCard(
    expense: Expense,
    people: List<Person>
) {
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
                Text("₹${String.format("%.2f", expense.amount)}", style = MaterialTheme.typography.bodyLarge)
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
            Text("₹${String.format("%.2f", amount)}", style = MaterialTheme.typography.headlineSmall)
        }
    }
}
