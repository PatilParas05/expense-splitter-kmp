package org.paraspatil.expensesplitter.domain.usecase

import org.paraspatil.expensesplitter.domain.calculateBalances
import org.paraspatil.expensesplitter.domain.model.Expense
import org.paraspatil.expensesplitter.domain.model.Person
import org.paraspatil.expensesplitter.domain.settlement.calculateSettlements

class CalculateExpenseUseCase {
    operator fun invoke(
        people : List<Person>,
        expenses : List<Expense>
    ): ExpenseResult {
        if (people.isEmpty()){
            return ExpenseResult(
                balances = emptyMap(),
                settlements = emptyList(),
                error = "No people found"
            )
        }
        val balances = calculateBalances(expenses, people)
        val settlements = calculateSettlements(balances)
        return ExpenseResult(
            balances = balances,
            settlements = settlements
        )
    }
}
