package com.example.uotaste;

import java.time.LocalDateTime;

/**
 * Represents an ingredient used in a recipe.
 *
 * Each ingredient is linked to a recipe and may include
 * a quantity and QR code for identification
 *
 */
public class Ingredient {
    private int ingredientId;
    private String ingredientName;
    private String qrCode;
    public static double baseQuantity;
    private String unit;

    private double carbohydrates;
    private double proteins;
    private double fats;
    private double fibers;
    private double calories;



/**
 * Default constructor
 * */
public Ingredient(){
   }
    /**
     * Constructs an Ingredient with all fields initialized
     *
     * @param ingredientName         Ingredient title provided by the user
     * @param qrCode        Encoder QR content (may contain numeric or text ID)
     * @param baseQuantity  Total quantity of the ingredient(e.g., 400g, 30pcs)
     * @param unit          unit of measure for the quantity (e.g., "g", "pcs")
     */
    public Ingredient (String ingredientName, String qrCode, double baseQuantity, String unit) {
        this.ingredientName = ingredientName;
        this.qrCode = qrCode;
        this.baseQuantity = baseQuantity;
        this.unit = unit;
    }

    public Ingredient (int ingredientId, String ingredientName, String qrCode, double baseQuantity, String unit){
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.qrCode = qrCode;
        this.baseQuantity = baseQuantity;
        this.unit = unit;
    }


    //------------------------------------------------
    //getter nutritif ingredient
    //------------------------------------------------

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public double getProteins() {
        return proteins;
    }

    public double getFats() {
        return fats;
    }

    public double getFibers() {
        return fibers;
    }

    public double getCalories() {
        return calories;
    }

    //------------------------------------------------
    //setter nutritif ingredient
    //------------------------------------------------
    public NutritionInfo getNutritionInfo() {
        return new NutritionInfo(carbohydrates, proteins, fats, fibers);
    }

    public void setNutritionInfo(NutritionInfo nutritionInfo){
        this.carbohydrates = nutritionInfo.getCarbohydrates();
        this.proteins = nutritionInfo.getProteins();
        this.fats = nutritionInfo.getFats();
        this.fibers = nutritionInfo.getFibers();
        this.calories = nutritionInfo.getCalories();
    }


    //------------------------------------------------
    //
    //------------------------------------------------

    //-----------------------------Getters & Settters----------------
    public String getIngredientName() {
        return ingredientName;
    }

    public double setCalories(double calories) {
        this.calories = calories;
        return calories;
    }

public double setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
        return carbohydrates;
    }

    public double setProteins(double proteins) {
        this.proteins = proteins;
        return proteins;
    }
    public double setFats(double fats) {
        this.fats = fats;
        return fats;
    }
    public double setFibers(double fibers) {
        this.fibers = fibers;
        return fibers;
    }



    public int getIngredientId(){ return ingredientId;}
    public void setIngredientId(int ingredientId){this.ingredientId = ingredientId;}

    public String getTitle() {return ingredientName;}

    public void setTitle(String name) {this.ingredientName = ingredientName;}
    public String getQrCode() {return qrCode;}
    public void setQrCode(String qrCode) {this.qrCode = qrCode;}
    public double getBaseQuantity() {return baseQuantity;}
    public void setBaseQuantity(double baseQuantity) {this.baseQuantity = baseQuantity;}
    public String getUnit() {return unit;}
    public void setUnit(int recipeId) {this.unit = unit;}

    /**
     * return a concise string representation of this ingredient
     *
     * @return Ingredient name and quantity as a string.
     **/

@Override
public String toString(){
    return "Ingredient{" +
            ", title='" + ingredientName + '\'' +
            ", qrCode='" + qrCode + '\'' +
            ", baseQuantity=" + baseQuantity + " " + unit +
            '}';
}

}
