package org.paraspatil.expensesplitter.domain.usecase

import org.paraspatil.expensesplitter.domain.calculateBalances
import org.paraspatil.expensesplitter.domain.model.Expense
import org.paraspatil.expensesplitter.domain.model.Person
import org.paraspatil.expensesplitter.domain.settlement.calculateSettlements
import kotlin.math.round

class CalculateExpenseUseCase {
    private val simplifyDebts = SimplifyDebtsUseCase()
    operator fun invoke(
        people : List<Person>,
        expenses : List<Expense>
    ): ExpenseResult {
        if (people.isEmpty()) {
            return ExpenseResult(
                balances = emptyMap(),
                settlements = emptyList(),
                error = "No people found in this session"
            )
        }
        val balances = mutableMapOf<String, Double>()
        people.forEach { balances[it.id] = 0.0 }

        expenses.forEach { expense ->
            val currentPayerBalance = balances[expense.paidBy] ?: 0.0
            balances[expense.paidBy] = currentPayerBalance + expense.amount

            expense.splits.forEach { split ->
                val currentOwerBalance = balances[split.personId] ?: 0.0
                balances[split.personId] = currentOwerBalance - split.amount
            }
        }
        val roundedBalances = balances.mapValues { (_, value) ->
        round(value * 100) / 100.0
    }
        val simplifiedSettlements = simplifyDebts(roundedBalances)

        return ExpenseResult(
            balances = roundedBalances,
            settlements = simplifiedSettlements
        )
    }
}
