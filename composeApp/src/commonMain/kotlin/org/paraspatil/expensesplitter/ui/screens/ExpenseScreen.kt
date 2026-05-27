package org.paraspatil.expensesplitter.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.launch
import org.paraspatil.expensesplitter.presentation.ExpenseViewModel
import org.paraspatil.expensesplitter.ui.components.cards.AddExpenseCard
import org.paraspatil.expensesplitter.ui.components.cards.AddPersonCard
import org.paraspatil.expensesplitter.ui.components.cards.ExpensesSection
import org.paraspatil.expensesplitter.ui.components.cards.PeopleSection
import org.paraspatil.expensesplitter.ui.components.drawer.NavigationDrawerContent
import org.paraspatil.expensesplitter.ui.components.sections.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseScreen(viewModel: ExpenseViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawerContent(viewModel, uiState, drawerState, scope)
        }
    ) {
        Scaffold(
            topBar = {
                MainTopAppBar(
                    title = uiState.currentGroupName,
                    onMenuClick = { scope.launch { drawerState.open() } }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Add Person Section
                    AddPersonCard(viewModel, uiState)

                    // People Section
                    if (uiState.people.isNotEmpty()) {
                        PeopleSection(viewModel, uiState)
                    }

                    // Add Expense Section
                    if (uiState.people.isNotEmpty()) {
                        AddExpenseCard(viewModel, uiState)
                    }

                    // Expenses Section
                    if (uiState.expenses.isNotEmpty()) {
                        ExpensesSection(uiState)
                    }

                    // Settlements Section
                    if (uiState.settlements.isNotEmpty()) {
                        SettlementsSection(uiState)
                    }

                    // Error Message
                    if (uiState.errorMessages != null) {
                        ErrorMessageCard(uiState.errorMessages!!)
                    }

                    // Reset Button
                    if (uiState.people.isNotEmpty() || uiState.expenses.isNotEmpty()) {
                        ResetButton(viewModel)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            // Rename Dialog
            if (uiState.showResetDialog) {
                RenameSessionDialog(viewModel, uiState)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainTopAppBar(title: String, onMenuClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier.shadow(4.dp)
    )
}