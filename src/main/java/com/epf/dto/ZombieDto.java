package com.epf.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ZombieDto {

    @NotNull(message = "ID Zombie cannot be null")
    private int id_zombie;

    @NotNull(message = "Name cannot be null")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String nom;

    @NotNull(message = "Health points cannot be null")
    private int point_de_vie;

    @NotNull(message = "Attack per second cannot be null")
    private double attaque_par_seconde;

    @NotNull(message = "Damage attack cannot be null")
    private int degat_attaque;

    @NotNull(message = "Movement speed cannot be null")
    private double vitesse_de_deplacement;

    private String chemin_image;
    private int id_map;

    public ZombieDto() {}

    public ZombieDto(int id_zombie, String nom, int point_de_vie, double attaque_par_seconde,
                     int degat_attaque, double vitesse_de_deplacement, String chemin_image, int id_map) {
        this.id_zombie = id_zombie;
        this.nom = nom;
        this.point_de_vie = point_de_vie;
        this.attaque_par_seconde = attaque_par_seconde;
        this.degat_attaque = degat_attaque;
        this.vitesse_de_deplacement = vitesse_de_deplacement;
        this.chemin_image = chemin_image;
        this.id_map = id_map;
    }

    public int getId_zombie() { return id_zombie; }
    public void setId_zombie(int id_zombie) { this.id_zombie = id_zombie; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public int getPoint_de_vie() { return point_de_vie; }
    public void setPoint_de_vie(int point_de_vie) { this.point_de_vie = point_de_vie; }

    public double getAttaque_par_seconde() { return attaque_par_seconde; }
    public void setAttaque_par_seconde(double attaque_par_seconde) { this.attaque_par_seconde = attaque_par_seconde; }

    public int getDegat_attaque() { return degat_attaque; }
    public void setDegat_attaque(int degat_attaque) { this.degat_attaque = degat_attaque; }

    public double getVitesse_de_deplacement() { return vitesse_de_deplacement; }
    public void setVitesse_de_deplacement(double vitesse_de_deplacement) { this.vitesse_de_deplacement = vitesse_de_deplacement; }

    public String getChemin_image() { return chemin_image; }
    public void setChemin_image(String chemin_image) { this.chemin_image = chemin_image; }

    public int getId_map() { return id_map; }
    public void setId_map(int id_map) { this.id_map = id_map; }
}
