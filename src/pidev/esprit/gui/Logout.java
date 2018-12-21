/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.esprit.gui;

import com.mycompany.myapp.MyApplication;
import pidev.esprit.Entity.User;

/**
 *
 * @author STA
 */
public class Logout 
{

    public Logout() {
        MyApplication.current_user = new User();
        Acceuil A = new Acceuil();
        A.getF().show();
    }
    
    
}
