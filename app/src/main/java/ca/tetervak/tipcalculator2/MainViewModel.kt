package ca.tetervak.tipcalculator2

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ca.tetervak.tipcalculator2.model.ServiceQuality
import ca.tetervak.tipcalculator2.model.calculateTip

class MainViewModel : ViewModel() {

    // output states
    private val _outputUiState = mutableStateOf(OutputUiSate())
    val outputUiSate: State<OutputUiSate> = _outputUiState

    // input states
    private val _inputUiState = mutableStateOf(
        InputUiState().copy(
            onChangeOfCostOfService = { setCostOfService(it) },
            onChangeOfServiceQuality = { setServiceQuality(it) },
            onChangeOfRoundUpTip = { setRoundUpTip(it) }
        )
    )
    val inputUiState: State<InputUiState> = _inputUiState

    val recalculateOutputs = {
        val tipData = calculateTip(
            costOfService = inputUiState.value.costOfService.toDoubleOrNull() ?: 0.0,
            serviceQuality = inputUiState.value.serviceQuality,
            roundUpTip = inputUiState.value.roundUpTip
        )
        _outputUiState.value = OutputUiSate(
            tipAmount = tipData.tipAmount,
            billTotal = tipData.billTotal
        )
    }

    private fun setCostOfService(costOfService: String) {
        val newInputUiState = inputUiState.value.copy(costOfService = costOfService)
        _inputUiState.value = newInputUiState
        recalculateOutputs()
    }

    private fun setServiceQuality(serviceQuality: ServiceQuality) {
        val newInputUiState = inputUiState.value.copy(serviceQuality = serviceQuality)
        _inputUiState.value = newInputUiState
        recalculateOutputs()
    }

    private fun setRoundUpTip(roundUpTip: Boolean) {
        val newInputUiState = inputUiState.value.copy(roundUpTip = roundUpTip)
        _inputUiState.value = newInputUiState
        recalculateOutputs()
    }

}