package edu.towson.cis.cosc442.project4.coffeemaker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class RecipeTest {

    @Test
    void newRecipeStartsWithZeroAmountsAndNullName() {
        Recipe recipe = new Recipe();
        assertNull(recipe.getName());
        assertEquals(0, recipe.getPrice());
        assertEquals(0, recipe.getAmtCoffee());
        assertEquals(0, recipe.getAmtMilk());
        assertEquals(0, recipe.getAmtSugar());
        assertEquals(0, recipe.getAmtChocolate());
    }

    @Test
    void storesRecipeNameAndPrice() {
        Recipe recipe = new Recipe();
        recipe.setName("Latte");
        recipe.setPrice(50);
        assertEquals("Latte", recipe.getName());
        assertEquals(50, recipe.getPrice());
        assertEquals("Latte", recipe.toString());
    }

    @Test
    void storesAllIngredientQuantities() {
        Recipe recipe = new Recipe();
        recipe.setAmtCoffee(1);
        recipe.setAmtMilk(2);
        recipe.setAmtSugar(3);
        recipe.setAmtChocolate(4);
        assertEquals(1, recipe.getAmtCoffee());
        assertEquals(2, recipe.getAmtMilk());
        assertEquals(3, recipe.getAmtSugar());
        assertEquals(4, recipe.getAmtChocolate());
    }

    @Test
    void zeroIsAcceptedForAllNumericFields() {
        Recipe recipe = new Recipe();
        recipe.setPrice(0);
        recipe.setAmtCoffee(0);
        recipe.setAmtMilk(0);
        recipe.setAmtSugar(0);
        recipe.setAmtChocolate(0);
        assertEquals(0, recipe.getPrice());
        assertEquals(0, recipe.getAmtCoffee());
        assertEquals(0, recipe.getAmtMilk());
        assertEquals(0, recipe.getAmtSugar());
        assertEquals(0, recipe.getAmtChocolate());
    }

    @Test
    void negativeValuesResetAllNumericFieldsToZero() {
        Recipe recipe = new Recipe();
        recipe.setPrice(10);
        recipe.setAmtCoffee(10);
        recipe.setAmtMilk(10);
        recipe.setAmtSugar(10);
        recipe.setAmtChocolate(10);

        recipe.setPrice(-1);
        recipe.setAmtCoffee(-1);
        recipe.setAmtMilk(-1);
        recipe.setAmtSugar(-1);
        recipe.setAmtChocolate(-1);

        assertEquals(0, recipe.getPrice());
        assertEquals(0, recipe.getAmtCoffee());
        assertEquals(0, recipe.getAmtMilk());
        assertEquals(0, recipe.getAmtSugar());
        assertEquals(0, recipe.getAmtChocolate());
    }

    @Test
    void recipesWithSameNonNullNameAreEqual() {
        Recipe first = new Recipe();
        Recipe second = new Recipe();
        first.setName("Mocha");
        second.setName("Mocha");
        second.setPrice(999);
        assertTrue(first.equals(second));
    }

    @Test
    void recipesWithDifferentNamesAreNotEqual() {
        Recipe first = new Recipe();
        Recipe second = new Recipe();
        first.setName("Latte");
        second.setName("Mocha");
        assertFalse(first.equals(second));
    }

    @Test
    void recipesWithNullNamesAreNotEqual() {
        Recipe first = new Recipe();
        Recipe second = new Recipe();
        assertFalse(first.equals(second));

        first.setName("Latte");
        assertFalse(first.equals(second));

        second.setName("Latte");
        first.setName(null);
        assertFalse(first.equals(second));
    }
}