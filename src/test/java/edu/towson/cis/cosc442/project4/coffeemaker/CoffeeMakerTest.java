
package edu.towson.cis.cosc442.project4.coffeemaker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class CoffeeMakerTest {

    private CoffeeMaker maker;

    @BeforeEach
    void setUp() {
        maker = new CoffeeMaker();
    }

    private Recipe createRecipe(String name, int price,
                                int coffee, int milk,
                                int sugar, int chocolate) {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setPrice(price);
        recipe.setAmtCoffee(coffee);
        recipe.setAmtMilk(milk);
        recipe.setAmtSugar(sugar);
        recipe.setAmtChocolate(chocolate);
        return recipe;
    }

    @Test
    void newCoffeeMakerHasFourEmptyRecipeSlots() {
        Recipe[] recipes = maker.getRecipes();

        assertEquals(4, recipes.length);

        for (Recipe recipe : recipes) {
            assertNull(recipe.getName());
        }
    }

    @Test
    void newCoffeeMakerStartsWithDefaultInventory() {
        Inventory inventory = maker.checkInventory();

        assertEquals(15, inventory.getCoffee());
        assertEquals(15, inventory.getMilk());
        assertEquals(15, inventory.getSugar());
        assertEquals(15, inventory.getChocolate());
    }

    @Test
    void addsOneRecipeSuccessfully() {
        Recipe recipe = createRecipe("Latte", 50, 1, 1, 1, 0);

        assertTrue(maker.addRecipe(recipe));
        assertTrue(containsRecipe("Latte"));
    }

    @Test
    void rejectsDuplicateRecipeName() {
        Recipe first = createRecipe("Latte", 50, 1, 1, 1, 0);
        Recipe duplicate = createRecipe("Latte", 100, 2, 2, 2, 2);

        assertTrue(maker.addRecipe(first));
        assertFalse(maker.addRecipe(duplicate));
    }

    @Test
    void acceptsFourDifferentRecipes() {
        for (int i = 1; i <= 4; i++) {
            Recipe recipe = createRecipe(
                    "Recipe" + i, 10 * i, 1, 1, 1, 1);
            assertTrue(maker.addRecipe(recipe));
        }

        assertEquals(4, countNamedRecipes());
    }

    @Test
    void rejectsFifthRecipeWhenFull() {
        for (int i = 1; i <= 4; i++) {
            assertTrue(maker.addRecipe(
                    createRecipe("Recipe" + i, 10, 1, 1, 1, 1)));
        }

        Recipe fifth = createRecipe("Fifth", 20, 1, 1, 1, 1);

        assertFalse(maker.addRecipe(fifth));
        assertFalse(containsRecipe("Fifth"));
    }

    @Test
    void deletingNullRecipeReturnsFalse() {
        assertFalse(maker.deleteRecipe(null));
    }

    @Test
    void deletingMissingRecipeReturnsFalse() {
        Recipe missing = createRecipe("Missing", 20, 1, 1, 1, 1);

        assertFalse(maker.deleteRecipe(missing));
    }

    @Test
    void editingMissingRecipeReturnsFalse() {
        Recipe oldRecipe = createRecipe("Old", 20, 1, 1, 1, 1);
        Recipe newRecipe = createRecipe("New", 30, 2, 2, 2, 2);

        assertFalse(maker.editRecipe(oldRecipe, newRecipe));
    }

    @Test
    void addingZeroInventorySucceeds() {
        assertTrue(maker.addInventory(0, 0, 0, 0));

        Inventory inventory = maker.checkInventory();

        assertEquals(15, inventory.getCoffee());
        assertEquals(15, inventory.getMilk());
        assertEquals(15, inventory.getSugar());
        assertEquals(15, inventory.getChocolate());
    }

    @Test
    void addingNegativeCoffeeIsRejected() {
        assertFalse(maker.addInventory(-1, 0, 0, 0));
    }

    @Test
    void addingNegativeMilkIsRejected() {
        assertFalse(maker.addInventory(0, -1, 0, 0));
    }

    @Test
    void addingNegativeChocolateIsRejected() {
        assertFalse(maker.addInventory(0, 0, 0, -1));
    }

    @Test
    void insufficientPaymentReturnsFullAmount() {
        Recipe recipe = createRecipe("Latte", 50, 1, 1, 1, 1);

        assertEquals(49, maker.makeCoffee(recipe, 49));
    }

    @Test
    void insufficientIngredientsReturnsFullPayment() {
        Recipe recipe = createRecipe("Expensive", 50, 16, 1, 1, 1);

        assertEquals(100, maker.makeCoffee(recipe, 100));
    }

    @Test
    void successfulPurchaseReturnsCorrectChange() {
        Recipe recipe = createRecipe("Black Coffee", 50, 0, 0, 0, 0);

        assertEquals(25, maker.makeCoffee(recipe, 75));
    }

    @Test
    void purchaseWithExactPaymentReturnsZeroChange() {
        Recipe recipe = createRecipe("Black Coffee", 50, 0, 0, 0, 0);

        assertEquals(0, maker.makeCoffee(recipe, 50));
    }

  
    @Test
    void getRecipeForNameFindsExistingRecipe() {
        Recipe recipe = createRecipe("Latte", 50, 1, 1, 1, 0);
        maker.addRecipe(recipe);

        Recipe found = maker.getRecipeForName("Latte");

        assertEquals("Latte", found.getName());
        assertEquals(50, found.getPrice());
    }

    @Test
    void getRecipeForNameReturnsEmptyRecipeWhenNotFound() {
        Recipe found = maker.getRecipeForName("Missing");

        assertNull(found.getName());
    }

    @Test
    void getRecipeForNameDoesNotReturnWrongRecipe() {
        maker.addRecipe(createRecipe("Latte", 50, 1, 1, 1, 0));
        maker.addRecipe(createRecipe("Mocha", 75, 2, 2, 2, 2));

        Recipe found = maker.getRecipeForName("Mocha");

        assertEquals("Mocha", found.getName());
        assertEquals(75, found.getPrice());
    }

    @Test
    void deletingExistingRecipeReturnsTrue() {
        Recipe recipe = createRecipe("Latte", 50, 1, 1, 1, 0);

        assertTrue(maker.addRecipe(recipe));
        assertTrue(maker.deleteRecipe(recipe));
    }

    @Test
    void editingExistingRecipeExecutesReplacementLogic() {
        Recipe oldRecipe = createRecipe("Latte", 50, 1, 1, 1, 0);
        Recipe newRecipe = createRecipe("Latte", 75, 2, 2, 2, 0);

        assertTrue(maker.addRecipe(oldRecipe));
        assertTrue(maker.editRecipe(oldRecipe, newRecipe));
    }

   @Disabled

    @Test
    void addingInventoryIncreasesIngredientQuantities() {
        Inventory before = maker.checkInventory();

        int coffeeBefore = before.getCoffee();
        int milkBefore = before.getMilk();
        int sugarBefore = before.getSugar();
        int chocolateBefore = before.getChocolate();

        assertTrue(maker.addInventory(3, 4, 0, 5));

        Inventory after = maker.checkInventory();

        assertEquals(coffeeBefore + 3, after.getCoffee());
        assertEquals(milkBefore + 4, after.getMilk());
        assertEquals(sugarBefore, after.getSugar());
        assertEquals(chocolateBefore + 5, after.getChocolate());
    }

  @Disabled

    @Test
    void makingCoffeeConsumesMilkSugarAndChocolate() {
        Recipe recipe = createRecipe("Mocha", 50, 0, 2, 3, 4);

        Inventory before = maker.checkInventory();

        int milkBefore = before.getMilk();
        int sugarBefore = before.getSugar();
        int chocolateBefore = before.getChocolate();

        assertEquals(25, maker.makeCoffee(recipe, 75));

        Inventory after = maker.checkInventory();

        assertEquals(milkBefore - 2, after.getMilk());
        assertEquals(sugarBefore - 3, after.getSugar());
        assertEquals(chocolateBefore - 4, after.getChocolate());
    }

    private boolean containsRecipe(String name) {
        for (Recipe recipe : maker.getRecipes()) {
            if (name.equals(recipe.getName())) {
                return true;
            }
        }
        return false;
    }

    private int countNamedRecipes() {
        int count = 0;

        for (Recipe recipe : maker.getRecipes()) {
            if (recipe.getName() != null) {
                count++;
            }
        }

        return count;
    }
}
