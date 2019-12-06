////////////////////////////////////////////////////////////////////
// [Nicolo] [Fassina] [1166190]
////////////////////////////////////////////////////////////////////

package it.unipd.tos;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.ArrayList;

import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.MenuItem.ItemType;
import it.unipd.tos.business.TakeAwayBillImpl;
import it.unipd.tos.business.exception.TakeAwayBillException;

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
    
    @Test
    public void Min1ItemsOrderTest() throws TakeAwayBillException {
        try {
            new TakeAwayBillImpl().getOrderPrice(null);
        }
        catch(TakeAwayBillException exception) {
            assertEquals("Empty menu", exception.getError());
        }
    }
    
    @Test
    public void Max30ItemsOrderTest() throws TakeAwayBillException {
        List<MenuItem> orderedItems = new ArrayList<>();
        for(int i=0; i<31; i++) {
            orderedItems.add(new MenuItem(ItemType.Bevande, "Coca Cola", 2d));
        }
        try {
            new TakeAwayBillImpl().getOrderPrice(orderedItems);
        }
        catch(TakeAwayBillException exception) {
            assertEquals("Too many items: Maximum 30 items allowed", exception.getError());
        }
    }
    
    @Test
    public void NoDiscountsForMoreThan10EuroAndLessThan50EuroOrderWithLessThan5SandwichesTest() {
        TakeAwayBillImpl order = new TakeAwayBillImpl();
        List<MenuItem> orderedItems = new ArrayList<MenuItem>();
        orderedItems.add(new MenuItem(ItemType.Panini, "Panino Vegano", 4d));
        orderedItems.add(new MenuItem(ItemType.Panini, "Panino Light", 3d));
        for(int index = 0; index < 3; ++index) {
            orderedItems.add(new MenuItem(ItemType.Fritti, "Olive Ascolane", 4.5d));
            orderedItems.add(new MenuItem(ItemType.Bevande, "Acuqua Frizzante", 1.2d));
        }
        try {
            assertEquals(24.1d, order.getOrderPrice(orderedItems), 0d);
        }
        catch(TakeAwayBillException exception) {
            exception.getMessage();
        }
    }
    
    @Test
    public void FiftyPerCentDiscountToTheCheaperSandwichForOrderWithMoreThan5SandwichesTest() {
        TakeAwayBillImpl order = new TakeAwayBillImpl();
        List<MenuItem> orderedItems = new ArrayList<MenuItem>();
        for(int index = 0; index < 4; ++index) {
            orderedItems.add(new MenuItem(ItemType.Panini, "Panino Primavera", 5d));
        }
        for(int index = 0; index < 2; ++index) {
            orderedItems.add(new MenuItem(ItemType.Panini, "Panino Vegano", 4d));
        }
        orderedItems.add(new MenuItem(ItemType.Fritti, "Patatine Fritte", 3.5d));
        orderedItems.add(new MenuItem(ItemType.Bevande, "Fanta", 1.5d));
        try {
            assertEquals(31d, order.getOrderPrice(orderedItems), 0d);
        }
        catch (TakeAwayBillException exception) {
            exception.getMessage();
        }
    }
    
    @Test
    public void FiftyCentsCommissionForLessThan10EuroOrderTest() {
        TakeAwayBillImpl order = new TakeAwayBillImpl();
        List<MenuItem> orderedItems = new ArrayList<MenuItem>();
        orderedItems.add(new MenuItem(ItemType.Panini, "Panino Light", 3d));
        orderedItems.add(new MenuItem(ItemType.Fritti, "Olive Ascolane", 4.5d));
        orderedItems.add(new MenuItem(ItemType.Bevande, "Pepsi", 1.8d));
        try {
            assertEquals(9.8d, order.getOrderPrice(orderedItems), 0d);
        }
        catch (TakeAwayBillException exception) {
            exception.getMessage();
        }
    }
    
    @Test
    public void DiscountToTheCheaperSandwichAndCommissionForLessThan10EuroOrderWithMoreThan5SandwichesTest() {
        TakeAwayBillImpl order = new TakeAwayBillImpl();
        List<MenuItem> orderedItems = new ArrayList<MenuItem>();
        for(int index = 0; index < 6; ++index) {
            orderedItems.add(new MenuItem(ItemType.Panini, "Panino Mignon", 1.8d));
        }
        try {
            assertEquals(10.4d, order.getOrderPrice(orderedItems), 0d);
        }
        catch (TakeAwayBillException exception) {
            exception.getMessage();
        }
    }
    
    @Test
    public void NoDiscountsForMoreThan50EuroButLessWithoutDrinksOrderTest() {
        TakeAwayBillImpl order = new TakeAwayBillImpl();
        List<MenuItem> orderedItems = new ArrayList<MenuItem>();
        for(int index = 0; index < 4; ++index) {
            orderedItems.add(new MenuItem(ItemType.Panini, "Panino Primavera", 5d));
            orderedItems.add(new MenuItem(ItemType.Fritti, "Frittura di Pesce", 6d));
        }
        for(int index = 0; index < 10; ++index) {
            orderedItems.add(new MenuItem(ItemType.Bevande, "Acqua Naturale", 1d));
        }
        try {
            assertEquals(54d, order.getOrderPrice(orderedItems), 0d);
        }
        catch (TakeAwayBillException exception) {
            exception.getMessage();
        }
    }
    
    @Test
    public void TenPerCentDiscountForMoreThan50EuroEvenWithoutDrinksOrderTest() {
        TakeAwayBillImpl order = new TakeAwayBillImpl();
        List<MenuItem> orderedItems = new ArrayList<MenuItem>();
        for(int index = 0; index < 4; ++index) {
            orderedItems.add(new MenuItem(ItemType.Panini, "Panino Vegetariano", 4.5d));
            orderedItems.add(new MenuItem(ItemType.Bevande, "Coca Cola", 2d));
        }
        for(int index = 0; index < 8; ++index) {
            orderedItems.add(new MenuItem(ItemType.Fritti, "Olive Ascolane", 4.5d));
        }
        try {
            assertEquals(55.8d, order.getOrderPrice(orderedItems), 0d);
        }
        catch (TakeAwayBillException exception) {
            exception.getMessage();
        }
    }
    
    @Test
    public void BothDiscountsForMoreThan50EuroEvenWithoutDrinksOrderWithMoreThan5SandwichesTest() {
        TakeAwayBillImpl order = new TakeAwayBillImpl();
        List<MenuItem> orderedItems = new ArrayList<MenuItem>();
        for(int index = 0; index < 4; ++index) {
            orderedItems.add(new MenuItem(ItemType.Panini, "Panino Primavera", 5d));
        }
        for(int index = 0; index < 2; ++index) {
            orderedItems.add(new MenuItem(ItemType.Panini, "Panino Light", 3d));
        }
        for(int index = 0; index < 10; ++index) {
            orderedItems.add(new MenuItem(ItemType.Fritti, "Patatine Fritte", 3.5d));
        }
        orderedItems.add(new MenuItem(ItemType.Bevande, "Fanta", 1.5d));
        try {
            assertEquals(54.9d, order.getOrderPrice(orderedItems), 0d);
        }
        catch (TakeAwayBillException exception) {
            exception.getMessage();
        }
    }
    
}
