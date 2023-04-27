package com.example.rucafe;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents the current order, including the order number and all MenuItems currently assigned to that order.
 * @author Luca Vespa, Chinmay Rajanahalli
 */
public class Order implements Serializable {

    public static final double TAX = 0.06625;
    public static final int STARTING_ITEM_NUM = 0;

    private int orderNum;
    private ArrayList<MenuItems> itemList;
    private int itemNum;

    /**
     * Default constructor for the Order object.
     */
    public Order() {
        orderNum = 0;
        this.itemList = new ArrayList<>();
        this.itemNum = STARTING_ITEM_NUM;
    }

    /**
     * Contstructor for when an order number is provided.
     * @param orderNum the number of the order
     */
    public Order(int orderNum) {
        this.orderNum = orderNum;
        this.itemList = new ArrayList<>();
        this.itemNum = STARTING_ITEM_NUM;
    }

    /**
     * Gets the number of the order as an integer.
     * @return the number of the order
     */
    public int getOrderNum() {
        return this.orderNum;
    }

    /**
     * Gets the list containing all the MenuItems under this order.
     * @return an ArrayList of MenuItems
     */
    public ArrayList<MenuItems> getItemList(){
        return this.itemList;
    }

    /**
     * Gets the value itemNum which is used to distinguish two MenuItems with the same properties.
     * @return itemNum as an integer
     */
    public int getItemNum() {
        itemNum++;
        return itemNum;
    }


    /**
     * Adds a MenuItem to the itemList ArrayList.
     * @param item the MenuItem to be added
     */
    public void addItem(MenuItems item) {
        itemList.add(item);
        itemNum++;
    }

    /**
     * Gets the number of items in the order.
     * @return the size of the itemList ArrayList
     */
    public int getSize() {
        return itemList.size();
    }

    /**
     * Calculates the subtotal of the entire order.
     * @return the subtotal of the entire order as a double.
     */
    public double getSubTotal() {
        double subTotal = 0.0;
        for (MenuItems item : itemList) {
            subTotal = item.itemPrice()*item.getAmount() + subTotal;
        }
        return subTotal;
    }

    /**
     * Calculates the tax amount of the entire order.
     * @return the tax amount of the entire order as a double
     */
    public double getTaxAmount() {
        return getSubTotal() * TAX;
    }

    /**
     * Calculates the cost of the final bill including the sub-total and the tax amount.
     * @return the cost of the final bill as a double
     */
    public double getFinalBill() {
        return getSubTotal() + getTaxAmount();
    }

}
