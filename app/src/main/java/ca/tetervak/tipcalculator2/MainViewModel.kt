package ca.tetervak.tipcalculator2

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ca.tetervak.tipcalculator2.model.ServiceQuality
import ca.tetervak.tipcalculator2.model.calculateTip

class MainViewModel : ViewModel() {

    private val _stateCalculatorUiState = mutableStateOf(
        CalculatorUiState(
            inputUiState = InputUiState().copy(
                onChangeOfCostOfService = { setCostOfService(it) },
                onChangeOfServiceQuality = { setServiceQuality(it) },
                onChangeOfRoundUpTip = { setRoundUpTip(it) }
            ),
            outputUiState = OutputUiState()
        )
    )
    val stateCalculatorUiState: State<CalculatorUiState> = _stateCalculatorUiState

    private val inputUiState: InputUiState
        get() = stateCalculatorUiState.value.inputUiState

    private fun recalculateOutputs(inputUiState: InputUiState): OutputUiState {
        val tipData = calculateTip(
            costOfService = inputUiState.costOfService.toDoubleOrNull() ?: 0.0,
            serviceQuality = inputUiState.serviceQuality,
            roundUpTip = inputUiState.roundUpTip
        )
        return OutputUiState(
            tipAmount = tipData.tipAmount,
            billTotal = tipData.billTotal
        )
    }

    private fun setCostOfService(costOfService: String) {
        val newInputUiState = inputUiState.copy(costOfService = costOfService)
        _stateCalculatorUiState.value = CalculatorUiState(
            inputUiState = newInputUiState,
            outputUiState = recalculateOutputs(newInputUiState)
        )
    }

    private fun setServiceQuality(serviceQuality: ServiceQuality) {
        val newInputUiState = inputUiState.copy(serviceQuality = serviceQuality)
        _stateCalculatorUiState.value = CalculatorUiState(
            inputUiState = newInputUiState,
            outputUiState = recalculateOutputs(newInputUiState)
        )
    }

    private fun setRoundUpTip(roundUpTip: Boolean) {
        val newInputUiState = inputUiState.copy(roundUpTip = roundUpTip)
        _stateCalculatorUiState.value = CalculatorUiState(
            inputUiState = newInputUiState,
            outputUiState = recalculateOutputs(newInputUiState)
        )
    }

}