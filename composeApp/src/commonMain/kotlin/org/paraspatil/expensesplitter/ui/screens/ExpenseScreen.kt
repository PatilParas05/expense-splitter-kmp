package org.paraspatil.expensesplitter.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import org.paraspatil.expensesplitter.presentation.ExpenseViewModel
import org.paraspatil.expensesplitter.ui.components.cards.AddExpenseCard
import org.paraspatil.expensesplitter.ui.components.cards.ExpenseTab
import org.paraspatil.expensesplitter.ui.components.cards.PeopleTab
import org.paraspatil.expensesplitter.ui.components.drawer.NavigationDrawerContent
import org.paraspatil.expensesplitter.ui.components.sections.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseScreen(viewModel: ExpenseViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var showAddExpenseSheet by remember { mutableStateOf(false) }
    val tabs = listOf("Balances", "Expenses", "People")

    val snackbarHostState = remember { SnackbarHostState() }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawerContent(viewModel, uiState, drawerState, scope)
        }
    ) {
        Scaffold(
            snackbarHost = {SnackbarHost(hostState = snackbarHostState)},
            topBar = {
                Column {
                    MainTopAppBar(
                        title = uiState.currentGroupName,
                        onMenuClick = { scope.launch { drawerState.open() } }
                    )
                    SecondaryTabRow(
                        selectedTabIndex = selectedTabIndex,
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.primary,
                        ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                text = { Text(title) }
                            )
                        }
                    }
                }
            },
            floatingActionButton = {

                FloatingActionButton(
                    onClick = {
                        if (uiState.people.isEmpty()){
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Please add at least one person first",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }else {
                            showAddExpenseSheet = true
                        }
                    },
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Expenses")
                }
            }
        ) { padding ->
            Box(modifier = Modifier.fillMaxSize().padding(padding)) {
                when (selectedTabIndex) {
                    0 -> SettlementTab(viewModel,uiState)
                    1 -> ExpenseTab(uiState)
                    2 -> PeopleTab(viewModel, uiState)
                }
            }
            if(showAddExpenseSheet){
                ModalBottomSheet(
                    onDismissRequest = { showAddExpenseSheet = false },
                    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                    containerColor = MaterialTheme.colorScheme.surface
                ){
                    Box(modifier = Modifier.padding(bottom = 32.dp)){
                        AddExpenseCard(
                           viewModel = viewModel,
                            uiState = uiState
                        )
                    }
                }
            }
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