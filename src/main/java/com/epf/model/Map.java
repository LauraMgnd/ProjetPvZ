package com.epf.model;

public class Map {

    private int idMap;
    private int ligne;
    private int colonne;
    private String cheminImage;

    public Map() {}

    public Map(int idMap, int ligne, int colonne, String cheminImage) {
        this.idMap = idMap;
        this.ligne = ligne;
        this.colonne = colonne;
        this.cheminImage = cheminImage;
    }
    public Map(int ligne, int colonne, String cheminImage) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.cheminImage = cheminImage;
    }

    public int getIdMap() {
        return idMap;
    }
    public void setIdMap(int idMap) {
        this.idMap = idMap;
    }

    public int getLigne() {
        return ligne;
    }
    public void setLigne(int ligne) {
        this.ligne = ligne;
    }

    public int getColonne() {
        return colonne;
    }
    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    public String getCheminImage() {
        return cheminImage;
    }
    public void setCheminImage(String cheminImage) {
        this.cheminImage = cheminImage;
    }

    @Override
    public String toString() {
        return "Map{" +
                "idMap=" + idMap +
                ", ligne=" + ligne +
                ", colonne=" + colonne +
                ", cheminImage='" + cheminImage + '\'' +
                '}';
    }
}
