package org.paraspatil.expensesplitter.data

import kotlinx.coroutines.flow.Flow
import org.paraspatil.expensesplitter.domain.model.Expense
import org.paraspatil.expensesplitter.domain.model.Person

interface ExpenseRepository {
    fun getAllPeople(): Flow<List<Person>>
    fun getAllExpenses(): Flow<List<Expense>>
    suspend fun insertPerson(person: Person)
    suspend fun insertExpense(expense: Expense)
    suspend fun deletePerson(id: String)
    suspend fun reset()
}