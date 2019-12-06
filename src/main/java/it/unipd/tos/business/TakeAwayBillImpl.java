////////////////////////////////////////////////////////////////////
// [Nicolo] [Fassina] [1166190]
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;

import java.util.List;

import it.unipd.tos.business.exception.TakeAwayBillException;
import it.unipd.tos.model.MenuItem;

public class TakeAwayBillImpl implements TakeAwayBill {
    @Override
    public double getOrderPrice(List<MenuItem> itemsOrdered) throws TakeAwayBillException {
        if(itemsOrdered == null) {
            throw new TakeAwayBillException("Empty menu");
        }
        if(itemsOrdered.size() > 30) {
            throw new TakeAwayBillException("Too many items: Maximum 30 items allowed");
        }
        double totalPriceFood = 0d;
        double totalPriceDrinks = 0d;
        double totalPrice = 0d;
        int sandwichCounter = 0;
        double minSandwichPrice = Double.MAX_VALUE;
        for(int index = 0; index < itemsOrdered.size(); ++index) {
            MenuItem item = itemsOrdered.get(index);
            if(item.getItemType().name() == "Bevande") {
                 totalPriceDrinks += item.getPrice();
            }
            else {
                if(item.getItemType().name() == "Panini") {
                    ++sandwichCounter;
                    if(item.getPrice() < minSandwichPrice) {
                        minSandwichPrice = item.getPrice();
                    }
                }
                totalPriceFood += item.getPrice();
            }
        }
        if(sandwichCounter > 5) {
            totalPriceFood -=  minSandwichPrice * 0.5d;
        }
        totalPrice = totalPriceFood + totalPriceDrinks;
        if(totalPriceFood > 50) {
            totalPrice -= totalPrice * 0.1d;
        }
        if(totalPrice < 10) {
            totalPrice += 0.5d;
        }
        return totalPrice;
    }
}
