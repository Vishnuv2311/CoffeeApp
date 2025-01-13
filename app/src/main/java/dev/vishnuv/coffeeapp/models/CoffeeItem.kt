package dev.vishnuv.coffeeapp.models

import androidx.annotation.DrawableRes
import dev.vishnuv.coffeeapp.R

data class CoffeeItem(
    val id: String,
    val name: String,
    val description: String,
    @DrawableRes val image: Int,
    val price: Float
)

val mockCoffeeItems = listOf(
    CoffeeItem(
        id = "1",
        name = "Iced Americano",
        description =
        "Prepared by combining our signature espresso with water and pouring it over ice to chill= All the flavour of your favourite brew, minus the heat.",
        image = R.drawable.glass_0,
        price = 2.0f
    ),
    CoffeeItem(
        id = "5",
        name = "Chai Frappuccino",
        description =
        "In many parts of the world, chai simply means tea, but in North America the word has come to be shorthand for masala chai, a traditional Indian spiced tea like the one offered by Tazo.",
        image = R.drawable.glass_4,
        price = 6.0f
    ),
    CoffeeItem(
        id = "4",
        name = "Iced Caramel Macchiato",
        description =
        "To our bold espresso we add a creamy mix of vanilla syrup and cold milk poured over ice; it\"s then topped with our proprietary buttery caramel sauce. Sweet! And, fret not, this ever-popular beverage is here to stay.",
        image = R.drawable.glass_3,
        price = 5.0f
    ),
    CoffeeItem(
        id = "9",
        name = "Batch Brew",
        description =
        "Back home in our tasting room, we take these amazing coffees and mix them with beans from other regions. That\"s how we create some of our most famous blends.",
        image = R.drawable.glass_8,
        price = 3.0f
    ),
    CoffeeItem(
        id = "3",
        name = "Caramel Macchiato",
        description =
        "you\'d think it was some kind of magical elixir. Well there\'s no hocus pocus here. We\'ll tell you exactly what goes into it= creamy vanilla-flavoured syrup, freshly steamed milk with a topping of velvety-rich foam, an intense hit of our Espresso Roast, a finishing of buttery caramel drizzle … okay, we take it back. That does sound like magic to us.",
        image = R.drawable.glass_2,
        price = 4.0f
    ),
    CoffeeItem(
        id = "2",
        name = "Iced Caffè Mocha",
        description =
        "What more could you want from a summertime treat? Espresso, bittersweet mocha sauce poured over ice and topped with rich, sweetened whipped cream. Delightful. The real question is what to call it, coffee or dessert?",
        image = R.drawable.glass_1,
        price = 3.0f
    ),
    CoffeeItem(
        id = "6",
        name = "Cappuccino",
        description =
        "To make it properly requires much skill and attentiveness. Arguably the most important part is frothing the foam to velvety perfection as the milk steams - something our baristas take great care to achieve. The milky moustache that clings to your upper lip is proof we\'ve made yours right. And may we say, you wear it well.",
        image = R.drawable.glass_5,
        price = 7.0f
    ),
    CoffeeItem(
        id = "7",
        name = "Strawberries Frappuccino",
        description =
        "Inspired by the classic, country-style treat of strawberries and cream, we\'ve created a refreshing beverage that sweetens any summer activity.",
        image = R.drawable.glass_6,
        price = 8.0f
    ),
    CoffeeItem(
        id = "8",
        name = "Java Chip Frappuccino",
        description =
        "We created this wondrously decadent beverage for those who love the taste of chocolate - and lots of it -  with their iced coffee. Rich, chocolatey chips punctuate a cool, refreshing blend of coffee and mocha flavours.",
        image = R.drawable.glass_7,
        price = 2.0f
    )
)
