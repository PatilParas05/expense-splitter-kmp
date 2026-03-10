package org.paraspatil.expensesplitter.domain.split

import kotlin.math.abs

class PercentageSplitCalculator(
    private val percentages: Map<String, Double>
) : SplitCalculator {

    override fun calculate(
        totalAmount: Double,
        participants: List<String>
    ): Map<String, Double> {
        val filteredPercentages = percentages.filterKeys { it in participants }
        val totalPercentage = filteredPercentages.values.sum()
        require(abs(totalPercentage - 100.0) < 0.01) {
            "Percentages ($totalPercentage) must sum to 100"
        }
        return filteredPercentages.mapValues { (_, percentage) ->
            totalAmount * percentage / 100.0
        }
    }
}
