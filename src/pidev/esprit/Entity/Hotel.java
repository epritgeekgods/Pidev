/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.esprit.Entity;

import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class Hotel {

    private int idHotel;
    private String nameHotel;
    private String adresseHotel;
    private int nb_etoile;
    private int nbreChambre;
    private int nbre_de_chambre_despo;
    private int prix_double;
    private int prix_indiv;
    private String image;

    public Hotel() {
    }

    public Hotel(int idHotel, String nameHotel, String adresseHotel, int nb_etoile, int nbreChambre, int nbre_de_chambre_despo, int prix_double, int prix_indiv, String image) {
        this.idHotel = idHotel;
        this.nameHotel = nameHotel;
        this.adresseHotel = adresseHotel;
        this.nb_etoile = nb_etoile;
        this.nbreChambre = nbreChambre;
        this.nbre_de_chambre_despo = nbre_de_chambre_despo;
        this.prix_double = prix_double;
        this.prix_indiv = prix_indiv;
        this.image = image;
    }

    @Override
    public String toString() {
        return "Hotel{" + "idHotel=" + idHotel + ", nameHotel=" + nameHotel + ", adresseHotel=" + adresseHotel + ", nb_etoile=" + nb_etoile + ", nbre_de_chambre_despo=" + nbre_de_chambre_despo + ", prix_double=" + prix_double + ", prix_indiv=" + prix_indiv + ", image=" + image + '}';
    }

    

    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    public void setNameHotel(String nameHotel) {
        this.nameHotel = nameHotel;
    }

    public void setAdresseHotel(String adresseHotel) {
        this.adresseHotel = adresseHotel;
    }

    public void setNb_etoile(int nb_etoile) {
        this.nb_etoile = nb_etoile;
    }

    public void setNbreChambre(int nbreChambre) {
        this.nbreChambre = nbreChambre;
    }

    public void setNbre_de_chambre_despo(int nbre_de_chambre_despo) {
        this.nbre_de_chambre_despo = nbre_de_chambre_despo;
    }

    public void setPrix_double(int prix_double) {
        this.prix_double = prix_double;
    }

    public void setPrix_indiv(int prix_indiv) {
        this.prix_indiv = prix_indiv;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIdHotel() {
        return idHotel;
    }

    public String getNameHotel() {
        return nameHotel;
    }

    public String getAdresseHotel() {
        return adresseHotel;
    }

    public int getNb_etoile() {
        return nb_etoile;
    }

    public int getNbreChambre() {
        return nbreChambre;
    }

    public int getNbre_de_chambre_despo() {
        return nbre_de_chambre_despo;
    }

    public int getPrix_double() {
        return prix_double;
    }

    public int getPrix_indiv() {
        return prix_indiv;
    }

    public String getImage() {
        return image;
    }
    
    

}
