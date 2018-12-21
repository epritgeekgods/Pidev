/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.esprit.Entity;

/**
 *
 * @author STA
 */
public class Excursion {
    private int id;
    private int capacite;
    private int nbre;
    private String date;
    private String destination;
    private int bus;
    private float prix;
    private String programme;
    private String googlemap;
    private String img1;
    private String img2;
    private String img3;

    public Excursion() {
    }

    public Excursion(int id, int capacite, int nbre, String date, String destination, float prix, String programme, String googlemap, String img1, String img2, String img3) {
        this.id = id;
        this.capacite = capacite;
        this.nbre = nbre;
        this.date = date;
        this.destination = destination;
        this.prix = prix;
        this.programme = programme;
        this.googlemap = googlemap;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
    }

    public Excursion(int capacite, int nbre, String date, String destination, float prix, String programme, String googlemap, String img1, String img2, String img3) {
        this.capacite = capacite;
        this.nbre = nbre;
        this.date = date;
        this.destination = destination;
        this.prix = prix;
        this.programme = programme;
        this.googlemap = googlemap;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public int getNbre() {
        return nbre;
    }

    public void setNbre(int nbre) {
        this.nbre = nbre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getBus() {
        return bus;
    }

    public void setBus(int bus) {
        this.bus = bus;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getGooglemap() {
        return googlemap;
    }

    public void setGooglemap(String googlemap) {
        this.googlemap = googlemap;
    }

    @Override
    public String toString() {
        return "Excursion{" + "id=" + id + ", capacite=" + capacite + ", nbre=" + nbre + ", date=" + date + ", destination=" + destination + ", bus=" + bus + ", prix=" + prix + ", programme=" + programme + ", googlemap=" + googlemap + '}';
    }
    
    
    
}
