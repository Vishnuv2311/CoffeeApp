package dev.vishnuv.coffeeapp.models

import androidx.annotation.DrawableRes
import dev.vishnuv.coffeeapp.R

data class TreatItem(
    val id: String,
    val name: String,
    val description: String,
    val colories: String,
    @DrawableRes val image: Int,
    val price: Float
)


val mockTreatItems = listOf(
    TreatItem(
        id = "1",
        name = "New York Cheesecake",
        description = "One of the creamiest cheesecakes you\'ll ever taste with a crumbly biscuit base.",
        image = R.drawable.treat_0,
        colories = "2195 kJ",
        price = 6.34f
    ),
    TreatItem(
        id = "2",
        name = "Strawberry Jam Filled Donut",
        description =
        "A traditional soft and tasy donut, iced with strawberry glaze and filled with strawberry jam. ",
        image = R.drawable.treat_1,
        colories = "1292 kJ",
        price = 4.23f
    ),
    TreatItem(
        id = "3",
        name = "Chocolate Jam Filled Donut",
        description =
        "A traditional soft and tasy donut, iced with chocolate glaze and filled with strawberry jam.",
        image = R.drawable.treat_2,
        colories = "1207 kJ",
        price = 3.23f
    ),
    TreatItem(
        id = "4",
        name = "Galaxy Donut",
        description =
        "A traditional soft and delicious donut iced with either pink or blue galaxy style glaze.",
        image = R.drawable.treat_3,
        colories = "1252 kJ",
        price = 5.38f
    ),
    TreatItem(
        id = "5",
        name = "Chocolate Raspberry Mudcake",
        description =
        "A dense and decadently rich flourless chocolate cake made completely from plant-based ingredients",
        image = R.drawable.treat_4,
        colories = "2916 kJ",
        price = 6.69f
    ),
    TreatItem(
        id = "6",
        name = "Donut Cookie",
        description = "Colourful shortbread cookies in fun, novelty shapes of donuts.",
        image = R.drawable.treat_5,
        colories = "705 kJ",
        price = 3.13f
    ),
    TreatItem(
        id = "7",
        name = "Chocolate Chip Cookie",
        description =
        "Baked to perfection by one of Melbourne’s best small-batch bakehouses with generous chunks of milk chocolate. So soft, so chewy, so snackable!",
        image = R.drawable.treat_6,
        colories = "978 kJ",
        price = 5.13f,
    )
)
