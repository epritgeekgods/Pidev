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
import pidev.esprit.Entity.Excursion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author STA
 */
public class ServiceExcursion 
{    
    public ArrayList<Excursion> getList(String json)
    {
        ArrayList<Excursion> listExcursions = new ArrayList<>();
        JSONParser j = new JSONParser();
        try {
            Map<String, Object> exMap = j.parseJSON(new CharArrayReader(json.toCharArray()));
            
            List<Map<String, Object>> list = (List<Map<String, Object>>) exMap.get("root");

            for(Map<String , Object> obj : list)
            {
                Excursion e = new Excursion();
                float id = Float.parseFloat(obj.get("idrando").toString());
                e.setId((int) id);
                e.setCapacite((int) Float.parseFloat(obj.get("capacite").toString()));
                e.setDate(obj.get("daterando").toString());
                e.setNbre((int) Float.parseFloat(obj.get("nbreclient").toString()));
                e.setDestination(obj.get("destination").toString());
                e.setPrix(Float.parseFloat(obj.get("prixpersonne").toString()));
                e.setProgramme(obj.get("programme").toString());
                e.setImg1(obj.get("imgurl1").toString());
                e.setImg2(obj.get("imgurl2").toString());
                e.setImg3(obj.get("imgurl3").toString());
                e.setGooglemap(obj.get("googlemaps").toString());
                
                //System.out.println(e);
                listExcursions.add(e);
            }
        } catch (IOException ex) {
            System.out.println("error "+ex.getMessage());
        }
        return listExcursions;
    }
    
    
    ArrayList<Excursion> listExcursion = new ArrayList<>();
    
    public ArrayList<Excursion> getList2()
    {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev/web/app_dev.php/excursion/mobile");
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceExcursion ser = new ServiceExcursion();
                listExcursion = ser.getList(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listExcursion;
    }
    
    public ArrayList<Excursion> getMyOldList()
    {
        ArrayList<Excursion> myOldListExcursions = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev/web/app_dev.php/excursion/oldreservation?id="+MyApplication.current_user.getId());
        con.setPost(false);
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                JSONParser j = new JSONParser();
                try {
                    Map<String, Object> myOldMap = j.parseJSON(new CharArrayReader((new String(con.getResponseData())).toCharArray()));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) myOldMap.get("root");
                    System.out.println("map: "+ myOldMap);
                    if(myOldMap.get("root") == null)
                    {
                        Excursion e = new Excursion();
                         myOldListExcursions.add(e);
                         System.out.println("nothing to show");
                    }
                    else
                    {
                        for(Map<String, Object> obj: list)
                        {
                            Excursion e = new Excursion();
                            String id = obj.get("idrando").toString();
                            if(id != null)
                                e.setId((int) Float.parseFloat(id));

                            String capacite = obj.get("capacite").toString();
                            if(capacite != null)
                                e.setCapacite((int) Float.parseFloat(capacite));

                            String date = obj.get("daterando").toString();
                            if(date != null)
                                e.setDate(date);

                            String destination = obj.get("destination").toString();
                            if(destination != null)
                                e.setDestination(destination);

                            String prixpersonne = obj.get("prixpersonne").toString();
                            if(prixpersonne != null)
                                e.setPrix(Float.parseFloat(prixpersonne));

                           String programme = obj.get("programme").toString();
                           if(programme != null)
                               e.setProgramme(programme);

                           String googlemaps = obj.get("googlemaps").toString();
                           if(googlemaps != null)
                               e.setGooglemap(googlemaps);

                           String imgurl1 = obj.get("imgurl1").toString();
                           if(imgurl1 != null)
                               e.setImg1(imgurl1);

                           String imgurl2 = obj.get("imgurl2").toString();
                           if(imgurl2 != null)
                               e.setImg2(imgurl2);

                           String imgurl3 = obj.get("imgurl3").toString();
                           if(imgurl3 != null)
                               e.setImg3(imgurl3);

                            myOldListExcursions.add(e);

                        }
                    }
                    
                } catch (IOException ex) {
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        
        return myOldListExcursions;
    }
    
    
    public ArrayList<Excursion> getMySearchList(String search)
    {
        ArrayList<Excursion> myOldListExcursions = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev/web/app_dev.php/excursion/mobilesearch?search="+search);
        con.setPost(false);
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                JSONParser j = new JSONParser();
                try {
                    Map<String, Object> myOldMap = j.parseJSON(new CharArrayReader((new String(con.getResponseData())).toCharArray()));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) myOldMap.get("root");
                    System.out.println("map: "+ myOldMap);
                    if(myOldMap.get("root") == null)
                    {
                        Excursion e = new Excursion();
                         myOldListExcursions.add(e);
                         System.out.println("nothing to show");
                    }
                    else
                    {
                        for(Map<String, Object> obj: list)
                        {
                            Excursion e = new Excursion();
                            String id = obj.get("idrando").toString();
                            if(id != null)
                                e.setId((int) Float.parseFloat(id));

                            String capacite = obj.get("capacite").toString();
                            if(capacite != null)
                                e.setCapacite((int) Float.parseFloat(capacite));

                            String date = obj.get("daterando").toString();
                            if(date != null)
                                e.setDate(date);

                            String destination = obj.get("destination").toString();
                            if(destination != null)
                                e.setDestination(destination);

                            String prixpersonne = obj.get("prixpersonne").toString();
                            if(prixpersonne != null)
                                e.setPrix(Float.parseFloat(prixpersonne));

                           String programme = obj.get("programme").toString();
                           if(programme != null)
                               e.setProgramme(programme);

                           String googlemaps = obj.get("googlemaps").toString();
                           if(googlemaps != null)
                               e.setGooglemap(googlemaps);

                           String imgurl1 = obj.get("imgurl1").toString();
                           if(imgurl1 != null)
                               e.setImg1(imgurl1);

                           String imgurl2 = obj.get("imgurl2").toString();
                           if(imgurl2 != null)
                               e.setImg2(imgurl2);

                           String imgurl3 = obj.get("imgurl3").toString();
                           if(imgurl3 != null)
                               e.setImg3(imgurl3);

                            myOldListExcursions.add(e);

                        }
                    }
                    
                } catch (IOException ex) {
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        
        return myOldListExcursions;
    }
    
    

}
