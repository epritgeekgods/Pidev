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

import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.MyApplication;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import pidev.esprit.Entity.Reservationvoyage;

import pidev.esprit.Entity.Voyage;


/**
 *
 * @author Sirine
 */
public class ServiceVoyage {
    
    
     public ArrayList<Voyage> getList(String json)
    {
        ArrayList<Voyage> listVoyage = new ArrayList<>();
        JSONParser j = new JSONParser();
        try {
            Map<String, Object> exMap = j.parseJSON(new CharArrayReader(json.toCharArray()));
              if(exMap.get("root") == null){
                 // System.out.println(exMap);
                  System.out.println("no thing to show for voyages");
              
              }
              else{
            List<Map<String, Object>> list = (List<Map<String, Object>>) exMap.get("root");

            System.out.println(list);
            
            for(Map<String , Object> obj : list)
           
                    {
                Voyage e = new Voyage();
                float id = Float.parseFloat(obj.get("idvoyage").toString());
                e.setIdVoyage((int) id);
                e.setCompagnie(obj.get("compagnie").toString());
                e.setNb_place_dispo((int) Float.parseFloat(obj.get("nbPlaceDispo").toString()));
                e.setDestinationVoyage(obj.get("destinationvoyage").toString());
                e.setPrix((int) Float.parseFloat(obj.get("prix").toString()));
                e.setDepartVoyage(obj.get("departvoyage").toString());
                e.setImage(obj.get("image").toString());
                e.setDateVoyageAller(obj.get("datevoyagealler").toString());
                e.setDateVoyageRetour(obj.get("datevoyageretour").toString());
                e.setHdepartVoyageAller(obj.get("hdepartvoyagealler").toString());
                e.setHarriveeVoyageAller(obj.get("harriveevoyagealler").toString());
                e.setHarriveeVoyageRetour(obj.get("harriveevoyageretour").toString());
                e.setHdepartVoyageRetour(obj.get("hdepartvoyageretour").toString());

                
                listVoyage.add(e);}
            }
        } catch (IOException ex) {
            System.out.println("error "+ex.getMessage());
        }
        return listVoyage;
    }
       ArrayList<Voyage> listVoyages = new ArrayList<>();
    
     public ArrayList<Voyage> getList2()
    {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev/web/app_dev.php/voyage/Mread");
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceVoyage ser = new ServiceVoyage();
                listVoyages = ser.getList(new String(con.getResponseData()));
     
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listVoyages;
    }
      public ArrayList<Voyage> getListbyid(String json)
    {
        ArrayList<Voyage> listVoyage = new ArrayList<>();
        JSONParser j = new JSONParser();
        try {
            Map<String, Object> exMap = j.parseJSON(new CharArrayReader(json.toCharArray()));
            if(exMap.get("root")==null){
            Voyage v=new Voyage();
            listVoyage.add(v);
                System.out.println("nothing to show for voyage de reservation");
            
            }
            else{
            for ( Iterator i = exMap.keySet().iterator(); i.hasNext();)
                    {
                        System.out.println(exMap.keySet());
                Voyage e = new Voyage();
                float id = Float.parseFloat(exMap.get("idvoyage").toString());
               e.setIdVoyage((int) id);
                e.setCompagnie(exMap.get("compagnie").toString());
                e.setNb_place_dispo((int) Float.parseFloat(exMap.get("nbPlaceDispo").toString()));
                e.setDestinationVoyage(exMap.get("destinationvoyage").toString());
                e.setPrix((int) Float.parseFloat(exMap.get("prix").toString()));
                e.setDepartVoyage(exMap.get("departvoyage").toString());
                e.setImage(exMap.get("image").toString());
                e.setDateVoyageAller(exMap.get("datevoyagealler").toString());
                e.setDateVoyageRetour(exMap.get("datevoyageretour").toString());
                e.setHdepartVoyageAller(exMap.get("hdepartvoyagealler").toString());
                e.setHarriveeVoyageAller(exMap.get("harriveevoyagealler").toString());
                e.setHarriveeVoyageRetour(exMap.get("harriveevoyageretour").toString());
                e.setHdepartVoyageRetour(exMap.get("hdepartvoyageretour").toString());

                
                System.out.println(e);
                listVoyage.add(e);//}
                
            }
        } }catch (IOException ex) {
            System.out.println("error       "+ex.getMessage());
        }
        return listVoyage;
    }
      
    public ArrayList<Voyage> getList2id(int id)
    {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev/web/app_dev.php/voyage/Mreadby?idvoyage="+id);
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceVoyage ser = new ServiceVoyage();
                listVoyages = ser.getList(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listVoyages;
    }
     
    public ArrayList<Reservationvoyage> getListreserv(String json)
    {
        ArrayList<Reservationvoyage> listReserv = new ArrayList<>();
        JSONParser j = new JSONParser();
        try {
            Map<String, Object> exMap = j.parseJSON(new CharArrayReader(json.toCharArray()));

           // System.out.println(list);
            if(exMap.get("root") == null)
            {
                Reservationvoyage e = new Reservationvoyage();
                listReserv.add(e);
                System.out.println("nothing to show");
            }
            else
            {
                List<Map<String, Object>> list = (List<Map<String, Object>>) exMap.get("root");
                for(Map<String , Object> obj : list)
                {
                    Reservationvoyage e = new Reservationvoyage();
                    float idreservation = Float.parseFloat(obj.get("idreservation").toString());
                    e.setIdReservation((int) idreservation);
                    float idvoyage = Float.parseFloat(obj.get("idvoyage").toString());
                    e.setIdVoyage((int) idvoyage);
                    float iduser = Float.parseFloat(obj.get("id").toString());
                    e.setId((int) iduser);
                    if(obj.get("nom")==null)
                         e.setNom("null");
                    else
                        e.setNom(obj.get("nom").toString());
                    
                    if(obj.get("prenom")==null)
                         e.setPrenom("null");
                    else
                        e.setPrenom(obj.get("prenom").toString());
                        
                        
                    e.setNbre_place_reserv((int) Float.parseFloat(obj.get("nbrePlaceReserv").toString()));
                    e.setEmail(obj.get("mail").toString());
                    float prix = Float.parseFloat(obj.get("prix").toString());
                    e.setPrix((int) prix);

                    //System.out.println(e);
                    listReserv.add(e);
                }
            }
        } 
        catch (IOException ex) 
        {
            System.out.println("error "+ex.getMessage());
        }
        
        return listReserv;
    }
    
    ArrayList<Reservationvoyage> listreservations = new ArrayList<>();

    public ArrayList<Reservationvoyage> getListreserv2()
    {
        ConnectionRequest con = new ConnectionRequest();
        int idUser = MyApplication.current_user.getId();
        con.setUrl("http://localhost/pidev/web/app_dev.php/voyage/Mreservation?user="+idUser);
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceVoyage ser = new ServiceVoyage();
                listreservations = ser.getListreserv(new String(con.getResponseData()));
     
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listreservations;
    }
    
    
    public ArrayList<Voyage> getsearch(String dest)
    {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev/web/app_dev.php/voyage/Msearch?destination="+dest);
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceVoyage ser = new ServiceVoyage();
                listVoyages = ser.getList(new String(con.getResponseData()));
     
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listVoyages;
    }
    
    
    public ArrayList<Reservationvoyage> getListreservactive()
    {
        ConnectionRequest con = new ConnectionRequest();
        int idUser = MyApplication.current_user.getId();
        con.setUrl("http://localhost/pidev/web/app_dev.php/voyage/Mactive?user="+idUser);
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceVoyage ser = new ServiceVoyage();
                listreservations = ser.getListreserv(new String(con.getResponseData()));
     
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listreservations;
    }


    
  
}
