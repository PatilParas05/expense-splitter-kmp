package org.paraspatil.expensesplitter.di

import org.koin.dsl.module
import org.paraspatil.expensesplitter.presentation.ExpenseViewModel

val commonModule = module {
    factory { ExpenseViewModel(get()) }
}