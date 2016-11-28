package stacserv;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.text.Normalizer;

public class StacServ {

    /**
     * @param args the command line arguments
     */
    
    public static JFrame mainFrame = null;
    public static JTextArea chatText = null;
    public static StringBuffer toAppend = new StringBuffer("");
    
   
    
    public static void initGUI(){
      JPanel chatPane = new JPanel(new BorderLayout());
      chatText = new JTextArea(20, 20);
      chatText.setLineWrap(true);
      chatText.setForeground(Color.blue);
      chatText.setFont(chatText.getFont().deriveFont(20f));
      
      JScrollPane chatTextPane = new JScrollPane(chatText,
      JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      
      
     
      chatPane.add(chatTextPane, BorderLayout.CENTER);
      chatPane.setPreferredSize(new Dimension(800, 400));

      // Set up the main pane
      JPanel mainPane = new JPanel(new BorderLayout());
      mainPane.add(chatPane, BorderLayout.CENTER);

      // Set up the main frame
      mainFrame = new JFrame("Client Messages");
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      mainFrame.setContentPane(mainPane);
      mainFrame.setSize(mainFrame.getPreferredSize());
      mainFrame.setLocation(300, 300);
      mainFrame.pack();
      mainFrame.setVisible(true);
      
    }
    
    public static void main(String[] args) throws Exception {
    initGUI();
    ServerSocket m_ServerSocket = new ServerSocket(1025);
    int id = 0;
    StacServ.chatText.append("Server is now running and listening on port 1025 " + "\n");
    while (true) {
        
      Socket clientSocket = m_ServerSocket.accept();
      ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);
      cliThread.start();
     
      
      
      
    }
  }  
}



class ClientServiceThread extends Thread {
  Socket clientSocket;
  int clientID = -1;
  String userName = null;
  boolean running = true;
  private MySQLAccess dbconnection;
  HashMap<Socket, Integer> connections = new HashMap<Socket, Integer>();
  public ArrayList<ClassData> queryResult = new ArrayList<ClassData>();
  StringBuilder sb = new StringBuilder();
  String commandName = null;
  String clientCommand = null;
  String restofString = null;
  String[] tokens = new String[2];
  
  
  ClientServiceThread(Socket s, Integer i) {
    
    
    clientSocket = s;
    clientID = i;
    
    connections.put(s, i);
    StacServ.chatText.append("Current Connections" + "\n");
    for(HashMap.Entry entry: connections.entrySet()){
        StacServ.chatText.append(entry.getKey() + ", " + entry.getValue() + "\n");
        
    }
  }
  
  
  
  @Override
  public void run() {
    try
       {   
            dbconnection = new MySQLAccess();
            dbconnection.connect("138.86.104.164","STACDB","SEClass","BearsRock");
       } catch (Exception e) {
            //System.out.println(e);
            //e.printStackTrace();
       }
      
    System.out.println("Accepted Client : ID - " + clientID + " : Address - "
        + clientSocket.getInetAddress().getHostName());
    StacServ.chatText.append("Accepted Client : ID - " + clientID + " : Address - "
        + clientSocket.getInetAddress().getHostName()+"\n");
    try {
      BufferedReader   in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      PrintWriter   out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
     
      ObjectOutputStream objectOutput = new ObjectOutputStream(clientSocket.getOutputStream());
      
      while (running) {
        out.flush();
       
        in.mark(0);
        in.reset();
        clientCommand = in.readLine();
        
        
        try{
        if(clientCommand == null){
        } else {
            

            clientCommand = clientCommand.replace("\"", "");
            tokens = clientCommand.split(" ", 2);
            commandName = tokens[0];
            restofString = tokens[1];
            
            
          }
        }  catch(ArrayIndexOutOfBoundsException e) {
    //Handle ArrayIndexOutOfBoundsException
        }
        
    
        
        if (commandName.equalsIgnoreCase("LOGO")) {
          System.out.print("Stopping client thread for client : " + clientID);
          StacServ.chatText.append("Stopping client thread for client : " + clientID+"\n");
          out.println("LOGO" + " logged out."+"\n");
          out.flush();
          running = false;
        }
        else if (commandName.equalsIgnoreCase("REGU")){
          System.out.println("Client Says :" + clientCommand);
          StacServ.chatText.append("Client Says :" + clientCommand+"\n");
          String[] keys = restofString.split("\"?( |$)(?=(([^\"]*\"){2})*[^\"]*$)\"?");
          String uName = keys[0];
          String uPass = keys[1];
          String fName = keys[2];
          String lName = keys[3];
          
          StacServ.chatText.append("Attemplting to add client: " + fName + " " + lName);
          dbconnection.createUser(uName, uPass, fName, lName);
          out.println("REGR S");
          out.flush();
        }
        else if (commandName.equalsIgnoreCase("CRCR")){
          System.out.println("Client Says :" + clientCommand);
          StacServ.chatText.append("Client Says :" + clientCommand+"\n");
          
          String[] keys = restofString.split("\"?( |$)(?=(([^\"]*\"){2})*[^\"]*$)\"?");
          String className = keys[0];
          String institution = keys[1];
          String startDate = keys[2];
          String endDate = keys[3];
          String ipAddress = keys[4];
          String meetDOW = keys[5];
          
          StacServ.chatText.append("Attemplting to add class: " + className);
          dbconnection.createClass(className, institution, startDate, endDate, ipAddress, meetDOW);
          out.println("CRER S" + className);
          out.flush();
        }
        
        else if (commandName.equalsIgnoreCase("CSRC")){
          System.out.println("Client Says :" + clientCommand);
          StacServ.chatText.append("Client Says :" + clientCommand+"\n");
          String[] keys = restofString.split("\"?( |$)(?=(([^\"]*\"){2})*[^\"]*$)\"?");
          String className = keys[0];
          String institution = keys[1];
          
          if(institution == " "){
              institution = "%";
          }
          
          
          StacServ.chatText.append("Attemplting to find class: " + className +" "  + "\n");
          ResultSet rs = dbconnection.searchClasses(className, institution);
          //ResultSet rs = dbconnection.searchClasses(className, institution);
          
          while(rs.next()){
                    queryResult.add(new ClassData(rs.getString("classID"), rs.getString("className"), rs.getString("adminID"), rs.getString("institution"), rs.getString("startDate"), rs.getString("endDate"), rs.getString("PublicIPAddress")));
                    String ctClassID = rs.getString("classID");
                    String ctClassName = rs.getString("className");
                    String ctAdminID = rs.getString("adminID");
                    String ctinstitution = rs.getString("institution");
                    String ctstartDate = rs.getString("startDate");
                    String ctendDate = rs.getString("startDate");
                    String ctipAddress = rs.getString("endDate");
                    out.println("CDTR S" +  ctClassID + ctClassName + ctAdminID  + ctinstitution + ctstartDate + ctendDate + ctipAddress);
                    StacServ.chatText.append("CDTR S" + " "  +  ctClassID + " " + ctClassName + " " + ctAdminID + " "  + ctinstitution + " " + ctstartDate + " " + ctendDate + " " + ctipAddress + "\n");
                    
                    
                } 
        }
        
        else if (commandName.equalsIgnoreCase("CTDL")){
          
          String[] keys = restofString.split("\"?( |$)(?=(([^\"]*\"){2})*[^\"]*$)\"?");
          String classID = keys[0];
          int classId = Integer.parseInt(classID);
          
          System.out.println("Client Says :" + clientCommand);
          StacServ.chatText.append("Client Says :" + clientCommand+"\n");
          
          
          
          
          StacServ.chatText.append("Attemplting to find class with ID: " + classId + "\n");
          ResultSet rs = dbconnection.searchClasses(classId);
          
              
                while(rs.next()){
                    queryResult.add(new ClassData(rs.getString("classID"), rs.getString("className"), rs.getString("adminID"), rs.getString("institution"), rs.getString("startDate"), rs.getString("endDate"), rs.getString("PublicIPAddress")));
                    String ctClassID = rs.getString("classID");
                    String ctClassName = rs.getString("className");
                    String ctAdminID = rs.getString("adminID");
                    String ctinstitution = rs.getString("institution");
                    String ctstartDate = rs.getString("startDate");
                    String ctendDate = rs.getString("startDate");
                    String ctipAddress = rs.getString("endDate");
                    out.println("CDTR S" +  ctClassID + ctClassName + ctAdminID  + ctinstitution + ctstartDate + ctendDate + ctipAddress);
                    StacServ.chatText.append("CDTR " + "S" +  ctClassID + ctClassName + ctAdminID  + ctinstitution + ctstartDate + ctendDate + ctipAddress + "\n");
                    
                    
                }
                
          
          
        }
        
        else if (commandName.equalsIgnoreCase("ELST")){
          
         
         
          StacServ.chatText.append("ELST TEST " + userName +"\n");
          System.out.println("Client Says :" + clientCommand + "\n");
          StacServ.chatText.append("Client Says :" + clientCommand+"\n");
          
          int userID = dbconnection.getUserID(userName);
          
          ResultSet userClasses = dbconnection.getStudentsClasses(userID);
          String dummy = Integer.toString(userID);
          StacServ.chatText.append("userID is: " +dummy +"\n");
          StacServ.chatText.append("Attemplting to find classes for user: " + userName + "\n");
          sb = new StringBuilder("");
                while(userClasses.next()){
                    
                    String ctClassID = userClasses.getString("classID");
                    
                    sb.append(ctClassID);
                    sb.append(" ");
                    
                }
                out.println("ELSR S" +  sb.toString() + "\n");
                StacServ.chatText.append("ELSR S" +  sb.toString() + "\n");
          
          
        }
        
        else if (commandName.equalsIgnoreCase("CLST")){
          
         
          userName = "ejohn";
          StacServ.chatText.append("ELST TEST " + userName +"\n");
          System.out.println("Client Says :" + clientCommand + "\n");
          StacServ.chatText.append("Client Says :" + clientCommand+"\n");
          
          int userID = dbconnection.getAdminsID(userName);
          
          ResultSet userClasses = dbconnection.getAdminClasses(userID);
          String dummy = Integer.toString(userID);
          StacServ.chatText.append("userID is: " +dummy +"\n");
          StacServ.chatText.append("Attemplting to find classes for user: " + userName + "\n");
           userName = "CCDawg";
          sb = new StringBuilder("");
                while(userClasses.next()){
                    
                    String ctClassID = userClasses.getString("classID");
                    
                    sb.append(ctClassID);
                    sb.append(" ");
                      
                }
                out.println("ELSR S" +  sb.toString() + "\n");
                StacServ.chatText.append("ELSR " + "S " +  sb.toString() + "\n");
          
          
        }
        
        else if(commandName.equalsIgnoreCase("ENRL")){
            System.out.println("Client Says :" + clientCommand);
            StacServ.chatText.append("Client Says :" + clientCommand+"\n");
            userName = "CCDawg";
            String flag = "0";
            
            
            String[] keys = restofString.split("\"?( |$)(?=(([^\"]*\"){2})*[^\"]*$)\"?");
            String classID = keys[0];
            String deviceID = keys[1];
            int classIDs = Integer.parseInt(classID);
            dbconnection.addDevice(userName, classIDs, deviceID, flag);
            
        }
        
        else if(commandName.equalsIgnoreCase("CDRP")){
            userName = "PillCosby";
            String[] keys = restofString.split("\"?( |$)(?=(([^\"]*\"){2})*[^\"]*$)\"?");
            String classID = keys[0];
            
            int classIDs = Integer.parseInt(classID);
            dbconnection.unenrollStudent(classIDs, userName);
            
        }
        
        
        else if (commandName.equalsIgnoreCase("REGA")){
          System.out.println("Client Says :" + clientCommand);
          StacServ.chatText.append("Client Says :" + clientCommand+"\n");
          
          String[] keys = restofString.split("\"?( |$)(?=(([^\"]*\"){2})*[^\"]*$)\"?");
          String uName = keys[0];
          String uPass = keys[1];
          String fName = keys[2];
          String lName = keys[3];
          
          StacServ.chatText.append("Attempting to add Admin: " + fName + " " + lName);
          dbconnection.createAdmin(uName, uPass, fName, lName);
          out.println("REGA " + "S"+"\n");
          out.flush();
        }
        else if (commandName.equalsIgnoreCase("LOGA")){
          ResultSet rs = null;
          System.out.println("Client Says :" + clientCommand+"\n");
          StacServ.chatText.append("Client Says :" + clientCommand+"\n");
          
          String[] keys = restofString.split("\"?( |$)(?=(([^\"]*\"){2})*[^\"]*$)\"?");
          String uName = keys[0];
          String uPass = keys[1];
          
          StacServ.chatText.append("Attempting to Login Admin: " + uName+"\n");
          rs =  dbconnection.checkAdmin(uName);
          if(rs != null){
              while(rs.next()){
                  String check = rs.getString(1);
                  if(uName.equals(check)){
                      check = rs.getString(2);
                      if(uPass.equals(check)){
                          StacServ.chatText.append("Admin: " + uName + " logged in."+"\n");
                          out.flush();
                          out.println("LOGR S");
                          userName = uName;
                          out.flush();
                          
                         
                          
                      }
                      else{
                          StacServ.chatText.append("Admin: " + uName + "Incorrect username or password"+"\n");
                          out.flush();
                          out.println("LOGR " + "F"+"\n");
                          out.flush();
                      }
                  }
                  
              }
          }
          else{
              StacServ.chatText.append("Admin: " + uName + "is not registered in the system");
              out.println("LOGA " + "F"+"\n");
              out.flush();
          }
          
        }
        else if (commandName.equalsIgnoreCase("LOGU")){
          ResultSet rs = null;
          System.out.println("Client Says :" + clientCommand);
          StacServ.chatText.append("Client Says :" + clientCommand+"\n");
          StacServ.chatText.append(restofString +"\n");
          //restofString = restofString.replaceAll("[\u0000-\u001f]", "");
          String[] keys = restofString.split("\"?( |$)(?=(([^\"]*\"){2})*[^\"]*$)\"?");
          String uName = keys[0];
          String uPass = keys[1];
          
          uName = uName.replace("\"", "");
          uPass = uPass.replace("\"", "");
          
          StacServ.chatText.append("Attempting to log in: " + uName+"\n");
          rs =  dbconnection.checkUser(uName);
          if(rs != null){
              while(rs.next()){
                  String check = rs.getString(1);
                  if(uName.equals(check)){
                      check = rs.getString(2);
                      if(uPass.equals(check)){
                          StacServ.chatText.append("User: " + uName + " logged in."+"\n");
                          out.println("LOGR S");
                          userName = uName;
                          
                          out.flush();
                      }
                      else{
                          StacServ.chatText.append("User: " + uName + "\n" + "Incorrect password" +"\n");
                          out.println("LOGR F" + "\n");
                          out.flush();
                      }
                  }
              }
          }
          else{
              StacServ.chatText.append("Admin: " + uName + "is not registered in the system");
              out.println("LOGR F");
              out.flush();
          }
          
        }
        else {
          //out.println(clientCommand);
          out.flush();
        }
      }
      
    } catch (Exception e) {
      //e.printStackTrace();
    }
  }
}

