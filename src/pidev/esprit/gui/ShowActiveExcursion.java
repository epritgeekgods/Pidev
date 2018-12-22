/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.esprit.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
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
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.SelectionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.ListModel;
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

/**
 *
 * @author STA
 */
public class ShowActiveExcursion 
{
    Form f;
    public static ArrayList<Excursion> excursions ;
    public static boolean oldExist ;
    public static boolean newExist ;
    public ShowActiveExcursion() 
    {
        f = new Form("My active excursions", new BorderLayout());

        
        Tabs t = new Tabs();
        Style s = UIManager.getInstance().getComponentStyle("Tab");
        FontImage icon1 = FontImage.createMaterial(FontImage.MATERIAL_HIGHLIGHT, s);
        FontImage icon2 = FontImage.createMaterial(FontImage.MATERIAL_UPDATE, s); //active
        FontImage icon3 = FontImage.createMaterial(FontImage.MATERIAL_ALARM_OFF, s); //old
        
        FontImage back = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        
        f.getToolbar().addCommandToRightBar("", back, (evt) ->{
            Acceuil A = new Acceuil();
            A.getF().show();
        });
        
        //Active excursions
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev/web/app_dev.php/excursion/activereservation?id="+MyApplication.current_user.getId());
        con.setPost(false);

        Container container1 = new Container(new BorderLayout());
        Container Container2 = new Container();
        container1.setScrollableY(true);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser j = new JSONParser();

                try {
                    Map<String, Object> myExMap = j.parseJSON(new CharArrayReader((new String(con.getResponseData())).toCharArray()));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) myExMap.get("root");
                    System.out.println("lmap: "+ myExMap);
                    excursions = new ArrayList<>();
                    if(myExMap.get("root") == null)
                    {
                        Excursion e = new Excursion();
                        excursions.add(e);
                        System.out.println("nothing to show");
                        
                        ImageViewer image = null;
                        image = new ImageViewer(Image.createImage("/error_excursion.png"));
                        
                        image.setPreferredW(Display.getInstance().getDisplayWidth());
                        Button btn_new = new Button("Enroll");
                        
                        image.getStyle().setMarginTop(100);

                        btn_new.getStyle().setBgColor(0x00adef);
                        btn_new.getStyle().setFgColor(0xffffff);
                        btn_new.getStyle().setBgTransparency(255);
                       
                        
                        btn_new.addActionListener(new ActionListener<ActionEvent>() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {
                                //container1.setEnabled(false);
                                //Container2.setEnabled(true);
                                //f.
                                //f.add(BorderLayout.CENTER, Container2);
                            }
                        });
                        
                        Container btn = new Container(new GridLayout(1, 3));
                        btn.add(new Label(""));
                        btn.add(btn_new);
                        btn.add(new Label(""));

                        Container error = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                        error.add(image);
                        error.add(btn);
                        container1.add(BorderLayout.CENTER, error);
                        container1.setScrollableY(false);
                        //container1.add(BorderLayout.CENTER, btn_new);
                    }
                    else
                    {
                        ArrayList<Map<String, Object>> data = new ArrayList<>();
                        data.add(createListEntry(null, null, null));

                        for(Map<String, Object> obj: list)
                        {
                            Excursion e = new Excursion();
                            e.setId((int) Float.parseFloat(obj.get("idrando").toString()));
                            e.setDate(obj.get("daterando").toString());
                            e.setDestination(obj.get("destination").toString());
                            excursions.add(e);

                            try 
                            {
                                EncodedImage placeholder = EncodedImage.create("/load.gif");

                                String url = "http://localhost/pidev/web/uploads/";
                                String imgString = obj.get("imgurl1").toString();
                                String path = url+ imgString;

                                Image image = URLImage.createToStorage(placeholder, imgString , path, URLImage.RESIZE_SCALE);

                                data.add(createListEntry(obj.get("destination").toString(), obj.get("daterando").toString(), image));
                                DefaultListModel<Map<String, Object>> model = new DefaultListModel<>(data);
                                MultiList ml = new MultiList(model);

                                ml.addSelectionListener(new SelectionListener() {
                                    @Override
                                    public void selectionChanged(int oldSelected, int newSelected) {

                                        Map<String, Object> item = (Map<String, Object>) (ml.getSelectedItem());
                                        System.out.println("newSeleted: "+ item); 
                                        while(item.get("Line1") != null && (oldSelected != newSelected))
                                        {
                                            if(Dialog.show("?", "Do you readdly want to disjoin this excursion " + item.get("Line1")+ "?", "Confirm", "Cancel"))
                                            {
                                                Excursion E = new Excursion();
                                                for(int i=0; i< excursions.size(); i++)
                                                {

                                                    if(excursions.get(i).getDestination().equals(item.get("Line1")) &&
                                                        excursions.get(i).getDate().equals(item.get("Line2")))
                                                        {
                                                            E = excursions.get(i);
                                                            System.out.println("exxcuriosn:: "+excursions.get(i));
                                                            break;
                                                        }
                                                }
                                                System.out.println("confirmed on: " + E.getId());


                                                ConnectionRequest con = new ConnectionRequest();
                                                con.setUrl("http://localhost/pidev/web/app_dev.php/excursion/unenroll?excursion="+E.getId()+"&user="+MyApplication.current_user.getId());
                                                con.setPost(false);

                                                con.addResponseListener(new ActionListener<NetworkEvent>() {
                                                    @Override
                                                    public void actionPerformed(NetworkEvent evt) {
                                                        JSONParser j = new JSONParser();

                                                        try {
                                                            Map<String, Object> exMap = j.parseJSON(new CharArrayReader((new String(con.getResponseData())).toCharArray()));

                                                            System.out.println("map: " + exMap);
                                                            int id = (int) Float.parseFloat(exMap.get("id").toString());

                                                            if(id == 0)
                                                            {
                                                                if(Dialog.show("Success", "You have successfully diskoined this excursion", "OK", null))
                                                                {
                                                                    ShowActiveExcursion ser = new ShowActiveExcursion();
                                                                    ser.getF().show();
                                                                }
                                                            }

                                                        } catch (IOException ex) {
                                                        }
                                                    }
                                                });
                                                NetworkManager.getInstance().addToQueueAndWait(con);
                                                break; 
                                            }
                                            else{
                                                break;
                                            }
                                        }
                                    }
                                });

                                container1.add(BorderLayout.CENTER, ml);
                            } 
                            catch (IOException ex) {}
                        }
                    }

                } 
                catch (IOException ex) {}
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);

        //All active Excursions
        ServiceExcursion ser = new ServiceExcursion();
        ArrayList<Excursion> excursions = new ArrayList<>();
        excursions = ser.getList2();
        
        f.getToolbar().addCommandToRightBar("back", null, (evt) ->{
            Acceuil A = new Acceuil();
            A.getF().show();
        });
        
        try {
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

                                                    try {
                                                        Map<String , Object> mapEnroll = j.parseJSON(new CharArrayReader((new String(con.getResponseData())).toCharArray()));
                                                        System.out.println("Map: "+ mapEnroll);
                                                        List<Map<String, Object>> list = (List<Map<String, Object>>) mapEnroll.get("root");
                                                       
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
                                                        else{
                                                             if(Dialog.show("Success", "you've successfully joined this excursion", "View My List", "Keep me here"))
                                                             {
                                                                ShowActiveExcursion s = new ShowActiveExcursion();
                                                                s.getF().show();
                                                             }
                                                        }  
                                                    } catch (IOException ex) {
                                                    }
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
                            {
                                btnEnroll.setEnabled(false);
                            }
                            
                            show.add(C);
                            show.show();
                            Container2.add(C);
                        }
                    });
                }
            } catch (IOException ex1) {
            }
       
        //old excursions
        Container Container3 = new Container(new BorderLayout());
        
        ArrayList<Excursion> oldExcursions = ser.getMyOldList();
        ArrayList<Map<String, Object>> data = new ArrayList<>();
        data.add(createListEntry(null, null, null));
        
        for(int i=0; i<oldExcursions.size(); i++)
        {
            if(oldExcursions.get(i).getId() == 0)
            {
                oldExist = false;
                break;   
            }
            else
              oldExist =true;      
        }
        
        
        if(oldExist)
        {
            for(Excursion E : oldExcursions)
            {
                try 
                {
                    EncodedImage placeholder = EncodedImage.create("/load.gif");

                    String url = "http://localhost/pidev/web/uploads/";
                    String imgString = E.getImg1();
                    String path = url+ imgString;

                    Image image = URLImage.createToStorage(placeholder, imgString , path, URLImage.RESIZE_SCALE);

                    Button btn = new Button("check");
                    data.add(createListEntry(E.getDestination(), E.getDate(), image));

                    DefaultListModel<Map<String, Object>> model = new DefaultListModel<>(data);
                    MultiList ml = new MultiList(model);

                    Container3.add(BorderLayout.CENTER, ml);

                } 
                catch (IOException ex) {}
            }
        }
        else{
            
            ImageViewer image = null;
            try {
                image = new ImageViewer(Image.createImage("/error_excursion.png"));
            } catch (IOException ex) {
            }

            image.setPreferredW(Display.getInstance().getDisplayWidth());


            image.getStyle().setMarginTop(100);

            Container3.add(BorderLayout.CENTER, image);
        }
        
        
        
        
        
        //Container3.add(new Label("Hello"));
        
        t.addTab("Excursion", icon1, Container2);
        t.addTab("Active", icon2, container1);
        t.addTab("Old",icon3, Container3);
        

        t.setScrollableY(true);
        f.setScrollableY(true);
        f.add(BorderLayout.CENTER, t);
       
       
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
