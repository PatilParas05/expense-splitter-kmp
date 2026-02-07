package org.paraspatil.expensesplitter.domain.split

class EqualSplitCalculator: SplitCalculator {
    override fun calculate(
        totalAmount: Double,
        participants: List<String>,
    ): Map<String, Double> {
        val perPerson=totalAmount/participants.size
        return participants.associateWith { perPerson }

    }
}