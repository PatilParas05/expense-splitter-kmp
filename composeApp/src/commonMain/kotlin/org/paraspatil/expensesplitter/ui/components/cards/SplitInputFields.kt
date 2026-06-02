package org.paraspatil.expensesplitter.ui.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.paraspatil.expensesplitter.domain.model.Person
import org.paraspatil.expensesplitter.domain.split.SplitTypes
import org.paraspatil.expensesplitter.presentation.ExpenseUiState
import org.paraspatil.expensesplitter.presentation.ExpenseViewModel
import org.paraspatil.expensesplitter.ui.components.utils.format


@Composable
fun SplitInputFields(
    viewModel: ExpenseViewModel,
    uiState: ExpenseUiState,
    people: List<Person>
){
    val selectedSplitType = uiState.splitType

    if (selectedSplitType == "EQUAL"){
        return
    }
    Column (
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        val splitTypeInfo = SplitTypes.getByType(selectedSplitType)

        Text(
            "Enter ${splitTypeInfo.inputLabel}",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold
        )
        Surface (
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                "📝${splitTypeInfo.hint}",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(8.dp),
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
        people.forEach { person ->
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ){
                Text(
                    person.name,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.width(80.dp),
                    fontWeight = FontWeight.SemiBold
                )
                OutlinedTextField(
                    value = uiState.splitValues[person.id] ?: "",
                    onValueChange = {newValue ->
                        viewModel.updateSplitValue(person.id, newValue) },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    label = { Text(
                        when(selectedSplitType){
                            "PERCENTAGE" -> "%"
                            "SHARES" -> "Shares"
                            else -> "Amount"
                        }
                    ) },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    textStyle = MaterialTheme.typography.bodySmall
                )
            }
        }

        if (selectedSplitType == "PERCENTAGE") {
            val totalPercentage = uiState.splitValues.values
                .mapNotNull { it.toDoubleOrNull() }
                .sum()

            val isValid = totalPercentage in 99.9..100.1

            Surface(
                color = if (isValid)
                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                else
                    MaterialTheme.colorScheme.errorContainer,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (isValid)
                        "✓ Total: ${totalPercentage.format(1)}% (Valid)"
                    else
                        "✗ Total: ${totalPercentage.format(1)}% (Must be 100%)",

                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(8.dp),
                    color = if (isValid)
                        MaterialTheme.colorScheme.onPrimaryContainer
                    else
                        MaterialTheme.colorScheme.onErrorContainer,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

    }
}
