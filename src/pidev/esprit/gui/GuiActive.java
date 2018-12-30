/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.esprit.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
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
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.events.SelectionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.MultiList;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.mycompany.myapp.MyApplication;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pidev.esprit.Entity.Excursion;
import pidev.esprit.Service.ServiceExcursion;
import static pidev.esprit.gui.GuiExcursion.oldExist;

/**
 *
 * @author STA
 */
public class GuiActive {
    
     Form f;
    public static ArrayList<Excursion> excursions ;
    public static boolean oldExist ;
    public static boolean newExist ;
    
    public GuiActive()
    {
        f = new Form("Excursions", new BorderLayout());
       
         Style s = UIManager.getInstance().getComponentStyle("Tab");
               
        FontImage back = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        
        Container Container2 = new Container();
        TextField searchField = new TextField("", "Search");
        searchField.getAllStyles().setAlignment(Component.LEFT);
        Container2.add(BorderLayout.north(searchField));
        FontImage searchIcon = FontImage.createMaterial(FontImage.MATERIAL_SEARCH, s);
        searchField.setHintIcon(searchIcon);
        
        searchField.addDataChangeListener(new DataChangedListener() {
            @Override
            public void dataChanged(int type, int index) {
                String text = searchField.getText();
                
                if(text.length() < 1)
                {
                    for(Component cmp : f.getContentPane())
                    {
                        cmp.setHidden(false);
                        cmp.setVisible(true);
                    }
                }
                else{
                    text = text.toLowerCase();
                    System.out.println("text: " + text);
                    
                    
                    for(Component cmp : f.getContentPane())
                    {
                        String val = null;
                        /*if(cmp instanceof Label) 
                        {
                            val = ((Label)cmp).getText();
                            System.out.println("label ");
                        }
                    
                        else 
                        {
                            if(cmp instanceof TextArea) 
                            {
                                val = ((TextArea)cmp).getText();
                                System.out.println("textarea");
                            } 
                            else 
                            {
                                val = (String)cmp.getPropertyValue("text");
                                System.out.println(cmp.getName());
                            }
                            
                            System.out.println("val: " + val);
                        }*/
                        
                        if(cmp instanceof Container)
                        {
                            
                            System.out.println("true");
                        }
                        
                        //System.out.println(cmp.get);
                        boolean show = val != null && val.toLowerCase().indexOf(text) > -1;
                        cmp.setHidden(!show); // <3>
                        cmp.setVisible(show);
                        
                    }
                }
            f.getContentPane().animateLayout(250);
            }
        });
        
        
       
        
        f.getToolbar().addCommandToRightBar("", back, (evt) ->{
            Acceuil A = new Acceuil();
            A.getF().show();
        });
        
       
        
        ServiceExcursion ser = new ServiceExcursion();
        ArrayList<Excursion> excursions = new ArrayList<>();
        excursions = ser.getList2();
        
         
        
        try 
        {
            for(Excursion E : excursions)
            {
                //Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                Container c = FlowLayout.encloseCenter();
                Container ex = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                Container details = new Container(new BoxLayout(BoxLayout.X_AXIS));

                ex.getStyle().setMargin(Component.TOP, 40);
                ex.getStyle().setBorder(RoundRectBorder.create());
                Label destination = new Label("Destination: "+ E.getDestination());
                destination.setUIID("subtitle2");
                Label price = new Label("     "+ E.getPrix()+ "$");
                price.setUIID("subtitle");
                int available = E.getCapacite() - E.getNbre();
                Label capacity = new Label("   Available places: "+ available);
                if(available == 0)
                    capacity.setUIID("expired");

                SpanLabel program = new SpanLabel("   Program: "+ E.getProgramme());

                String url = "http://localhost/pidev/web/uploads/";
                EncodedImage eimg;
                    eimg = EncodedImage.create("/load.gif");
                eimg.scale(300,300);
                Image img = URLImage.createToStorage(eimg, E.getImg1(), url + E.getImg1(), URLImage.RESIZE_SCALE);

                //System.out.println("img: "+ img.getImageName());
                ImageViewer imgv = new ImageViewer(img);
                int deviceWidth = Display.getInstance().getDisplayWidth();
                int imageWidth =(int) (deviceWidth *0.8);
                int imageHeight =(int) (deviceWidth *0.6);
                imgv.setPreferredH(imageHeight);
                imgv.setPreferredW(imageWidth);

                details.add(capacity);

                ex.add(destination);
                ex.add(imgv);
                ex.add(details);
                ex.add(price);
                ex.add(program);


                Button view = new Button("View And Enroll");

                view.setScrollVisible(false);

                view.getStyle().setBgColor(0xe59100);
                view.getStyle().setFgColor(0x545353);
                view.getStyle().setBgTransparency(255);
                c.add(ex);
                c.add(view);
                //c.setLayout(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));

                Container2.add(c);
                Container2.setScrollableY(true);
                //f.add(c);

                view.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        Form show = new Form("Excursion Details");
                        Style s = UIManager.getInstance().getComponentStyle("titleCommand");
                        Image back = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
                         show.getToolbar().addCommandToRightBar("", back, (ev) ->{
                            f.show();
                        });


                        Container C = new Container(new BoxLayout(BoxLayout.Y_AXIS));

                        Label destination = new Label("Destination: "+ E.getDestination());
                        destination.getStyle().setMarginLeft(10.2f);

                        String url = "http://localhost/pidev/web/uploads/";
                        EncodedImage eimg;
                        Label price = new Label(E.getPrix()+ "$");
                        price.getStyle().setBgColor(0xCCCCCC);

                        Label date = new Label("date: "+ E.getDate());

                        SpanLabel program = new SpanLabel("Program: "+ E.getProgramme());

                        int place = (int)E.getCapacite() - (int)E.getNbre();
                        Label nbre= new Label("Available: "+ place);
                        C.add(destination);

                        try {
                           eimg = EncodedImage.create("/load.gif");
                           eimg.scale(300,300);
                           Image img = URLImage.createToStorage(eimg, E.getImg1(), url + E.getImg1(), URLImage.RESIZE_SCALE);

                           //System.out.println("img: "+ img.getImageName());
                           ImageViewer imgv = new ImageViewer(img);
                           int deviceWidth = Display.getInstance().getDisplayWidth();
                           int imageWidth =(int) (deviceWidth *0.8);
                           int imageHeight =(int) (deviceWidth *0.6);
                           imgv.setPreferredH(imageHeight);
                           imgv.setPreferredW(imageWidth);
                           C.add(imgv);

                        } catch (IOException ex1) {
                        }


                        C.add(price);
                        C.add(date);
                        C.add(nbre);
                        C.add(program);

                        Button btnEnroll = new Button("Enroll");
                        btnEnroll.getStyle().setBgColor(0xCCCCCC);
                        C.add(btnEnroll);

                        if(place > 0)
                        {
                            btnEnroll.setEnabled(true);  
                            btnEnroll.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent evt) {
                                    if(MyApplication.current_user.getId() > 0)
                                    {
                                        int idUser = MyApplication.current_user.getId();
                                        ConnectionRequest con = new ConnectionRequest();
                                        con.setUrl("http://localhost/pidev/web/app_dev.php/excursion/enroll?excursion="+ E.getId()+"&user="+idUser);
                                        //con.setUrl("http://localhost/pidev/web/app_dev.php/excursion/enroll?excursion=80&user="+idUser);
                                        con.setPost(false);
                                        con.addResponseListener(new ActionListener<NetworkEvent>() {
                                            @Override
                                            public void actionPerformed(NetworkEvent evt) {
                                                String json = con.getResponseData().toString();
                                                System.out.println("Json : "+json);
                                                JSONParser j = new JSONParser();

                                                try 
                                                {
                                                    Map<String , Object> mapEnroll = j.parseJSON(new CharArrayReader((new String(con.getResponseData())).toCharArray()));
                                                    int idReservation =(int)  Float.parseFloat(mapEnroll.get("id").toString());

                                                    if(idReservation == -1)
                                                    {
                                                        //déjà inscrit
                                                        Dialog.show("Error", "You are already registred to this excursion", "OK", null);
                                                    }
                                                    else if(idReservation == 0)
                                                    {
                                                        //place insuffaisante
                                                        System.out.println("insuffisant places");
                                                        Dialog.show("Error", "This excursion is already complete", "OK", null);
                                                    }
                                                    else if(idReservation == -2)
                                                    {
                                                        //excursion invalide
                                                        System.out.println("Excursion does not exist");
                                                        Dialog.show("Error", "Excursion does not exist", "OK", null);
                                                    }
                                                    else
                                                    {
                                                         if(Dialog.show("Success", "you've successfully joined this excursion", "View My List", "Keep me here"))
                                                         {
                                                            GuiExcursion s = new GuiExcursion();
                                                            s.getF().show();
                                                         }
                                                    }  
                                                } catch (IOException ex) {}
                                            }
                                        });
                                        NetworkManager.getInstance().addToQueue(con);                                                 
                                    }
                                    else
                                    {
                                       if(Dialog.show("Error", "You have to login first", "Ok", "Cancel"))
                                        {
                                            Login login = new Login();
                                            login.getF().show();
                                        } 
                                    }
                                }
                            });
                        }
                        else
                            btnEnroll.setEnabled(false);

                        show.add(C);
                        show.show();
                    }
                });
            }
        } catch (IOException ex1) {}
        
        
        
        
        
        
        
        
        
        
        
        
        

        
        f.setScrollableY(true);
        f.add(BorderLayout.CENTER, Container2);
    }

    private Map<String, Object> createListEntry(String name, String date, Image icon) 
    {
        Map<String, Object> entry = new HashMap<>();
        entry.put("Line1", name);
        entry.put("Line2", date);
        entry.put("icon", icon);
        return entry;
    }

    
    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    
}