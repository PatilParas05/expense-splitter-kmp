package di

import org.koin.dsl.module
import org.paraspatil.expensesplitter.data.DesktopExpenseRepository
import org.paraspatil.expensesplitter.data.ExpenseEntity
import org.paraspatil.expensesplitter.data.ExpenseRepository
import org.paraspatil.expensesplitter.data.createDatabase

val desktopModule = module {
    single { createDatabase() }
    single { get <org.paraspatil.expensesplitter.data.AppDatabase>().expenseDao() }
    single <ExpenseRepository> { DesktopExpenseRepository(get()) }
}