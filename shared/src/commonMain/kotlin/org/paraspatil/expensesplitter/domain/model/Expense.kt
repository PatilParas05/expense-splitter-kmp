package org.paraspatil.expensesplitter.domain.model

import com.benasher44.uuid.uuid4
data class Expense(
    val id : String = uuid4().toString(),
    val amount : Double,
    val paidBy : String,
    val splits : List<Split> =emptyList(),
    val description: String = ""
)
