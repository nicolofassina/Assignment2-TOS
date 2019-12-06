////////////////////////////////////////////////////////////////////
// [Nicolo] [Fassina] [1166190]
////////////////////////////////////////////////////////////////////

package it.unipd.tos;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.MenuItem.ItemType;

public class PaninotecaPadovanaTest {
    
    @Test
    public void MenuItemGetItemTypeTest() {
        MenuItem sandwich = new MenuItem(ItemType.Panini, "Panino Primavera", 5d);
        assertEquals(ItemType.Panini, sandwich.getItemType());
    }

    @Test
    public void MenuItemGetNameTest() {
        MenuItem fried = new MenuItem(ItemType.Fritti, "Arancini", 5.5d);
        assertEquals("Arancini", fried.getName());
    }

    @Test
    public void MenuItemGetPriceTest() {
        MenuItem drink = new MenuItem(ItemType.Bevande, "Fanta", 1.5d);
        assertEquals(1.5d, drink.getPrice(), 0d);
    }
}
