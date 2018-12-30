/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.esprit.Entity;

/**
 *
 * @author Sirine
 */
public class Reservationvoyage {
     private int idReservation;
    private int idVoyage;
    private int id;
    private int nbre_place_reserv;
    private String nom;
    private String prenom;
    private String email;
    private  int prix;

    @Override
    public String toString() {
        return "Reservationvoyage{" + "idReservation=" + idReservation + ", idVoyage=" + idVoyage + ", id=" + id + ", nbre_place_reserv=" + nbre_place_reserv + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", prix=" + prix + '}';
    }

    public int getIdReservation() {
        return idReservation;
    }

    public int getIdVoyage() {
        return idVoyage;
    }

    public int getId() {
        return id;
    }

    public int getNbre_place_reserv() {
        return nbre_place_reserv;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public int getPrix() {
        return prix;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public void setIdVoyage(int idVoyage) {
        this.idVoyage = idVoyage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNbre_place_reserv(int nbre_place_reserv) {
        this.nbre_place_reserv = nbre_place_reserv;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public Reservationvoyage() {
    }

    public Reservationvoyage(int idReservation, int idVoyage, int id, int nbre_place_reserv, String nom, String prenom, String email, int prix) {
        this.idReservation = idReservation;
        this.idVoyage = idVoyage;
        this.id = id;
        this.nbre_place_reserv = nbre_place_reserv;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.prix = prix;
    }
    
    
    
}
