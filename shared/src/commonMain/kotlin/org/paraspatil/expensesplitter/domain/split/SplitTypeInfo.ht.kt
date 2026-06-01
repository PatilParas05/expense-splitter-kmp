package org.paraspatil.expensesplitter.domain.split

data class SplitTypeInfo(
    val type: String,
    val displayName: String,
    val description: String,
    val requiresInput: Boolean,
    val inputLabel: String = "",
    val hint: String ="",
)
object SplitTypes{
    val EQUAL = SplitTypeInfo(
        type = "EQUAL",
        displayName = "Equal Split",
        description = "Each person pays the same amount.",
        requiresInput = false,
        hint = "Perfect for splitting bills equally among everyone"
    )
    val EXACT = SplitTypeInfo(
        type = "EXACT",
        displayName = "Exact Amount",
        description = "Specify exact amount each person owes.",
        requiresInput = true,
        inputLabel = "Amount per person",
        hint = "Enter the exact amount for each participants"
    )
    val PERCENTAGE = SplitTypeInfo(
        type = "PERCENTAGE",
        displayName = "Percentage Split",
        description = "Split by percentage (must sum to 100%).",
        requiresInput = true,
        inputLabel = "Percentage(%)",
        hint = "Percentages must add up to 100%"
    )
    val SHARES = SplitTypeInfo(
        type = "SHARES",
        displayName = "Shares/Weights",
        description = "Split proportionally by shares.",
        requiresInput = true,
        inputLabel = "Number of share",
        hint = "E.g., 2 shares vs 1 share splits 2:1"
    )
    fun getAll() = listOf(EQUAL,EXACT,PERCENTAGE,SHARES)

    fun getByType(type: String): SplitTypeInfo = when (type){
        EQUAL.type -> EQUAL
        EXACT.type -> EXACT
        PERCENTAGE.type -> PERCENTAGE
        SHARES.type -> SHARES
        else -> EQUAL
    }
}