package org.paraspatil.expensesplitter.presentation

import com.benasher44.uuid.uuid4
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.paraspatil.expensesplitter.domain.model.Expense
import org.paraspatil.expensesplitter.domain.model.Person
import org.paraspatil.expensesplitter.domain.model.Split
import org.paraspatil.expensesplitter.domain.usecase.CalculateExpenseUseCase

class ExpenseViewModel(
    private val useCase: CalculateExpenseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExpenseUiState())
    val uiState: StateFlow<ExpenseUiState> = _uiState

    fun addPerson(name: String) {
        val newPerson = Person(
            id = uuid4().toString(),
            name = name
        )
        _uiState.update {
            it.copy(persons = it.persons + newPerson)
        }
    }

    fun addExpense(
        amount: Double,
        paidBy: String,
    ) {
        val persons = _uiState.value.persons

        if (persons.isEmpty()) return

        val splitAmount = amount / persons.size

        val splits = persons.map {
            Split(
                personId = it.id,
                amount = splitAmount
            )
        }
        val expense = Expense(
            id = uuid4().toString(),
            amount = amount,
            paidBy = paidBy,
            sharedWith = persons,
            splits = splits

        )
        _uiState.update {
            it.copy(expenses = it.expenses + expense)
        }
    }

    fun calculate() {
        val result = useCase.execute(_uiState.value.expenses, _uiState.value.persons)

        _uiState.update {
            it.copy(settlements = result.settlements)

        }
    }
    fun addBalances(name: String, amount: Double) {
        _uiState.update { current ->
            current.copy(
                balances = current.balances + (name to amount)
            )
        }
    }
}
