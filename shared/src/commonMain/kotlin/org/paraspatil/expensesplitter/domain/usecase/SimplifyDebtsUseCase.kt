package org.paraspatil.expensesplitter.domain.usecase

import org.paraspatil.expensesplitter.domain.settlement.Settlements
import kotlin.math.abs
import kotlin.math.min

class SimplifyDebtsUseCase{
    operator fun invoke (balance:Map<String, Double>): List<Settlements>{
        val debtors = balance.filter { it.value < -0.01 }
            .map { it.key to abs(it.value) }
            .sortedByDescending { it.second }
            .toMutableList()

        val creditors = balance.filter { it.value > 0.01 }
            .map { it.key to it.value }
            .sortedByDescending { it.second }
            .toMutableList()

        val simplifiedSettlements = mutableListOf<Settlements>()

        var dIdx = 0
        var cIdx = 0

        while (dIdx < debtors.size && cIdx < creditors.size){
            val debtor = debtors[dIdx]
            val creditor = creditors[cIdx]

            val amountToSettle = min(debtor.second, creditor.second)
            if (amountToSettle > 0.01){
                simplifiedSettlements.add(
                    Settlements(
                       fromPersonId =  debtor.first,
                       toPersonId = creditor.first,
                       amount = amountToSettle
                    )
                )
            }
            debtors[dIdx] = debtor.first to (debtor.second - amountToSettle)
            creditors[cIdx] = creditor.first to (creditor.second - amountToSettle)

            if (debtors[dIdx].second <= 0.01) dIdx++
            if (creditors[cIdx].second <= 0.01) cIdx++
        }
        return simplifiedSettlements
    }
}