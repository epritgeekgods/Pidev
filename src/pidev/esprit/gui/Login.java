/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.esprit.gui;

import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.mycompany.myapp.MyApplication;
import java.io.IOException;

import pidev.esprit.Entity.User;
import pidev.esprit.Service.ServiceUser;

/**
 *
 * @author STA
 */
public class Login 
{
    Form f;

    public Login() 
    {
        f = new Form("Login", new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER));
        
        try {
            Image image = Image.createImage("/b5.jpg");
            f.setBgImage(image);
        } catch (IOException ex) {
        }
        
        f.getToolbar().addCommandToRightBar("back", null, (evt) ->{
            Acceuil A = new Acceuil();
            A.getF().show();
        });
        
        
        /*TextField user = new TextField();
        user.setHint("Username");
        TextField password = new TextField();
        password.setHint("Password");
        password.setConstraint(TextField.PASSWORD);
        
        Container C = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        C.add(user);
        C.add(password);
        
        Button signin = new Button("Sign In");
        C.add(signin);
        
        signin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ServiceUser ser = new ServiceUser();
                User utilisateur = new User();
                ser.login(user.getText(), password.getText());
                 utilisateur = (User) MyApplication.current_user;
                if(utilisateur.getId() > 0)
                {
                    System.out.println("id user connected: "+utilisateur.getId());
                   
                    Acceuil A = new Acceuil();
                    A.getF().show();
                    System.out.println("user authetificated");
                }
                else if (utilisateur.getId() == 0){
                    System.out.println("password incorrect");
                }
                else{
                    System.out.println("error login");
                }
            }
        });
        
        f.add(C);*/
        
        
        
        
        
        
        Button ok = new Button("OK");
        Button cancel = new Button("Cancel");

        Label loginLabel = new Label("Username", "Container");
        loginLabel.getAllStyles().setAlignment(Component.CENTER);

        Label passwordLabel = new Label("Password", "Container");
        passwordLabel.getAllStyles().setAlignment(Component.CENTER);

        TextField login = new TextField("", "Username", 20, TextArea.ANY);
        TextField passwords = new TextField("", "Password", 20, TextArea.PASSWORD);
        Style loginStyle = login.getAllStyles();
        Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
        loginStyle.setBorder(RoundBorder.create().
                rectangle(true).
                color(0xffffff).
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        loginStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
        loginStyle.setMargin(Component.BOTTOM, 3);
        Style passwordStyle = passwords.getAllStyles();
        passwordStyle.setBorder(RoundBorder.create().
                rectangle(true).
                color(0xffffff).
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));


        Container box = BoxLayout.encloseY(
                loginLabel,
                login,
                passwordLabel,
                passwords,
                    GridLayout.encloseIn(2, cancel, ok));

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ServiceUser ser = new ServiceUser();
                User utilisateur = new User();
                ser.login(login.getText(), passwords.getText());
                 utilisateur = (User) MyApplication.current_user;
                if(utilisateur.getId() > 0)
                {
                    System.out.println("id user connected: "+utilisateur.getId());
                   
                    Acceuil A = new Acceuil();
                    A.getF().show();
                    System.out.println("user authetificated");
                    LocalNotification n = new LocalNotification();
                    n.setId("demo-notification");
                    n.setAlertBody("It's time to take a break and look at me");
                    n.setAlertTitle("Break Time!");
                    n.setAlertSound("/notification_sound_beep-01a.mp3");
                        // alert sound file name must begin with notification_sound

                    Display.getInstance().scheduleLocalNotification(
                            n,
                            System.currentTimeMillis() + 10 * 1000, // fire date/time
                            LocalNotification.REPEAT_MINUTE  // Whether to repeat and what frequency
                    );
                }
                else if (utilisateur.getId() == 0){
                    System.out.println("password incorrect");
                    passwords.setText("");
                }
                else{
                    Dialog.show("Error", "Your entries are incorrect", "OK", null);
                    passwords.setText("");
                    login.setText("");
                }
            }
        });
        
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                passwords.setText("");
                login.setText("");
            }
        });
        
        Container layers = LayeredLayout.encloseIn(box);
        Style boxStyle = box.getUnselectedStyle();
        boxStyle.setBgTransparency(255);
        boxStyle.setBgColor(0xeeeeee);
        boxStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
        boxStyle.setPaddingUnit(Style.UNIT_TYPE_DIPS);
        boxStyle.setMargin(4, 3, 3, 3);
        boxStyle.setPadding(2, 2, 2, 2);

        f.add(BorderLayout.CENTER, layers);

        
    }

    
    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    
    
}
