package ca.tetervak.tipcalculator2

import ca.tetervak.tipcalculator2.model.ServiceQuality

data class InputUiState(
    val costOfService: String = "",
    val serviceQuality: ServiceQuality = ServiceQuality.GOOD,
    val roundUpTip: Boolean = true
)
