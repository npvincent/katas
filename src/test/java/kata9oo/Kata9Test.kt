import org.testng.annotations.Test
import kotlin.test.assertEquals

class Kata9Test {

	var cartCalculator = CartCalculator()

	@Test
	fun test(){
		cartCalculator.calculate(emptyList(), listOf(
				Promotion("A", 25, 20),
				Promotion("A", 100, 50),
				Promotion("A", 10, 9),
				Promotion("A", 50, 30)
		))





	}

	@Test
	fun cartCalculate1() {
		val price = cartCalculator.calculate(
				listOf(
						CartItem("A", 1, 100)
				),
				listOf(
						Promotion("A", 30, 20)
				))

		assertEquals(70, price, "Calculation was correct")
	}

	@Test
	fun cartCalculate2() {
		val price = cartCalculator.calculate(
				listOf(
						CartItem("A", 1, 100)
				),
				listOf(
						Promotion("A", 30, 20),
						Promotion("A", 6, 5)
				))

		assertEquals(69, price, "Calculation was correct")
		assertEquals(2,3)
	}

}