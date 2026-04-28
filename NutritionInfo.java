package com.example.uotaste;

/**
 * Represnts the nutritional composition of a recipe or ingredient.
 * <p>
 *    This class stores values for macronutrients such as carbohydrates,
 *    proteins, fats, fibers, and total calories. It can be associated
 *    with a specific {@link  Recipe} or {@link Ingredient}.
 * </p>
 *
 * <p><b>Example</b><br>
 * Recipe: "Oatmeal Bowl"<br>
 *     NutritionInfo: 50g carbs, 10g proteins, 5g fat, 4g fiber, 300 kcal
 * </p>
 */
public class NutritionInfo {
    private int nutritionId;
    private double carbohydrates;
    private double proteins;
    private double fats;
    private double fibers;
    private double calories;
    private int recipeId;

    /**
     * Default constructor initializing all nutrient values to 0.
     * <p>
     * This insure no all data is stored and simplifies calculations
     * </p>
     */
    public NutritionInfo() {
        this.carbohydrates = 0.0;
        this.proteins = 0.0;
        this.fats = 0.0;
        this.fibers = 0.0;
        this.calories = 0.0;
    }

    public NutritionInfo(double carbohydrates, double proteins, double fats, double fibers) {
                    this.carbohydrates = carbohydrates;
                    this.proteins = proteins;
                    this.fats = fats;
                    this.fibers = fibers;
                    this.calories = (proteins * 4) + (fats * 9) + (carbohydrates * 4);
    }


    /**
     * parametrized constructors for defining nutritional values.
     *
     * @param carbohydrates Total carbohydrates(g)
     * @param proteins      Total proteins(g)
     * @param fats          Total fats(g)
     * @param fibers        Total fibers(g)
     * @param calories      Total calories(kcal)
     * @param recipeId      ID of the related recipe
     */
    public NutritionInfo(double carbohydrates, double proteins, double fats, double fibers, double calories, int recipeId) {
        this.carbohydrates = carbohydrates;
        this.proteins = proteins;
        this.fats = fats;
        this.fibers = fibers;
        this.calories = calories;
        this.recipeId = recipeId;
    }

    // ------------------Getters & Setters-----------------------
    public double getNutritionId() {
        return nutritionId;
    }

    public void setNutritionId(int nutritionId) {
        this.nutritionId = nutritionId;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getFibers() {
        return fibers;
    }

    public void setFibers(double fibers) {
        this.fibers = fibers;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }





    /**
     * Adds the nutrition values of another NutritionInfo object to this one.
     * <p>
     * Useful when aggregating the nutrition of multiple ingredients in a recipe.
     * </p>
     *
     * @param other Anothet NutritionInfo instance to combine with this one
     */
    public void add(NutritionInfo other) {
        this.carbohydrates += other.carbohydrates;
        this.proteins += other.proteins;
        this.fats += other.fats;
        this.fibers += other.fibers;
        this.calories = other.calories;
    }

    /**
     * Return a human-readable summary of this nutrition record.
     *
     * @return A formatted string describing the nutrition values.
     */

    @Override
    public String toString() {
        return "NutritionInfo {" +
                "carbohydrates=" + carbohydrates + "g," +
                "proteins=" + proteins + "g," +
                "fats=" + fats + "g," +
                "fibers=" + fibers + "g," +
                "calories" + calories + "cal}";
    }

    public String formatNutritionInfo(NutritionInfo nutritioninfo) {
        return "-----------------------------------\n"+
                "Bilan nutritionnel :\n" +
                "__________________________________\n\n"+

                "Carbohydrates : " + String.format("%.2f", nutritioninfo.getCarbohydrates()) + "g\n\n"+
                "Proteins      : " + String.format("%.2f", nutritioninfo.getProteins()) + "g\n\n" +
                "Fats          : " + String.format("%.2f", nutritioninfo.getFats()) + "g\n\n" +
                "Fibers        : " + String.format("%.2f", nutritioninfo.getFibers()) + "g\n\n" +
                "Calories      : " + String.format("%.2f", nutritioninfo.getCalories()) + "kcal\n\n"+

                "--------------------------------------";



    }

}
