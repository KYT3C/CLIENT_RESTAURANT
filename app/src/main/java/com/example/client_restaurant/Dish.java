package com.example.client_restaurant;

public class Dish {

    private String nameDish;
    private Integer idItemDish;
    private float price;
    private int quantityStock;
    private int statusDish;
    private String descriptionDish;
    private String dniKitchen;


    public Dish(String nameDish, Integer idItemDish, float price, int quantityStock, int statusDish, String descriptionDish, String dniKitchen) {
        this.nameDish = nameDish;
        this.idItemDish = idItemDish;
        this.price = price;
        this.quantityStock = quantityStock;
        this.statusDish = statusDish;
        this.descriptionDish = descriptionDish;
        this.dniKitchen = dniKitchen;

    }

    public String getName() {
        return nameDish;
    }

    public void setName(String name) {
        this.nameDish = name;
    }

    public Integer getIdItemDish() {
        return idItemDish;
    }

    public void setIdItemDish(Integer idItemDish) {
        this.idItemDish = idItemDish;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantityStock() {
        return quantityStock;
    }

    public void setQuantityStock(int quantityStock) {
        this.quantityStock = quantityStock;
    }

    public int getStatusDish() {
        return statusDish;
    }

    public void setStatusDish(int statusDish) {
        this.statusDish = statusDish;
    }

    public String getDescriptionDish() {
        return descriptionDish;
    }

    public void setDescriptionDish(String descriptionDish) {
        this.descriptionDish = descriptionDish;
    }

    public String getDniKitchen() {
        return dniKitchen;
    }

    public void setDniKitchen(String dniKitchen) {
        this.dniKitchen = dniKitchen;
    }

    public void addDishStock(){

        setQuantityStock(getQuantityStock() + 1);
    }
    public void minusDishStock(){

        setQuantityStock(getQuantityStock() - 1);
    }
}
