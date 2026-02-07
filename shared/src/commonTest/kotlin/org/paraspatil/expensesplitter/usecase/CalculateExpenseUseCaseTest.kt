package org.paraspatil.expensesplitter.usecase

import org.paraspatil.expensesplitter.domain.model.Expense
import org.paraspatil.expensesplitter.domain.model.Person
import org.paraspatil.expensesplitter.domain.model.Split
import org.paraspatil.expensesplitter.domain.usecase.CalculateExpenseUseCase
import kotlin.math.exp
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateExpenseUseCaseTest {
    private val useCase = CalculateExpenseUseCase()

    @Test
    fun `single expense is calculated correctly`(){
        val amit = Person(id = "1", name = "Amit")
        val sumit = Person(id = "2", name = "Sumit")

        val expense= Expense(
            id = "e1",
            amount = 1000.0,
            paidBy = "1",
            splits = listOf(
                Split("1",500.0),
                Split("2",500.0)
            )
        )

        val result=useCase.execute(
            expenses = listOf(expense),
            persons = listOf(amit,sumit)
        )
        assertEquals(500.0,result.balances["1"])
        assertEquals(-500.0,result.balances["2"])

        assertEquals(
            listOf(
                result.settlements.first()
            ),
            result.settlements
            )
    }

}