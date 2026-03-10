package org.paraspatil.expensesplitter.ui.expense

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.paraspatil.expensesplitter.domain.model.Expense
import org.paraspatil.expensesplitter.domain.model.Person
import org.paraspatil.expensesplitter.domain.settlement.Settlements

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseScreen(
    persons: List<Person>,
    expenses: List<Expense>,
    settlements: List<Settlements>,
    onAddPerson: (String) -> Unit,
    onAddExpense: (Double, String) -> Unit,
    onCalculate: () -> Unit
) {
    var personName by remember { mutableStateOf("") }
    var expenseAmount by remember { mutableStateOf("") }
    var selectedPersonId by remember { mutableStateOf("") }
    var selectedPersonName by remember { mutableStateOf("") }
    var dropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .padding(top = 30.dp)
    ) {
        // Section 1 — Add Person
        Text("Add Person", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = personName,
            onValueChange = { personName = it },
            label = { Text("Person Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (personName.isNotBlank()) {
                    onAddPerson(personName.trim())
                    personName = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Person")
        }

        Spacer(modifier = Modifier.height(8.dp))

        persons.forEach { person ->
            Text("• ${person.name}")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Section 2 — Add Expense
        Text("Add Expense", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = expenseAmount,
            onValueChange = { expenseAmount = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = dropdownExpanded,
            onExpandedChange = { dropdownExpanded = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedPersonName,
                onValueChange = {},
                readOnly = true,
                label = { Text("Paid By") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false }
            ) {
                persons.forEach { person ->
                    DropdownMenuItem(
                        text = { Text(person.name) },
                        onClick = {
                            selectedPersonId = person.id
                            selectedPersonName = person.name
                            dropdownExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val amount = expenseAmount.toDoubleOrNull()
                if (amount != null && selectedPersonId.isNotBlank()) {
                    onAddExpense(amount, selectedPersonId)
                    expenseAmount = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Expense")
        }

        Spacer(modifier = Modifier.height(8.dp))

        expenses.forEach { expense ->
            val paidByName = persons.find { it.id == expense.paidBy }?.name ?: expense.paidBy
            Text("• $paidByName paid ₹${"%.2f".format(expense.amount)}")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Section 3 — Calculate & Results
        Text("Settlements", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onCalculate,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate Settlements")
        }

        Spacer(modifier = Modifier.height(8.dp))

        settlements.forEach { settlement ->
            val fromName = persons.find { it.id == settlement.from }?.name ?: settlement.from
            val toName = persons.find { it.id == settlement.to }?.name ?: settlement.to
            Text("• $fromName owes $toName ₹${"%.2f".format(settlement.amount)}")
        }
    }
}

