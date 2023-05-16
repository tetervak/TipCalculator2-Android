package ca.tetervak.tipcalculator2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.tetervak.tipcalculator2.model.ServiceQuality
import ca.tetervak.tipcalculator2.ui.theme.TipCalculator2Theme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculator2Theme {
                val windowSize = calculateWindowSizeClass(this)
                if (windowSize.heightSizeClass == WindowHeightSizeClass.Compact) {
                    TipCalculatorCompactHeightScreen()
                } else {
                    TipCalculatorDefaultScreen()
                }
            }
        }
    }
}

@Composable
fun TipCalculatorCompactHeightScreen(viewModel: MainViewModel = viewModel()) {

    val calculatorUiState: CalculatorUiState by viewModel.uiState.collectAsState()
    val inputUiState = calculatorUiState.inputUiState

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .wrapContentWidth()
                .padding(32.dp)
                .weight(1f),
        ){
            Text(
                text = stringResource(R.string.tip_calculator_header),
                style = MaterialTheme.typography.h1
            )
            ServiceQualityInput(
                serviceQuality = inputUiState.serviceQuality,
                onChange = inputUiState.onChangeOfServiceQuality
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .wrapContentWidth()
                .padding(32.dp)
                .weight(1f)
        ) {
            CostOfServiceInput(
                costOfServiceInput = inputUiState.costOfService,
                onChange = inputUiState.onChangeOfCostOfService,
                modifier = Modifier.sizeIn(minWidth = 192.dp)
            )
            RoundUpTipInput(
                roundUpTip = inputUiState.roundUpTip,
                onChange = inputUiState.onChangeOfRoundUpTip
            )
            Divider()
            CalculatorOutputs(calculatorUiState.outputUiState)
        }
    }
}

@Composable
fun TipCalculatorDefaultScreen(viewModel: MainViewModel = viewModel()) {

    val calculatorUiState: CalculatorUiState by viewModel.uiState.collectAsState()
    val inputUiState = calculatorUiState.inputUiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.tip_calculator_header),
            style = MaterialTheme.typography.h1
        )
        //CalculatorInputs(calculatorUiState.inputUiState)
        ServiceQualityInput(
            serviceQuality = inputUiState.serviceQuality,
            onChange = inputUiState.onChangeOfServiceQuality,
            //modifier = Modifier.padding(start = 16.dp, top = 8.dp)
        )
        CostOfServiceInput(
            costOfServiceInput = inputUiState.costOfService,
            onChange = inputUiState.onChangeOfCostOfService,
            modifier = Modifier.sizeIn(minWidth = 192.dp)
        )
        RoundUpTipInput(
            roundUpTip = inputUiState.roundUpTip,
            onChange = inputUiState.onChangeOfRoundUpTip,
            //modifier = Modifier.padding(16.dp)
        )
        Divider()
        CalculatorOutputs(calculatorUiState.outputUiState)
    }
}

@Composable
fun CalculatorOutputs(
    outputUiSate: OutputUiState,
) {
    Card(elevation = 4.dp, shape = RoundedCornerShape(8.dp)) {
        var labelWidth by remember { mutableStateOf(0) }
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = stringResource(R.string.tip_amount_label),
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier
                        .layout(measure = { measurable, constraints ->
                            val placeable = measurable.measure(constraints)
                            val width = placeable.width
                            labelWidth = maxOf(width, labelWidth)
                            layout(width = labelWidth, height = placeable.height) {
                                placeable.placeRelative(labelWidth - width, 0)
                            }
                        })
                )
                CurrencyOutput(amount = outputUiSate.tipAmount)
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.bill_total_label),
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier
                        .layout(measure = { measurable, constraints ->
                            val placeable = measurable.measure(constraints)
                            val width = placeable.width
                            labelWidth = maxOf(width, labelWidth)
                            layout(width = labelWidth, height = placeable.height) {
                                placeable.placeRelative(labelWidth - width, 0)
                            }
                        })

                )
                CurrencyOutput(amount = outputUiSate.billTotal)
            }
        }
    }

}

@Composable
fun CurrencyOutput(amount: Double) {
    Text(
        text = formatCurrency(amount),
        style = MaterialTheme.typography.h2,
        color = colorResource(id = R.color.purple_500)
    )
}

fun formatCurrency(amount: Double): String =
    NumberFormat.getCurrencyInstance().format(amount)

@Composable
fun RoundUpTipInput(
    roundUpTip: Boolean,
    onChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.round_up_tip_input_label),
            style = MaterialTheme.typography.h2
        )
        Switch(
            checked = roundUpTip,
            onCheckedChange = onChange,
        )
    }
}

@Composable
fun ServiceQualityInput(
    serviceQuality: ServiceQuality,
    onChange: (ServiceQuality) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.service_quality_input_label),
            style = MaterialTheme.typography.h2
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = serviceQuality == ServiceQuality.AMAZING,
                onClick = { onChange(ServiceQuality.AMAZING) }
            )
            Text(
                text = stringResource(id = R.string.quality_amazing_label),
                style = MaterialTheme.typography.body1
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = serviceQuality == ServiceQuality.GOOD,
                onClick = { onChange(ServiceQuality.GOOD) }
            )
            Text(
                text = stringResource(id = R.string.quality_good_label),
                style = MaterialTheme.typography.body1
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = serviceQuality == ServiceQuality.OK,
                onClick = { onChange(ServiceQuality.OK) }
            )
            Text(
                text = stringResource(id = R.string.quality_okay_label),
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun CostOfServiceInput(
    costOfServiceInput: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    TextField(
        label = { Text(text = stringResource(id = R.string.cost_of_service_label)) },
        value = costOfServiceInput,
        onValueChange = onChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        singleLine = true,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculator2Theme {
        TipCalculatorDefaultScreen()
    }
}