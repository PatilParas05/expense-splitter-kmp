package org.paraspatil.expensesplitter.domain

import org.paraspatil.expensesplitter.domain.model.Expense
import org.paraspatil.expensesplitter.domain.model.Person

fun calculateBalances(
    expenses: List<Expense>,
    people: List<Person>
): Map<String, Double> {
    val balances = mutableMapOf<String, Double>()

    //initialize all people with 0 balance
    people.forEach { person ->
        balances[person.id] = 0.0
    }
    //calculate balances
    expenses.forEach { expense ->
        balances[expense.paidBy] =
            balances[expense.paidBy]!! + expense.amount

        expense.splits.forEach { split ->
            balances[split.personId] =
                balances[split.personId]!! - split.amount
        }
    }
    return balances
}