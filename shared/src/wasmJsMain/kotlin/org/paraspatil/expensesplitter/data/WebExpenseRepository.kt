package org.paraspatil.expensesplitter.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import org.paraspatil.expensesplitter.domain.model.Expense
import org.paraspatil.expensesplitter.domain.model.ExpenseGroup
import org.paraspatil.expensesplitter.domain.model.Person
import kotlin.collections.emptyList

class WebExpenseRepository : ExpenseRepository {
    private val groups = MutableStateFlow(listOf(ExpenseGroup("default", "Current Split")))
    private val activeGroupId = MutableStateFlow("default")

    private val peopleMap = MutableStateFlow(mapOf<String, List<Person>>("default" to emptyList()))
    private val expensesMap = MutableStateFlow(mapOf<String, List<Expense>>("default" to emptyList()))

    override fun getAllPeople(): Flow<List<Person>> = activeGroupId.map { peopleMap.value[it] ?: emptyList() }
    override fun getAllExpenses(): Flow<List<Expense>> = activeGroupId.map { expensesMap.value[it] ?: emptyList() }

    override suspend fun insertPerson(person: Person) {
        val current = peopleMap.value.toMutableMap()
        val list = (current[activeGroupId.value] ?: emptyList()) + person
        current[activeGroupId.value] = list
        peopleMap.value = current
    }

    override suspend fun insertExpense(expense: Expense) {
        val current = expensesMap.value.toMutableMap()
        val list = (current[activeGroupId.value] ?: emptyList()) + expense
        current[activeGroupId.value] = list
        expensesMap.value = current
    }

    override suspend fun deletePerson(id: String) {
        val currentPeople = peopleMap.value.toMutableMap()
        val currentExpenses = expensesMap.value.toMutableMap()
        currentPeople[activeGroupId.value] = currentPeople[activeGroupId.value]?.filter { it.id != id } ?: emptyList()
        currentExpenses[activeGroupId.value] = currentExpenses[activeGroupId.value]?.filter { it.paidBy != id } ?: emptyList()
        peopleMap.value = currentPeople
        expensesMap.value = currentExpenses
    }

    override suspend fun reset() {
        val p = peopleMap.value.toMutableMap()
        val e = expensesMap.value.toMutableMap()
        p[activeGroupId.value] = emptyList()
        e[activeGroupId.value] = emptyList()
        peopleMap.value = p
        expensesMap.value = e
    }

    override fun getAllGroups(): Flow<List<ExpenseGroup>> = groups

    override suspend fun getGroupById(groupId: String) = groups.value.find { it.id == groupId }

    override suspend fun createNewGroup(groupId: String, name: String) {
        groups.value = groups.value + ExpenseGroup(groupId, name)
    }

    override suspend fun updateGroupName(groupId: String, name: String) {
        groups.value = groups.value.map { if (it.id == groupId) it.copy(name = name) else it }
    }

    override suspend fun switchToGroup(groupId: String) {
        activeGroupId.value = groupId
    }
}