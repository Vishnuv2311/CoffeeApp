@file:OptIn(ExperimentalSharedTransitionApi::class)

package dev.vishnuv.coffeeapp.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.vishnuv.coffeeapp.R
import dev.vishnuv.coffeeapp.models.mockCoffeeItems
import dev.vishnuv.coffeeapp.noRippleClickable
import dev.vishnuv.coffeeapp.ui.theme.BrownColor
import dev.vishnuv.coffeeapp.ui.theme.CoffeeAppTheme
import dev.vishnuv.coffeeapp.ui.theme.dancingScript

@Composable
fun IntroScreen(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onClick: () -> Unit = {}
) {
    val config = LocalConfiguration.current
    val width = config.screenWidthDp
    val height = config.screenHeightDp
    Box(
        modifier = modifier
            .fillMaxSize()
            .noRippleClickable { onClick() }
            .background(
                brush = Brush.linearGradient(
                    start = Offset(width / 2f, 0.0f),
                    end = Offset(width / 2f, height.toFloat()),
                    colors = listOf(
                        Color.White,
                        BrownColor,
                        Color.White.copy(alpha = 0.8f),
                    )
                )
            )
    ) {
        (1..4).forEach { index ->
            val scale = 1.0f + (index - 1) * 0.32f + (if (index == 4) 0.3f else 0.0f)
            val translate =
                (index - 1) * (height * 0.25) + (if (index == 4) 90f else 0f) - (height * 0.06)
            with(sharedTransitionScope) {
                Image(
                    painter = painterResource(id = mockCoffeeItems[index].image),
                    contentDescription = mockCoffeeItems[index].name,
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(x = 10.dp, y = (translate - 100).dp)
                        .scale(scale)
                        .sharedElement(
                            rememberSharedContentState(key = "coffee_${mockCoffeeItems[index].id}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                )
            }
        }

        Box(modifier = Modifier.align(Alignment.Center)) {
            Column {
                Text(
                    "Steizy",
                    style = TextStyle(
                        fontFamily = dancingScript,
                        fontSize = 80.sp,
                        fontWeight = FontWeight.W100,
                        color = Color.White
                    )
                )
                Text(
                    "Coffee", style = TextStyle(
                        fontSize = 50.sp,
                        fontWeight = FontWeight.W700,
                        color = Color.White
                    )
                )
            }
            Image(
                painter = painterResource(R.drawable.bean),
                contentDescription = "Logo",
                modifier = Modifier
                    .offset(56.dp, 10.dp)
                    .rotate(180f / 6)
                    .height(22.dp)
            )
        }
    }
}

@Preview
@Composable
private fun IntroScreenPreview() {
    CoffeeAppTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                IntroScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}