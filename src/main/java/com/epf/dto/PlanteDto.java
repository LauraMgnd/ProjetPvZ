package com.epf.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PlanteDto {

    @NotNull(message = "ID Plante cannot be null")
    private int id_plante;

    @NotNull(message = "Name cannot be null")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String nom;

    @NotNull(message = "Health points cannot be null")
    private int point_de_vie;

    @NotNull(message = "Attack per second cannot be null")
    private double attaque_par_seconde;

    @NotNull(message = "Damage attack cannot be null")
    private int degat_attaque;

    @NotNull(message = "Cost cannot be null")
    private int cout;

    @NotNull(message = "Sun per second cannot be null")
    private double soleil_par_seconde;

    private String effet;
    private String chemin_image;

    public PlanteDto() {}

    public PlanteDto(int id_plante, String nom, int point_de_vie, double attaque_par_seconde,
                     int degat_attaque, int cout, double soleil_par_seconde, String effet, String chemin_image) {
        this.id_plante = id_plante;
        this.nom = nom;
        this.point_de_vie = point_de_vie;
        this.attaque_par_seconde = attaque_par_seconde;
        this.degat_attaque = degat_attaque;
        this.cout = cout;
        this.soleil_par_seconde = soleil_par_seconde;
        this.effet = effet;
        this.chemin_image = chemin_image;
    }

    public int getId_plante() { return id_plante; }
    public void setId_plante(int id_plante) { this.id_plante = id_plante; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public int getPoint_de_vie() { return point_de_vie; }
    public void setPoint_de_vie(int point_de_vie) { this.point_de_vie = point_de_vie; }

    public double getAttaque_par_seconde() { return attaque_par_seconde; }
    public void setAttaque_par_seconde(double attaque_par_seconde) { this.attaque_par_seconde = attaque_par_seconde; }

    public int getDegat_attaque() { return degat_attaque; }
    public void setDegat_attaque(int degat_attaque) { this.degat_attaque = degat_attaque; }

    public int getCout() { return cout; }
    public void setCout(int cout) { this.cout = cout; }

    public double getSoleil_par_seconde() { return soleil_par_seconde; }
    public void setSoleil_par_seconde(double soleil_par_seconde) { this.soleil_par_seconde = soleil_par_seconde; }

    public String getEffet() { return effet; }
    public void setEffet(String effet) { this.effet = effet; }

    public String getChemin_image() { return chemin_image; }
    public void setChemin_image(String chemin_image) { this.chemin_image = chemin_image; }
}
