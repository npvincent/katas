package kata9

data class CartItem(val sku:String="", val unitPrice:Int=0)
data class PricingResult(val items:List<CartItem>, val totalPrice:Int)

interface PricingRule {
	fun apply(itemsBySku:Map<String,MutableList<CartItem>>):Collection<PricingResult>
}

// A cart which contains individual line items
class Cart(val items:List<CartItem>) {

	fun calculate(rules:List<PricingRule>):Int {

		// Make a lookup table with items grouped by sku, with mutable keys and prices sorted so that expensive items are taken first
		val itemsBySku = this.items
				.groupBy { it.sku }.entries
				.associate { entry -> entry.key to entry.value.sortedByDescending { it.unitPrice }.toMutableList() }

		// Single threaded only please as we are holding calculation state in the itemsBySky object
		val pricedCart = rules.map { it.apply(itemsBySku) }.flatten()

		return pricedCart.fold(0) { total, item -> total + item.totalPrice }
	}
}

// A multibuy which operates on a single sku
class SingleSkuMultibuy(val sku:String, val quantity:Int, val totalPrice: Int) : PricingRule {
	override fun apply(itemsBySku: Map<String, MutableList<CartItem>>): Collection<PricingResult> {

		if(itemsBySku.containsKey(sku)) {
			val (qualified, leftover) = itemsBySku.getOrDefault(sku, mutableListOf())
					.chunked(quantity)
					.partition { it.size == quantity }

			return listOf(
					qualified.map { PricingResult(it, totalPrice) },
					listOf(PricingResult(leftover.flatten(), leftover.flatten().fold(0) { total, item -> total+item.unitPrice} ))
			).flatten()
		}

		return emptyList()
	}
}