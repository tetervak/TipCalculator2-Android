package ca.tetervak.tipcalculator2

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ca.tetervak.tipcalculator2.model.ServiceQuality
import ca.tetervak.tipcalculator2.model.calculateTip

class MainViewModel : ViewModel() {

    // output states
    private val _stateOutputUiState = mutableStateOf(OutputUiState())
    val stateOutputUiState: State<OutputUiState> = _stateOutputUiState

    // input states
    private val _stateInputUiState = mutableStateOf(
        InputUiState().copy(
            onChangeOfCostOfService = { setCostOfService(it) },
            onChangeOfServiceQuality = { setServiceQuality(it) },
            onChangeOfRoundUpTip = { setRoundUpTip(it) }
        )
    )
    val stateInputUiState: State<InputUiState> = _stateInputUiState

    val recalculateOutputs = {
        val tipData = calculateTip(
            costOfService = stateInputUiState.value.costOfService.toDoubleOrNull() ?: 0.0,
            serviceQuality = stateInputUiState.value.serviceQuality,
            roundUpTip = stateInputUiState.value.roundUpTip
        )
        _stateOutputUiState.value = OutputUiState(
            tipAmount = tipData.tipAmount,
            billTotal = tipData.billTotal
        )
    }

    private fun setCostOfService(costOfService: String) {
        val newInputUiState = stateInputUiState.value.copy(costOfService = costOfService)
        _stateInputUiState.value = newInputUiState
        recalculateOutputs()
    }

    private fun setServiceQuality(serviceQuality: ServiceQuality) {
        val newInputUiState = stateInputUiState.value.copy(serviceQuality = serviceQuality)
        _stateInputUiState.value = newInputUiState
        recalculateOutputs()
    }

    private fun setRoundUpTip(roundUpTip: Boolean) {
        val newInputUiState = stateInputUiState.value.copy(roundUpTip = roundUpTip)
        _stateInputUiState.value = newInputUiState
        recalculateOutputs()
    }

}