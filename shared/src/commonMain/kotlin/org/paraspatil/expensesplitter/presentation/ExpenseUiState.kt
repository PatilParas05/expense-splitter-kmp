package org.paraspatil.expensesplitter.presentation

import org.paraspatil.expensesplitter.domain.settlement.Settlements

data class ExpenseUiState(
    val balances: Map<String, Double> = emptyMap(),
    val settlements: List<Settlements> = emptyList()
)
