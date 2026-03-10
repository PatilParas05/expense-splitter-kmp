package org.paraspatil.expensesplitter.domain.split

import kotlin.math.abs

class ExactSplitCalculator(
    private val exactAmounts: Map<String, Double>
) : SplitCalculator {

    override fun calculate(
        totalAmount: Double,
        participants: List<String>
    ): Map<String, Double> {
        val filteredAmounts = exactAmounts.filterKeys { it in participants }
        val sum = filteredAmounts.values.sum()
        require(abs(sum - totalAmount) < 0.01) {
            "Exact amounts ($sum) must sum to total amount ($totalAmount)"
        }
        return filteredAmounts
    }
}
