package org.paraspatil.expensesplitter.domain

import org.paraspatil.expensesplitter.domain.model.Expense
import org.paraspatil.expensesplitter.domain.model.Person

fun calculateBalances(
    expenses: List<Expense>,
    persons: List<Person>
): Map<String, Double> {
    val balances = persons
        .associate { person -> person.id to 0.0 }
        .toMutableMap()

    expenses.forEach { expense ->
        balances[expense.paidBy] =
            balances.getValue(expense.paidBy) + expense.amount

        expense.splits.forEach { split ->
            balances[split.personId] =
                balances.getValue(split.personId) - split.amount
        }
    }
    return balances
}