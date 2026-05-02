package org.paraspatil.expensesplitter.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetPerson(person: PersonEntity)

    @Query("SELECT * FROM PersonEntity")
    fun getAllPeople(): Flow<List<PersonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM ExpenseEntity")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Query("DELETE FROM PersonEntity")
    suspend fun deleteAllPeople()

    @Query("DELETE FROM ExpenseEntity")
    suspend fun deleteAllExpenses()
}
@Database(entities = [PersonEntity::class,ExpenseEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase (){
    abstract fun expenseDao(): ExpenseDao
}