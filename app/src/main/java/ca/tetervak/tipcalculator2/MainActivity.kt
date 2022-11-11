package ca.tetervak.tipcalculator2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.tetervak.tipcalculator2.model.ServiceQuality
import ca.tetervak.tipcalculator2.ui.theme.TipCalculator2Theme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculator2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TipCalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun TipCalculatorScreen(viewModel: MainViewModel = viewModel()) {

    // input states
    val inputUiState = viewModel.inputUiState
    // output states
    val outputUiState = viewModel.outputUiSate

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.tip_calculator_header),
            fontSize = 24.sp,
            color = colorResource(id = R.color.pink_500)
        )
        CalculatorInputs(inputUiState.value)
        CalculatorOutputs(outputUiState.value)
    }
}

@Composable
fun CalculatorOutputs(
    outputUiSate: OutputUiSate,
) {
    Card(elevation = 4.dp, shape = RoundedCornerShape(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = stringResource(R.string.tip_amount_label),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .sizeIn(minWidth = 112.dp)
                        .wrapContentWidth(align = Alignment.End)
                )
                Text(
                    text = formatCurrency(outputUiSate.tipAmount),
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.purple_500)
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.bill_total_label),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .sizeIn(minWidth = 112.dp)
                        .wrapContentWidth(align = Alignment.End)
                )
                Text(
                    text = formatCurrency(outputUiSate.billTotal),
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.purple_500)
                )
            }
        }
    }

}

fun formatCurrency(amount: Double): String =
    NumberFormat.getCurrencyInstance().format(amount)

@Composable
fun CalculatorInputs(
    inputUiState: InputUiState
) {
    Card(elevation = 4.dp, shape = RoundedCornerShape(8.dp)) {
        Column {
            CostOfServiceInput(
                costOfServiceInput = inputUiState.costOfService,
                onChange = { inputUiState.onChangeOfCostOfService(it) }
            )
            ServiceQualityInput(
                serviceQuality = inputUiState.serviceQuality,
                onChange = { inputUiState.onChangeOfServiceQuality(it) }
            )
            RoundUpTipInput(
                roundUpTip = inputUiState.roundUpTip,
                onChange = { inputUiState.onChangeOfRoundUpTip(it) }
            )
        }
    }
}

@Composable
fun RoundUpTipInput(roundUpTip: Boolean, onChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.round_up_tip_input_label),
            fontSize = 20.sp
        )
        Switch(
            checked = roundUpTip,
            onCheckedChange = { onChange (it) },
        )
    }
}

@Composable
fun ServiceQualityInput(
    serviceQuality: ServiceQuality,
    onChange: (ServiceQuality) -> Unit
) {
    Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp)) {
        Text(
            text = stringResource(R.string.service_quality_input_label),
            fontSize = 20.sp
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = serviceQuality == ServiceQuality.AMAZING,
                onClick = { onChange(ServiceQuality.AMAZING) }
            )
            Text(
                text = stringResource(id = R.string.quality_amazing_label),
                fontSize = 18.sp
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = serviceQuality == ServiceQuality.GOOD,
                onClick = { onChange(ServiceQuality.GOOD) }
            )
            Text(
                text = stringResource(id = R.string.quality_good_label),
                fontSize = 18.sp
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = serviceQuality == ServiceQuality.OK,
                onClick = { onChange(ServiceQuality.OK) }
            )
            Text(
                text = stringResource(id = R.string.quality_okay_label),
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun CostOfServiceInput(
    costOfServiceInput: String,
    onChange: (String) -> Unit
) {
    TextField(
        label = { Text(text = stringResource(id = R.string.cost_of_service_label)) },
        value = costOfServiceInput,
        onValueChange = { onChange(it) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        modifier = Modifier
            .padding(16.dp)
            .sizeIn(minWidth = 256.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculator2Theme {
        TipCalculatorScreen()
    }
}