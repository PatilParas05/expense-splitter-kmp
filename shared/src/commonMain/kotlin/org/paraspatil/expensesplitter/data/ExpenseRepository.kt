package org.paraspatil.expensesplitter.data

import kotlinx.coroutines.flow.Flow
import org.paraspatil.expensesplitter.domain.model.Expense
import org.paraspatil.expensesplitter.domain.model.ExpenseGroup
import org.paraspatil.expensesplitter.domain.model.Person

interface ExpenseRepository {
    fun getAllPeople(): Flow<List<Person>>
    fun getAllExpenses(): Flow<List<Expense>>
    suspend fun insertPerson(person: Person)
    suspend fun insertExpense(expense: Expense)
    suspend fun deletePerson(id: String)
    suspend fun reset()

    fun getAllGroups(): Flow<List<ExpenseGroup>>
    suspend fun createNewGroup(id: String, name: String)
    suspend fun updateGroupName(groupId: String, newName: String)
    suspend fun switchToGroup(groupId: String)
    suspend fun getGroupById(groupId: String): ExpenseGroup?
}