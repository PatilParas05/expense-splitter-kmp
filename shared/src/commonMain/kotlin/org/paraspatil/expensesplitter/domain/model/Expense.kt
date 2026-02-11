package org.paraspatil.expensesplitter.domain.model

data class Expense(
    val id : String,
    val amount : Double,
    val paidBy : String,
    val splits : List<Split>,
    val sharedWith : List<Person>
)
