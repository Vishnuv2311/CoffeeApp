@file:OptIn(ExperimentalSharedTransitionApi::class)

package dev.vishnuv.coffeeapp.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.vishnuv.coffeeapp.models.CoffeeItem
import dev.vishnuv.coffeeapp.models.mockCoffeeItems
import dev.vishnuv.coffeeapp.ui.theme.CoffeeAppTheme
import dev.vishnuv.coffeeapp.ui.theme.TitleColor
import dev.vishnuv.coffeeapp.ui.theme.montserrat
import dev.vishnuv.coffeeapp.ui.theme.questrial

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SweetTreats(
    modifier: Modifier = Modifier,
    coffee: CoffeeItem,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onCoffeeTreatClick: (Int, Int?) -> Unit = { _, _ -> }
) {
    val config = LocalConfiguration.current
    val width = config.screenWidthDp
    val height = config.screenHeightDp

    val titleStyle = TextStyle(
        fontFamily = montserrat,
        fontSize = 30.sp,
        fontWeight = FontWeight.W700,
        color = TitleColor
    )


    Scaffold(topBar = { TopAppBar(title = {}) }) {innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {

            Background()
            TreatsList(
                coffee = coffee,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope,
                onCoffeeTreatClick = onCoffeeTreatClick
            )
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.width((width * 0.55).dp)
                ) {
                    with(sharedTransitionScope) {
                        Text(
                            coffee.name,
                            style = titleStyle,
                            textAlign = TextAlign.End,
                            modifier = Modifier.sharedElement(
                                rememberSharedContentState(key = "coffee_${coffee.id}_name"),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                        )
                    }
                    Spacer(Modifier.height(30.dp))
                    Text(
                        "Would you like to add some sweet treats?",
                        style = TextStyle(
                            fontFamily = questrial,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W100,
                            color = TitleColor.copy(alpha = .5f)
                        ),
                        textAlign = TextAlign.End
                    )
                    Spacer(Modifier.height(15.dp))
                    ElevatedButton(
                        onClick = { onCoffeeTreatClick(mockCoffeeItems.indexOf(coffee), null) },
                        colors = ButtonDefaults.elevatedButtonColors(containerColor = TitleColor),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 15.dp),
                        shape = RoundedCornerShape(10.dp),
                    ) {
                        Text("No, thanks!")
                    }
                }
            }


        }
    }

}

@Preview
@Composable
private fun SweetTreatsPreview() {
    CoffeeAppTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                SweetTreats(
                    coffee = mockCoffeeItems.first(),
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}