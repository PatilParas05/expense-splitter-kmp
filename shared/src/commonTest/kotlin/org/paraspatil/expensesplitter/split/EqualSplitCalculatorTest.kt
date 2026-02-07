package org.paraspatil.expensesplitter.split

import org.paraspatil.expensesplitter.domain.split.EqualSplitCalculator
import kotlin.test.Test
import kotlin.test.assertEquals

class EqualSplitCalculatorTest(){

    @Test
    fun equalSplitWorks(){
        val calculator = EqualSplitCalculator()

        val result=calculator.calculate(
        totalAmount = 300.0,
            participants = listOf("amit","sumit","vinit")
        )
        assertEquals(
            mapOf(
                "amit" to 100.0,
                "sumit" to 100.0,
                "vinit" to 100.0
            ),
            result
        )
    }
}