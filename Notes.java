import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.text.SimpleDateFormat;  
import java.util.Date;
import java.time.LocalDate;
import java.sql.*;


public class Notes{
   static JFrame home,note_home,main_note_page;
   static JButton plus,submit,close_page;
   static JLabel subject,date_note,f_message,main_note_lb;
   static JTextField subj_data;
   static JTextArea notes_data;
   static Color bt_color=new Color(58, 76, 115);
   static Color frame_color=new Color(222, 233, 160);
   static Color note_color=new Color(222, 218, 160);
   static String sub_notes,notes_data_msg,note_date_msg;
   static LocalDate dt = LocalDate.now(); 
   static String date_nt=String.valueOf(dt); 
   static String f_subject,f_date,f_notes,message; 
   static  int count=0,top_gape=0,id=0,f_id,count_id=1,main_id;
   static JPanel pan1,pan2;
 
   static void main_home()
   {
    
     home=new JFrame("Notes");
     home.setBounds(200, 100, 500, 600);

     fetch_data();   
     plus=new JButton("Add ");
     plus.setBounds(350, 500, 80, 40);
     plus.setBackground(bt_color);
     plus.setForeground(Color.WHITE);
     
     home.getContentPane().setBackground(frame_color);
     home.add(plus);
     home.setLayout(null);
     home.setVisible(true);

     ActionListener click=new ActionListener(){
        public void actionPerformed(ActionEvent e)
        { 
           home.setVisible(false); 
           add_note();
            
        }
    };
    plus.addActionListener(click);  
   }

   static public void add_note()
   {
          
        note_home=new JFrame("Note Page");
        note_home.setBounds(200, 100, 500, 600);
        
        subject=new JLabel("Topic: ");
        subject.setBounds(10, 10, 70, 20);
        subj_data=new JTextField();
        subj_data.setBounds(80, 10, 150, 20);
         
        date_note=new JLabel(date_nt);
        date_note.setBounds(300, 10, 120, 20);
        notes_data=new JTextArea();
        notes_data.setBounds(10, 40, 450, 400);

        submit=new JButton("Submit ");
        submit.setBounds(350, 500, 80, 40);
        submit.setBackground(bt_color);
        submit.setForeground(Color.WHITE);
        note_home.getContentPane().setBackground(frame_color);
        note_home.add(subject);note_home.add(subj_data);note_home.add(date_note);
        note_home.add(notes_data);note_home.add(submit);
        note_home.setLayout(null);
        note_home.setVisible(true);

        ActionListener submit_data=new ActionListener(){
            public void actionPerformed(ActionEvent e)
            { 
                
               try {
                     sub_notes=subj_data.getText();
                     notes_data_msg=notes_data.getText();
                     note_date_msg=date_nt;
                     Class.forName("com.mysql.cj.jdbc.Driver");
                     Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/notes", 
                      "root", "12345");
                  
                      String fetch_rec="Select * from note_file";
                      Statement sm=con.createStatement();
                      ResultSet rs=sm.executeQuery(fetch_rec);
                      while(rs.next())
                      {
                        id++;
                      }
                      
                    String insert_data="Insert into note_file value("+"'"+sub_notes+"'"+","+"'"+note_date_msg+"'"+","+"'"+notes_data_msg+"'"+","+id+")";
                   
                    Statement st=con.createStatement();
                   
                    st.executeUpdate(insert_data);                   
                   subj_data.setText("");notes_data.setText("");
                   note_home.setVisible(false);
                    main_home();
               } catch (Exception ee) {
                // TODO: handle exception
               }
                
            }
        };
        submit.addActionListener(submit_data); 

   }
   static void fetch_data()
     {
        try {
               
                Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/notes", 
                "root", "12345");

                String fetch_rec="Select * from note_file ORDER BY id DESC";
                Statement sm=con.createStatement();
                ResultSet rs=sm.executeQuery(fetch_rec);
             
                while(rs.next())
                {
                    pan1=new JPanel();
                   f_id=rs.getInt("id");
                   f_subject=rs.getString("subject_note");
                   f_date=rs.getString("date_note");
                   f_notes=rs.getString("note_data");
                   f_message=new JLabel("<html><label >"+count_id+".&nbsp;"+f_subject+"</label><label><br>&nbsp;&nbsp;&nbsp;"+f_date+"</label></html>");
                   f_message.setBounds(10, 10, 400, 40);
                   pan1.setBounds(1, top_gape, 490, 60);
                   pan1.setOpaque(true);
                   pan1.setBackground(note_color);
                   pan1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
                   JButton show_note=new JButton(String.valueOf("Show"));
                   show_note.setBounds(250, top_gape, 70, 60);
                   show_note.setForeground(Color.WHITE);
                   show_note.setBackground(bt_color);

                   JButton show_id=new JButton(String.valueOf(f_id));
                   show_id.setBounds(370, top_gape, 70, 60);
                   show_id.setVisible(false);
                   pan1.add(f_message);    pan1.add(show_note);   pan1.add(show_id);               
                   home.add(pan1);
                   count++;top_gape=top_gape+60;count_id++;
                   ActionListener show_data=new ActionListener(){
                    public void actionPerformed(ActionEvent e)
                    { 
                        
                       try {
                            
                              main_id=Integer.parseInt(show_id.getText());
                              home.setVisible(false); 
                              show_notes_page(main_id);
                             
                             
                       } catch (Exception ee) {
                        // TODO: handle exception
                       }
                        
                    }
                };
                show_note.addActionListener(show_data); 
                }
                count_id=1;
                if(count==0)
                {
                    f_message=new JLabel("<html><div><label>No any Notes</label></div></html>");
                    f_message.setBounds(10, 50, 250, 20);
                    home.add(f_message);
                }
                top_gape=0;


            } catch (Exception e) {
             System.out.println(e);
        }
        
    }
   static void show_notes_page(int id)
   {
        try {
            
                int id_nt=id;
              
                
                
                
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/notes", 
                "root", "12345");
            
                String fetch_rec="Select * from note_file where id="+id_nt+"";                
                Statement sm=con.createStatement();
                ResultSet rs=sm.executeQuery(fetch_rec);
                 String msg1,msg2,msg3;                 
                while(rs.next())
                {
                    msg1=rs.getString("subject_note");
                    
                    msg2=rs.getString("date_note");
                    msg3=rs.getString("note_data");
                    main_note_page=new JFrame(msg1);
                    main_note_page.setBounds(200, 100, 500, 600);
                    main_note_page.getContentPane().setBackground(frame_color);
                    main_note_lb=new JLabel(msg3);
                    main_note_lb.setBounds(10, 10, 460, 40);
                  
                   
                }
                close_page=new JButton("Close");
                close_page.setBounds(350, 450, 70, 35);
                close_page.setBackground(bt_color);
                close_page.setForeground(Color.WHITE);
                main_note_page.add(main_note_lb);
                main_note_page.add(close_page); 
                
                ActionListener mainpage=new ActionListener(){
                    public void actionPerformed(ActionEvent e)
                    { 
                        main_note_page.setVisible(false);
                        main_home();
                        
                    }
                };
                close_page.addActionListener(mainpage); 
                   
               
                main_note_page.setLayout(null);
                main_note_page.setVisible(true);
        } catch (Exception eee) {
            // TODO: handle exception
        }
   }

    public static void main(String[] args) {
        main_home();

    }
}