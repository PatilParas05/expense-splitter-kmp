package org.paraspatil.expensesplitter.domain.split

import org.paraspatil.expensesplitter.domain.model.Split
import kotlin.math.round

class PercentageSplitCalculator : SplitCalculator {
    override fun calculateSplits(
        amount: Double,
        personIds: List<String>,
        params: Map<String, Double>
    ): List<Split> {
        return personIds.map { personId ->
            val percentage = params[personId] ?: 0.0
            val splitAmount = amount * (percentage / 100)
            val roundedAmount = round(splitAmount * 100) / 100
            Split(personId, roundedAmount)
        }
    }
}
