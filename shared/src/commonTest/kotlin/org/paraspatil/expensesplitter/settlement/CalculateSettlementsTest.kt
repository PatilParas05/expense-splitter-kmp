package org.paraspatil.expensesplitter.settlement

import org.paraspatil.expensesplitter.domain.settlement.Settlements
import org.paraspatil.expensesplitter.domain.settlement.calculateSettlements
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateSettlementsTest {
    @Test
    fun simplestSettlementTest() {
        val balances = mapOf(
            "amit" to 300.0,
            "sumit" to -200.0,
            "vinit" to -100.0
        )
        val result = calculateSettlements(balances)
        assertEquals(
            listOf(
                Settlements("sumit", "amit", 200.0),
                Settlements("vinit", "amit", 100.0)
            ),
            result
        )

    }
}