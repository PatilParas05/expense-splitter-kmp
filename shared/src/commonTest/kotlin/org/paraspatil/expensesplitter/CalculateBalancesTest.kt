package org.paraspatil.expensesplitter

import org.paraspatil.expensesplitter.domain.model.Expense
import org.paraspatil.expensesplitter.domain.model.Person
import org.paraspatil.expensesplitter.domain.model.Split
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateBalancesTest {

    @Test
    fun simpleSplitWorks(){
        val alice = Person("1", "Alice")
        val bob = Person("2", "Bob")

        val expense= Expense(
            id = "e1",
            amount = 1000.0,
            paidBy = "1",
            splits = listOf(
                Split("1", 500.0),
                Split("2",500.0),

            )
        )
        val balances = calculateBalances(
            expenses= listOf(expense),
            listOf(alice, bob)
        )
        assertEquals(500.0,balances["1"])
        assertEquals(-500.0,balances["2"])

    }
}