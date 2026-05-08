package di

import org.koin.dsl.module
import org.paraspatil.expensesplitter.data.ExpenseRepository
import org.paraspatil.expensesplitter.data.WebExpenseRepository

val webModule = module{
    single<ExpenseRepository> { WebExpenseRepository() }
}