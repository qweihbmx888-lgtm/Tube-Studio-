package com.tubepilot.studio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme()) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EarningsCalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun EarningsCalculatorScreen() {
    var viewsInput by remember { mutableStateOf("50000") }
    var rpmInput by remember { mutableStateOf("2.50") }
    var isShortsMode by remember { mutableStateOf(false) }

    val views = viewsInput.toDoubleOrNull()?.coerceAtLeast(0.0) ?: 0.0
    val rpm = rpmInput.toDoubleOrNull()?.coerceAtLeast(0.0) ?: 0.0

    val effectiveRpm = if (isShortsMode) rpm * 0.15 else rpm
    val estimatedEarnings = (views / 1000.0) * effectiveRpm
    val per1kViews = effectiveRpm
    val per10kViews = effectiveRpm * 10.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "TubePilot Studio",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Creator Views & Revenue Estimator",
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            FilterChip(
                selected = !isShortsMode,
                onClick = { isShortsMode = false },
                label = { Text("Long Videos") }
            )
            FilterChip(
                selected = isShortsMode,
                onClick = { isShortsMode = true },
                label = { Text("Shorts Mode") }
            )
        }

        OutlinedTextField(
            value = viewsInput,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    viewsInput = newValue
                }
            },
            label = { Text("Estimated Views") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = rpmInput,
            onValueChange = { newValue ->
                if (newValue.count { it == '.' } <= 1 && newValue.all { it.isDigit() || it == '.' }) {
                    rpmInput = newValue
                }
            },
            label = { Text("Base RPM ($ per 1,000 views)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Quick RPM Presets ($):",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("0.80", "1.50", "3.00", "6.00").forEach { preset ->
                OutlinedButton(
                    onClick = { rpmInput = preset },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("$$preset", fontSize = 12.sp)
                }
            }
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "Estimated Revenue",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$${String.format(Locale.US, "%.2f", estimatedEarnings)}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Effective RPM: $${String.format(Locale.US, "%.2f", effectiveRpm)} / 1K views",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "At 10K views: $${String.format(Locale.US, "%.2f", per10kViews)}  |  Per 1K: $${String.format(Locale.US, "%.2f", per1kViews)}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Button(
            onClick = {
                viewsInput = "50000"
                rpmInput = "2.50"
                isShortsMode = false
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reset Defaults")
        }
    }
}
