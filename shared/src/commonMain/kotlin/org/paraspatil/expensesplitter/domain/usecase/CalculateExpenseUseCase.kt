package org.paraspatil.expensesplitter.domain.usecase

import org.paraspatil.expensesplitter.domain.calculateBalances
import org.paraspatil.expensesplitter.domain.model.Expense
import org.paraspatil.expensesplitter.domain.model.Person
import org.paraspatil.expensesplitter.domain.settlement.calculateSettlements

class CalculateExpenseUseCase {
    fun execute(
        expenses: List<Expense>,
        persons: List<Person>
    ): ExpenseResult {
        val balances = calculateBalances(
            expenses = expenses,
            persons = persons
        )
        val settlements = calculateSettlements(balances)
        return ExpenseResult(
            balances = balances,
            settlements = settlements
        )
    }

}