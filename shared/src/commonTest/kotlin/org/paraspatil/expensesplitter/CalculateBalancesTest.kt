package org.paraspatil.expensesplitter

import org.paraspatil.expensesplitter.domain.calculateBalances
import org.paraspatil.expensesplitter.domain.model.Expense
import org.paraspatil.expensesplitter.domain.model.Person
import org.paraspatil.expensesplitter.domain.model.Split
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateBalancesTest {

    @Test
    fun simpleSplitWorks(){
        val alice = Person("1", "Amit")
        val bob = Person("2", "Sumit")

        val expense= Expense(
            id = "e1",
            amount = 1000.0,
            paidBy = "1",
            splits = listOf(
                Split("1", 500.0),
                Split("2",500.0)

            )
        )
        val balances = calculateBalances(
            expenses = listOf(expense),
            listOf(alice, bob)
        )
        assertEquals(500.0,balances["1"])
        assertEquals(-500.0,balances["2"])

    }

    fun multipleExpensesAreHandledCorrectly() {
        val alice = Person("1", "Amit")
        val bob = Person("2", "Sumit")
        val dinner = Expense(
            id = "e1",
            amount = 1000.0,
            paidBy = "1",
            splits = listOf(
                Split("1", 500.0),
                Split("2",500.0)
            )
        )

        val taxi = Expense(
            id = "e2",
            amount = 400.0,
            paidBy = "2",
            splits = listOf(
                Split("1",200.0),
                Split("2",200.0)
            )
        )
        val balances = calculateBalances(
            expenses = listOf(dinner, taxi),
            listOf(alice, bob)
        )
        assertEquals(300.0,balances["1"])
        assertEquals(-300.0,balances["2"])

    }
    fun unevenSplitWorks(){
        val alice = Person("1", "Amit")
        val bob = Person("2", "Sumit")

        val expense= Expense(
            id = "e1",
            amount = 1000.0,
            paidBy = "1",
            splits = listOf(
                Split("1", 700.0),
                Split("2",300.0)
                )
            )
        val balances = calculateBalances(
            expenses = listOf(expense),
            persons = listOf(alice, bob)
        )
        assertEquals(300.0,balances["1"])
        assertEquals(-700.0,balances["2"])


    }
}