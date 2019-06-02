package com.example.client_restaurant;

public class Ticket {

    private Integer idTicket;
    private Float totalPrice;
    private Integer idTable;

    public Ticket(Float totalPrice, Integer idTable) {
        this.totalPrice = totalPrice;
        this.idTable = idTable;
    }

    public Integer getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Integer idTicket) {
        this.idTicket = idTicket;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getIdTable() {
        return idTable;
    }

    public void setIdTable(Integer idTable) {
        this.idTable = idTable;
    }
}
