package kata9

import org.testng.annotations.Test
import kotlin.test.assertEquals

class Kata9Test {

	@Test
	fun testRulesWorkOnDifferentSkus() {
		val cart = Cart(listOf(
				CartItem("A", 10),
				CartItem("A", 10),
				CartItem("A", 10),
				CartItem("B", 10),
				CartItem("B", 5)
		))

		assertEquals(29, cart.calculate(listOf(
				SingleSkuMultibuy("A", 3, 25),
				SingleSkuMultibuy("B", 2, 4)
		)))
	}

	@Test
	fun testSingleRule(){
		val cart = Cart(listOf(
				CartItem("A", 10),
				CartItem("A", 10),
				CartItem("A", 10)
		))

		assertEquals(25, cart.calculate(listOf(
				SingleSkuMultibuy("A", 3, 25)
		)))
	}

	@Test
	fun testOneLeftoverItem(){
		val cart = Cart(listOf(
				CartItem("A", 10),
				CartItem("A", 10),
				CartItem("A", 10),
				CartItem("A", 10),
				CartItem("A", 10),
				CartItem("A", 10),
				CartItem("A", 10),
				CartItem("A", 10),
				CartItem("A", 10),
				CartItem("A", 10)
		))

		assertEquals(85, cart.calculate(listOf(
				SingleSkuMultibuy("A", 3, 25)
		)))
	}

	@Test
	fun testMultipleLeftoverItems() {
		val cart = Cart(listOf(
				CartItem("A", 10),
				CartItem("A", 10),
				CartItem("A", 10),
				CartItem("A", 10),
				CartItem("A", 10),
				CartItem("A", 10),
				CartItem("A", 10),
				CartItem("A", 10),
				CartItem("A", 10),
				CartItem("A", 10)
		))

		assertEquals(70, cart.calculate(listOf(
				SingleSkuMultibuy("A", 4, 25)
		)))
	}

	@Test
	fun testMostExpensiveItemsTakenFirst() {
		val cart = Cart(listOf(
				CartItem("A", 15),
				CartItem("A", 10),
				CartItem("A", 20)
		))

		assertEquals(13, cart.calculate(listOf(
				SingleSkuMultibuy("A", 2, 3)
		)))
	}


}