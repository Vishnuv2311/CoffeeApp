@file:OptIn(ExperimentalSharedTransitionApi::class)

package dev.vishnuv.coffeeapp.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.vishnuv.coffeeapp.models.CoffeeItem
import dev.vishnuv.coffeeapp.models.TreatItem
import dev.vishnuv.coffeeapp.models.mockCoffeeItems
import dev.vishnuv.coffeeapp.models.mockTreatItems
import dev.vishnuv.coffeeapp.ui.theme.CoffeeAppTheme
import dev.vishnuv.coffeeapp.ui.theme.TitleColor
import dev.vishnuv.coffeeapp.ui.theme.montserrat
import dev.vishnuv.coffeeapp.ui.theme.questrial
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    coffee: CoffeeItem,
    treat: TreatItem? = null
) {
    val config = LocalConfiguration.current
    val width = config.screenWidthDp
    val height = config.screenHeightDp

    Scaffold(topBar = { TopAppBar(title = {}) }) { innerPadding ->
        Box(Modifier.padding(innerPadding), contentAlignment = Alignment.Center) {
            Background()
            with(sharedTransitionScope) {
                Image(
                    painter = painterResource(coffee.image),
                    contentDescription = coffee.name,
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState("coffee_${coffee.id}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .aspectRatio(1f)
                )
            }
            if (treat != null) {
                with(sharedTransitionScope) {
                    Image(
                        painter = painterResource(treat.image),
                        contentDescription = treat.name,
                        modifier = Modifier
                            .align(BiasAlignment(2f, .5f))
                            .sharedElement(
                                rememberSharedContentState("treat_${treat.id}"),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                            .width((width * 0.8).dp),
                    )
                }
            }
            Column(modifier = Modifier.padding(25.dp)) {
                Text(
                    "My Order",
                    style = TextStyle(
                        fontFamily = montserrat,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.W700,
                        lineHeight = 1.sp,
                        color = TitleColor
                    )
                )
                Spacer(Modifier.height(25.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        coffee.name,
                        style = TextStyle(
                            fontFamily = questrial,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W500,
                            letterSpacing = 1.sp,
                            color = TitleColor
                        )
                    )
                    Text(
                        "$${coffee.price}€",
                        style = TextStyle(
                            fontFamily = questrial,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W700,
                            letterSpacing = 1.sp,
                            color = TitleColor.copy(alpha = 0.7f)
                        )
                    )
                }

                if (treat != null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            treat.name,
                            style = TextStyle(
                                fontFamily = questrial,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W500,
                                letterSpacing = 1.sp,
                                color = TitleColor
                            )
                        )
                        Text(
                            "$${treat.price}€",
                            style = TextStyle(
                                fontFamily = questrial,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W700,
                                letterSpacing = 1.sp,
                                color = TitleColor.copy(alpha = 0.7f)
                            )
                        )
                    }
                }
                Spacer(Modifier.weight(1f))
                ElevatedButton(
                    onClick = {},
                    colors = ButtonDefaults.elevatedButtonColors(containerColor = TitleColor),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(vertical = 20.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Checkout (${
                            String.format(
                                Locale.ENGLISH,
                                "%.2f",
                                coffee.price + (treat?.price ?: 0f)
                            )
                        }€)"
                    )
                }
            }
        }
    }


}

@Preview
@Composable
private fun CheckoutScreenPreview() {
    CoffeeAppTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                CheckoutScreen(
                    coffee = mockCoffeeItems.first(),
                    treat = mockTreatItems.first(),
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}