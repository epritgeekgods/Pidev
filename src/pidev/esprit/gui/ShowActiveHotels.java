/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.esprit.gui;

import com.codename1.components.ImageViewer;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;

import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.mycompany.myapp.MyApplication;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pidev.esprit.Entity.Hotel;
import pidev.esprit.Entity.Reservationhotel;
import pidev.esprit.Service.ServiceHotel;

/**
 *
 * @author ASUS
 */
public class ShowActiveHotels {

    Form f;
    Form aff;

    public ShowActiveHotels() {
        f = new Form("Welcome to our Hotels", new BorderLayout());
        Style s = UIManager.getInstance().getComponentStyle("Tab");
        FontImage back = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        FontImage view = FontImage.createMaterial(FontImage.MATERIAL_VIEW_LIST, s);
        FontImage payer = FontImage.createMaterial(FontImage.MATERIAL_PAYMENT, s);
        f.getToolbar().addCommandToLeftBar("", back, (evt) -> {
            Acceuil A = new Acceuil();
            A.getF().show();
        });
        f.getToolbar().addCommandToOverflowMenu("My reservations", view, (evt) -> {

            ///////////////////////////////////////////affichage des réservations //////////////////////////////////////////////////////////////////////
            Form f_reserv = new Form("Your Reservation Mr/Msr " + MyApplication.current_user.getNom(), new BorderLayout());
            f_reserv.getToolbar().addCommandToLeftBar("", back, (evt1) -> {
                Acceuil A = new Acceuil();
                A.getF().show();
            });
            Container C_reserv = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            ServiceHotel sh = new ServiceHotel();
            ArrayList<Reservationhotel> listreservation = new ArrayList<>();
            listreservation = sh.getreservation();
            for (Reservationhotel h : listreservation) {

                //Read reservations //////////////////////////
                if ((h.getIdReservation() != -2) && (h.getIdReservation() != -1) && (h.getIdReservation() != 0)) {
                    Label nb_ch_indiv = new Label("      Single room  = " + h.getNbre_indiv());
                    Label nb_ch_double = new Label("     Double room  = " + h.getNbre_double());
                    Container C_nb = new Container(new BoxLayout(BoxLayout.X_AXIS));
                    Label price = new Label(h.getPrix() + " $", payer);
                    C_nb.add(nb_ch_indiv).add(nb_ch_double);
                    ArrayList<Hotel> listh = new ArrayList<>();
                    listh = sh.gethotelByid(h.getIdHotel());
                    for (int i = 0; i < listh.size(); i++) {

                        //Read hotel de chaque reservation///////////////
                        Hotel hot = new Hotel();
                        hot = listh.get(i);
                        hot.setIdHotel(h.getIdHotel());
                        System.out.println(hot.getIdHotel());
                        FontImage iconhotel = FontImage.createMaterial(FontImage.MATERIAL_HOTEL, s);
                        FontImage star = FontImage.createMaterial(FontImage.MATERIAL_STAR_BORDER, s);
                        String url = "http://localhost/mobile/pidev/web/uploads/";
                        EncodedImage eimg;
                        star.setBgTransparency(255);
                        FontImage iconadresse = FontImage.createMaterial(FontImage.MATERIAL_PLACE, s);
                        Container C_nom = new Container(new BoxLayout(BoxLayout.X_AXIS));
                        Label nom = new Label(hot.getNameHotel());
                        C_nom.add("              ").add(iconadresse).add(nom);
                        nom.setUIID("title2");
                        Container C_etoile = new Container(new BoxLayout(BoxLayout.X_AXIS));
                        Label nbetoile = new Label("              ");
                        C_etoile.add(nbetoile);
                        for (int j = 0; j < hot.getNb_etoile(); j++) {
                            C_etoile.add(star);
                        }
                        C_reserv.add(C_nom);
                        C_reserv.add(C_etoile);

//affichage de l'image de l'hotel//////////////////////
                        try {
                            eimg = EncodedImage.create("/load.gif");
                            eimg.scale(300, 300);
                            Image img = URLImage.createToStorage(eimg, hot.getImage(), url + URLImage.RESIZE_SCALE);
                            ImageViewer imgv = new ImageViewer(img);
                            int deviceWidth = Display.getInstance().getDisplayWidth();
                            int imageWidth = (int) (deviceWidth * 0.8);
                            int imageHeight = (int) (deviceWidth * 0.6);
                            imgv.setPreferredH(imageHeight);
                            imgv.setPreferredW(imageWidth);
                            C_reserv.add(imgv);
                        } catch (IOException ex1) {
                            ex1.getMessage();
                        }
                    }
                    C_reserv.add(price);
                    C_reserv.add(C_nb);
                    Button btnannuler = new Button("Cancel");

                    ////////////////////////////////////////////Annulation des reservations ////////////////////////////////////////////////////////////////////////////
                    btnannuler.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            ConnectionRequest con = new ConnectionRequest();
                            con.setUrl("http://localhost/mobile/pidev/web/app_dev.php/hotel/annulerm?user=" + MyApplication.current_user.getId() + "&hotel=" + h.getIdHotel());
                            con.setPost(false);
                            con.addResponseListener(new ActionListener<NetworkEvent>() {
                                @Override
                                public void actionPerformed(NetworkEvent evt) {
                                    JSONParser j = new JSONParser();
                                    try {
                                        Map<String, Object> exMap = j.parseJSON(new CharArrayReader((new String(con.getResponseData())).toCharArray()));
                                        System.out.println("map: " + exMap);
                                        int id = (int) Float.parseFloat(exMap.get("idreservation").toString());
                                        if (id == 0) {
                                            if (Dialog.show("Success", "You have successfully disjoined this hotel", "OK", null)) {
                                                ShowActiveHotels ser = new ShowActiveHotels();
                                                ser.getF().show();
                                            }
                                        } else {
                                            if (Dialog.show("Error", "this reservation is already canceled !", "OK", null)) {
                                                ShowActiveHotels ser = new ShowActiveHotels();
                                                ser.getF().show();
                                            }
                                        }
                                    } catch (IOException ex) {
                                    }
                                }
                            });
                            NetworkManager.getInstance().addToQueueAndWait(con);
                        }
                    });
                    C_reserv.add(btnannuler);
                } else {
                    C_reserv.add("Sorry Mr " + MyApplication.current_user.getNom() + "No reservation to show !");
                }
            }
            f_reserv.add(BorderLayout.CENTER, C_reserv);
            f_reserv.show();
        });
/////////////////////////////////////////////////////////////page de recherche://////////////////////////////////////////////////////////////////////////
        FontImage iconsearch = FontImage.createMaterial(FontImage.MATERIAL_SEARCH, s);
        Container rechercher = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container C_rech_adresse = new Container(new BoxLayout(BoxLayout.X_AXIS));
        TextField rech_adresse = new TextField(null, "Search By Country ...");
        int deviceWidth = Display.getInstance().getDisplayWidth();
        int textWidth = (int) (deviceWidth * 0.8);
        rech_adresse.setPreferredW(textWidth);
        Button btnrech_adresse = new Button(iconsearch);
//Rechercher selon l'adresse////////////////////////////////////////////////////
        btnrech_adresse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String adresse = rech_adresse.getText().toString();
                aff = new Form(adresse + " Hotel's ", new BorderLayout());
                aff.getStyle().setBgColor(0xffffff);
                aff.getToolbar().addCommandToRightBar("", back, (evtt) -> {
                    Acceuil A = new Acceuil();
                    A.getF().show();
                });
                ServiceHotel sh = new ServiceHotel();
                ArrayList<Hotel> listhotel = new ArrayList<>();
                listhotel = sh.gethotelByadresse(adresse);
                System.out.println(listhotel);
                Container result_adresse = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                result_adresse.getStyle().setMargin(Component.TOP, 40);
                result_adresse.getStyle().setBorder(RoundRectBorder.create());

                //Afficher le résultat de recherche/////////////////////////////////////////
                for (Hotel h : listhotel) {

                    //Aucune résultat n'est trouvé//////////////////////////////////////////////                
                    if (h.getIdHotel() == 0) {
                        Form f = new Form("Sorry", new BorderLayout());
                        f.getStyle().setBgColor(0xffffff);
                        f.getToolbar().addCommandToRightBar("", back, (evtt) -> {
                            Acceuil A = new Acceuil();
                            A.getF().show();
                        });
                        Container dzl = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                        ImageViewer img = null;
                        try {
                            img = new ImageViewer(Image.createImage("/téléchargement.jpg"));
                        } catch (IOException ex) {
                        }
                        img.getStyle().setMarginTop(300);
                        dzl.add(img);
                        Label ex = new Label("Sorry Nothing To Show !");
                        ex.setUIID("details");
                        dzl.add(ex);
                        f.add(BorderLayout.CENTER, dzl);
                        f.show();
                    } //Les hotels Cherchés////////////////////////////////////////////////////////
                    else {
                        FontImage iconhotel = FontImage.createMaterial(FontImage.MATERIAL_HOTEL, s);
                        // FontImage iconadresse = FontImage.createMaterial(FontImage.MATERIAL_MAP, s);
                        FontImage star = FontImage.createMaterial(FontImage.MATERIAL_STAR_BORDER, s);
                        String url = "http://localhost/mobile/pidev/web/uploads/";
                        EncodedImage eimg;
                        star.setBgTransparency(255);
                        FontImage iconadresse = FontImage.createMaterial(FontImage.MATERIAL_PLACE, s);
                        Container C_nom = new Container(new BoxLayout(BoxLayout.X_AXIS));
                        Label nom = new Label(h.getNameHotel());
                        C_nom.add("              ").add(iconadresse).add(nom);
                        nom.setUIID("title2");
                        Container C_etoile = new Container(new BoxLayout(BoxLayout.X_AXIS));
                        Label nbetoile = new Label("              ");
                        C_etoile.add(nbetoile);
                        for (int i = 0; i < h.getNb_etoile(); i++) {
                            C_etoile.add(star);
                        }
                        result_adresse.add(C_nom);
                        result_adresse.add(C_etoile);
                        try {
                            eimg = EncodedImage.create("/load.gif");
                            eimg.scale(300, 300);
                            Image img = URLImage.createToStorage(eimg, h.getImage(), url + URLImage.RESIZE_SCALE);
                            ImageViewer imgv = new ImageViewer(img);
                            int deviceWidth = Display.getInstance().getDisplayWidth();
                            int imageWidth = (int) (deviceWidth * 0.8);
                            int imageHeight = (int) (deviceWidth * 0.6);
                            imgv.setPreferredH(imageHeight);
                            imgv.setPreferredW(imageWidth);
                            result_adresse.add(imgv);
                        } catch (IOException ex1) {
                            ex1.getMessage();
                        }
                        Container prix = new Container(new BoxLayout(BoxLayout.X_AXIS));
                        FontImage icc = FontImage.createMaterial(FontImage.MATERIAL_ROOM, s);
                        FontImage icons = FontImage.createMaterial(FontImage.MATERIAL_AIRLINE_SEAT_INDIVIDUAL_SUITE, s);
                        Label space = new Label("       ");
                        Label prixindiv = new Label(h.getPrix_indiv() + " $");
                        Label prixdouble = new Label(h.getPrix_double() + " $");
                        prix.add(new Label("Prices:     Single:")).add(prixindiv).add(new Label("     Double:")).add(prixdouble);
                        result_adresse.add(prix);
                        Button btnBook = new Button("Book");

                        //Choisir l'hotel à reserver et passer à la page pour entrer les données//////
                        btnBook.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {
                                Dialog d = new Dialog("Booking");
                                Container btn = new Container(new BoxLayout(BoxLayout.X_AXIS));

                                TextField nbsingle = new TextField(null, "Single room..");
                                TextField nbdouble = new TextField(null, "Double room..");

                                Button save = new Button("Save");
                                Button goback = new Button("Cancel");

                                save.setUIID("available");
                                goback.setUIID("available");
                                goback.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent evt) {
                                        Acceuil A = new Acceuil();
                                        A.getF().show();
                                    }
                                });

//////////////////////////////////////////////Reservation////////////////////////////////////////////////////////////////////////////////////////////////
                                save.addPointerPressedListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent evt) {
                                        String valsingle = nbsingle.getText().toString();
                                        String valdouble = nbdouble.getText().toString();
                                        System.out.println(Float.parseFloat(valdouble));
                                        Label error = new Label("Invalid numbers");
                                        if (valsingle.equals("") || valdouble.equals("") || (Float.parseFloat(valdouble) <= 0) || (Float.parseFloat(valsingle) <= 0)) {
                                            //save.setEnabled(false);
                                            error.remove();
                                            d.add(error);
                                            System.out.println("invalid number");
                                        } else {
                                            error.remove();

//////////////tester sur le nbre de place //////////////////////////////////////
                                            if (h.getNbre_de_chambre_despo() >= Float.parseFloat(valdouble) + Float.parseFloat(valsingle)) //Reserver///////////////////////////////////////////////////////////
                                            {
                                                if (MyApplication.current_user.getId() > 0) {
                                                    int idUser = MyApplication.current_user.getId();
                                                    ConnectionRequest con = new ConnectionRequest();
                                                    con.setUrl("http://localhost/mobile/pidev/web/app_dev.php/hotel/reserverm?user=" + idUser + "&hotel=" + h.getIdHotel() + "&indiv=" + nbsingle.getText().toString() + "&double=" + nbdouble.getText().toString());
                                                    con.setPost(false);
                                                    con.addResponseListener(new ActionListener<NetworkEvent>() {
                                                        @Override
                                                        public void actionPerformed(NetworkEvent evt) {
                                                            String json = con.getResponseData().toString();
                                                            System.out.println("Json : " + json);
                                                            JSONParser j = new JSONParser();

                                                            try {
                                                                Map<String, Object> mapEnroll = j.parseJSON(new CharArrayReader((new String(con.getResponseData())).toCharArray()));
                                                                System.out.println("Map: " + mapEnroll);
                                                                List<Map<String, Object>> list = (List<Map<String, Object>>) mapEnroll.get("root");
                                                                int idReservation = (int) Float.parseFloat(mapEnroll.get("idreservation").toString());
                                                                if (idReservation == -1) {
                                                                    //déjà inscrit
                                                                    Dialog.show("Error", "You have already a reservation in this hotel", "OK", null);
                                                                } else if (idReservation == 0) {
                                                                    //place insuffaisante
                                                                    System.out.println("insuffisant places");
                                                                    Dialog.show("Error", "This hotel is already complete", "OK", null);
                                                                } else if (idReservation == -2) {
                                                                    //excursion invalide
                                                                    System.out.println("Hotel does not exist");
                                                                    Dialog.show("Error", "Hotel does not exist", "OK", null);
                                                                } else {
                                                                    if (Dialog.show("Success", "you've successfully Booked in this hotel, The price = " + mapEnroll.get("prix").toString() + "  $", "Keep me here", null)) {

                                                                        ShowActiveHotels s = new ShowActiveHotels();
                                                                        s.getF().show();
                                                                    }
                                                                }
                                                            } catch (IOException ex) {
                                                            }
                                                        }
                                                    });
                                                    NetworkManager.getInstance().addToQueue(con);
                                                } else {
                                                    if (Dialog.show("Error", "You have to login first", "Ok", "Cancel")) {
                                                        Login login = new Login();
                                                        login.getF().show();
                                                    }
                                                }
                                            } else {
                                                Dialog.show("Error", "This hotel is already complete", "OK", null);

                                            }
                                        }

                                    }
                                });
                                d.add(nbsingle);
                                d.add(nbdouble);
                                btn.add(save);
                                btn.add(goback);
                                d.add(btn);
                                d.show();
                            }
                        });
                        result_adresse.add(btnBook);
                    }
                }
                result_adresse.setScrollableY(true);
                aff.setScrollableY(true);
                aff.add(BorderLayout.CENTER, result_adresse);
                aff.show();
            }
        });

        C_rech_adresse.add(rech_adresse).add(btnrech_adresse);
        rechercher.add(C_rech_adresse);
        f.add(BorderLayout.CENTER, rechercher);
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
