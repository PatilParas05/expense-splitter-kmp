package org.paraspatil.expensesplitter.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import org.paraspatil.expensesplitter.domain.model.Expense
import org.paraspatil.expensesplitter.domain.model.ExpenseGroup
import org.paraspatil.expensesplitter.domain.model.Person
import kotlin.collections.map

class DesktopExpenseRepository (
    private val dao: ExpenseDao
) : ExpenseRepository{
    private val activeGroupId = MutableStateFlow("default")

    override fun getAllPeople(): Flow<List<Person>> =
    dao.getAllPeople().map{
        list -> list.map{
            Person(id = it.id, name = it.name)
        }
    }

    override fun getAllExpenses(): Flow<List<Expense>> =
        dao.getAllExpenses().map{
            list -> list.map{
                Expense(
                    id = it.id,
                    amount = it.amount,
                    paidBy = it.paidBy,
                    description = it.description,
                    splits = Converters().fromSplitsJson(it.splitsJson)
                )
            }
        }
    override suspend fun insertPerson(person: Person) =
        dao.insertPerson(PersonEntity(id = person.id, name = person.name))

    override suspend fun insertExpense(expense: Expense) =
        dao.insertExpense(ExpenseEntity(
            id = expense.id,
            amount = expense.amount,
            paidBy = expense.paidBy,
            description = expense.description,
            splitsJson = Converters().toSplitsJson(expense.splits)
            )
        )

    override suspend fun deletePerson(id: String) =dao.deletePerson(id)

    override suspend fun reset() {
        dao.deleteAllPeople()
        dao.deleteAllExpenses()
    }
    override fun getAllGroups(): Flow<List<ExpenseGroup>> =
        dao.getAllGroups()
            .map { list -> list.map { ExpenseGroup(id = it.id, name = it.name, it.timestamp) } }

    override suspend fun createNewGroup(id: String, name: String) =
        dao.insertGroup(ExpenseGroupEntity(id = id, name = name, System.currentTimeMillis()))

    override suspend fun updateGroupName(groupId: String, newName: String) =
        dao.updateGroupName(groupId,newName)

    override suspend fun switchToGroup(groupId: String) {
        activeGroupId.value = groupId
    }
    override suspend fun getGroupById(groupId: String): ExpenseGroup? =
        dao.getGroupById(groupId)?.let { ExpenseGroup(it.id, it.name, it.timestamp) }

}