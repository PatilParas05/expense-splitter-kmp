package org.paraspatil.expensesplitter.domain.settlement

import kotlin.math.abs
import kotlin.math.round

fun calculateSettlements(
    balances: Map<String, Double>
): List<Settlements> {

    val settlements = mutableListOf<Settlements>()

    val mutableBalances = balances.mapValues { (_,v)->
        round(v*100)/100
    }.toMutableMap()

    while (true) {
        val debtor = mutableBalances.entries.find { it.value < -0.01 } ?: break
        val creditor = mutableBalances.entries.find { it.value > 0.01 } ?: break

        if (debtor == null || creditor == null) break

        val settledAmount = minOf(abs(debtor.value),creditor.value)

        settlements.add(
            Settlements(
                fromPersonId = debtor.key,
                toPersonId = creditor.key,
               amount =  round(settledAmount * 100)/100
            )
        )
        mutableBalances[debtor.key] =round((debtor.value + settledAmount) * 100)/100
        mutableBalances[creditor.key] =round((creditor.value - settledAmount) * 100)/100
    }

    return settlements
}