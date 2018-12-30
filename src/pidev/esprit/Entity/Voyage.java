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
public class Voyage {
    private int idVoyage;
    private String destinationVoyage;
    private String dateVoyageAller;
    private String hdepartVoyageAller;
    private String harriveeVoyageAller;
    private String dateVoyageRetour;
    private String hdepartVoyageRetour;
    private String harriveeVoyageRetour;
    private String departVoyage;
    private int nb_place_dispo;
    private int Prix ;
    private String compagnie;
    private int num;
    private String image;

    public Voyage() {
    }

    public int getIdVoyage() {
        return idVoyage;
    }

    public String getDestinationVoyage() {
        return destinationVoyage;
    }

    public String getDateVoyageAller() {
        return dateVoyageAller;
    }

    public String getHdepartVoyageAller() {
        return hdepartVoyageAller;
    }

    public String getHarriveeVoyageAller() {
        return harriveeVoyageAller;
    }

    public String getDateVoyageRetour() {
        return dateVoyageRetour;
    }

    public String getHdepartVoyageRetour() {
        return hdepartVoyageRetour;
    }

    public String getHarriveeVoyageRetour() {
        return harriveeVoyageRetour;
    }

    public String getDepartVoyage() {
        return departVoyage;
    }

    public int getNb_place_dispo() {
        return nb_place_dispo;
    }

    public int getPrix() {
        return Prix;
    }

    public String getCompagnie() {
        return compagnie;
    }

    public int getNum() {
        return num;
    }

    public String getImage() {
        return image;
    }

    public void setIdVoyage(int idVoyage) {
        this.idVoyage = idVoyage;
    }

    public void setDestinationVoyage(String destinationVoyage) {
        this.destinationVoyage = destinationVoyage;
    }

    public void setDateVoyageAller(String dateVoyageAller) {
        this.dateVoyageAller = dateVoyageAller;
    }

    public void setHdepartVoyageAller(String hdepartVoyageAller) {
        this.hdepartVoyageAller = hdepartVoyageAller;
    }

    public void setHarriveeVoyageAller(String harriveeVoyageAller) {
        this.harriveeVoyageAller = harriveeVoyageAller;
    }

    public void setDateVoyageRetour(String dateVoyageRetour) {
        this.dateVoyageRetour = dateVoyageRetour;
    }

    public void setHdepartVoyageRetour(String hdepartVoyageRetour) {
        this.hdepartVoyageRetour = hdepartVoyageRetour;
    }

    public void setHarriveeVoyageRetour(String harriveeVoyageRetour) {
        this.harriveeVoyageRetour = harriveeVoyageRetour;
    }

    public void setDepartVoyage(String departVoyage) {
        this.departVoyage = departVoyage;
    }

    public void setNb_place_dispo(int nb_place_dispo) {
        this.nb_place_dispo = nb_place_dispo;
    }

    public void setPrix(int Prix) {
        this.Prix = Prix;
    }

    public void setCompagnie(String compagnie) {
        this.compagnie = compagnie;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Voyage{" + "idVoyage=" + idVoyage + ", destinationVoyage=" + destinationVoyage + ", dateVoyageAller=" + dateVoyageAller + ", hdepartVoyageAller=" + hdepartVoyageAller + ", harriveeVoyageAller=" + harriveeVoyageAller + ", dateVoyageRetour=" + dateVoyageRetour + ", hdepartVoyageRetour=" + hdepartVoyageRetour + ", harriveeVoyageRetour=" + harriveeVoyageRetour + ", departVoyage=" + departVoyage + ", nb_place_dispo=" + nb_place_dispo + ", Prix=" + Prix + ", compagnie=" + compagnie + ", num=" + num + ", image=" + image + '}';
    }

    public Voyage(int idVoyage, String destinationVoyage, String dateVoyageAller, String hdepartVoyageAller, String harriveeVoyageAller, String dateVoyageRetour, String hdepartVoyageRetour, String harriveeVoyageRetour, String departVoyage, int nb_place_dispo, int Prix, String compagnie, int num, String image) {
        this.idVoyage = idVoyage;
        this.destinationVoyage = destinationVoyage;
        this.dateVoyageAller = dateVoyageAller;
        this.hdepartVoyageAller = hdepartVoyageAller;
        this.harriveeVoyageAller = harriveeVoyageAller;
        this.dateVoyageRetour = dateVoyageRetour;
        this.hdepartVoyageRetour = hdepartVoyageRetour;
        this.harriveeVoyageRetour = harriveeVoyageRetour;
        this.departVoyage = departVoyage;
        this.nb_place_dispo = nb_place_dispo;
        this.Prix = Prix;
        this.compagnie = compagnie;
        this.num = num;
        this.image = image;
    }

    
    
    
}
