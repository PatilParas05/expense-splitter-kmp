package org.paraspatil.expensesplitter.domain.split

import org.paraspatil.expensesplitter.domain.model.Split
import kotlin.math.round

class EqualSplitCalculator : SplitCalculator {
    override fun calculateSplits(
        amount: Double,
        personIds: List<String>,
        params: Map<String, Double>
    ): List<Split> {
        if (personIds.isEmpty()) return emptyList()

        val perPerson = amount / personIds.size
        val roundedAmount = round(perPerson * 100) / 100

        return personIds.map { personId ->
            Split(personId, roundedAmount)
        }
    }
}
