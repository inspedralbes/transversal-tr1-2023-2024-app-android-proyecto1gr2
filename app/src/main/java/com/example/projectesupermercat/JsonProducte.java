package com.example.projectesupermercat;

public class JsonProducte {
    private int id;
    private int quantitat;

    // Constructor, getters y setters
    public JsonProducte(int id, int quantitat) {
        this.id = id;
        this.quantitat = quantitat;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }
}
