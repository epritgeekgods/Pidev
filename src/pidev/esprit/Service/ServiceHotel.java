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
import pidev.esprit.Entity.Excursion;
import pidev.esprit.Entity.Hotel;
import pidev.esprit.Entity.Reservationhotel;

/**
 *
 * @author ASUS
 */
public class ServiceHotel {
     public ArrayList<Hotel> getList(String json)
    {
        ArrayList<Hotel> listhotel = new ArrayList<>();
        JSONParser j = new JSONParser();
        try {
            Map<String, Object> exMap = j.parseJSON(new CharArrayReader(json.toCharArray()));
            if(exMap.get("root")==null)
            {
                Hotel h=new Hotel();
                listhotel.add(h);
                System.out.println("nothing to show");
            }
            else{
            List<Map<String, Object>> list = (List<Map<String, Object>>) exMap.get("root");

            for(Map<String , Object> obj : list)
            {
                Hotel e=new Hotel();
                float id = Float.parseFloat(obj.get("idhotel").toString());
                e.setIdHotel((int) id);
                e.setNbre_de_chambre_despo((int) Float.parseFloat(obj.get("nbreDeChambreDespo").toString()));
                e.setAdresseHotel(obj.get("adressehotel").toString());
                e.setNameHotel(obj.get("namehotel").toString());
                float etoile = Float.parseFloat(obj.get("typehotel").toString());
                e.setNb_etoile((int) etoile);
                
                e.setImage(obj.get("imghotel").toString());
                e.setPrix_double((int) Float.parseFloat(obj.get("prixDouble").toString()));
                e.setPrix_double((int) Float.parseFloat(obj.get("prixIndiv").toString()));
                //System.out.println(e);
                listhotel.add(e);
            }
        }} catch (IOException ex) {
            System.out.println("error "+ex.getMessage());
        }
        return listhotel;
    }
     ArrayList<Hotel> hotels = new ArrayList<>();
      public ArrayList<Hotel> gethotelByadresse(String adresse)
    {
        
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev/web/app_dev.php/hotel/searchm?adresse="+adresse);
        con.setPost(false);
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceHotel sh=new ServiceHotel();
                hotels=sh.getList(new String(con.getResponseData()));
                
                
            }});
        NetworkManager.getInstance().addToQueueAndWait(con);
        return hotels;
    }
   
    public ArrayList<Reservationhotel> getListReservation(String json)
    {
        ArrayList<Reservationhotel> listreserv = new ArrayList<>();
        JSONParser j = new JSONParser();
        try {
            Map<String, Object> exMap = j.parseJSON(new CharArrayReader(json.toCharArray()));
            if(exMap.get("root")==null)
            {
                Reservationhotel h=new Reservationhotel();
                listreserv.add(h);
                System.out.println("nothing to show");
            }
            else{
            List<Map<String, Object>> list = (List<Map<String, Object>>) exMap.get("root");

            for(Map<String , Object> obj : list)
            {
                Reservationhotel e=new Reservationhotel();
                float id = Float.parseFloat(obj.get("idreservation").toString());
                e.setIdReservation((int) id);
                 float nbdiv = Float.parseFloat(obj.get("nbreIndiv").toString());
                e.setNbre_indiv((int) nbdiv);
                 float nbdoub = Float.parseFloat(obj.get("nbreDouble").toString());
                e.setNbre_double((int) nbdoub);
                float prix = Float.parseFloat(obj.get("prix").toString());
                e.setPrix((int)prix);
                 float idhotel = Float.parseFloat(obj.get("idhotel").toString());
                e.setIdHotel((int)idhotel);
                
                //System.out.println(e);
                listreserv.add(e);
            }
        }} catch (IOException ex) {
            System.out.println("error "+ex.getMessage());
        }
        return listreserv;
    }
     ArrayList<Reservationhotel> listreservs = new ArrayList<>();
       public ArrayList<Reservationhotel> getreservation()
    {
        
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev/web/app_dev.php/hotel/readreservm?user="+MyApplication.current_user.getId());
        con.setPost(false);
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceHotel sh=new ServiceHotel();
                listreservs=sh.getListReservation(new String(con.getResponseData()));
                
                
            }});
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listreservs;
    }
       public ArrayList<Hotel> getListByid(String json)
    {
        ArrayList<Hotel> listhotel = new ArrayList<>();
        JSONParser j = new JSONParser();
        try {
            Map<String, Object>obj = j.parseJSON(new CharArrayReader(json.toCharArray()));
            if(obj.get("root")==null)
            {
                Hotel h=new Hotel();
                listhotel.add(h);
                System.out.println("nothing to show");
            }
            else{
                
          

            for(Iterator i=obj.keySet().iterator(); i.hasNext();){
                Hotel e=new Hotel();
                System.out.println("ici:"+obj.keySet());
               float id = Float.parseFloat(obj.get("idhotel").toString());
                e.setIdHotel((int) id);
                e.setNbre_de_chambre_despo((int) Float.parseFloat(obj.get("nbreDeChambreDespo").toString()));
                e.setAdresseHotel(obj.get("adressehotel").toString());
                e.setNameHotel(obj.get("namehotel").toString());
                float etoile = Float.parseFloat(obj.get("typehotel").toString());
                e.setNb_etoile((int) etoile);
                
                e.setImage(obj.get("imghotel").toString());
                e.setPrix_double((int) Float.parseFloat(obj.get("prixDouble").toString()));
                e.setPrix_double((int) Float.parseFloat(obj.get("prixIndiv").toString()));
                //System.out.println(e);
                listhotel.add(e);
            }
        }} catch (IOException ex) {
            System.out.println("error "+ex.getMessage());
        }
        return listhotel;
    }
       public ArrayList<Hotel> gethotelByid(int id)
    {
        
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev/web/app_dev.php/hotel/readhm?idhotel="+id);
        con.setPost(false);
        
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceHotel sh=new ServiceHotel();
                hotels=sh.getList(new String(con.getResponseData()));
                
                
            }});
        NetworkManager.getInstance().addToQueueAndWait(con);
        return hotels;
    }
   
}
