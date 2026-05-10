package org.paraspatil.expensesplitter.presentation

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.paraspatil.expensesplitter.data.ExpenseRepository
import org.paraspatil.expensesplitter.domain.model.Expense
import org.paraspatil.expensesplitter.domain.model.Person
import org.paraspatil.expensesplitter.domain.split.EqualSplitCalculator
import org.paraspatil.expensesplitter.domain.usecase.CalculateExpenseUseCase

class ExpenseViewModel (
    private val repository: ExpenseRepository
) {
    private val scope = CoroutineScope(Dispatchers.Main)
    private val _uiState = MutableStateFlow(ExpenseUiState())
    val uiState: StateFlow<ExpenseUiState> = _uiState.asStateFlow()

    private val calculateExpenseUseCase = CalculateExpenseUseCase()
    private val splitCalculator = EqualSplitCalculator()

    init {
        scope.launch {
            delay(2000)
            _uiState.value = _uiState.value.copy(isSplashScreenFinished = true)
        }
        scope.launch {
            combine(
                repository.getAllPeople(),
                repository.getAllExpenses()
            ) { people, expenses ->
                Pair(people, expenses)
            }.collect { (people, expenses) ->
                val result = calculateExpenseUseCase(people, expenses)
                _uiState.value = _uiState.value.copy(
                    people = people,
                    expenses = expenses,
                    balances = result.balances,
                    settlements = result.settlements
                )
            }
        }
    }

    fun addPerson(name: String) {
        if (name.isBlank()) return
        val currentState = _uiState.value
        val newPerson = Person(
            id = uuid4().toString(),
            name = name.trim()
        )
        scope.launch {
            repository.insertPerson(newPerson)
        }
        _uiState.value = currentState.copy(
            people = currentState.people + newPerson,
            newPersonName = "",
            selectedPaidBy = if (currentState.selectedPaidBy.isEmpty()) newPerson.id else currentState.selectedPaidBy
        )
    }

    fun updatePersonName(name: String) {
        _uiState.value = _uiState.value.copy(newPersonName = name)
    }

    fun updateExpenseAmount(amount: String) {
        _uiState.value = _uiState.value.copy(expenseAmount = amount)
    }

    fun updateExpenseDescription(description: String) {
        _uiState.value = _uiState.value.copy(expenseDescription = description)
    }

    fun selectedPaidBy(personId: String) {
        _uiState.value = _uiState.value.copy(selectedPaidBy = personId)
    }

    fun addExpense() {
        val currentState = _uiState.value

        if (currentState.expenseAmount.isBlank() || currentState.selectedPaidBy.isBlank()) {
            _uiState.value = currentState.copy(errorMessages = "Please enter amount and select paid by")
            return
        }

        try {
            val amount = currentState.expenseAmount.toDouble()
            if (amount <= 0) {
                _uiState.value = currentState.copy(errorMessages = "Amount must be greater than 0")
                return
            }

            val splits = splitCalculator.calculateSplits(
                amount = amount,
                personIds = currentState.people.map { it.id },
            )

            val expense = Expense(
                amount = amount,
                paidBy = currentState.selectedPaidBy,
                splits = splits,
                description = currentState.expenseDescription
            )
            scope.launch {
                repository.insertExpense(expense)
            }

            val updatedExpenses = currentState.expenses + expense

            val result = calculateExpenseUseCase(
                people = currentState.people,
                expenses = updatedExpenses
            )

            _uiState.value = currentState.copy(
                expenses = updatedExpenses,
                balances = result.balances,
                settlements = result.settlements,
                expenseAmount = "",
                expenseDescription = "",
                errorMessages = result.error
            )
        } catch (e: Exception) {
            _uiState.value = currentState.copy(errorMessages = "Invalid input: ${e.message}")
        }
    }

    fun removePerson(personId: String) {
        val currentState = _uiState.value
        val updatedPeople = currentState.people.filter { it.id != personId }
        val updatedExpenses = currentState.expenses.filter { expense ->
            expense.paidBy != personId && expense.splits.none { it.personId == personId }
        }
        scope.launch {
            repository.deletePerson(personId)
        }
        
        val result = calculateExpenseUseCase(updatedPeople, updatedExpenses)

        _uiState.value = currentState.copy(
            people = updatedPeople,
            expenses = updatedExpenses,
            balances = result.balances,
            settlements = result.settlements,
            selectedPaidBy = if (currentState.selectedPaidBy == personId) "" else currentState.selectedPaidBy
        )
    }

    fun reset() {
        scope.launch {
            repository.reset()
        }
        _uiState.value = ExpenseUiState()
    }
}
