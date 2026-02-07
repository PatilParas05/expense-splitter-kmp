package org.paraspatil.expensesplitter.domain.split

 sealed class SplitType {
     data object Equal : SplitType()
     data class Exact (val amount: Map<String, Double>): SplitType()
     data class Percentage (val percentage: Map<String, Double>): SplitType()

}