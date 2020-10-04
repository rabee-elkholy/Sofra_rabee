package com.androdu.sofra.data.local.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class cartItem {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int itemId;
    private String itemName;
    private int quantity;
    private String image;
    private String note;
    private float cost;

    public cartItem(int itemId, String itemName, int quantity, String image, String note, float cost ) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.image = image;
        this.note = note;
        this.cost = cost;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public String getNote() {
        return note;
    }

    public float getCost() {
        return cost;
    }


}
