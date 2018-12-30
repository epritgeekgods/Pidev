/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.esprit.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.mycompany.myapp.MyApplication;
import java.io.IOException;
import java.util.ArrayList;
import pidev.esprit.Entity.Event;
import pidev.esprit.Service.ServiceEvent;

/**
 *
 * @author STA
 */
public class GuiEvent {
    Form f;

    public GuiEvent() 
    {
        f = new Form("Events");
        Style s = UIManager.getInstance().getComponentStyle("Tab");
               
        FontImage back = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        
        f.getToolbar().addCommandToRightBar("", back, (evt) ->{
            Acceuil A = new Acceuil();
            A.getF().show();
        });
        
        ServiceEvent service = new ServiceEvent();
        ArrayList<Event> events = service.getAll();
        
        Container container = new Container();
        Container myContainer = new Container();
        Button myListButton = new Button("My Events");
        myListButton.setPreferredW(Display.getInstance().getDisplayWidth());
        container.add(BorderLayout.north(myListButton));
        
        
        myListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Form F2 = new Form("My Events");
                
                F2.getToolbar().addCommandToRightBar("", back, (e) ->{
                    f.show();
                });
                
                if(MyApplication.current_user.getId() > 0)
                {
                    ArrayList<Event> myEvents = service.myList(MyApplication.current_user.getId());
                    try 
                    { 
                        if(myEvents.get(0).getId() != 0)
                        {
                            for(Event E : myEvents)
                            {
                                Container c = FlowLayout.encloseCenter();
                                Container ex = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                                Container details = new Container(new BoxLayout(BoxLayout.X_AXIS));

                                ex.getStyle().setMargin(Component.TOP, 40);
                                ex.getStyle().setBorder(RoundRectBorder.create());
                                Label destination = new Label(E.getNom());
                                destination.getStyle().setBgTransparency(0);
                                destination.setUIID("subtitle2");
                                Label price = new Label("     "+ E.getPrix()+ "$");
                                price.setUIID("subtitle");
                                int available = E.getCapacite() - E.getNbre();
                                Label capacity = new Label("   Available places: "+ available);
                                if(available == 0)
                                    capacity.setUIID("expired");

                                SpanLabel program = new SpanLabel("   Program: "+ E.getProgramme());

                                String url = "http://localhost/pidev/web/uploads/";
                                EncodedImage eimg;

                                eimg = EncodedImage.create("/load.gif");

                                eimg.scale(300,300);
                                Image img = URLImage.createToStorage(eimg, E.getImg(), url + E.getImg(), URLImage.RESIZE_SCALE);

                                ImageViewer imgv = new ImageViewer(img);
                                int deviceWidth = Display.getInstance().getDisplayWidth();
                                int imageWidth =(int) (deviceWidth *0.8);
                                int imageHeight =(int) (deviceWidth *0.6);
                                imgv.setPreferredH(imageHeight);
                                imgv.setPreferredW(imageWidth);
                                imgv.setUIID("image");
                                details.add(capacity);

                                destination.getStyle().setMargin(Component.LEFT, (int) (deviceWidth *0.2));
                                price.getStyle().setMargin(Component.LEFT, (int) (deviceWidth *0.2));
                                details.getStyle().setMargin(Component.LEFT, (int) (deviceWidth *0.2));
                                program.getStyle().setMargin(Component.LEFT, (int) (deviceWidth *0.1));

                                ex.add(destination);
                                ex.add(imgv);
                                ex.add(details);
                                ex.add(price);
                                ex.add(program);
                                c.add(ex);


                                Button btnView = new Button();

                                c.setLeadComponent(btnView);

                                btnView.addActionListener(new ActionListener<ActionEvent>() {
                                    @Override
                                    public void actionPerformed(ActionEvent evt) {

                                        Container ex2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                                        final InteractionDialog dlg = new InteractionDialog(E.getNom());
                                        //details2.add(capacity.getText());

                                        //ex2.add(destination);
                                        ImageViewer imgv2 = new ImageViewer(img);
                                        imgv2.setPreferredH(imageHeight);
                                        imgv2.setPreferredW(imageWidth);
                                        imgv2.setUIID("image");
                                        ex2.add(imgv2);
                                        FontImage iconDate = FontImage.createMaterial(FontImage.MATERIAL_EVENT, s);

                                        Label date = new Label("Date: " +E.getDate(), iconDate);
                                        date.getStyle().setMargin(Component.TOP, 50);
                                        date.getStyle().setMargin(Component.LEFT, 100);
                                        ex2.add(date);

                                        Label nbreLabel = new Label("Number of participants: " +Integer.toString(E.getNbre()));
                                        nbreLabel.getStyle().setMargin(Component.LEFT, 100);                        
                                        ex2.add(nbreLabel);

                                        Label priceLabel = new Label("Price: " +Float.toString(E.getPrix()) + "$");
                                        priceLabel.getStyle().setMargin(Component.LEFT, 100);                        
                                        ex2.add(priceLabel);

                                        Button join = new Button("Disjoin");
                                        join.getStyle().setBgColor(0xe68620);
                                        join.getStyle().setBgTransparency(255);
                                        join.getStyle().setFgColor(0x000000);
                                        //join.getStyle().set(255);


                                        join.addActionListener(new ActionListener<ActionEvent>() {
                                            @Override
                                            public void actionPerformed(ActionEvent evt) {
                                                if(MyApplication.current_user.getId()>0)
                                                {
                                                    System.out.println("disjoining");
                                                    service.disJoin(MyApplication.current_user.getId(), E.getId());
                                                    F2.show();
                                                }
                                                else
                                                {
                                                    Acceuil.path = "Event";
                                                    Login login = new Login();
                                                    login.getF().show();
                                                }  
                                            }
                                        });

                                        ex2.add(join);
                                        dlg.setLayout(new BorderLayout()); 
                                        dlg.addComponent(BorderLayout.CENTER, ex2); 
                                        Button close = new Button("Close"); 
                                        close.addActionListener(new ActionListener() { 
                                            public void actionPerformed(ActionEvent evt) { 
                                                dlg.dispose(); 
                                                myContainer.repaint();
                                            } 
                                        }); 
                                        dlg.addComponent(BorderLayout.SOUTH, close); 
                                        Dimension pre = dlg.getContentPane().getPreferredSize(); 
                                        dlg.show(120, 120, 50, 50);
                                        //dlg.show(0, 0, Display.getInstance().getDisplayWidth() - (pre.getWidth() + pre.getWidth() / 6), 0); 
                                    }
                                });
                                myContainer.add(c);
                            } 
                            F2.add(myContainer); 
                            F2.show();
                        }
                        else
                        {
                            Label empty = new Label("You haven't joined any event yet");
                            Button newBtn = new Button("Join");
                            Container c = new Container(BoxLayout.y());
                            c.add(empty).add(newBtn);
                            
                            newBtn.addActionListener(new ActionListener<ActionEvent>() {
                                @Override
                                public void actionPerformed(ActionEvent evt) {
                                    f.show();
                                }
                            });
                            
                            myContainer.add(BorderLayout.center(c));
                            F2.add(myContainer);
                            F2.show();
                        }
                        
                    } catch (IOException ex1) {}   
                }
                else
                {
                    Acceuil.path = "Event";
                    Login login = new Login();
                    login.getF().show();
                }
            }
        });
        
        
        try 
        { 
            for(Event E : events)
            {
                Container c = FlowLayout.encloseCenter();
                Container ex = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                Container details = new Container(new BoxLayout(BoxLayout.X_AXIS));

                ex.getStyle().setMargin(Component.TOP, 40);
                ex.getStyle().setBorder(RoundRectBorder.create());
                Label destination = new Label(E.getNom());
                destination.getStyle().setBgTransparency(0);
                destination.setUIID("subtitle2");
                Label price = new Label("     "+ E.getPrix()+ "$");
                price.setUIID("subtitle");
                int available = E.getCapacite() - E.getNbre();
                Label capacity = new Label("   Available places: "+ available);
                if(available == 0)
                    capacity.setUIID("expired");

                SpanLabel program = new SpanLabel("   Program: "+ E.getProgramme());

                String url = "http://localhost/pidev/web/uploads/";
                EncodedImage eimg;
            
                eimg = EncodedImage.create("/load.gif");
           
                eimg.scale(300,300);
                Image img = URLImage.createToStorage(eimg, E.getImg(), url + E.getImg(), URLImage.RESIZE_SCALE);

                ImageViewer imgv = new ImageViewer(img);
                int deviceWidth = Display.getInstance().getDisplayWidth();
                int imageWidth =(int) (deviceWidth *0.8);
                int imageHeight =(int) (deviceWidth *0.6);
                imgv.setPreferredH(imageHeight);
                imgv.setPreferredW(imageWidth);
                imgv.setUIID("image");
                details.add(capacity);

                destination.getStyle().setMargin(Component.LEFT, (int) (deviceWidth *0.2));
                price.getStyle().setMargin(Component.LEFT, (int) (deviceWidth *0.2));
                details.getStyle().setMargin(Component.LEFT, (int) (deviceWidth *0.2));
                program.getStyle().setMargin(Component.LEFT, (int) (deviceWidth *0.1));
                
                ex.add(destination);
                ex.add(imgv);
                ex.add(details);
                ex.add(price);
                ex.add(program);
                c.add(ex);
                

                Button btnView = new Button();
                
                c.setLeadComponent(btnView);
                
                btnView.addActionListener(new ActionListener<ActionEvent>() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        
                        Container ex2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                        final InteractionDialog dlg = new InteractionDialog(E.getNom());
                        //details2.add(capacity.getText());

                        //ex2.add(destination);
                        ImageViewer imgv2 = new ImageViewer(img);
                        imgv2.setPreferredH(imageHeight);
                        imgv2.setPreferredW(imageWidth);
                        imgv2.setUIID("image");
                        ex2.add(imgv2);
                        FontImage iconDate = FontImage.createMaterial(FontImage.MATERIAL_EVENT, s);
                        
                        Label date = new Label("Date: " +E.getDate(), iconDate);
                        date.getStyle().setMargin(Component.TOP, 50);
                        date.getStyle().setMargin(Component.LEFT, 100);
                        ex2.add(date);
                        
                        Label nbreLabel = new Label("Number of participants: " +Integer.toString(E.getNbre()));
                        nbreLabel.getStyle().setMargin(Component.LEFT, 100);                        
                        ex2.add(nbreLabel);
                        
                        Label priceLabel = new Label("Price: " +Float.toString(E.getPrix()) + "$");
                        priceLabel.getStyle().setMargin(Component.LEFT, 100);                        
                        ex2.add(priceLabel);
                        
                        Button join = new Button("Join");
                        join.getStyle().setBgColor(0xe68620);
                        join.getStyle().setBgTransparency(255);
                        join.getStyle().setFgColor(0x000000);
                        //join.getStyle().set(255);
                        
                        
                        join.addActionListener(new ActionListener<ActionEvent>() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {
                                if(MyApplication.current_user.getId()>0)
                                {
                                    System.out.println("joining");
                                    service.join(MyApplication.current_user.getId(), E.getId());
                                }
                                else
                                {
                                    Acceuil.path = "Event";
                                    Login login = new Login();
                                    login.getF().show();
                                }  
                            }
                        });
                         
                        ex2.add(join);
                        dlg.setLayout(new BorderLayout()); 
                        dlg.addComponent(BorderLayout.CENTER, ex2); 
                        Button close = new Button("Close"); 
                        close.addActionListener(new ActionListener() { 
                            public void actionPerformed(ActionEvent evt) { 
                                dlg.dispose(); 
                            } 
                        }); 
                        dlg.addComponent(BorderLayout.SOUTH, close); 
                        Dimension pre = dlg.getContentPane().getPreferredSize(); 
                        dlg.show(120, 120, 50, 50);
                        //dlg.show(0, 0, Display.getInstance().getDisplayWidth() - (pre.getWidth() + pre.getWidth() / 6), 0); 
                    }
                });
                
                
                container.add(c);
            }
        } catch (IOException ex1) {}
        
        f.add(container);
        
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
}
