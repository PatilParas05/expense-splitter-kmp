package org.paraspatil.expensesplitter.data

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.io.File

fun createDatabase() : AppDatabase {
    val dbFile = File(System.getProperty("user.home"), "expense_splitter.db")
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}