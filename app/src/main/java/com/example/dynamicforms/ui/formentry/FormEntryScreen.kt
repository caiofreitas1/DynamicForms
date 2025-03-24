import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dynamicforms.domain.Field
import com.example.dynamicforms.ui.formentry.FormEntryViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormEntryScreen(
    formId: String,
    onBack: () -> Unit,
    viewModel: FormEntryViewModel = hiltViewModel()
) {
    val form = viewModel.form.collectAsState().value
    val sections = viewModel.sections.collectAsState().value
    val fields = viewModel.fields.collectAsState().value
    val fieldValues by viewModel.fieldValues.collectAsState()

    val scope = rememberCoroutineScope()

    LaunchedEffect(formId) {
        viewModel.loadForm(formId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(form?.title ?: "Form") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        bottomBar = {
            SaveButtonBar(
                onSaveClicked = {
                    scope.launch {
                        viewModel.saveEntry()
                        onBack()
                    }
                }
            )
        }
    ) { innerPadding ->
        if (form == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Loading")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                sections.forEach { section ->
                    item {
                        SectionHeader(title = section.title)
                    }

                    val sectionFields = fields.filter {
                        it.orderIndex in section.fromIndex..section.toIndex
                    }

                    items(sectionFields) { field ->
                        Card(
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            FieldComposable(
                                field = field,
                                value = fieldValues[field.fieldId] ?: "",
                                onFieldValueChanged = { newVal ->
                                    viewModel.updateFieldValue(field.fieldId, newVal)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SaveButtonBar(onSaveClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onSaveClicked,
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Text("Save")
        }
    }
}



@Composable
fun SectionHeader(title: String) {
    val spannedText = HtmlCompat.fromHtml(title, HtmlCompat.FROM_HTML_MODE_COMPACT)

    Text(
        text = spannedText.toString(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun FieldComposable(
    field: Field,
    value: String,
    onFieldValueChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        when (field.type) {
            "description" -> DescriptionField(field)
            "dropdown" -> DropdownField(field, value, onFieldValueChanged)
            "number" -> NumberField(field, value, onFieldValueChanged)
            else -> TextField(field, value, onFieldValueChanged)
        }
    }
}

@Composable
fun DescriptionField(field: Field) {
    val spannedText = HtmlCompat.fromHtml(field.label, HtmlCompat.FROM_HTML_MODE_COMPACT)

    Text(
        text = spannedText.toString(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    field: Field,
    value: String,
    onFieldValueChanged: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedOptionLabel = field.options.find { it.value == value }?.label ?: "Select..."

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = field.label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedOptionLabel,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                field.options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.label) },
                        onClick = {
                            onFieldValueChanged(option.value)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NumberField(
    field: Field,
    value: String,
    onFieldValueChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = field.label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                    onFieldValueChanged(newValue)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun TextField(
    field: Field,
    value: String,
    onFieldValueChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = field.label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        OutlinedTextField(
            value = value,
            onValueChange = onFieldValueChanged,
            modifier = Modifier.fillMaxWidth()
        )
    }
}