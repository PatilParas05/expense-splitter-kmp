package org.paraspatil.expensesplitter.split

import org.paraspatil.expensesplitter.domain.split.ExactSplitCalculator
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ExactSplitCalculatorTest {

    @Test
    fun exactSplitReturnsCorrectAmounts() {
        val exactAmounts = mapOf(
            "amit" to 600.0,
            "sumit" to 400.0
        )
        val calculator = ExactSplitCalculator(exactAmounts)

        val result = calculator.calculate(
            totalAmount = 1000.0,
            participants = listOf("amit", "sumit")
        )

        assertEquals(600.0, result["amit"])
        assertEquals(400.0, result["sumit"])
    }

    @Test
    fun exactSplitThrowsWhenAmountsDoNotSumToTotal() {
        val exactAmounts = mapOf(
            "amit" to 600.0,
            "sumit" to 300.0
        )
        val calculator = ExactSplitCalculator(exactAmounts)

        assertFailsWith<IllegalArgumentException> {
            calculator.calculate(
                totalAmount = 1000.0,
                participants = listOf("amit", "sumit")
            )
        }
    }

    @Test
    fun exactSplitFiltersToParticipantsOnly() {
        val exactAmounts = mapOf(
            "amit" to 600.0,
            "sumit" to 400.0
        )
        val calculator = ExactSplitCalculator(exactAmounts)

        val result = calculator.calculate(
            totalAmount = 600.0,
            participants = listOf("amit")
        )

        assertEquals(600.0, result["amit"])
        assertEquals(null, result["sumit"])
    }
}
