package com.example.rucafe;

import java.util.ArrayList;


/**
 * This class extends the MenuItem class and represents a coffee order, including the size, amount, and any add-ins.
 * @author Luca Vespa, Chinmay Rajanahalli
 */
public class Coffee extends MenuItems {

    public static final double SHORT_COFFEE_PRICE = 1.89;
    public static final double TALL_COFFEE_PRICE = 2.29;
    public static final double GRANDE_COFFEE_PRICE = 2.69;
    public static final double VENTI_COFFEE_PRICE = 3.09;
    public static final double ADD_INS_PRICE = 0.30;

    private String cupSize;
    private ArrayList<String> addIns;
    private int amount;
    private int itemNum;

    /**
     * Default constructor for the Coffee Class.
     */
    public Coffee () {
        cupSize = "";
        addIns = null;
        amount = 0;
        itemNum = 0;
    }

    /**
     * Constructor for when all parameters are provided.
     * @param cupSize the size of cup
     * @param addIns an ArrayList of the coffee's add-ins
     * @param amount the amount of coffees that will be ordered
     * @param itemNum a number used to keep track of separate orders with the same properties
     */
    public Coffee (String cupSize, ArrayList<String> addIns, int amount, int itemNum) {
        this.cupSize = cupSize;
        this.addIns = addIns;
        this.amount = amount;
        this.itemNum = itemNum;
    }

    /**
     * Gets the size of the cup as a String.
     * @return the size of the cup
     */
    public String getCupSize() {
        return cupSize;
    }

    /**
     * Gets all the coffee's add-ins as an ArrayList of Strings.
     * @return the ArrayList of the add-ins
     */
    public ArrayList<String> getAddIns() {
        return addIns;
    }

    /**
     * Gets the unique number identifier for the coffee order and returns it as an integer.
     * @return the item number
     */
    public int getItemNum() {
        return itemNum;
    }

    /**
     * Increases the amount of coffees in the order.
     * @param amount the amount of coffees that are added to the total amount
     */
    @Override
    public void increaseAmount(int amount) {
        this.amount = this.amount + amount;
    }

    /**
     * Decreases the amount of coffees in the order.
     * @param amount the amount of coffees that are subtracted from the total amount
     */
    @Override
    public void decreaseAmount(int amount) {
        this.amount = this.amount - amount;
    }

    /**
     * Gets the amount of coffees in the coffee order and returns it as an integer.
     * @return the amount of coffees
     */
    @Override
    public int getAmount() {
        return amount;
    }

    /**
     * Calculates the price of an individual coffee using the size and amount of add-ins.
     * @return the price of an individual coffee with the corresponding attributes
     */
    @Override
    public double itemPrice(){
        double price = 0.0;
        if (cupSize.equals("Short")) {
            price = SHORT_COFFEE_PRICE;
        } else if (cupSize.equals("Tall")) {
            price = TALL_COFFEE_PRICE;
        } else if (cupSize.equals("Grande")) {
            price = GRANDE_COFFEE_PRICE;
        } else if (cupSize.equals("Venti")){
            price = VENTI_COFFEE_PRICE;
        }
        return price + (ADD_INS_PRICE*addIns.size());
    }

    /**
     * An override of the equals method.
     * @param obj an Object expected to be of type Coffee
     * @return true if the two Coffees are the same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coffee)) {
            return false;
        }
        Coffee checkCoffee = (Coffee) obj;
        Boolean equalAddIns = checkCoffee.getAddIns().contains(this.addIns) && this.addIns.contains(checkCoffee.getAddIns());
        return checkCoffee.getAmount() == this.amount && equalAddIns && checkCoffee.getCupSize().equals(this.cupSize) && checkCoffee.getItemNum() == this.itemNum;
    }

    /**
     * An override of the toString method.
     * @return a String representation of the coffee order
     */
    @Override
    public String toString() {
        String addInsString = "";
        for(int i = 0; i < addIns.size(); i++) {
            addInsString = addInsString + addIns.get(i);
            if (i != addIns.size() - 1) {
                addInsString = addInsString + ", ";
            }
        }
        if(addIns.isEmpty()) {
            return getCupSize() + " Black Coffee (" + getAmount() + ") " + "Price: $" + String.format("%.2f", itemPrice()*amount);
        }
        return getCupSize() + " Coffee with " + addInsString + " (" + getAmount() + ") " + "Price: $" + String.format("%.2f", itemPrice()*amount);
    }

}
