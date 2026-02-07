package org.paraspatil.expensesplitter.domain.split

interface SplitCalculator {
    fun calculate(
        totalAmount: Double,
        participants: List<String>,
    ): Map<String, Double>
}
