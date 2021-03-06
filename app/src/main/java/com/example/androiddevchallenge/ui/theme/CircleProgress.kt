/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.theme

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.FULL_DEGREE
import com.example.androiddevchallenge.MainViewModel
import com.example.androiddevchallenge.PROGRESS_CIRCLE_RADIUS
import com.example.androiddevchallenge.PROGRESS_SIZE

@Composable
fun ShowCircleProgress(onCountdownEndAction: () -> Unit = {}) {
    val viewModel: MainViewModel = viewModel()
    val totalCountTime = viewModel.totalCountdownTime
    val angle = if (totalCountTime == 0) {
        0f
    } else {
        viewModel.currentCountdownTime / totalCountTime.toFloat() * FULL_DEGREE
    }

    if (angle == FULL_DEGREE) {
        onCountdownEndAction.invoke()
    }

    CircleProgress(sweepAngle = angle)
    CountdownText(totalCountTime, viewModel.currentCountdownTime)
}

@Composable
fun CountdownText(totalCountdownTime: Int, currentCountdownTime: Int) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val leftTime = totalCountdownTime - currentCountdownTime
        val hour = leftTime / 60 / 60
        val minute = leftTime / 60 % 60
        val seconds = leftTime % 60
        Text(text = String.format("%02d:%02d:%02d", hour, minute, seconds))
    }
}

@Composable
fun CircleProgress(sweepAngle: Float) {
    val animateAngle: Float by animateFloatAsState(
        targetValue = sweepAngle,
        animationSpec = tween(
            1000, 0,
            LinearEasing
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val radius = PROGRESS_CIRCLE_RADIUS.dp.toPx()
        val arcSize = radius * 2

        drawCircle(
            color = progressColor1,
            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
            style = Stroke(width = PROGRESS_SIZE),
            radius = radius
        )

        drawArc(
            color = progressColor2,
            startAngle = -90f,
            sweepAngle = animateAngle,
            useCenter = false,
            topLeft = Offset(x = (canvasWidth / 2) - radius, y = (canvasHeight / 2 - radius)),
            size = Size(width = arcSize, height = arcSize),
            style = Stroke(width = PROGRESS_SIZE, cap = StrokeCap.Round, miter = 1f)
        )
    }
}

@Preview
@Composable
fun PreviewCircleProgress() {
    ShowCircleProgress()
}
