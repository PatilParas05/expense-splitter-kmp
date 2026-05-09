package org.paraspatil.expensesplitter.data

import androidx.room.TypeConverter
import org.paraspatil.expensesplitter.domain.model.Split

class Converters {
    @TypeConverter
    fun fromSplitsJson(json : String): List<Split> {
        if(json == "[]" || json.isBlank()) return emptyList()
        return json.split("|").mapNotNull { entry ->
            val parts = entry.split(":")
            if (parts.size == 2){
                Split(
                    personId = parts[0],
                    amount = parts[1].toDoubleOrNull() ?: return@mapNotNull null
                )
            }else null
        }
    }
    @TypeConverter
    fun toSplitsJson(splits: List<Split>): String {
        if (splits.isEmpty()) return "[]"
        return splits.joinToString("|"){ "${it.personId}:${it.amount}"}
    }
}