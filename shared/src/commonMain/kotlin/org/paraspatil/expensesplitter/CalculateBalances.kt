package org.paraspatil.expensesplitter

import org.paraspatil.expensesplitter.domain.model.Expense
import org.paraspatil.expensesplitter.domain.model.Person

fun calculateBalances(expenses: List<Expense>, persons: List<Person>): Map<String, Double> {
    val balances = persons.associate { it.id to 0.0 }.toMutableMap()

    for (expense in expenses) {
        // The person who paid gets the money back, so their balance increases.
        balances[expense.paidBy] = (balances[expense.paidBy] ?: 0.0) + expense.amount

        // The people who are part of the split owe money, so their balance decreases.
        for (split in expense.splits) {
            balances[split.personId] = (balances[split.personId] ?: 0.0) - split.amount
        }
    }

    return balances
}