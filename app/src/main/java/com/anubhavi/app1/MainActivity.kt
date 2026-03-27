package com.anubhavi.app1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anubhavi.app1.ui.theme.App1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App1Theme {
                CalculatorUI()
            }
        }
    }
}

@Composable
fun CalculatorUI() {
    var display by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {

        // Display Screen
        Text(
            text = display,
            fontSize = 32.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )

        // Buttons
        val buttons = listOf(
            listOf("7", "8", "9", "/"),
            listOf("4", "5", "6", "*"),
            listOf("1", "2", "3", "-"),
            listOf("0", "C", "=", "+")
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { label ->
                    Button(
                        onClick = {
                            when (label) {
                                "C" -> display = ""
                                "=" -> display = calculate(display)
                                else -> display += label
                            }
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                    ) {
                        Text(text = label, fontSize = 20.sp)
                    }
                }
            }
        }
    }
}

fun calculate(expression: String): String {
    return try {
        val operators = listOf("+", "-", "*", "/")
        var operatorUsed = ""

        for (op in operators) {
            if (expression.contains(op)) {
                operatorUsed = op
                break
            }
        }

        if (operatorUsed.isEmpty()) return expression

        val parts = expression.split(operatorUsed)

        if (parts.size != 2) return "Error"

        val num1 = parts[0].toDouble()
        val num2 = parts[1].toDouble()

        val result = when (operatorUsed) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> num1 / num2
            else -> return "Error"
        }

        result.toString()
    } catch (e: Exception) {
        "Error"
    }
}