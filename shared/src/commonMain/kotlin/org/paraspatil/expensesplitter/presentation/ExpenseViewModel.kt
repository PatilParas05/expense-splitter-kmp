package org.paraspatil.expensesplitter.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.paraspatil.expensesplitter.domain.model.Expense
import org.paraspatil.expensesplitter.domain.model.Person
import org.paraspatil.expensesplitter.domain.usecase.CalculateExpenseUseCase

class ExpenseViewModel (
    private val useCase: CalculateExpenseUseCase
){
    private val scope = CoroutineScope(Dispatchers.Default)

    private val _uiState = MutableStateFlow(ExpenseUiState())
    val uiState : StateFlow<ExpenseUiState> = _uiState

    fun calculate(
        expenses: List<Expense>,
        persons: List<Person>
    ){
        scope.launch {
            val result = useCase.execute(expenses, persons)
            _uiState.value = ExpenseUiState(
                balances = result.balances,
                settlements = result.settlements
            )
        }
    }

}
