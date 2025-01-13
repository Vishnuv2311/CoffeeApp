package dev.vishnuv.coffeeapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import dev.vishnuv.coffeeapp.ui.theme.BrownColor

@Composable
fun Background(modifier: Modifier = Modifier) {
    val config = LocalConfiguration.current
    val width = config.screenWidthDp
    val height = config.screenHeightDp

    Column(modifier.fillMaxSize()) {
        Box(
            Modifier
                .weight(1f)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            BrownColor.copy(alpha = 0.7f),
                            BrownColor.copy(alpha = 0f)
                        ),
                        start = Offset(width.toFloat(), height.toFloat()),
                        end = Offset(0f, 0f)
                    )
                )
        )
        Box(
            Modifier
                .weight(1f)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            BrownColor.copy(alpha = 0.5f),
                            BrownColor.copy(alpha = 0f)
                        ),
                        start = Offset(width.toFloat(), 0f),
                        end = Offset(0f, height.toFloat())
                    )
                )
        )
    }
}

@Preview
@Composable
private fun BackgroundPreview() {
    Background()
}