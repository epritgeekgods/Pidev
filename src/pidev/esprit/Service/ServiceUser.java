/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.esprit.Service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.MyApplication;
import java.io.IOException;
import java.util.Map;
import pidev.esprit.Entity.User;

/**
 *
 * @author STA
 */
public class ServiceUser 
{
    public void login(String username, String password)
    {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev/web/app_dev.php/logins?username="+username+"&password="+password);
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                System.out.println("establishing connection");
                String json = new String(con.getResponseData());
                JSONParser j = new JSONParser();
                Map<String, Object> u;
                User user = new User();
                try {
                    u = j.parseJSON(new CharArrayReader(json.toCharArray()));
                    float id = Float.parseFloat(u.get("id").toString());
                    
                    if(id > 0)
                    {
                        user.setId((int) id);
                        user.setEmail(u.get("email").toString());
                        
                        if( u.get("nom") != null)
                           user.setNom( u.get("nom").toString() ); 
                        else
                        {
                            user.setNom("");
                            System.out.println("empty last name");
                        }
                            
                        
                        if(u.get("prenom") != null)
                           user.setPrenom(u.get("prenom").toString());
                        else
                        {
                            user.setPrenom(""); 
                            System.out.println("empty name");
                        }
                           
                        
                        user.setUsername(u.get("username").toString());
                        System.out.println("user: "+username);
                        Dialog.show("Success","Hello Mr. "+username+"", null, "OK");

                    }
                    else if((int) id == 0)
                    {
                        //password invalid
                        user.setId(0);
                        //System.out.println("invalid password");
                        Dialog.show("Error","Hello Mr. "+username+" your password is incorrect", null, "OK");
                        
                    }
                    else
                    {
                        // (int)id = -1
                        //username invalid
                        user.setId(-1); 
                        Dialog.show("Error","There is no data registred for this username", null, "OK");
                        System.out.println("invalid login");
                    }
                    MyApplication.current_user = user;
                        
                    //return user;
                } catch (IOException ex) {
                }     
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);  
    }
            
    
}
