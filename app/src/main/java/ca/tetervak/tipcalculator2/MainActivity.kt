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
import androidx.compose.ui.text.input.TextInputService
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun TipCalculatorScreen() {
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
        CalculatorInputs(
            costOfServiceInput = "100",
            serviceQuality = ServiceQuality.GOOD,
            roundUpTip = true
        )
        CalculatorOutputs(
            tipAmount = 18.0,
            billTotal = 118.0
        )

    }
}

@Composable
fun CalculatorOutputs(tipAmount: Double, billTotal: Double) {
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
                    text = formatCurrency(tipAmount),
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
                    text = formatCurrency(billTotal),
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
    costOfServiceInput: String,
    serviceQuality: ServiceQuality,
    roundUpTip: Boolean
) {
    Card(elevation = 4.dp, shape = RoundedCornerShape(8.dp)) {
        Column {
            CostOfServiceInput(costOfServiceInput = costOfServiceInput)
            ServiceQualityInput(serviceQuality = serviceQuality)
            RoundUpTipInput(roundUpTip = roundUpTip)
        }
    }
}

@Composable
fun RoundUpTipInput(roundUpTip: Boolean) {
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
            onCheckedChange = {},
        )
    }
}

@Composable
fun ServiceQualityInput(serviceQuality: ServiceQuality) {
    Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp)) {
        Text(
            text = stringResource(R.string.service_quality_input_label),
            fontSize = 20.sp
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = serviceQuality == ServiceQuality.AMAZING,
                onClick = { }
            )
            Text(
                text = stringResource(id = R.string.quality_amazing_label),
                fontSize = 18.sp
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = serviceQuality == ServiceQuality.GOOD,
                onClick = { }
            )
            Text(
                text = stringResource(id = R.string.quality_good_label),
                fontSize = 18.sp
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = serviceQuality == ServiceQuality.OK,
                onClick = { }
            )
            Text(
                text = stringResource(id = R.string.quality_okay_label),
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun CostOfServiceInput(costOfServiceInput: String) {
    TextField(
        label = { Text(text = stringResource(id = R.string.cost_of_service_label)) },
        value = costOfServiceInput,
        onValueChange = { },
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