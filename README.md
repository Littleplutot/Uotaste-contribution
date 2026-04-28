# UOTaste — Restaurant Management Application

## Overview
UOTaste is an Android restaurant management application developed as a team project. It allows chefs to browse recipes, manage ingredients, and view detailed nutritional information for each recipe.

---


## Tech Stack
- **Backend:** Java, JDBC
- **Frontend:** XML (Android layouts)
- **Database:** SQLite
- **Tools:** Android Studio, Git


---


## My Contributions

### Java Classes
- **NutritionCalculator.java** - Contains the Java logic that computes the total nutritional summary of a recipe based on its ingredients.
- **NutritionInfo.java** -  Handles the binding logic between nutritional values and their associated ingredients.
- **RecipeNutritionInfoActivity.java** - Links the recipe selected in UpdateRecipeActivity.java with NutritionCalculator.java to display the computed nutritional results.
- **UpdateRecipeActivity.java** -  Contains the logic that opens RecipeNutritionInfoActivity.java to display the nutritional summary.
- **Ingredient.java** - Adds logic to associate nutritional values with ingredients at the time of their creation and afterward.
- **DatabaseProject.java** - Added database methods linking ingredients with nutritional values to recipes: Recipe getRecipeById(long recipeId) ,Ingredient getIngredientByName(String ingredientName)

### XML Layout
- **activity_nutrition_info.xml** —  Interface that displays the nutritional breakdown of a selected recipe.

---

## Author
**Monza Sango**
B.Sc. Honours Computer Science — University of Ottawa
msang006@uottawa.ca