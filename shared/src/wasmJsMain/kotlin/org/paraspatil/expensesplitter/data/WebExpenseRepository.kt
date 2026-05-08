package org.paraspatil.expensesplitter.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.paraspatil.expensesplitter.domain.model.Expense
import org.paraspatil.expensesplitter.domain.model.Person
import kotlin.collections.emptyList

class WebExpenseRepository : ExpenseRepository {
    private val peopleFlow = MutableStateFlow<List<Person>>(emptyList())
    private val expensesFlow = MutableStateFlow<List<Expense>>(emptyList())

    override fun getAllPeople(): Flow<List<Person>> = peopleFlow
    override fun getAllExpenses(): Flow<List<Expense>> = expensesFlow

    override suspend fun insertPerson(person: Person) {
        val updated = peopleFlow.value + person
        peopleFlow.value = updated
    }

    override suspend fun insertExpense(expense: Expense) {
        val updated = expensesFlow.value + expense
        expensesFlow.value = updated
    }

    override suspend fun deletePerson(id: String) {
    peopleFlow.value = peopleFlow.value.filter { it.id != id }
    }

    override suspend fun reset() {
        peopleFlow.value = emptyList()
        expensesFlow.value = emptyList()
    }
}