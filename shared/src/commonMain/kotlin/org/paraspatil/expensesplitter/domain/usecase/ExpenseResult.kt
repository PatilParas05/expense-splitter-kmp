package org.paraspatil.expensesplitter.domain.usecase

import org.paraspatil.expensesplitter.domain.settlement.Settlements

data class ExpenseResult(
    val balances : Map<String, Double>,
    val settlements: List<Settlements>
)
