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
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import pidev.esprit.Entity.Event;
import pidev.esprit.gui.GuiActive;
import pidev.esprit.gui.GuiEvent;

/**
 *
 * @author STA
 */
public class ServiceEvent 
{
    public ArrayList<Event> getAll()
    {
        ArrayList<Event> events = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev/web/app_dev.php/event/list");
        con.setPost(false);
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                String json = new String(con.getResponseData());
                JSONParser j = new JSONParser();
                
                try 
                {
                    Map<String, Object> evMap = j.parseJSON(new CharArrayReader(json.toCharArray()));
                    List<Map<String, Object>> list = ( List<Map<String, Object>>) evMap.get("root");
                    
                    for(Map<String, Object> obj: list)
                    {
                        Event e = new Event();
                        e.setId((int) Float.parseFloat(obj.get("idevent").toString()));
                        e.setDate(obj.get("dateevent").toString());
                        e.setLieu(obj.get("lieuevent").toString());
                        e.setNom(obj.get("nomevent").toString());
                        e.setProgramme(obj.get("description").toString());
                        e.setImg(obj.get("eventImg").toString());
                        e.setNbre((int) Float.parseFloat(obj.get("nbrepersonnes").toString()));
                        e.setCapacite((int) Float.parseFloat(obj.get("capevent").toString()));
                        e.setPrix(Float.parseFloat(obj.get("ticketprice").toString()));
                        
                        events.add(e);
                    }
                    
                } catch (IOException ex) {}
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return events;
    }
    
    public void join(int user, int event)
    {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev/web/app_dev.php/event/join?user="+user+"&event="+event);
        System.out.println("user: "+ user + " event: "+ event);
        System.out.println("establishing connection");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                String json = new String(con.getResponseData());
                JSONParser j = new JSONParser();
                System.out.println("parsing json");
                try
                {
                    System.out.println("entering to try catch");
                    Map<String, Object> joinMap = j.parseJSON(new CharArrayReader(json.toCharArray()));
                    
                    int id =(int) Float.parseFloat(joinMap.get("id").toString());
                    
                    switch (id) {
                        case -1:
                            Dialog.show("Error", "You have already joined this event", "OK", null);
                            break;
                        case 0:
                            Dialog.show("Error", "Sorry this event is out of places", "OK", null);
                            break;
                        default:
                            if(Dialog.show("Success", "Now you have successfully joined this event", "OK", null))
                            {
                                GuiEvent ev = new GuiEvent();
                                ev.getF().show();
                            }
                            break;
                    }         
                } catch (IOException ex) {}
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
    public void disJoin(int user, int event)
    {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev/web/app_dev.php/event/disjoin?user="+user+"&event="+event);
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                String json = new String(con.getResponseData());
                JSONParser j = new JSONParser();
                try
                {
                    Map<String, Object> joinMap = j.parseJSON(new CharArrayReader(json.toCharArray()));
                    if(Dialog.show("Success", "Now you have successfully disjoined this event", "OK", null))
                    {
                        GuiEvent ev = new GuiEvent();
                        ev.getF().show();
                    }
                    
                } catch (IOException ex) {}
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    
    public ArrayList<Event> myList(int user)
    {
        ArrayList<Event> events = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev/web/app_dev.php/event/mylist?user="+user);
        con.setPost(false);
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                String json = new String(con.getResponseData());
                JSONParser j = new JSONParser();
                
                try 
                {
                    Map<String, Object> evMap = j.parseJSON(new CharArrayReader(json.toCharArray()));
                    
                    if(evMap.get("root") != null)
                    {
                        List<Map<String, Object>> list = ( List<Map<String, Object>>) evMap.get("root");
                        for(Map<String, Object> obj: list)
                        {
                            Event e = new Event();

                            int id = (int) Float.parseFloat(obj.get("idevent").toString());

                            if(id > 0)
                            {
                                e.setId(id);
                                e.setDate(obj.get("dateevent").toString());
                                e.setLieu(obj.get("lieuevent").toString());
                                e.setNom(obj.get("nomevent").toString());
                                e.setProgramme(obj.get("description").toString());
                                e.setImg(obj.get("eventImg").toString());
                                e.setNbre((int) Float.parseFloat(obj.get("nbrepersonnes").toString()));
                                e.setCapacite((int) Float.parseFloat(obj.get("capevent").toString()));
                                e.setPrix(Float.parseFloat(obj.get("ticketprice").toString()));
                            }
                            events.add(e);
                        }
                    }
                    else
                    {
                        Event e = new Event(); 
                        events.add(e);
                    }
                    
                    
                } catch (IOException ex) {}
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return events;
    }
}
