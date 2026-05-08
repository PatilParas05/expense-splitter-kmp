package di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.paraspatil.expensesplitter.data.AndroidExpenseRepository
import org.paraspatil.expensesplitter.data.ExpenseRepository
import org.paraspatil.expensesplitter.data.createDatabase

val androidModule = module {
    single { createDatabase(androidContext()) }
    single { get <org.paraspatil.expensesplitter.data.AppDatabase>().expenseDao() }
    single<ExpenseRepository>{ AndroidExpenseRepository(get()) }
}