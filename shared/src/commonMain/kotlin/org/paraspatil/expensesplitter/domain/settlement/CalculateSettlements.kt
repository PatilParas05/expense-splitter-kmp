package org.paraspatil.expensesplitter.domain.settlement

fun calculateSettlements(
    balances: Map<String, Double>
): List<Settlements> {
        val creditors = ArrayDeque(
            balances.filter { it.value > 0 }
                .map { it.key to it.value }
        )
        val debtors = ArrayDeque(
            balances.filter { it.value < 0 }
                .map { it.key to -it.value }
        )
    val settlements = mutableListOf<Settlements>()

    while (creditors.isNotEmpty() && debtors.isNotEmpty()) {

        val (creditor, creditAmount) = creditors.removeFirst()
        val (debtor, debtAmount) = debtors.removeFirst()

        val settledAmount = minOf(creditAmount, debtAmount)

        settlements.add(
            Settlements(
                from = debtor,
                to = creditor,
               amount =  settledAmount
            )
        )

        if (creditAmount > settledAmount) {
            creditors.addFirst(creditor to creditAmount - settledAmount)
        }

        if (debtAmount > settledAmount) {
            debtors.addFirst(debtor to debtAmount - settledAmount)
        }
    }

    return settlements
}