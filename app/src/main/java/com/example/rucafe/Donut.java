package com.example.rucafe;

/**
 * This class extends the MenuItem class and represents a donut order, including the type of donut, the flavor, and the amount.
 * @author Luca Vespa, Chinmay Rajanahalli
 */
public class Donut extends MenuItems {

    private int amount;
    private String donutType;
    private String donutFlavor;
    public int itemNum;

    public static final double DONUT_HOLES_COST = 0.39;
    public static final double YEAST_DONUT__COST = 1.59;
    public static final double cakeDonutCost = 1.79;

    /**
     * Default constructor for the Donut class.
     */
    public Donut() {
        this.amount = 0;
        this.donutType = "";
        this.donutFlavor = "";
    }

    /**
     * Constructor for when all parameters are provided.
     * @param donutType the type of donut
     * @param amount the amount of donuts in the order
     * @param donutFlavor the flavor of the donut
     * @param itemNum a number used to keep track of separate orders with the same properties
     */
    public Donut(String donutType, int amount, String donutFlavor, int itemNum) {
        this.amount = amount;
        this.donutType = donutType;
        this.donutFlavor = donutFlavor;
        this.itemNum = itemNum;
    }

    /**
     * Gets the type of donut and returns it as a String.
     * @return the type of donut
     */
    public String getDonutType() {
        return donutType;
    }

    /**
     * Gets the flavor of the donut and returns it as a String.
     * @return
     */
    public String getFlavor() {
        return donutFlavor;
    }

    /**
     * Gets the unique number identifier for the donut order and returns it as an integer.
     * @return the item number
     */
    public int getItemNum() {
        return itemNum;
    }

    /**
     * Gets the amount of Donuts in the donut order and returns it as an integer.
     * @return the amount of donuts in the order
     */
    @Override
    public int getAmount() {
        return amount;
    }

    /**
     * Increases the amount of donuts in the order and caps it at 100.
     * @param amount the amount of donuts that are added to the total amount
     */
    @Override
    public void increaseAmount(int amount) {
        this.amount = this.amount + amount;
        if (this.amount > 100) {
            this.amount = 100;
        }
    }

    /**
     * Decreases the amount of donuts in the order.
     * @param amount the amount of donuts that are subtracted from the total amount
     */
    @Override
    public void decreaseAmount(int amount) {
        this.amount = this.amount - amount;
    }

    /**
     * A version of the toString method meant exclusively for the Ordering Donuts View.
     * @return a string representation of the donut
     */
    public String toStringDonutView() {
        String shortenedType;
        if (donutType.equals("Yeast Donuts")) {
            shortenedType = "(Yeast) ";
        } else if (donutType.equals("Cake Donuts")) {
            shortenedType = "(Cake) ";
        } else {
            shortenedType = "(Donut Hole) ";
        }
        return shortenedType + donutFlavor;
    }

    /**
     * An override of the toString method.
     * @return a String representation of the Donut order
     */
    @Override
    public String toString() {
        return donutFlavor + " " + donutType + " (" + amount + ") Price: $" + String.format("%.2f",itemPrice()*amount);
    }

    /**
     * An override of the equals method.
     * @param obj an Object expected to be of type Donut
     * @return true if the two Donuts are the same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Donut)) {
            return false;
        }
        Donut checkDonut = (Donut) obj;
        return checkDonut.getAmount() == this.amount && checkDonut.getFlavor().equals(this.donutFlavor) && checkDonut.getDonutType().equals(this.donutType)
                && checkDonut.getItemNum() == this.itemNum;
    }

    /**
     * Calculates the price of an individual donut based on the type of donut.
     * @return the price of an individual donut with the corresponding attributes
     */
    @Override
    public double itemPrice(){
        if (donutType.equals("Yeast Donuts")) {
            return YEAST_DONUT__COST;
        }
        if (donutType.equals("Cake Donuts")) {
            return cakeDonutCost;
        }
        return DONUT_HOLES_COST;
    }


}
