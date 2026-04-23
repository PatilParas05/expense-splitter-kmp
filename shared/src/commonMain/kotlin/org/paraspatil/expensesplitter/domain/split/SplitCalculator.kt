package org.paraspatil.expensesplitter.domain.split

import org.paraspatil.expensesplitter.domain.model.Split

interface SplitCalculator {
    fun calculateSplits(
        amount: Double,
        personIds: List<String>,
        params : Map<String, Double> = emptyMap()
    ): List<Split>
}
