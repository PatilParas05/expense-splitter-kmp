package org.paraspatil.expensesplitter.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class PersonEntity(
    @PrimaryKey val id: String,
    val name: String
)

@Entity
data class ExpenseEntity(
    @PrimaryKey val id: String,
    val amount: Double,
    val paidBy: String,
    val description: String
)
