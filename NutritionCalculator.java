package com.example.uotaste;
import java.util.List;

public class NutritionCalculator {

    public static NutritionInfo calculateRecipeNutrition(Recipe recipe){
        // si recipe est null dans un try catch pour continuer l'execution du code
        try{
            if (recipe == null){
            }
        }catch ( NullPointerException e){
            System.err.println("Erreur : Recipe est null");
            return new NutritionInfo(0.0, 0.0, 0.0, 0.0);
        }

        // je recupere la liste des recipeIngrediens dans la class recipe  pour pouvoir les utiliser par recette
        List<RecipeIngredient> recipeIngredientsList = recipe.getIngredients();

        // si la liste est vide je renvoie les valeur nutritionnels en null.
        if (recipeIngredientsList.isEmpty()){
            return new NutritionInfo(0.0, 0.0, 0.0, 0.0,0.0, recipe.getRecipeId());
        }

        double totalCarbohydrates = 0.0;
        double totalProteins = 0.0;
        double totalFats = 0.0;
        double totalFibers = 0.0;
        double totalCalories = 0.0;

        for( RecipeIngredient recipeIngredient : recipeIngredientsList){

            Ingredient ingredient = recipeIngredient.getIngredient();

            // methode de securite pour eviter les erreurs de nullpointerException

            if(ingredient == null ){
                continue;
            }

            NutritionInfo infoNutrition = ingredient.getNutritionInfo();
            // methode de securite pour eviter les erreurs de nullpointerException

            if(infoNutrition == null){
                continue;
            }

            double percentage = recipeIngredient.getPercentageUsed();

            totalCarbohydrates += infoNutrition.getCarbohydrates() * (percentage/100);
            totalProteins += infoNutrition.getProteins() * (percentage/100);
            totalFats += infoNutrition.getFats() * (percentage/100);
            totalFibers += infoNutrition.getFibers() * (percentage/100);
            totalCalories += infoNutrition.getCalories() * (percentage/100);
        }

        return new NutritionInfo(totalCarbohydrates, totalProteins, totalFats, totalFibers, totalCalories, recipe.getRecipeId());
    }


}
