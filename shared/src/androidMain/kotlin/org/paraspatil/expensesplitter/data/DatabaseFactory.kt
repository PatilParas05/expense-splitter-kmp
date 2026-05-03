package org.paraspatil.expensesplitter.data

import android.content.Context
import androidx.room.Room

fun createDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "expense_splitter.db"
    ).build()
}