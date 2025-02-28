package com.example.dynamicforms.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.dynamicforms.domain.Field

@Composable
fun FieldComposable(
    field: Field,
    value: String,
    onFieldValueChanged: (String) -> Unit
) {
    Column {
        when (field.type.lowercase()) {
            "description" -> {
                Text(text = "HTML ${field.label}")
            }
            "dropDown" -> {
                var selected by remember {mutableStateOf(value)}

                field.options.forEach { option ->
                    Text(
                        text = " - ${option.label}",
                        modifier = Modifier.clickable {
                            selected = option.value
                            onFieldValueChanged(option.value)
                        }
                    )
                }
                Text("Selected: $selected")
            }

            "number" -> {
                var localValue by remember { mutableStateOf(value) }
                Text("Number: ${field.label}")
                BasicTextField(
                    value = localValue,
                    onValueChange = {
                        localValue = it
                        onFieldValueChanged(it)
                    }
                )
            }
            else -> {
                var textValue by remember { mutableStateOf(value) }

                Text("Field [${field.type}]: ${field.label}")
                BasicTextField(
                    value = textValue,
                    onValueChange = {
                        textValue = it
                        onFieldValueChanged(it)
                    }
                )
            }
        }
    }
}