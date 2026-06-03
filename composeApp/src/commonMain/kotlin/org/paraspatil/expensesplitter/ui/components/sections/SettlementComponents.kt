package org.paraspatil.expensesplitter.ui.components.sections


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.paraspatil.expensesplitter.presentation.ExpenseViewModel
import org.paraspatil.expensesplitter.presentation.ExpenseUiState
import org.paraspatil.expensesplitter.ui.components.utils.format

@Composable
fun SettlementTab(viewModel: ExpenseViewModel,uiState: ExpenseUiState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SummaryBalanceCard(uiState.expenses.sumOf { it.amount })

        if (uiState.settlements.isNotEmpty()) {
            SettlementsSection(uiState)
        } else {
            ErrorMessageCard("No debts to settle. Everything is clear!")
        }
        Spacer(modifier = Modifier.weight(1f))
        ResetButton(viewModel)
    }
}
@Composable
private fun SummaryBalanceCard(total: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ){
        Box(modifier = Modifier.background(
            Brush.horizontalGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.tertiary))
        ).padding(24.dp)) {
            Column {
                Text("Total Group Spending", color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.labelMedium)
                Text("₹${total.format(2)}", color = Color.White, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
            }
        }
    }
}
@Composable
fun SettlementsSection(uiState: ExpenseUiState) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Settlements", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            SuggestionChip(
                onClick = {},
                label = { Text("Simplified", style = MaterialTheme.typography.labelSmall) },
                icon = { Icon(Icons.Default.CheckCircle, null, Modifier.size(14.dp)) }
            )
        }

        uiState.settlements.forEach { settlement ->
            val fromPerson = uiState.people.find { it.id == settlement.fromPersonId }
            val toPerson = uiState.people.find { it.id == settlement.toPersonId }
            if (fromPerson != null && toPerson != null) {
                SettlementCard(fromPerson.name, toPerson.name, settlement.amount)
            }
        }
    }
}


@Composable
private fun SettlementCard(fromName: String, toName: String, amount: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(fromName, fontWeight = FontWeight.Bold)
                Text("owes $toName", style = MaterialTheme.typography.bodySmall)
            }
            Text("₹${amount.format(2)}", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
        }
    }
}

@Composable
fun ErrorMessageCard(error: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(
                color = MaterialTheme.colorScheme.error,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.size(32.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("!", color = MaterialTheme.colorScheme.onError, fontWeight = FontWeight.Bold)
                }
            }
            Text(
                error,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun ResetButton(viewModel: ExpenseViewModel) {
    Button(
        onClick = { viewModel.reset() },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text("Reset All", fontWeight = FontWeight.SemiBold)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RenameSessionDialog(viewModel: ExpenseViewModel, uiState: ExpenseUiState) {
    AlertDialog(
        onDismissRequest = { viewModel.dismissRenameDialog() },
        title = {
            Text(
                "Rename Session",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            OutlinedTextField(
                value = uiState.renameTextFieldValue,
                onValueChange = { viewModel.updateRenameTextFieldValue(it) },
                label = { Text("Session Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )
        },
        confirmButton = {
            Button(
                onClick = { viewModel.confirmRename() },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Save", fontWeight = FontWeight.SemiBold)
            }
        },
        dismissButton = {
            TextButton(
                onClick = { viewModel.dismissRenameDialog() },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Cancel")
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}