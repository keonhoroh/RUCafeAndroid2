package com.example.rucafe;

import java.io.Serializable;

/**
 * This class represents any item on the RUCafe menu.
 * @author Luca Vespa, Chinmay Rajanahalli
 */
public abstract class MenuItems implements Serializable {

    /**
     * Calculates the price of the MenuItem.
     * @return the price of the item as a double
     */
    public abstract double itemPrice();

    /**
     * Increases the amount of the MenuItem by the amount provided.
     * @param amount the magnitude of the increase
     */
    public abstract void increaseAmount(int amount);

    /**
     * Decreases the amount of the MenuItem by the amount provided.
     * @param amount the magnitude of the decrease
     */
    public abstract void decreaseAmount(int amount);

    /**
     * Gets the amount of the MenuItem as an integer.
     * @return the amount of the specific MenuItem
     */
    public abstract int getAmount();
}
