import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

sealed class Coffee(
    val name: String,
    val sugar: Int,
    val volume: Double
) {
    class Americano(sugar: Int, volume: Double) : Coffee("Americano", sugar, volume)
    class Cappuccino(sugar: Int, volume: Double, val milkAmount: Double) :
        Coffee("Cappuccino", sugar, volume)

    class Latte(sugar: Int, volume: Double) : Coffee("Latte", sugar, volume)
}

fun selectMenu(): Coffee {
    println("Выберите кофе:")
    println("1. Americano")
    println("2. Cappuccino")
    println("3. Latte")

    val coffeeType = readLine()?.toIntOrNull() ?: 1

    println("Введите количество сахара (в ложках):")
    val sugar = readLine()?.toIntOrNull() ?: 0

    println("Введите объем кофе (например, 0.2, 0.3):")
    val volume = readLine()?.toDoubleOrNull() ?: 0.2

    return when (coffeeType) {
        1 -> Coffee.Americano(sugar, volume)
        2 -> {
            println("Введите количество молока (в мл):")
            val milkAmount = readLine()?.toDoubleOrNull() ?: 50.0
            Coffee.Cappuccino(sugar, volume, milkAmount)
        }
        3 -> Coffee.Latte(sugar, volume)
        else -> Coffee.Americano(sugar, volume)
    }
}
fun prepareCoffee(coffee: Coffee) = runBlocking {
    println("Приготовление кофе началось...")
    for (i in 10..100 step 10) {
        delay(500L)
        println("$i%")
    }

    when (coffee) {
        is Coffee.Americano -> {
            println("Ваш кофе готов: ${coffee.name}, сахар: ${coffee.sugar}, объем: ${coffee.volume}")
        }
        is Coffee.Cappuccino -> {
            println(
                "Ваш кофе готов: ${coffee.name}, сахар: ${coffee.sugar}, объем: ${coffee.volume}, молоко: ${coffee.milkAmount} мл"
            )
        }
        is Coffee.Latte -> {
            println("Ваш кофе готов: ${coffee.name}, сахар: ${coffee.sugar}, объем: ${coffee.volume}")
        }
    }
}

fun main() {
    println("Добро пожаловать в кофе-машину!")
    val selectedCoffee = selectMenu()
    prepareCoffee(selectedCoffee)
}