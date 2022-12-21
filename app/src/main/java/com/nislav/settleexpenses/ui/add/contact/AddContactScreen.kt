package com.nislav.settleexpenses.ui.add.contact

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.themeadapter.material.MdcTheme
import com.nislav.settleexpenses.R
import com.nislav.settleexpenses.util.NoOp

// TODO keyboard issues & bring into view.

@Composable
fun AddContact(
    onSubmit: (firstName: String, lastName: String) -> Unit = { _, _ -> NoOp }
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val focusRequester = remember { FocusRequester() }
        val firstName = remember { mutableStateOf("") }
        val lastName = remember { mutableStateOf("") }
        val valid = remember {
            derivedStateOf { firstName.value.isNotBlank() && lastName.value.isNotBlank() }
        }

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(8.dp))
        Text(
            text = stringResource(id = R.string.add_new_contact),
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(16.dp))
        InputField(
            state = firstName,
            label = stringResource(R.string.first_name),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusRequester.requestFocus() }
            ),
        )
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(8.dp))
        InputField(
            state = lastName,
            label = stringResource(id = R.string.last_name),
            modifier = Modifier.focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = if (valid.value) ImeAction.Done else ImeAction.None
            ),
            keyboardActions = KeyboardActions(
                onDone = { onSubmit(firstName.value, lastName.value) }
            ),
        )
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(8.dp))
        Button(
            onClick = { onSubmit(firstName.value, lastName.value) },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.End),
            enabled = valid.value
        ) {
            Text(text = stringResource(id = R.string.save))
        }
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
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.body2,
                )
            }
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

@Preview(name = "Add Contact light theme")
@Preview(
    name = "Add Contact dark theme",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun Preview() {
    MdcTheme {
        AddContact()
    }
}