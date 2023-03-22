package com.nislav.settleexpenses.ui.add.contact

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.themeadapter.material.MdcTheme
import com.nislav.settleexpenses.R
import com.nislav.settleexpenses.db.entities.Contact
import com.nislav.settleexpenses.util.DayNightPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddContact(
    vm: AddContactViewModel = hiltViewModel(),
    onDone: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequesterFirst = remember { FocusRequester() }
    val focusRequesterSecond = remember { FocusRequester() }

    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val valid = remember {
        derivedStateOf { firstName.value.isNotBlank() && lastName.value.isNotBlank() }
    }

    fun confirm() {
        scope.launch {
            vm.addContact(firstName.value, lastName.value)
            onDone()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.safeContent)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        Text(
            text = stringResource(id = R.string.add_new_contact),
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        InputField(
            state = firstName,
            label = stringResource(R.string.first_name),
            modifier = Modifier.focusRequester(focusRequesterFirst),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusRequesterSecond.requestFocus() }
            ),
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        InputField(
            state = lastName,
            label = stringResource(id = R.string.last_name),
            modifier = Modifier.focusRequester(focusRequesterSecond),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = if (valid.value) ImeAction.Done else ImeAction.None
            ),
            keyboardActions = KeyboardActions(
                onDone = { confirm() }
            ),
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        Button(
            onClick = { confirm() },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.End),
            enabled = valid.value
        ) {
            Text(text = stringResource(id = R.string.save))
        }
    }
    LaunchedEffect(null) {
        focusRequesterFirst.requestFocus()
        delay(500L)
        keyboardController?.show()
    }
}

@Composable
fun InputField(
    state: MutableState<String>,
    label: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        value = state.value,
        onValueChange = {
            state.value = it
        },
        label = {
            Text(
                text = label,
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.body2,
            )
        },
        singleLine = true,
        maxLines = 1,
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}

@DayNightPreview
@Composable
fun Preview() {
    MdcTheme {
        AddContact()
    }
}