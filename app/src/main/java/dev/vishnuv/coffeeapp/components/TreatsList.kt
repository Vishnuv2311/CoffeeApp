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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.vishnuv.coffeeapp.models.CoffeeItem
import dev.vishnuv.coffeeapp.models.mockCoffeeItems
import dev.vishnuv.coffeeapp.models.mockTreatItems
import dev.vishnuv.coffeeapp.ui.theme.CoffeeAppTheme
import dev.vishnuv.coffeeapp.ui.theme.TitleColor
import dev.vishnuv.coffeeapp.ui.theme.montserrat
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.absoluteValue


@Composable
fun TreatsList(
    modifier: Modifier = Modifier,
    coffee: CoffeeItem,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onCoffeeTreatClick: (Int, Int?) -> Unit = { _, _ -> }
) {

    val config = LocalConfiguration.current
    val width = config.screenWidthDp
    val height = config.screenHeightDp


    var currentPosition by remember { mutableIntStateOf(0) }
    var currentHeading by remember { mutableIntStateOf(0) }

    val headerState = rememberPagerState(pageCount = { mockTreatItems.size }, initialPage = 2)
    val treatsState = rememberPagerState(pageCount = { mockTreatItems.size + 1 }, initialPage = 2)

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(treatsState) {
        currentPosition = treatsState.currentPage
        currentHeading = headerState.currentPage


        coroutineScope.launch {
            snapshotFlow { treatsState.currentPage }.collect { page ->
                currentPosition = page
                if (currentPosition != currentHeading) {
                    currentHeading = currentPosition
                    headerState.animateScrollToPage(
                        currentHeading,
                        animationSpec = tween(300, easing = EaseInOut)
                    )

                }
            }
        }

    }

    val twoPagesPerViewport = object : PageSize {
        override fun Density.calculateMainAxisPageSize(availableSpace: Int, pageSpacing: Int): Int {
            return (availableSpace - 2 * pageSpacing) / 2
        }
    }




    Box(modifier.fillMaxSize()) {


        Image(
            painter = painterResource(coffee.image),
            contentDescription = coffee.name,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = (-width * 0.52).dp)
                .width(width.dp)
                .height((height * 0.7).dp)
                .scale(1.36f)
        )



        VerticalPager(
            state = treatsState,
            modifier = Modifier
                .height((height * 0.5).dp)
                .fillMaxWidth()
                .align(Alignment.BottomEnd),
            pageSize = twoPagesPerViewport,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) { page ->


            val pageOffset = ((treatsState.currentPage - page + 1) + treatsState
                .currentPageOffsetFraction)
                .absoluteValue
            val scale = (2f - pageOffset * 0.38f).coerceAtLeast(0f)
            val translateX = (pageOffset * width * 0.2).dp

            if (page == 0) {
                Box {}
            } else {
                with(sharedTransitionScope) {
                    Box(
                        Modifier
                            .padding(bottom = (height * 0.1).dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Image(
                            painter = painterResource(mockTreatItems[page - 1].image),
                            contentDescription = mockTreatItems[page - 1].name,
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier
                                .scale(scale)
                                .offset(x = translateX)
                                .graphicsLayer {
                                    cameraDistance = 8f
                                }
                                .sharedElement(
                                    rememberSharedContentState(key = "treat_${mockTreatItems[page - 1].id}"),
                                    animatedVisibilityScope = animatedVisibilityScope
                                )


                        )
                    }
                }
            }


        }

        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(bottom = (height * 0.1).dp),
            horizontalAlignment = Alignment.End
        ) {

            Text(
                "${
                    String.format(
                        Locale.ENGLISH,
                        " %.2f",
                        mockTreatItems[currentHeading.coerceIn(0, mockTreatItems.size - 1)].price
                    )
                }€", style = TextStyle(
                    fontFamily = montserrat,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.W700,
                    lineHeight = 1.sp,
                    color = TitleColor
                ),
                modifier = Modifier.padding(end = 20.dp)
            )
            Spacer(Modifier.height(15.dp))
            HorizontalPager(
                state = headerState,
                userScrollEnabled = false,
                modifier = Modifier
                    .height(80.dp)
            ) { page ->
                Text(
                    mockTreatItems[page].name, style = TextStyle(
                        fontFamily = montserrat,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W500,
                        color = TitleColor.copy(alpha = 0.8f)
                    ), textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(PaddingValues(start = 220.dp, end = 20.dp))
                )
            }

        }

        Text(
            mockTreatItems[headerState.currentPage.coerceIn(0, mockTreatItems.size - 1)].colories,
            style = TextStyle(
                fontFamily = montserrat,
                fontSize = 20.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 1.sp,
                color = TitleColor.copy(alpha = 0.5f)
            ),
            textAlign = TextAlign.Left,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp)
        )

        IconButton(
            onClick = {
                onCoffeeTreatClick(
                    mockCoffeeItems.indexOf(coffee),
                    mockTreatItems.indexOf(
                        mockTreatItems[currentHeading.coerceIn(
                            0,
                            mockTreatItems.size - 1
                        )]
                    )
                )
            },
            colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 60.dp, bottom = (height * 0.25).dp)

        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Add",
                tint = TitleColor,
                modifier = Modifier.size(32.dp)
            )
        }

    }
}

@Preview
@Composable
private fun TreatsListPreview() {
    CoffeeAppTheme {
        SharedTransitionLayout {
            AnimatedVisibility(true) {
                TreatsList(
                    coffee = mockCoffeeItems.first(),
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this,

                    )
            }
        }
    }
}