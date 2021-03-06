package com.example.androiddevchallenge.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.MAX_PROGRESS_TIME
import com.example.androiddevchallenge.MainViewModel


@Composable
fun HomeScreen() {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "CountdownTimer")
        })
    }) {
        val viewModel: MainViewModel = viewModel()
        InputCountdownTime {
            // just start countdown
            if (viewModel.isCounting)return@InputCountdownTime

            viewModel.startCountdown(it)
        }
        ShowCircleProgress {
            // Time is up!
            viewModel.resetCountdown()
        }
    }
}

@Composable
fun InputCountdownTime(startCountdown: (Int) -> Unit) {
    var value by rememberSaveable { mutableStateOf(value = "") }

    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
    ) {
        var isError by rememberSaveable { mutableStateOf(value = false) }

        OutlinedTextField(
            value = value,
            modifier = Modifier.fillMaxWidth(),
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = "Please input countdown time in seconds") },
            onValueChange = {
                isError = (it.length > 6 || (it.isNotEmpty() && it.toInt() > MAX_PROGRESS_TIME))
                if (isError) return@OutlinedTextField

                value = it
            })

        Button(
            onClick = { startCountdown(value.toInt()) },
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(60.dp)
                .padding(top = 10.dp),
            enabled = !isError && value.isNotEmpty()
        ) {
            Text(text = "Start")
        }
    }
}

