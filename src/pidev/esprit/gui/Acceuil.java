/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.esprit.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import static com.codename1.ui.CN.RIGHT;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.MyApplication;
import java.io.IOException;
/**
 *
 * @author STA
 */
public class Acceuil {
    
    Form f;
    
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
            AffichageExcursion x = new AffichageExcursion();
            x.getF().show();});
        tb.addMaterialCommandToSideMenu("Flight" , FontImage.MATERIAL_FLIGHT, e ->{
            if(MyApplication.current_user.getId()> 0)
            {
                ShowActiveExcursion s = new ShowActiveExcursion();
                s.getF().show();
            }
            else{
                Login login = new Login();
                login.getF().show();
            }
            
        });
        tb.addMaterialCommandToSideMenu("Event" , FontImage.MATERIAL_EVENT, e ->{
            ShowOldExcursion old = new ShowOldExcursion();
            old.getF().show();
        });
        tb.addMaterialCommandToSideMenu("Hotel" , FontImage.MATERIAL_HOTEL, e ->{});
        
       
        String autString = MyApplication.current_user.getUsername();
        System.out.println("user id: "+ MyApplication.current_user.getId());
        if(MyApplication.current_user.getId() > 0)
        {
             tb.addMaterialCommandToSideMenu("Logout" , FontImage.MATERIAL_POWER_SETTINGS_NEW, e ->{
                 Logout logout = new Logout();
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
        
        
        f.add(about);
        f.add(services);
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    
}
