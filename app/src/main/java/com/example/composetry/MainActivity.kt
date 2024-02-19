package com.example.composetry

import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composetry.ui.theme.CalculatorButton
import com.example.composetry.ui.theme.ComposeTryTheme
import javax.script.ScriptEngineManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTryTheme {
                DefaultPreview()
            }
        }
    }
}

interface CalculatorOperations {
    fun clear()
    fun write(calculatorButton: CalculatorButton)
    fun delete()
    fun calculate()
}


fun evaluateExpression(expression: String): Double? {
    val scriptEngineManager = ScriptEngineManager()
    val scriptEngine = scriptEngineManager.getEngineByName("js")

    try {
        return (scriptEngine.eval(expression) as? Number)?.toDouble()
    } catch (e: Exception) {
        // Handle potential errors during evaluation
        e.printStackTrace()
    }

    return null
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val calculatorButtonsList = listOf(
        listOf(
            CalculatorButton.AC,
            CalculatorButton.PARENTHESIS,
            CalculatorButton.PERCENTAGE,
            CalculatorButton.DIVIDE
        ),
        listOf(
            CalculatorButton.SEVEN,
            CalculatorButton.EIGHT,
            CalculatorButton.NINE,
            CalculatorButton.MULTIPLY
        ),
        listOf(
            CalculatorButton.FOUR,
            CalculatorButton.FIVE,
            CalculatorButton.SIX,
            CalculatorButton.MINUS
        ),
        listOf(
            CalculatorButton.ONE,
            CalculatorButton.TWO,
            CalculatorButton.THREE,
            CalculatorButton.PLUS
        ),
        listOf(
            CalculatorButton.ZERO,
            CalculatorButton.DECIMAL,
            CalculatorButton.DELETE,
            CalculatorButton.EQUALS
        )
    )
    val text = remember { mutableStateOf("") }
    val textStyleBody1 = TextStyle(fontSize = 60.sp)
    var textStyle by remember { mutableStateOf(textStyleBody1) }
    var readyToDraw by remember { mutableStateOf(false) }
    val errorState = remember { mutableStateOf("") }
    val calculatorOperations = object : CalculatorOperations {
        override fun clear() {
            text.value = ""
            textStyle = textStyleBody1
        }

        override fun write(calculatorButton: CalculatorButton) {
            errorState.value = ""
            if (calculatorButton.value == CalculatorButton.PARENTHESIS.value) {
                if (text.value.endsWith(CalculatorButton.START_PARENTHESIS.value)
                    || text.value.endsWith(CalculatorButton.PLUS.value)
                    || text.value.endsWith(CalculatorButton.MINUS.value)
                    || text.value.endsWith(CalculatorButton.MULTIPLY.value)
                    || text.value.endsWith(CalculatorButton.DIVIDE.value)
                ) {
                    text.value += CalculatorButton.START_PARENTHESIS.value
                } else {
                    val endParenthesisCount =
                        text.value.count { it.toString() == CalculatorButton.END_PARENTHESIS.value }
                    val startParenthesisCount =
                        text.value.count { it.toString() == CalculatorButton.START_PARENTHESIS.value }
                    if (startParenthesisCount - endParenthesisCount == 0) {
                        text.value += CalculatorButton.START_PARENTHESIS.value
                    } else {
                        if (endParenthesisCount < startParenthesisCount) {
                            text.value += CalculatorButton.END_PARENTHESIS.value
                        }
                    }
                }
            } else {
                text.value += calculatorButton.value
            }
        }

        override fun delete() {
            text.value = text.value.dropLast(1)
            if (textStyle.fontSize<textStyleBody1.fontSize) {
                textStyle = textStyle.copy(fontSize = textStyle.fontSize / 0.9)
            }
        }

        override fun calculate() {
            val result = evaluateExpression(text.value)
            text.value = if (result != null) {
                val formattedResult = if (result % 1 == 0.0) {
                    result.toInt().toString()
                } else {
                    result.toString()
                }
                formattedResult
            } else {
                errorState.value = CalculatorButton.ERROR.value
               text.value
            }

        }

    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.LightGray
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 20.dp)
                    .background(Color.White),
            ) {
                Column(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End) {
                    Text(
                        text = text.value,
                        maxLines = 1,
                        style = textStyle,
                        softWrap = false,
                        modifier = Modifier.drawWithContent {
                            if (readyToDraw) drawContent()
                        },
                        onTextLayout = { textLayoutResult: TextLayoutResult ->
                            if (textLayoutResult.didOverflowWidth) {
                                textStyle = textStyle.copy(fontSize = textStyle.fontSize * 0.9)
                            } else {
                                readyToDraw = true
                            }
                        }
                    )
                    if(errorState.value==CalculatorButton.ERROR.value) {
                        Text(
                            text = CalculatorButton.ERROR.value,
                            fontSize = 40.sp
                        )
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.Bottom
            ) {
                calculatorButtonsList.forEach {
                    CalculatorRow(
                        list = it,
                        buttonSize = 80.dp,
                        bottomPadding = 6.dp,
                        horizontalSpacing = 12.dp,
                        calculatorOperations = calculatorOperations
                    )
                }
            }
        }
    }

}


@Composable
fun CalculatorRow(
    list: List<CalculatorButton>,
    buttonSize: Dp,
    bottomPadding: Dp,
    horizontalSpacing: Dp,
    calculatorOperations: CalculatorOperations
) {
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .padding(bottom = bottomPadding),
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacing)
    ) {
        list.forEach {
            CircleButton(button = it, size = buttonSize) {
                operation(it, calculatorOperations)
            }
        }
    }
}

fun operation(calculatorButton: CalculatorButton, calculatorOperations: CalculatorOperations) {
    when (calculatorButton) {
        CalculatorButton.AC -> calculatorOperations.clear()
        CalculatorButton.DELETE -> calculatorOperations.delete()
        CalculatorButton.EQUALS -> calculatorOperations.calculate()
        else -> calculatorOperations.write(calculatorButton)
    }
}

@Composable
fun CircleButton(button: CalculatorButton, size: Dp, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(Color.DarkGray)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = button.value,
            fontSize = 36.sp,
            color = Color.White,
        )
    }
}
