/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.esprit.Entity;


/**
 *
 * @author ASUS
 */
public class Reservationhotel {
    private int idReservation;
      private int idClient;
      private int idHotel;
      private int nbre_indiv;
      private int nbre_double;
      private int prix;

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    public void setNbre_indiv(int nbre_indiv) {
        this.nbre_indiv = nbre_indiv;
    }

    public void setNbre_double(int nbre_double) {
        this.nbre_double = nbre_double;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getIdReservation() {
        return idReservation;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getIdHotel() {
        return idHotel;
    }

    public int getNbre_indiv() {
        return nbre_indiv;
    }

    public int getNbre_double() {
        return nbre_double;
    }

    public int getPrix() {
        return prix;
    }

    @Override
    public String toString() {
        return "Reservationhotel{" + "idReservation=" + idReservation + ", idClient=" + idClient + ", idHotel=" + idHotel + ", nbre_indiv=" + nbre_indiv + ", nbre_double=" + nbre_double + ", prix=" + prix + '}';
    }

    public Reservationhotel() {
    }

    public Reservationhotel(int idReservation, int idClient, int idHotel, int nbre_indiv, int nbre_double, int prix) {
        this.idReservation = idReservation;
        this.idClient = idClient;
        this.idHotel = idHotel;
        this.nbre_indiv = nbre_indiv;
        this.nbre_double = nbre_double;
        this.prix = prix;
    }
      
      
    
}
