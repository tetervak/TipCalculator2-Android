package ca.tetervak.tipcalculator2.model

import java.util.*

data class TipData (
    val costOfService: Double,
    val serviceQuality: ServiceQuality,
    val roundUpTip: Boolean,
    val tipAmount: Double,
    val billTotal: Double,
    val date: Date = Date(),
    val id: Int = 0,
)
