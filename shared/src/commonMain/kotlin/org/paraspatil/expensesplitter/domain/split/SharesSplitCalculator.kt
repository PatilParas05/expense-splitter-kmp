package org.paraspatil.expensesplitter.domain.split

import org.paraspatil.expensesplitter.domain.model.Split
import kotlin.math.round

class SharesSplitCalculator : SplitCalculator {
    override fun calculateSplits(
        amount: Double,
        personIds: List<String>,
        params: Map<String, Double>
    ): List<Split> {
        val shares = personIds.mapNotNull { personId ->
            val share = params[personId] ?: return@mapNotNull null
            personId to share
        }.toMap()

        if (shares.isEmpty()) return emptyList()

        val totalShares = shares.values.sum()
        if (totalShares == 0.0) return emptyList()

        return personIds.map { personId ->
            val share = shares[personId] ?: 0.0
            val splitAmount = (share / totalShares) * amount
            val roundedAmount = round(splitAmount * 100) / 100
            Split(personId, roundedAmount)
        }
    }
}