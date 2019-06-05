package com.example.client_restaurant;

class DinnerTable {

    private int idTable;
    private String locationTable;
    private int numberDinnerTable;

    public DinnerTable(int idTable, String locationTable, int numberDinnerTable) {
        this.idTable = idTable;
        this.locationTable = locationTable;
        this.numberDinnerTable = numberDinnerTable;
    }

    public int getIdTable() {
        return idTable;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    public String getLocationTable() {
        return locationTable;
    }

    public void setLocationTable(String locationTable) {
        this.locationTable = locationTable;
    }

    public int getNumberDinnerTable() {
        return numberDinnerTable;
    }

    public void setNumberDinnerTable(int numberDinnerTable) {
        this.numberDinnerTable = numberDinnerTable;
    }
}
