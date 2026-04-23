package org.paraspatil.expensesplitter.domain.split

import org.paraspatil.expensesplitter.domain.model.Split

class ExactSplitCalculator: SplitCalculator {
    override fun calculateSplits(
        amount: Double,
        personIds: List<String>,
        params: Map<String, Double>
    ): List<Split> {
        return personIds.mapNotNull { personId ->
            val splitAmount = params[personId] ?: return@mapNotNull null
            Split(personId, splitAmount)
        }
    }
}
