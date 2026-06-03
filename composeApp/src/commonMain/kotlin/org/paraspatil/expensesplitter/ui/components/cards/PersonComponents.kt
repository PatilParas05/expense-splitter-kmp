package org.paraspatil.expensesplitter.ui.components.cards

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.paraspatil.expensesplitter.domain.model.Person
import org.paraspatil.expensesplitter.presentation.ExpenseViewModel
import org.paraspatil.expensesplitter.presentation.ExpenseUiState
import org.paraspatil.expensesplitter.ui.components.utils.format

@Composable
fun PeopleTab(viewModel: ExpenseViewModel, uiState: ExpenseUiState) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        AddPersonCard(viewModel, uiState)

        HorizontalDivider()

        Text("Group Members", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        uiState.people.forEach { person ->
            CompactPersonCard(
                person = person,
                balance = uiState.balances[person.id] ?: 0.0,
                onDelete = { viewModel.removePerson(person.id) }
            )
        }
    }
}

@Composable
fun CompactPersonCard(person: Person, balance: Double, onDelete: () -> Unit) {
    val balanceColor = when {
        balance > 0.1 -> Color(0xFF2E7D32) // Green
        balance < -0.1 -> Color(0xFFC62828) // Red
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            // Avatar Circle
            Surface(Modifier.size(40.dp), shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.primaryContainer) {
                Box(contentAlignment = Alignment.Center) {
                    Text(person.name.take(1).uppercase(), fontWeight = FontWeight.Bold)
                }
            }
            Spacer(Modifier.width(12.dp))
            Text(person.name, Modifier.weight(1f), fontWeight = FontWeight.SemiBold)

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = if (balance >= 0) "is owed ₹${balance.format(2)}" else "owes ₹${(-balance).format(2)}",
                    color = balanceColor,
                    style = MaterialTheme.typography.labelSmall
                )
                IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.Delete, null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}
@Composable
fun AddPersonCard(viewModel: ExpenseViewModel, uiState: ExpenseUiState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Add Person",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            OutlinedTextField(
                value = uiState.newPersonName,
                onValueChange = { viewModel.updatePersonName(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Enter person's name") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                leadingIcon = {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            )

            Button(
                onClick = { viewModel.addPerson(uiState.newPersonName) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = uiState.newPersonName.isNotBlank()
            ) {
                Text("Add Person", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun PeopleSection(viewModel: ExpenseViewModel, uiState: ExpenseUiState) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "People",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.size(32.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        uiState.people.size.toString(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        uiState.people.forEach { person ->
            AnimatedVisibility(
                visible = true,
                enter = slideInHorizontally() + fadeIn(),
                exit = slideOutHorizontally() + fadeOut()
            ) {
                PersonCard(
                    person,
                    uiState.balances,
                    onDelete = { viewModel.removePerson(person.id) }
                )
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
    val isPositive = balance > 0.1
    val isNegative = balance < -0.1

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isPositive -> MaterialTheme.colorScheme.tertiaryContainer
                isNegative -> MaterialTheme.colorScheme.errorContainer
                else -> MaterialTheme.colorScheme.secondaryContainer
            }
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    person.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    "Balance: ₹${balance.format(2)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = when {
                        isPositive -> MaterialTheme.colorScheme.tertiary
                        isNegative -> MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.secondary
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            balance.format(0),
                            color = MaterialTheme.colorScheme.surface,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }

                IconButton(onClick = onDelete, modifier = Modifier.size(40.dp)) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}