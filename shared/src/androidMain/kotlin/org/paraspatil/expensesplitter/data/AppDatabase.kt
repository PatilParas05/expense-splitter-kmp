package org.paraspatil.expensesplitter.data
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.flow.Flow
import org.paraspatil.expensesplitter.domain.model.ExpenseGroup

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: PersonEntity)

    @Query("SELECT * FROM PersonEntity")
    fun getAllPeople(): Flow<List<PersonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM ExpenseEntity")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Query("DELETE FROM PersonEntity WHERE id = :id")
    suspend fun deletePerson(id: String)

    @Query("DELETE FROM PersonEntity")
    suspend fun deleteAllPeople()

    @Query("DELETE FROM ExpenseEntity")
    suspend fun deleteAllExpenses()

    @Query("SELECT * FROM ExpenseGroupEntity ORDER BY timestamp DESC")
    fun getAllGroups(): Flow<List<ExpenseGroup>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: ExpenseGroup)

    @Query("UPDATE ExpenseGroupEntity SET name = :newName WHERE id = :groupId")
    suspend fun updateGroupName(groupId: String, newName: String)

    @Query("SELECT * FROM ExpenseGroupEntity WHERE id = :groupId")
    suspend fun getGroupById(groupId: String): ExpenseGroup?

}

@Database(entities = [PersonEntity::class, ExpenseEntity::class], version = 2,exportSchema = false )
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}