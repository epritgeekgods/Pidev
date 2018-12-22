/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.esprit.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.RIGHT;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.mycompany.myapp.MyApplication;
import java.io.IOException;
import pidev.esprit.Entity.User;
/**
 *
 * @author STA
 */
public class Acceuil {
    
    Form f;
    public static String path;
    
    public Acceuil()
    {
        f = new Form("   Holidays Now");
        Toolbar tb = f.getToolbar();
        
        Image image = null;
        try {
             image = Image.createImage("/holidaysNow.png");
        } catch (IOException ex) {}
        Container topBar = BorderLayout.east(new Label(image));
        topBar.add(BorderLayout.SOUTH,new Label("Welcome","DodemenuTagline"));
        topBar.setUIID("SideCommand");
        
        tb.getStyle().setBgColor(0xf2994a);
        tb.getStyle().setBgTransparency(255);
        //tb.getStyle().set
        tb.addComponentToSideMenu(topBar);
        
        tb.addMaterialCommandToSideMenu("Home" , FontImage.MATERIAL_HOME, e ->{
            f.show();
        });
        
        
        tb.addMaterialCommandToSideMenu("Excursion" , FontImage.MATERIAL_HIGHLIGHT, e ->{
            if(MyApplication.current_user.getId()> 0)
            {
                ShowActiveExcursion s = new ShowActiveExcursion();
                s.getF().show();
            }
            else{
                path = "excursion";
                System.out.println("path: excursion");
                Login login = new Login();
                login.getF().show();
            }
        });
        
        tb.addMaterialCommandToSideMenu("Flight" , FontImage.MATERIAL_FLIGHT, e ->{ 
        });
        
        tb.addMaterialCommandToSideMenu("Event" , FontImage.MATERIAL_EVENT, e ->{
        });
        
        tb.addMaterialCommandToSideMenu("Hotel" , FontImage.MATERIAL_HOTEL, e ->{});
        
        if(MyApplication.current_user.getId() > 0)
        {
             tb.addMaterialCommandToSideMenu("Logout" , FontImage.MATERIAL_POWER_SETTINGS_NEW, e ->{
                MyApplication.current_user = new User();
                Acceuil A = new Acceuil();
                A.getF().show();
            });
        }
        else
        {
            tb.addMaterialCommandToSideMenu("Login" , FontImage.MATERIAL_ACCOUNT_CIRCLE, e ->{
            Login login = new Login();
            login.getF().show();
            });
        }
        
        tb.addMaterialCommandToSideMenu("About" , FontImage.MATERIAL_INFO, e ->{});

        
       Container C = new Container();
       ImageViewer img = null;
       
       int deviceWidth = Display.getInstance().getDisplayWidth();
        System.out.println("width: "+ deviceWidth);
        try {
            img = new ImageViewer(Image.createImage("/cover.jpg"));
            //Image imag =  Image.createImage("/cover.jpg");
            img.setPreferredW(deviceWidth);
            img.setPreferredH(deviceWidth);
            f.add(img);
        } catch (IOException ex) {
        }
       
        Container about = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Label title = new Label("About Us");
        title.getStyle().setMargin(Component.TOP, 100);
        int center = (int) (deviceWidth*0.35);
        title.setUIID("title");
        title.getStyle().setMargin(Component.LEFT, center);
        about.add(title);
        
       
        Label welcome = new Label("Welcome To HolidaysNow");
        welcome.setUIID("subtitle");
        welcome.getStyle().setMargin(Component.LEFT, 30);
        about.add(welcome);
        
        SpanLabel t = new SpanLabel("GIVE TIME A BREAK AND TAKE A LOOK ARROUND YOU");
        t.setUIID("subtitle2");
        t.getStyle().setMargin(Component.LEFT, 15);
        t.getStyle().setMargin(Component.TOP, 20);
        about.add(t);
        
        SpanLabel text = new SpanLabel("Holidays Now is a divertisment provider that "
                + "arranges you as many services as possible so you can access them "
                + "in a glimpse of an eye. We are aware of the fatigue and timeless "
                + "life you're living and that's why we are here, to make everything easier. .");
        text.getStyle().setMargin(Component.LEFT, 20);
        text.getStyle().setMargin(Component.TOP, 40);
        text.getStyle().setAlignment(RIGHT);
        about.add(text);
        
        ImageViewer aboutImg = null;
       
        try {
            aboutImg = new ImageViewer(Image.createImage("/about.jpg"));
            aboutImg.setPreferredW((int)(deviceWidth*0.75));
            aboutImg.setPreferredH((int)(deviceWidth*0.75));
        } catch (IOException ex) {
        }
        
        aboutImg.setUIID("imageborder");
        //aboutImg.getStyle().setBorder(border);
        about.add(aboutImg);
        
        
        Container services = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Label titleS = new Label("Our Services");
        titleS.getStyle().setMargin(Component.TOP, 100);
        int centerS = (int) (deviceWidth*0.3);
        titleS.setUIID("title");
        titleS.getStyle().setMargin(Component.LEFT, centerS);
        services.add(titleS);
        
        Container images = new Container(new BoxLayout(BoxLayout.Y_AXIS));
         
        try {
            ImageViewer imgv1 = new ImageViewer(Image.createImage("/i1.JPG"));
            //Image country = Image.createImage("/country.JPG");
            imgv1.setPreferredW(Display.getInstance().getDisplayWidth());
            imgv1.setPreferredH(Display.getInstance().getDisplayWidth());
            Container firstimg = new Container();
            imgv1.setUIID("image");
            Label des = new Label("DISCOVER YOUR COUNTRY");
            SpanLabel wr = new SpanLabel("Access nearby excursions with a click and be the first to go to whatever excursion you want.");
            wr.getStyle().setMargin(Component.TOP, 10);
            des.setUIID("title2");
            wr.setUIID("paragraph");
            
            firstimg.add(imgv1);
            firstimg.add(des);
            firstimg.add(wr);
            
            Button fisrtbtn = new Button();
            fisrtbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    System.out.println("frst");
                }
            });
            firstimg.setLeadComponent(fisrtbtn);
            //firstimg.getStyle().setMargin(Component.BOTTOM, 80);
            images.add(firstimg);
            
            ImageViewer imgv2 = new ImageViewer(Image.createImage("/i2.JPG"));
            //Image country = Image.createImage("/country.JPG");
            imgv2.setPreferredW(Display.getInstance().getDisplayWidth());
            imgv2.setPreferredH(Display.getInstance().getDisplayWidth());
            Container secondeimg = new Container();
            imgv2.setUIID("image");
            Label des2 = new Label("GO PLACES");
            SpanLabel wr2 = new SpanLabel("Have a look at our unlimited destinations and be one of the special people to reach out to unique places.");
            wr2.getStyle().setMargin(Component.TOP, 10);
            des2.setUIID("title2");
            wr2.setUIID("paragraph");
            
            secondeimg.add(imgv2);
            secondeimg.add(des2);
            secondeimg.add(wr2);
            Button secodenbtn = new Button();
            secodenbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    System.out.println("seconde");
                }
            });
            
            secondeimg.setLeadComponent(secodenbtn);
            images.add(secondeimg);
            
            ImageViewer imgv3 = new ImageViewer(Image.createImage("/i3.JPG"));
            //Image country = Image.createImage("/country.JPG");
            imgv3.setPreferredW(Display.getInstance().getDisplayWidth());
            imgv3.setPreferredH(Display.getInstance().getDisplayWidth());
            Container thirdimg = new Container();
            imgv2.setUIID("image");
            Label des3 = new Label("ACCESS FANCY HOTELS");
            SpanLabel wr3 = new SpanLabel("Exclusive can be ambiguous but when it comes to HolidaysNow hotels, it means price, perks, privacy, pampering, protection and pools.");
            wr3.getStyle().setMargin(Component.TOP, 10);
            des3.setUIID("title2");
            wr3.setUIID("paragraph");
            
            Button thirdbtn = new Button();
            
            thirdbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                                       
                }
            });
            
            thirdimg.setLeadComponent(thirdbtn);
            thirdimg.add(imgv3);
            thirdimg.add(des3);
            thirdimg.add(wr3);
            images.add(thirdimg);
            
            
        } catch (IOException ex) {
        }
        
        f.add(about);
        f.add(services);
        f.add(images);
        
        
        
        Container footer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container content = new Container(new GridLayout(1, 2));

        footer.getStyle().setMargin(Component.TOP, 100);
        //ImageViewer i = new ImageViewer()
        Style s = UIManager.getInstance().getComponentStyle("Tab");
        
        FontImage call_img = FontImage.createMaterial(FontImage.MATERIAL_PHONE, s);
        FontImage mail_img = FontImage.createMaterial(FontImage.MATERIAL_EMAIL, s);
        
        Label call = new Label("Call Us", call_img);
        Label email = new Label("Send Message", mail_img);
        
        call.getStyle().setMargin(Component.TOP, 100);
        email.getStyle().setMargin(Component.TOP, 100);
                
        call.setUIID("details");
        email.setUIID("details");
        content.add(call);
        content.add(email);
        footer.add(content);
        f.add(footer);
        
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    
}
