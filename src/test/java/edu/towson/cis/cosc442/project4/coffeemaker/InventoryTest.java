package edu.towson.cis.cosc442.project4.coffeemaker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }

    @Test
    void newInventoryStartsWith15OfEachIngredient() {
        assertEquals(15, inventory.getCoffee());
        assertEquals(15, inventory.getMilk());
        assertEquals(15, inventory.getSugar());
        assertEquals(15, inventory.getChocolate());
    }

    @Test
    void settersAcceptPositiveValues() {
        inventory.setCoffee(10);
        inventory.setMilk(20);
        inventory.setSugar(30);
        inventory.setChocolate(40);

        assertEquals(10, inventory.getCoffee());
        assertEquals(20, inventory.getMilk());
        assertEquals(30, inventory.getSugar());
        assertEquals(40, inventory.getChocolate());
    }

    @Test
    void settersAcceptZero() {
        inventory.setCoffee(0);
        inventory.setMilk(0);
        inventory.setSugar(0);
        inventory.setChocolate(0);

        assertEquals(0, inventory.getCoffee());
        assertEquals(0, inventory.getMilk());
        assertEquals(0, inventory.getSugar());
        assertEquals(0, inventory.getChocolate());
    }

    @Test
    void negativeValuesResetIngredientsToZero() {
        inventory.setCoffee(-1);
        inventory.setMilk(-1);
        inventory.setSugar(-1);
        inventory.setChocolate(-1);

        assertEquals(0, inventory.getCoffee());
        assertEquals(0, inventory.getMilk());
        assertEquals(0, inventory.getSugar());
        assertEquals(0, inventory.getChocolate());
    }

    @Test
    void enoughIngredientsReturnsTrueForExactAmounts() {
        Recipe recipe = new Recipe();
        recipe.setAmtCoffee(15);
        recipe.setAmtMilk(15);
        recipe.setAmtSugar(15);
        recipe.setAmtChocolate(15);

        assertTrue(inventory.enoughIngredients(recipe));
    }

    @Test
    void enoughIngredientsReturnsTrueWhenInventoryExceedsRequirements() {
        Recipe recipe = new Recipe();
        recipe.setAmtCoffee(5);
        recipe.setAmtMilk(5);
        recipe.setAmtSugar(5);
        recipe.setAmtChocolate(5);

        assertTrue(inventory.enoughIngredients(recipe));
    }

    @Test
    void enoughIngredientsReturnsFalseWhenCoffeeIsInsufficient() {
        Recipe recipe = new Recipe();
        recipe.setAmtCoffee(16);

        assertFalse(inventory.enoughIngredients(recipe));
    }

    @Test
    void enoughIngredientsReturnsFalseWhenMilkIsInsufficient() {
        Recipe recipe = new Recipe();
        recipe.setAmtMilk(16);

        assertFalse(inventory.enoughIngredients(recipe));
    }

    @Test
    void enoughIngredientsReturnsFalseWhenSugarIsInsufficient() {
        Recipe recipe = new Recipe();
        recipe.setAmtSugar(16);

        assertFalse(inventory.enoughIngredients(recipe));
    }

    @Test
    void enoughIngredientsReturnsFalseWhenChocolateIsInsufficient() {
        Recipe recipe = new Recipe();
        recipe.setAmtChocolate(16);

        assertFalse(inventory.enoughIngredients(recipe));
    }

    @Test
    void enoughIngredientsReturnsFalseWhenMultipleIngredientsAreInsufficient() {
        Recipe recipe = new Recipe();
        recipe.setAmtCoffee(16);
        recipe.setAmtMilk(16);
        recipe.setAmtSugar(16);
        recipe.setAmtChocolate(16);

        assertFalse(inventory.enoughIngredients(recipe));
    }

    @Test
    void inventoryToStringDisplaysAllQuantities() {
        String expected =
                "Coffee: 15\n" +
                "Milk: 15\n" +
                "Sugar: 15\n" +
                "Chocolate: 15\n";

        assertEquals(expected, inventory.toString());
    }
}