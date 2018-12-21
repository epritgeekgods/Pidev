package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import static com.codename1.ui.CN.*;

import com.codename1.ui.Form;
import com.codename1.ui.Dialog;

import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import java.io.IOException;
import pidev.esprit.Entity.User;
import pidev.esprit.gui.Acceuil;
import pidev.esprit.gui.AffichageExcursion;
import pidev.esprit.gui.Login;

public class MyApplication {

    private Form current;
    public Resources theme;
    public static User current_user = new User("","","","","",0);

    
    public void init(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if(err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });

        
    }
    
    public void start() {
        if(current != null){
            current.show();
            return; 
        }   
        Acceuil A = new Acceuil();
        A.getF().show();
    }

    public void stop() {
        current = getCurrentForm();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = getCurrentForm();
        }
        
       
    }
    
    public void destroy() {
    }

}
