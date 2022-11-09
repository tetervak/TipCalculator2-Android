package ca.tetervak.tipcalculator2.model

fun calculateTip(
    costOfService: Double,
    serviceQuality: ServiceQuality = ServiceQuality.OK,
    roundUpTip: Boolean = true
): TipData {
    var tipAmount = costOfService * tipPercentage(serviceQuality)
    if (roundUpTip) {
        tipAmount = kotlin.math.ceil(tipAmount)
    }
    return TipData(
        costOfService = costOfService,
        serviceQuality = serviceQuality,
        roundUpTip = roundUpTip,
        tipAmount = tipAmount,
        billTotal = costOfService + tipAmount
    )
}

private fun tipPercentage(serviceQuality: ServiceQuality): Double =
    when (serviceQuality) {
        ServiceQuality.OK -> 0.15
        ServiceQuality.GOOD -> 0.18
        ServiceQuality.AMAZING -> 0.20
    }