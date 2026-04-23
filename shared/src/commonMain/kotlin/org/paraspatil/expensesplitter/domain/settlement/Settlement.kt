package org.paraspatil.expensesplitter.domain.settlement

data class Settlements (
    val fromPersonId: String,
    val toPersonId: String,
    val amount: Double
)