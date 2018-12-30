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
import com.codename1.l10n.L10NManager;
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
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.mycompany.myapp.MyApplication;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import pidev.esprit.Entity.Reservationvoyage;
import pidev.esprit.Entity.Voyage;
import pidev.esprit.Service.ServiceVoyage;

/**
 *
 * @author Sirine
 */
public class ShowActiveVoyage 
{
    Form f;
    Form fr;
    Form fs;

    public ShowActiveVoyage() throws IOException 
    {
        f = new Form("Journeys", new BorderLayout());
        f.getStyle().setBgColor(0xffffff);
        Style s = UIManager.getInstance().getComponentStyle("Tab");

        FontImage back = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        FontImage iconsearch = FontImage.createMaterial(FontImage.MATERIAL_SEARCH, s);
        
        f.getToolbar().addCommandToRightBar("", back, (evt) -> {
            Acceuil A = new Acceuil();
            A.getF().show();
        });
        
        ServiceVoyage sv = new ServiceVoyage();
        ArrayList<Voyage> listvoyage = sv.getList2();
        Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container cs = new Container(new BoxLayout(BoxLayout.X_AXIS));

        TextField search = new TextField(null, "Enter your destination ..");
        int deviceWidth = Display.getInstance().getDisplayWidth();
        int TextWidth = (int) (deviceWidth * 0.8);
        search.setPreferredW(TextWidth);
        Button btnsearch = new Button(iconsearch);
        cs.add(search);
        cs.add(btnsearch);
        btnsearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                fs = new Form("Search result", new BorderLayout());
                fs.getStyle().setBgColor(0xffffff);
                fs.getToolbar().addCommandToRightBar("", back, (evtt) -> {
                    Acceuil A = new Acceuil();
                    A.getF().show();
                });
                Container cr = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                ServiceVoyage sv = new ServiceVoyage();
                String des = search.getText().toString();
                ArrayList<Voyage> listvoyage = sv.getsearch(des);
                cr.setScrollableY(true);

                for (Voyage E : listvoyage) 
                {
                    cr.getStyle().setMargin(Component.TOP, 40);
                    cr.getStyle().setBorder(RoundRectBorder.create());

                    FontImage icondepart = FontImage.createMaterial(FontImage.MATERIAL_FLIGHT_LAND, s);
                    FontImage iconarrive = FontImage.createMaterial(FontImage.MATERIAL_FLIGHT_TAKEOFF, s);

                    Container desti = new Container(new GridLayout(1, 2));
                    Label destination = new Label(E.getDestinationVoyage(), icondepart);
                    destination.setUIID("details");
                    Label depart = new Label(E.getDepartVoyage(), iconarrive);
                    Label space = new Label("");
                    desti.add(depart).add(destination);
                    desti.getStyle().setMargin(Component.LEFT, (int) (Display.getInstance().getDisplayWidth() * 0.05));

                    depart.setUIID("details");
                    Label compagnie = new Label("Compagnie: " + E.getCompagnie());
                    compagnie.setUIID("label2");
                    Label price = new Label("     " + E.getPrix() + "$");

                    cr.add(desti);
                    String url = "http://localhost/pidev/web/uploads/";
                    EncodedImage img;
                    try {
                        System.out.println(url + E.getImage());
                        img = EncodedImage.create("/load.gif");
                        img.scale(300, 300);
                        Image imge = URLImage.createToStorage(img, E.getImage(), url + E.getImage(), URLImage.RESIZE_SCALE);
                        ImageViewer imgv = new ImageViewer(imge);
                        int deviceWidth = Display.getInstance().getDisplayWidth();
                        int imageWidth = (int) (deviceWidth * 0.8);
                        int imageHeight = (int) (deviceWidth * 0.6);
                        imgv.setPreferredH(imageHeight);
                        imgv.setPreferredW(imageWidth);
                        cr.add(imgv);

                    } catch (IOException ex1) {
                        ex1.getMessage();
                    }
                    //ex.add(destination);
                    //ex.add(depart);
                    cr.add(compagnie);
                    cr.add(price);

                    Button btnreserver = new Button("Book");
                    int place = (int) E.getNb_place_dispo();
                    btnreserver.getStyle().setBgColor(0xCCCCCC);
                    if (E.getNb_place_dispo() == 0) {
                        btnreserver.setEnabled(true);
                    }
                    cr.add(btnreserver);
                    if (place > 0) {
                        btnreserver.setEnabled(true);
                        btnreserver.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {
                                if (MyApplication.current_user.getId() > 0) {
                                    int idUser = MyApplication.current_user.getId();
                                    Form reservation = new Form("Booking", new BorderLayout());
                                    reservation.getStyle().setBgColor(0xffffff);
                                    Button btnpass = new Button("Book");
                                    Container pass = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                                    Container desti = new Container(new GridLayout(1, 2));
                                    Label destination = new Label(E.getDestinationVoyage(), icondepart);
                                    destination.setUIID("details");
                                    Label depart = new Label(E.getDepartVoyage(), iconarrive);
                                    //Label space = new Label("");
                                    desti.add(depart).add(destination);
                                    desti.getStyle().setMargin(Component.LEFT, (int) (Display.getInstance().getDisplayWidth() * 0.05));

                                    depart.setUIID("details");
                                    Label compagnie = new Label("            Compagnie : " + E.getCompagnie());
                                    compagnie.setUIID("label2");
                                    Label date1 = new Label("            Departure  :");
                                    Label datedep = new Label(E.getDateVoyageAller(), iconarrive);
                                    Container dep = new Container(new BoxLayout(BoxLayout.X_AXIS));
                                    Label hdepaller = new Label(E.getHdepartVoyageAller());
                                    Label harraller = new Label(E.getHarriveeVoyageAller());
                                    Container hdep = new Container(new BoxLayout(BoxLayout.X_AXIS));
                                    hdep.add(new Label("                ")).add(hdepaller).add(new Label("___")).add(harraller);

                                    Label date2 = new Label("            Arrival  :  ");
                                    Label datearr = new Label(E.getDateVoyageRetour(), icondepart);
                                    Container arr = new Container(new BoxLayout(BoxLayout.X_AXIS));
                                    Label hdepretour = new Label(E.getHdepartVoyageRetour());
                                    Label harrretour = new Label(E.getHarriveeVoyageRetour());
                                    Container harr = new Container(new BoxLayout(BoxLayout.X_AXIS));
                                    harr.add(new Label("                ")).add(hdepretour).add(new Label("___")).add(harrretour);
                                    TextField tplace = new TextField(null, "Places to book ");

                                    dep.add(date1);
                                    dep.add(datedep);

                                    arr.add(date2);
                                    arr.add(datearr);

                                    pass.add(desti);
                                    pass.add(compagnie);
                                    pass.add(dep);
                                    pass.add(hdep);
                                    pass.add(arr);
                                    pass.add(harr);
                                    pass.add(tplace);
                                    pass.add(btnpass);
                                    reservation.add(BorderLayout.CENTER, pass);
                                    reservation.getToolbar().addCommandToRightBar("", back, (evt1) -> {
                                        f.show();
                                    });
                                    reservation.show();
                                    btnpass.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent evt) {

                                            reservation.show();
                                            ConnectionRequest con = new ConnectionRequest();
                                            con.setUrl("http://localhost/pidev/web/app_dev.php/voyage/Mreserver?voyage=" + E.getIdVoyage() + "&user=" + idUser + "&nbreserver=" + tplace.getText());
                                            con.setPost(false);

                                            con.addResponseListener(new ActionListener<NetworkEvent>() {
                                                @Override
                                                public void actionPerformed(NetworkEvent evt) {
                                                    String json = con.getResponseData().toString();
                                                    System.out.println("Json : " + json);
                                                    JSONParser j = new JSONParser();

                                                    try {
                                                        Map<String, Object> mapreserv = j.parseJSON(new CharArrayReader((new String(con.getResponseData())).toCharArray()));
                                                        System.out.println("Map: " + mapreserv);
                                                        List<Map<String, Object>> list = (List<Map<String, Object>>) mapreserv.get("root");

                                                        int idReservation = (int) Float.parseFloat(mapreserv.get("idreservation").toString());

                                                        if (idReservation == -1) {
                                                            //déjà inscrit
                                                            Dialog.show("Error", "You are already registred to this journey", "OK", null);
                                                        } else if (idReservation == 0) {
                                                            //place insuffaisante
                                                            System.out.println("insuffisant places");
                                                            Dialog.show("Error", "This journey is already complete", "OK", null);
                                                        } else if (idReservation == -2) {
                                                            //excursion invalide
                                                            System.out.println("Journey does not exist");
                                                            Dialog.show("Error", "Journey does not exist", "OK", null);
                                                        } else {
                                                            // int nbplace_reserver = (int) Float.parseFloat(mapreserv.get("nbrePlaceReserv").toString());

                                                            if (Dialog.show("Success", "you've successfully joined this Journey", null, "Keep me here")) {
                                                                ShowActiveVoyage s = new ShowActiveVoyage();
                                                                s.getF().show();
                                                            }
                                                        }
                                                    } catch (IOException ex) {
                                                    }
                                                }
                                            });
                                            NetworkManager.getInstance().addToQueue(con);
                                        }
                                    });
                                } else {
                                    if (Dialog.show("Error", "You have to login first", "Ok", "Cancel")) {
                                        Login login = new Login();
                                        login.getF().show();
                                    }
                                }

                            }
                        });
                    } else {
                        btnreserver.setEnabled(false);
                    }
                }
                fs.setScrollableY(true);
                fs.add(BorderLayout.CENTER, cr);
                fs.show();
            }
        });

        c.add(cs);

        for (Voyage E : listvoyage) {

            // Container c = FlowLayout.encloseCenter();
            Container ex = new Container(new BoxLayout(BoxLayout.Y_AXIS));

            ex.getStyle().setMargin(Component.TOP, 40);
            ex.getStyle().setBorder(RoundRectBorder.create());

            //destination.getStyle().setMarginLeft(9.2f);
            // Style s1 = UIManager.getInstance().getComponentStyle("Tab");
            FontImage icondepart = FontImage.createMaterial(FontImage.MATERIAL_FLIGHT_LAND, s);
            FontImage iconarrive = FontImage.createMaterial(FontImage.MATERIAL_FLIGHT_TAKEOFF, s);

            Container desti = new Container(new GridLayout(1, 2));
            Label destination = new Label(E.getDestinationVoyage(), icondepart);
            destination.setUIID("details");
            Label depart = new Label(E.getDepartVoyage(), iconarrive);
            Label space = new Label("");
            desti.add(depart).add(destination);
            desti.getStyle().setMargin(Component.LEFT, (int) (Display.getInstance().getDisplayWidth() * 0.05));

            depart.setUIID("details");
            Label compagnie = new Label("Compagnie: " + E.getCompagnie());
            compagnie.setUIID("label2");
            Label price = new Label("     " + E.getPrix() + "$");

            ex.add(desti);

            String url = "http://localhost/pidev/web/uploads/";
            EncodedImage img;
            try {
                System.out.println(url + E.getImage());
                img = EncodedImage.create("/load.gif");
                img.scale(300, 300);
                Image imge = URLImage.createToStorage(img, E.getImage(), url + E.getImage(), URLImage.RESIZE_SCALE);
                ImageViewer imgv = new ImageViewer(imge);
                int deviceWidth2 = Display.getInstance().getDisplayWidth();
                int imageWidth = (int) (deviceWidth2 * 0.8);
                int imageHeight = (int) (deviceWidth2 * 0.6);
                imgv.setPreferredH(imageHeight);
                imgv.setPreferredW(imageWidth);
                ex.add(imgv);

            } catch (IOException ex1) {
                ex1.getMessage();
            }
            //ex.add(destination);
            //ex.add(depart);
            ex.add(compagnie);
            ex.add(price);
            c.add(ex);
            Button btnreserver = new Button("Book");
            if (E.getNb_place_dispo() == 0) {
                btnreserver.setEnabled(true);
            }
            int place = (int) E.getNb_place_dispo();
            btnreserver.getStyle().setBgColor(0xCCCCCC);
            c.add(btnreserver);
            if (place > 0) {
                btnreserver.setEnabled(true);
                btnreserver.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if (MyApplication.current_user.getId() > 0) {
                            int idUser = MyApplication.current_user.getId();
                            Form reservation = new Form("Booking", new BorderLayout());
                            reservation.getStyle().setBgColor(0xffffff);
                            Button btnpass = new Button("Book");
                            Container pass = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                            Container desti = new Container(new GridLayout(1, 2));
                            Label destination = new Label(E.getDestinationVoyage(), icondepart);
                            destination.setUIID("details");
                            Label depart = new Label(E.getDepartVoyage(), iconarrive);
                            //Label space = new Label("");
                            desti.add(depart).add(destination);
                            desti.getStyle().setMargin(Component.LEFT, (int) (Display.getInstance().getDisplayWidth() * 0.05));

                            depart.setUIID("details");
                            Label compagnie = new Label("            Compagnie : " + E.getCompagnie());
                            compagnie.setUIID("label2");
                            Label date1 = new Label("            Departure  :");
                            Label datedep = new Label(E.getDateVoyageAller(), iconarrive);
                            Container dep = new Container(new BoxLayout(BoxLayout.X_AXIS));
                            Label hdepaller = new Label(E.getHdepartVoyageAller());
                            Label harraller = new Label(E.getHarriveeVoyageAller());
                            Container hdep = new Container(new BoxLayout(BoxLayout.X_AXIS));
                            hdep.add(new Label("                ")).add(hdepaller).add(new Label("___")).add(harraller);

                            Label date2 = new Label("            Arrival  :  ");
                            Label datearr = new Label(E.getDateVoyageRetour(), icondepart);
                            Container arr = new Container(new BoxLayout(BoxLayout.X_AXIS));
                            Label hdepretour = new Label(E.getHdepartVoyageRetour());
                            Label harrretour = new Label(E.getHarriveeVoyageRetour());
                            Container harr = new Container(new BoxLayout(BoxLayout.X_AXIS));
                            harr.add(new Label("                ")).add(hdepretour).add(new Label("___")).add(harrretour);
                            TextField tplace = new TextField(null, "Places to book ", 4, TextArea.NUMERIC);

                            dep.add(date1);
                            dep.add(datedep);

                            arr.add(date2);
                            arr.add(datearr);

                            pass.add(desti);
                            pass.add(compagnie);
                            pass.add(dep);
                            pass.add(hdep);
                            pass.add(arr);
                            pass.add(harr);
                            pass.add(tplace);
                            pass.add(btnpass);

                            reservation.add(BorderLayout.CENTER, pass);
                            reservation.getToolbar().addCommandToRightBar("", back, (evt1) -> {
                                f.show();
                            });
                            reservation.show();
                            btnpass.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent evt) {

                                    reservation.show();
                                    ConnectionRequest con = new ConnectionRequest();
                                    if ((Float.parseFloat(tplace.getText().toString())) < 1) {
                                        pass.add(new Label("Invalid Number"));
                                        System.out.println("invalid number");
                                        return;
                                    }
                                    con.setUrl("http://localhost/pidev/web/app_dev.php/voyage/Mreserver?voyage=" + E.getIdVoyage() + "&user=" + idUser + "&nbreserver=" + tplace.getText());
                                    con.setPost(false);

                                    con.addResponseListener(new ActionListener<NetworkEvent>() {
                                        @Override
                                        public void actionPerformed(NetworkEvent evt) {
                                            String json = con.getResponseData().toString();
                                            System.out.println("Json : " + json);
                                            JSONParser j = new JSONParser();

                                            try {
                                                Map<String, Object> mapreserv = j.parseJSON(new CharArrayReader((new String(con.getResponseData())).toCharArray()));
                                                System.out.println("Map: " + mapreserv);
                                                List<Map<String, Object>> list = (List<Map<String, Object>>) mapreserv.get("root");

                                                int idReservation = (int) Float.parseFloat(mapreserv.get("idreservation").toString());

                                                if (idReservation == -1) {
                                                    //déjà inscrit
                                                    Dialog.show("Error", "You are already registred to this journey", "OK", null);
                                                } else if (idReservation == 0) {
                                                    //place insuffaisante
                                                    System.out.println("insuffisant places");
                                                    Dialog.show("Error", "This journey is already complete", "OK", null);
                                                } else if (idReservation == -2) {
                                                    //excursion invalide
                                                    System.out.println("Journey does not exist");
                                                    Dialog.show("Error", "Journey does not exist", "OK", null);
                                                } else {
                                                    // int nbplace_reserver = (int) Float.parseFloat(mapreserv.get("nbrePlaceReserv").toString());

                                                    if (Dialog.show("Success", "you've successfully joined this Journey", "View My List", "Keep me here")) {
                                                        ShowActiveVoyage s = new ShowActiveVoyage();
                                                        s.getF().show();
                                                    }
                                                }
                                            } catch (IOException ex) {
                                            }
                                        }
                                    });
                                    NetworkManager.getInstance().addToQueue(con);
                                }
                            });
                        } else {
                            if (Dialog.show("Error", "You have to login first", "Ok", "Cancel")) {
                                Login login = new Login();
                                login.getF().show();
                            }
                        }

                    }
                });
            } else {
                btnreserver.setEnabled(false);
            }
        }

        c.setScrollableY(true);
        f.setScrollableY(true);
        f.add(BorderLayout.CENTER, c);

/////////////////////////////////Affichage des reservations 
        fr = new Form("Your Reservations", new BorderLayout());

        fr.getStyle().setBgColor(0xffffff);
        ArrayList<Reservationvoyage> listreservation = sv.getListreserv2();
        //System.out.println(listreservation);

        Container cr = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        fr.getToolbar().addCommandToLeftBar("", back, (evt) -> {
            Acceuil A = new Acceuil();
            A.getF().show();
        });
        Container c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        FontImage iconold = FontImage.createMaterial(FontImage.MATERIAL_LIST, s);
        if (listreservation == null) {
            c1.add(new Label("No reservation to show"));
        } else {
            for (Reservationvoyage E : listreservation) {
                if ((E.getIdReservation() == 0) || (E.getIdReservation() == -1) || (E.getIdReservation() == -2)) {

                    System.out.println("trouuuuuuuuuuu");
                    Button reserver = new Button("Book");
                    reserver.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            f.show();
                        }
                    });
                    ImageViewer image = null;
                    image = new ImageViewer(Image.createImage("/about.jpg"));

                    image.setPreferredW(Display.getInstance().getDisplayWidth());
                    image.getStyle().setMarginTop(100);

                    c1.add(image);
                    c1.add(reserver);
                    fr.add(BorderLayout.CENTER, c1);

                } else {

                    Container C = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                    C.getStyle().setMargin(Component.TOP, 40);
                    C.getStyle().setBorder(RoundRectBorder.create());

                    Label prix = new Label("         Price  :  " + E.getPrix() + "  $");
                    Label place = new Label("       Places  : " + E.getNbre_place_reserv());
                    Container nb = new Container(new BoxLayout(BoxLayout.X_AXIS));

                    nb.add(prix).add(place);
                    //affichage de voyage de chaque reservation
                    System.out.println(E.getIdVoyage());
                    ArrayList<Voyage> listV = sv.getList2id((int) E.getIdVoyage());

                    for (int i = 0; i < listV.size(); i++) {
                        Voyage v = new Voyage();
                        v = listV.get(i);
                        // System.out.println(listV);

                        v.setIdVoyage(E.getIdVoyage());
                        FontImage icondepart = FontImage.createMaterial(FontImage.MATERIAL_FLIGHT_LAND, s);
                        FontImage iconarrive = FontImage.createMaterial(FontImage.MATERIAL_FLIGHT_TAKEOFF, s);
                        FontImage date = FontImage.createMaterial(FontImage.MATERIAL_DATE_RANGE, s);
                        FontImage time = FontImage.createMaterial(FontImage.MATERIAL_ALARM, s);

                        Container voy = new Container(new BoxLayout(BoxLayout.X_AXIS));

                        Label destination = new Label(v.getDestinationVoyage(), icondepart);
                        destination.setUIID("details");
                        Label depart = new Label(v.getDepartVoyage(), iconarrive);
                        depart.setUIID("details");
                        voy.add("     ").add(depart).add(new Label("       ")).add(destination);
                        Label datedep = new Label(v.getDateVoyageAller(), date);
                        Container dep = new Container(new BoxLayout(BoxLayout.X_AXIS));
                        Container arr = new Container(new BoxLayout(BoxLayout.X_AXIS));
                        Label hdepaller = new Label(v.getHdepartVoyageAller(), time);
                        Label harraller = new Label(v.getHarriveeVoyageAller(), time);
                        Container date1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
                        Container date2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
                        Label hdepretour = new Label(v.getHdepartVoyageRetour(), time);
                        Label harrretour = new Label(v.getHarriveeVoyageRetour(), time);
                        date1.add(new Label("           ")).add(hdepaller).add(new Label("   _   ")).add(harraller);
                        date2.add(new Label("           ")).add(hdepretour).add(new Label("   _   ")).add(harrretour);

                        Label datearr = new Label(v.getDateVoyageAller(), date);
                        dep.add("                   ").add(datedep);
                        arr.add("                   ").add(datearr);
                        C.add(voy);
                        C.add(dep);
                        C.add(date1);
                        C.add(arr);
                        C.add(date2);

                        String url = "http://localhost/mobile/pidev/web/uploads/";
                        EncodedImage img;
                        try {
                            System.out.println(url + v.getImage());
                            img = EncodedImage.create("/load.gif");
                            img.scale(300, 300);
                            Image imge = URLImage.createToStorage(img, v.getImage(), url + v.getImage(), URLImage.RESIZE_SCALE);
                            ImageViewer imgv = new ImageViewer(imge);
                            int deviceWidth1 = Display.getInstance().getDisplayWidth();
                            int imageWidth = (int) (deviceWidth1 * 0.8);
                            int imageHeight = (int) (deviceWidth1 * 0.6);
                            imgv.setPreferredH(imageHeight);
                            imgv.setPreferredW(imageWidth);
                            C.add(imgv);

                        } catch (IOException ex1) {
                            ex1.getMessage();
                        }

                        C.add(nb);

                        //}
                        c1.add(C);

                    }
                    Button annuler = new Button("Cancel");
                    annuler.setUIID("expired");
                    annuler.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {

                            ConnectionRequest con = new ConnectionRequest();
                            con.setUrl("http://localhost/pidev/web/app_dev.php/voyage/Mcancel?user=" + MyApplication.current_user.getId() + "&reservation=" + E.getIdReservation());
                            con.setPost(false);

                            con.addResponseListener(new ActionListener<NetworkEvent>() {
                                @Override
                                public void actionPerformed(NetworkEvent evt) {
                                    String json = con.getResponseData().toString();
                                    System.out.println("Json : " + json);
                                    JSONParser j = new JSONParser();
                                    try {
                                        Map<String, Object> exMap = j.parseJSON(new CharArrayReader((new String(con.getResponseData())).toCharArray()));

                                        int idreservation = (int) Float.parseFloat(exMap.get("idreservation").toString());

                                        if (idreservation == 0) {
                                            if (Dialog.show("Success", "You have successfully disjoined this Journey", "OK", null)) {
                                                fr.show();
                                            }
                                        } else {
                                            if (Dialog.show("Error", "This reservation is already canceled", "OK", null)) {
                                                fr.show();
                                            }

                                        }
                                    } catch (IOException ex) {
                                    }
                                }
                            });
                            NetworkManager.getInstance().addToQueueAndWait(con);

                        }

                    });

                    c1.add(annuler);
                     fr.add(BorderLayout.CENTER, c1);

                }
            }
           


        }
         c1.setScrollableY(true);
            fr.setScrollableY(true);
                   


        fr.getToolbar().addCommandToOverflowMenu("Active Reservation", iconold, (evt) -> {

            //////////////////////////////////////////////////////////////////////////////////////////
            Form fr_activ = new Form("Your Active Reservations", new BorderLayout());
            Container c2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));

            ArrayList<Reservationvoyage> listreservationactive = sv.getListreservactive();
            if (listreservationactive == null) {
                System.out.println("lehnaaaaaaaaaaaaaaaa");
            } else {

                for (Reservationvoyage E : listreservationactive) {
                    if (E.getPrix() == 0) {
                        // if(E.getPrix()){
                        if ((E.getIdReservation() == 0) || (E.getIdReservation() == -1) || (E.getIdReservation() == -2)) {

                            System.out.println("trouuuuuuuuuuu");
                            Button reserver = new Button("Book");
                            reserver.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent evt) {
                                    f.show();
                                }
                            });
                            ImageViewer image = null;
                            try {
                                image = new ImageViewer(Image.createImage("/about.jpg"));
                            } catch (IOException ex) {
                            }

                            image.setPreferredW(Display.getInstance().getDisplayWidth());
                            image.getStyle().setMarginTop(100);

                            c2.add(image);
                            c2.add(reserver);
                            fr_activ.add(BorderLayout.CENTER, c2);

                        } else {

                            Container C = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                            C.getStyle().setMargin(Component.TOP, 40);
                            C.getStyle().setBorder(RoundRectBorder.create());

                            Label prix = new Label("         Price  :  " + E.getPrix() + "  $");
                            Label place = new Label("       Places  : " + E.getNbre_place_reserv());
                            Container nb = new Container(new BoxLayout(BoxLayout.X_AXIS));

                            nb.add(prix).add(place);
                            //affichage de voyage de chaque reservation
                            System.out.println(E.getIdVoyage());
                            ArrayList<Voyage> listV = sv.getList2id((int) E.getIdVoyage());

                            for (int i = 0; i < listV.size(); i++) {
                                Voyage v = new Voyage();
                                v = listV.get(i);

                                // System.out.println(listV);
                                v.setIdVoyage(E.getIdVoyage());
                                FontImage icondepart = FontImage.createMaterial(FontImage.MATERIAL_FLIGHT_LAND, s);
                                FontImage iconarrive = FontImage.createMaterial(FontImage.MATERIAL_FLIGHT_TAKEOFF, s);
                                FontImage date = FontImage.createMaterial(FontImage.MATERIAL_DATE_RANGE, s);
                                FontImage time = FontImage.createMaterial(FontImage.MATERIAL_ALARM, s);

                                Container voy = new Container(new BoxLayout(BoxLayout.X_AXIS));

                                Label destination = new Label(v.getDestinationVoyage(), icondepart);
                                destination.setUIID("details");
                                Label depart = new Label(v.getDepartVoyage(), iconarrive);
                                depart.setUIID("details");
                                voy.add("     ").add(depart).add(new Label("       ")).add(destination);
                                Label datedep = new Label(v.getDateVoyageAller(), date);
                                Container dep = new Container(new BoxLayout(BoxLayout.X_AXIS));
                                Container arr = new Container(new BoxLayout(BoxLayout.X_AXIS));
                                Label hdepaller = new Label(v.getHdepartVoyageAller(), time);
                                Label harraller = new Label(v.getHarriveeVoyageAller(), time);
                                Container date1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
                                Container date2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
                                Label hdepretour = new Label(v.getHdepartVoyageRetour(), time);
                                Label harrretour = new Label(v.getHarriveeVoyageRetour(), time);
                                date1.add(new Label("           ")).add(hdepaller).add(new Label("   _   ")).add(harraller);
                                date2.add(new Label("           ")).add(hdepretour).add(new Label("   _   ")).add(harrretour);

                                Label datearr = new Label(v.getDateVoyageAller(), date);
                                dep.add("                   ").add(datedep);
                                arr.add("                   ").add(datearr);
                                C.add(voy);
                                C.add(dep);
                                C.add(date1);
                                C.add(arr);
                                C.add(date2);

                                String url = "http://localhost/pidev/web/uploads/";
                                EncodedImage img;
                                try {
                                    System.out.println(url + v.getImage());
                                    img = EncodedImage.create("/load.gif");
                                    img.scale(300, 300);
                                    Image imge = URLImage.createToStorage(img, v.getImage(), url + v.getImage(), URLImage.RESIZE_SCALE);
                                    ImageViewer imgv = new ImageViewer(imge);
                                    int deviceWidth1 = Display.getInstance().getDisplayWidth();
                                    int imageWidth = (int) (deviceWidth1 * 0.8);
                                    int imageHeight = (int) (deviceWidth1 * 0.6);
                                    imgv.setPreferredH(imageHeight);
                                    imgv.setPreferredW(imageWidth);
                                    C.add(imgv);

                                } catch (IOException ex1) {
                                    ex1.getMessage();
                                }

                                C.add(nb);

                                //}
                                c2.add(C);

                            }
                            Button annuler = new Button("Cancel");
                            annuler.setUIID("expired");
                            annuler.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent evt) {

                                    ConnectionRequest con = new ConnectionRequest();
                                    con.setUrl("http://localhost/pidev/web/app_dev.php/voyage/Mcancel?user=" + MyApplication.current_user.getId() + "&reservation=" + E.getIdReservation());
                                    con.setPost(false);

                                    con.addResponseListener(new ActionListener<NetworkEvent>() {
                                        @Override
                                        public void actionPerformed(NetworkEvent evt) {
                                            String json = con.getResponseData().toString();
                                            System.out.println("Json : " + json);
                                            JSONParser j = new JSONParser();
                                            try {
                                                Map<String, Object> exMap = j.parseJSON(new CharArrayReader((new String(con.getResponseData())).toCharArray()));

                                                int idreservation = (int) Float.parseFloat(exMap.get("idreservation").toString());

                                                if (idreservation == 0) {
                                                    if (Dialog.show("Success", "You have successfully disjoined this Journey", "OK", null)) {
                                                        fr_activ.show();
                                                    }
                                                } else {
                                                    if (Dialog.show("Error", "This reservation is already canceled", "OK", null)) {
                                                        fr_activ.show();
                                                    }

                                                }
                                            } catch (IOException ex) {
                                            }
                                        }
                                    });
                                    NetworkManager.getInstance().addToQueueAndWait(con);

                                }

                            });

                            c2.add(annuler);
            fr_activ.add(BorderLayout.CENTER, c2);

                        }
                    } else {
                        c2.add(new Label("Sorry , Nothing To Show"));
                                    fr_activ.add(BorderLayout.CENTER, c2);

                    }
                }
            }
            fr_activ.getToolbar().addCommandToRightBar("", back, (evt2) -> {
                Acceuil A = new Acceuil();
                A.getF().show();
            });
            c2.setScrollableY(true);

            fr_activ.setScrollableY(true);
            fr_activ.show();
            ///////////////////////////////////////////////////////////////////////////////////////////////
        });

    }

    public Form getF() {
        return f;
    }

    public Form getFR() {
        return fr;
    }

    public Form getFS() {
        return fs;
    }

}
