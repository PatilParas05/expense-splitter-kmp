package org.paraspatil.expensesplitter.split

import org.paraspatil.expensesplitter.domain.split.PercentageSplitCalculator
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PercentageSplitCalculatorTest {

    @Test
    fun percentageSplitCalculatesCorrectAmounts() {
        val percentages = mapOf(
            "amit" to 60.0,
            "sumit" to 40.0
        )
        val calculator = PercentageSplitCalculator(percentages)

        val result = calculator.calculate(
            totalAmount = 1000.0,
            participants = listOf("amit", "sumit")
        )

        assertEquals(600.0, result["amit"])
        assertEquals(400.0, result["sumit"])
    }

    @Test
    fun percentageSplitThrowsWhenPercentagesDoNotSumTo100() {
        val percentages = mapOf(
            "amit" to 60.0,
            "sumit" to 30.0
        )
        val calculator = PercentageSplitCalculator(percentages)

        assertFailsWith<IllegalArgumentException> {
            calculator.calculate(
                totalAmount = 1000.0,
                participants = listOf("amit", "sumit")
            )
        }
    }

    @Test
    fun percentageSplitWorksWithThreePeople() {
        val percentages = mapOf(
            "amit" to 50.0,
            "sumit" to 30.0,
            "vinit" to 20.0
        )
        val calculator = PercentageSplitCalculator(percentages)

        val result = calculator.calculate(
            totalAmount = 300.0,
            participants = listOf("amit", "sumit", "vinit")
        )

        assertEquals(150.0, result["amit"])
        assertEquals(90.0, result["sumit"])
        assertEquals(60.0, result["vinit"])
    }
}
