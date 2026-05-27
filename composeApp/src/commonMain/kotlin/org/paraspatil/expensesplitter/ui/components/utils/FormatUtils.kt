package org.paraspatil.expensesplitter.ui.components.utils


import kotlin.math.roundToInt

fun Double.format(decimals: Int): String {
    val integerPart = this.toLong()
    if (decimals <= 0) return integerPart.toString()

    val multiplier = 1.0
    var factor = 1
    repeat(decimals) { factor *= 10 }

    val fractionalPart = ((this - integerPart) * factor).roundToInt()
    val fractionalString = fractionalPart.toString().padStart(decimals, '0')

    return "$integerPart.$fractionalString"
}