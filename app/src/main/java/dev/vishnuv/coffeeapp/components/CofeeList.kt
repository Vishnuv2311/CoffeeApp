@file:OptIn(ExperimentalFoundationApi::class, ExperimentalSharedTransitionApi::class)

package dev.vishnuv.coffeeapp.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.vishnuv.coffeeapp.models.mockCoffeeItems
import dev.vishnuv.coffeeapp.noRippleClickable
import dev.vishnuv.coffeeapp.ui.theme.BrownColor
import dev.vishnuv.coffeeapp.ui.theme.CoffeeAppTheme
import dev.vishnuv.coffeeapp.ui.theme.TitleColor
import dev.vishnuv.coffeeapp.ui.theme.montserrat
import java.util.Locale
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeList(
    modifier: Modifier = Modifier, sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope, onCoffeeClick: (Int) -> Unit = {}
) {

    val config = LocalConfiguration.current
    val width = config.screenWidthDp
    val height = config.screenHeightDp

    val coffeState = rememberPagerState(pageCount = { mockCoffeeItems.size + 2 })
    val headingState = rememberPagerState(pageCount = { mockCoffeeItems.size + 2 })

    val threePagesPerViewport = object : PageSize {
        override fun Density.calculateMainAxisPageSize(availableSpace: Int, pageSpacing: Int): Int {
            return (availableSpace - 2 * pageSpacing) / 3
        }
    }

    var currentHeading by remember { mutableIntStateOf(0) }

    LaunchedEffect(coffeState) {
        currentHeading = headingState.currentPage

        coffeState.animateScrollToPage(
            4,
            animationSpec = tween(durationMillis = 300, easing = EaseInOut)
        )
        snapshotFlow { coffeState.currentPage }.collect {
            currentHeading = (it).coerceIn(0, mockCoffeeItems.size - 1)
            headingState.animateScrollToPage(
                it, animationSpec = tween(durationMillis = 300, easing = EaseInOut)
            )
        }
    }

    Scaffold(topBar = { TopAppBar(title = {}) }) { innerPadding ->
        with(sharedTransitionScope) {
            Box(Modifier.padding(innerPadding)) {
                Background()
                VerticalPager(
                    state = coffeState,
                    pageSize = threePagesPerViewport,
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { page ->

                    if (page < 2) {
                        Box(Modifier.fillMaxSize()) {}
                    } else {
                        val currentPage = page - 2
                        val pageOffset = (
                                (coffeState.currentPage - currentPage) + coffeState.currentPageOffsetFraction)
                            .absoluteValue

                        val scale = (2f - (pageOffset * 0.345f)).coerceIn(0f, 2f)

                        val yOffset = (((1f - scale).absoluteValue * 1.5f *
                                LocalDensity.current.density * 20) +
                                (pageOffset - 1f).coerceIn(0f, 1f) * 20f)

                        Image(
                            painter = painterResource(id = mockCoffeeItems[currentPage].image),
                            contentDescription = mockCoffeeItems[currentPage].name,
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier
                                .padding(bottom = (height * 0.1f).dp)
                                .sharedElement(
                                    rememberSharedContentState(key = "coffee_${mockCoffeeItems[currentPage].id}"),
                                    animatedVisibilityScope = animatedVisibilityScope
                                )
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                    translationY = -yOffset
                                }
                                .noRippleClickable { onCoffeeClick(currentPage) }
                        )
                    }


                }




                Column(
                    modifier = Modifier
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.White,
                                    Color.White.copy(alpha = 0f)
                                ),
                                start = Offset(x = width / 2f, y = 0f),
                                end = Offset(x = width / 2f, y = height.toFloat())
                            )
                        ),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HorizontalPager(state = headingState) {
                        val item = mockCoffeeItems[currentHeading]
                        with(sharedTransitionScope) {
                            Text(
                                text = item.name,
                                style = TextStyle(
                                    fontFamily = montserrat,
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.W700,
                                    color = TitleColor,
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 80.dp)
                                    .sharedElement(
                                        rememberSharedContentState(key = "coffee_${item.id}_name"),
                                        animatedVisibilityScope = animatedVisibilityScope
                                    )
                            )
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    Text(
                        "${
                            String.format(
                                Locale.ENGLISH,
                                "%.2f",
                                mockCoffeeItems[currentHeading.coerceIn(
                                    0,
                                    mockCoffeeItems.size - 1
                                )].price
                            )
                        }€",
                        style = TextStyle(
                            fontFamily = montserrat,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.W500,
                            color = TitleColor
                        )
                    )


                }

                //   Overlays()
            }
        }

    }
}


@Composable
fun BoxScope.Background(modifier: Modifier = Modifier) {
    val config = LocalConfiguration.current
    val width = config.screenWidthDp
    val height = config.screenHeightDp

    Box(
        modifier
            .align(Alignment.BottomCenter)
            .offset(y = (height * 0.26).dp)
            .width((width).dp)
            .height((height * 0.5).dp)
            .background(
                brush = Brush.radialGradient(
                    0.0f to BrownColor,
                    0.9f to BrownColor.copy(alpha = 0.01f)
                )
            )
    )


    Box(
        modifier
            .align(Alignment.BottomEnd)
            .offset(x = (5.8 * width).dp, y = -(0.45 * height).dp)
            .size(350.dp)
            .background(color = BrownColor.copy(alpha = 0.4f), shape = CircleShape)
    )

}


@Composable
fun BoxScope.Overlays(modifier: Modifier = Modifier) {
    Box(
        Modifier
            .align(Alignment.BottomCenter)
            .height(115.dp)
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        BrownColor.copy(red = 170f, alpha = 0.6f),
                        BrownColor.copy(alpha = 0f)
                    )
                )
            )
    )
}


@Preview
@Composable
private fun CoffeeListPreview() {
    CoffeeAppTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                CoffeeList(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}

@Preview
@Composable
private fun OverlaysPreview() {
    CoffeeAppTheme {
        Box {
            Overlays()
        }
    }
}

@Preview
@Composable
private fun BackgroundPreview() {
    CoffeeAppTheme {
        Box {
            Background()
        }
    }
}