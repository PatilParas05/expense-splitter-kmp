package org.paraspatil.expensesplitter.ui.components.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.paraspatil.expensesplitter.domain.split.SplitTypeInfo
import org.paraspatil.expensesplitter.domain.split.SplitTypes
import org.paraspatil.expensesplitter.presentation.ExpenseUiState
import org.paraspatil.expensesplitter.presentation.ExpenseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplitMethodsSelector(
    viewModel: ExpenseViewModel,
    uiState: ExpenseUiState
) {
   Column(
       verticalArrangement = Arrangement.spacedBy(12.dp),
       modifier = Modifier.fillMaxWidth()
   ) {
       Text(
           "Split Method",
           style = MaterialTheme.typography.labelLarge,
           fontWeight = FontWeight.SemiBold
       )
       Row (
           modifier = Modifier.fillMaxWidth()
               .horizontalScroll(androidx.compose.foundation.rememberScrollState()),
           horizontalArrangement = Arrangement.spacedBy(8.dp)
       ){
           SplitTypes.getAll().forEach { splitTypeInfo ->
               SplitMethodCard(
                   splitTypeInfo = splitTypeInfo,
                   isSelected = uiState.splitType == splitTypeInfo.type,
                   onClick = { viewModel.updateSplitType(splitTypeInfo.type) }
               )
           }
       }
       val selectedInfo = SplitTypes.getByType(uiState.splitType)
       Surface(
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
           shape = RoundedCornerShape(8.dp),
           modifier = Modifier.fillMaxWidth( )
       ){
           Column (
               modifier = Modifier.padding(12.dp),
               verticalArrangement = Arrangement.spacedBy(4.dp)
           ){
               Text(
                   selectedInfo.displayName,
                   style = MaterialTheme.typography.labelMedium,
                   fontWeight = FontWeight.Bold,
                   color = MaterialTheme.colorScheme.onPrimaryContainer
               )
               Text(
                   selectedInfo.description,
                   style = MaterialTheme.typography.labelSmall,
                   color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
               )

           }
       }

   }

}

@Composable
private fun SplitMethodCard(
    splitTypeInfo: SplitTypeInfo,
    isSelected: Boolean,
    onClick: () -> Unit
){
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable ( onClick = onClick )
            .width(120.dp),
        color = if (isSelected)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if (isSelected){
                Icon(
                    Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            Text(
                splitTypeInfo.displayName,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                color = if (isSelected)
                MaterialTheme.colorScheme.onPrimary
                else
                MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )
        }
    }
}

@Preview
@Composable
fun SplitMethodCardPreview(){
    SplitMethodCard(
        splitTypeInfo = SplitTypes.EQUAL,
        isSelected = true,
        onClick = {}
    )
}


