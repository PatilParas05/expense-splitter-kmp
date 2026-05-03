package org.paraspatil.expensesplitter.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.paraspatil.expensesplitter.domain.model.Expense
import org.paraspatil.expensesplitter.domain.model.Person

class AndroidExpenseRepository (
    private val dao: ExpenseDao
) : ExpenseRepository {

    override fun getAllPeople(): Flow<List<Person>> =
        dao.getAllPeople().map { list ->
            list.map { Person(id = it.id, name = it.name) }
        }

    override fun getAllExpenses(): Flow<List<Expense>> =
        dao.getAllExpenses().map { list ->
        list.map {
            Expense(
                id = it.id,
                amount = it.amount,
                paidBy = it.paidBy,
                description = it.description,
            )
        }
        }

    override suspend fun insertPerson(person: Person) =
        dao.insertPerson(PersonEntity(id = person.id, name = person.name))

    override suspend fun insertExpense(expense: Expense) =
        dao.insertExpense(
            ExpenseEntity(
                id = expense.id,
                amount = expense.amount,
                paidBy = expense.paidBy,
                description = expense.description
            )
        )

    override suspend fun deletePerson(id: String) =dao.deletePerson(id)

    override suspend fun reset() {
        dao.deleteAllPeople()
        dao.deleteAllExpenses()
    }
}