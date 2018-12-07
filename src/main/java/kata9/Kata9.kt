import java.util.*


data class CartItem(val itemCode:String="", val unitPrice:Int=0, val quantity:Int=0)
data class Promotion(val itemCode:String="", val quantity:Int=0, val price:Int=0)

class CartCalculator {
	fun calculate(cartContents:List<CartItem>, promotions:Collection<Promotion>):Int {

		// Create a lookup structure for the promotions.  Key by promotion, then each key should be a navigable map keyed by the quantity that applies to the promotion
		val promoLookup = promotions.groupBy {it.itemCode}.entries.associate { it.key to TreeMap(it.value.groupBy { it.quantity }) }
		val cartByItem = cartContents.groupBy { it.itemCode }

		val itemTotals = cartByItem.entries.associate {

			var itemTotal = 0
			var quantityRemaining = it.value.fold(0) { sum, item -> sum + item.quantity }
			val promosForItem = promoLookup[it.key]
			if(promosForItem!=null) {
				var bestPromo: Promotion?
				do {
					bestPromo = promosForItem.floorEntry(quantityRemaining)?.value?.first()
					if(bestPromo!=null) {
						itemTotal += bestPromo.price
						quantityRemaining -= bestPromo.quantity
					}
				} while (bestPromo != null)

				itemTotal += quantityRemaining*it.value.first().unitPrice
			}

			it.key to itemTotal
		}

		// Add up the item totals
		return itemTotals.values.reduce { a,b -> a+b }
	}
}