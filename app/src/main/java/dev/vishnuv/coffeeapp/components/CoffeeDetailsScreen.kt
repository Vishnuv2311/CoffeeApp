@file:OptIn(ExperimentalMaterial3Api::class)

package dev.vishnuv.coffeeapp.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.vishnuv.coffeeapp.R
import dev.vishnuv.coffeeapp.models.CoffeeItem
import dev.vishnuv.coffeeapp.models.mockCoffeeItems
import dev.vishnuv.coffeeapp.noRippleClickable
import dev.vishnuv.coffeeapp.ui.theme.CoffeeAppTheme
import dev.vishnuv.coffeeapp.ui.theme.TitleColor
import dev.vishnuv.coffeeapp.ui.theme.questrial
import java.util.Locale


@Composable
fun CoffeeDetailsScreen(
    modifier: Modifier = Modifier,
    coffee: CoffeeItem,
    onSweetTreatsClick: (Int) -> Unit = {}
) {

    var sizeCoffee by remember { mutableStateOf('M') }

    val coffeeScale by animateFloatAsState(
        targetValue = if (sizeCoffee == 'M') 1.36f else if (sizeCoffee == 'L') 1.5f else 1.2f,
        label = "Coffee Cup Scale Animation",
        animationSpec = tween(durationMillis = 400, easing = EaseOutBack)
    )

    val config = LocalConfiguration.current
    val width = config.screenWidthDp
    val height = config.screenHeightDp

    val titleStyle = TextStyle(
        fontSize = 30.sp,
        fontWeight = FontWeight.W700,
        color = TitleColor
    )


    Scaffold(topBar = { TopAppBar(title = {}) }) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {

            dev.vishnuv.coffeeapp.components.Background()

            Column(Modifier.padding(25.dp)) {

                Text(
                    coffee.name,
                    style = titleStyle,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.width((width * 0.6).dp)
                )

                Spacer(Modifier.height(20.dp))


                Text(
                    coffee.description,
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontFamily = questrial,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W400,
                        color = TitleColor.copy(alpha = 0.5f)
                    )
                )





                Spacer(Modifier.weight(1f))

                Text(
                    "${
                        String.format(
                            Locale.ENGLISH,
                            "%.2f",
                            (coffee.price + (if (sizeCoffee == 'M') 0f else if (sizeCoffee == 'L') 1.2f else -.8f))
                        )
                    }€", style = titleStyle,
                    modifier = Modifier.align(Alignment.Start)
                )

                Column(horizontalAlignment = Alignment.Start) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painterResource(R.drawable.ic_coffee),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            when (sizeCoffee) {
                                'M' -> "Basic"
                                'L' -> "Large"
                                else -> "Small"
                            },
                            style = TextStyle(
                                fontFamily = questrial,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W400,
                                color = TitleColor
                            )
                        )


                    }

                    Spacer(Modifier.height(20.dp))

                    Row {
                        listOf('S', 'M', 'L').forEach { sizeCoffe ->

                            val fontColor by animateColorAsState(
                                if (sizeCoffee != sizeCoffe) TitleColor else Color.White,
                                label = "Coffe Soze Text Animation",
                                animationSpec = tween(durationMillis = 300)
                            )

                            Text(
                                sizeCoffe.toString(),
                                style = TextStyle(
                                    fontFamily = questrial,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.W400,
                                    color = fontColor
                                ),
                                modifier =
                                Modifier
                                    .padding(horizontal = 4.dp)
                                    .noRippleClickable { sizeCoffee = sizeCoffe }
                                    .background(
                                        color = if (sizeCoffee == sizeCoffe) TitleColor else Color.Transparent,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .border(
                                        width = 2.dp,
                                        color = if (sizeCoffee != sizeCoffe) TitleColor else Color.Transparent,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp)

                            )


                        }

                    }
                }

                Spacer(Modifier.height(100.dp))

                ElevatedButton(
                    onClick = {
                        onSweetTreatsClick(mockCoffeeItems.indexOf(coffee))
                    },
                    colors = ButtonDefaults.elevatedButtonColors(containerColor = TitleColor),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 15.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Add to cart",
                        style = TextStyle(
                            fontFamily = questrial,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W400,
                            color = Color.White
                        )
                    )
                    Spacer(Modifier.width(5.dp))
                    Icon(
                        painterResource(R.drawable.ic_chevron_right),
                        contentDescription = "Add to cart",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.weight(1f))

                }

            }

            Image(
                painter = painterResource(coffee.image),
                contentDescription = coffee.name,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .width(width.dp)
                    .height((height * 0.7).dp)
                    .offset(x = (width * 0.38).dp, y = (height * 0.15).dp)
                    .scale(coffeeScale)
            )

        }
    }


}

@Preview
@Composable
private fun CoffeeDetailsScreenPreview() {
    CoffeeAppTheme {
        CoffeeDetailsScreen(coffee = mockCoffeeItems.first())
    }
}