package ca.tetervak.tipcalculator2

import androidx.lifecycle.ViewModel
import ca.tetervak.tipcalculator2.model.ServiceQuality
import ca.tetervak.tipcalculator2.model.calculateTip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _flowUiState = MutableStateFlow(
        CalculatorUiState(
            inputUiState = InputUiState().copy(
                onChangeOfCostOfService = { setCostOfService(it) },
                onChangeOfServiceQuality = { setServiceQuality(it) },
                onChangeOfRoundUpTip = { setRoundUpTip(it) }
            ),
            outputUiState = OutputUiState()
        )
    )
    val flowUiState: StateFlow<CalculatorUiState> = _flowUiState.asStateFlow()

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
        _flowUiState.update { uiState ->
            val newInputUiState = uiState.inputUiState.copy(costOfService = costOfService)
            CalculatorUiState(
                inputUiState = newInputUiState,
                outputUiState = recalculateOutputs(newInputUiState)
            )
        }
    }

    private fun setServiceQuality(serviceQuality: ServiceQuality) {
        _flowUiState.update { uiState ->
            val newInputUiState = uiState.inputUiState.copy(serviceQuality = serviceQuality)
            CalculatorUiState(
                inputUiState = newInputUiState,
                outputUiState = recalculateOutputs(newInputUiState)
            )
        }
    }

    private fun setRoundUpTip(roundUpTip: Boolean) {
        _flowUiState.update { uiState ->
            val newInputUiState = uiState.inputUiState.copy(roundUpTip = roundUpTip)
            CalculatorUiState(
                inputUiState = newInputUiState,
                outputUiState = recalculateOutputs(newInputUiState)
            )
        }
    }

}