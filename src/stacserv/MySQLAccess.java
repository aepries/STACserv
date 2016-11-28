package stacserv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;




public class MySQLAccess {
    private Connection connect = null;
    
    /*public MySQLAccess() {
        try {
        my.connect("138.86.122.233", "se2014", "se2014", "STACDB");
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
     
      try{
         //my.createUser("newtest", "newpass", "New Test", "User", "newtest@user.com");
          //my.createAdmin("NewAdmin", "NewAdminPass", "New", "Admin", "new@admin.com");
          //my.createClass(20140113, "Test Class", "5", "UNC", "M:0900-1000");
                 //my.addDevice("g.house", 12992015, "D5:C8:FF:EF:A9:B8");
                 //my.changeDeviceForUser("g.house", 12992015, "BB:BB:BB:BB:BB:BB");
      }catch(Exception e)
      {
          System.out.println(e);
          e.printStackTrace();
      }
      
      try{
          String[] keys = {"ID",  "FName", "LName", "UName", "Password", "RegTime"};
          ResultSet rs = my.runQuery("SELECT", "Users", keys, null, null, null, 0, 0);
          dump(rs);
          
          String[] keys2 = {"ClassID", "ClassName", "Institution", "AdminID", "MeetTimes"};
          rs = my.runQuery("SELECT", "Classes", keys2, null, null, null, 0, 0);
          dump(rs);
          
          rs = my.runQuery("SELECT", "Admins", keys, null, null, null, 0, 0);
          dump(rs);
                  
                  String[] deviceKeys = {"ID", "UserID", "ClassID", "DeviceID"};
                  rs = my.runQuery("SELECT", "Devices", deviceKeys, null, null, null, 0, 0);
                  dump(rs);
                
                  //Dump Attendance info when ready...
                 /* String[] attnKeys = {"ID", "UserID", "ClassID", "AttnTime"};
                  rs = my.runQuery("SELECT", "Attendance", attnKeys, null, null, null, 0, 0);
                  dump(rs);
                 */
      /*}catch(Exception e)
      {
          System.out.println(e);
          e.printStackTrace();
      }
    }*/
    
    
    /*
     * void connect
     * @author David Worth
     * @param String ip
     *         The IP address of the server
     * 
     * @param String username
     *         The username to authenticate with
     * 
     * @param String password
     *         The password to authenticate with
     * 
     * @param String database
     *         The database to connect to
     * 
     * @return void
     * 
     * @exception Exception
     *         Throws an exception if there is an error connecting to the database.
     */
    public void connect(String ip, String name, String username, String password) throws Exception
    {
        Class.forName("com.mysql.jdbc.Driver");
        
        connect = DriverManager.getConnection("jdbc:mysql://"+ip+"/"+name, username, password);
    }
    
    /* * Deprecated
     *
    public void testSetup() {
        try {
        connect("138.86.122.233", "STACDB", "se2014", "se2014");
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
     
      try{
         //my.createUser("newtest", "newpass", "New Test", "User", "newtest@user.com");
          //my.createAdmin("NewAdmin", "NewAdminPass", "New", "Admin", "new@admin.com");
          //my.createClass(20140113, "Test Class", "5", "UNC", "M:0900-1000");
                 //my.addDevice("g.house", 12992015, "D5:C8:FF:EF:A9:B8");
                 //my.changeDeviceForUser("g.house", 12992015, "BB:BB:BB:BB:BB:BB");
      }catch(Exception e)
      {
          System.out.println(e);
          e.printStackTrace();
      }
      
      try{
          String[] keys = {"ID",  "FName", "LName", "UName", "Password", "RegTime"};
          ResultSet rs = runQuery("SELECT", "Users", keys, null, null, null, 0, 0);
          dump(rs);
          
          String[] keys2 = {"ClassID", "ClassName", "Institution", "AdminID", "MeetTimes"};
          rs = runQuery("SELECT", "Classes", keys2, null, null, null, 0, 0);
          dump(rs);
          
          rs = runQuery("SELECT", "Admins", keys, null, null, null, 0, 0);
          dump(rs);
                  
                  String[] deviceKeys = {"ID", "UserID", "ClassID", "DeviceID"};
                  rs = runQuery("SELECT", "Devices", deviceKeys, null, null, null, 0, 0);
                  dump(rs);
                
      }catch(Exception e)
      {
          System.out.println(e);
          e.printStackTrace();
      }
    }
    **/
     
    /* * Deprecated
     *
    public void readDatabase() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        
        connect = DriverManager.getConnection("jdbc:mysql://138.86.122.233/STACDB" , "se2014", "se2014");
        //statement = connect.createStatement();
    }
    **/
    
    /*
     * ResultSet runQuery
     * @author David Worth
     * @param String cmd
     *         The SQL command
     *         @value INSERT
     *         @value SELECT
     *         @value UPDATE
     *         @value DELETE
     * 
     * @param String table
     *         The table in the database to run the command on
     * 
     * @param String[] keys
     *         An array of the table columns to be included in the statement.
     * 
     * @param String[] values
     *         An array of the values corresponding to the table columns supplied in @keys
     * 
     * @param String[] whereKeys
     *         An array of the table columns to be included in the WHERE clause.
     * 
     * @param String[] whereVals
     *         An array of the values corresponding to the table colums supplied in @whereKeys
     * 
     * @param int limitStart
     *      First row index to include
     * 
     * @param int limitCount
     *      Number of rows to include
     *      Set to 0 to include all rows.
     * 
     * @return ResultSet
     *         The result set returned by the database for the query.
     *         
     * Example queries:
     * INSERT
     *     runQuery("INSERT", "table", ["id", "fname", "lname"], [null, "Bob", "Smith"], null, null, 0, 30);
     * Unbiased SELECT
     *     runQuery("SELECT", "table", ["id", "classID"], null, null, null, 0, 30);
     * Biased SELECT
     *     runQuery("SELECT", "table", ["id", "classID"], null, ["fname", "lname"], ["Bob", "Smith"], 0, 30);
     * Unbiased UPDATE
     *     runQuery("UPDATE", "table", ["device"], [null], null, null, 0, 30);
     * Biased UPDATE
     *     runQuery("UPDATE", "table", ["device"], ["FF:FF:FF:FF:FF:FF"], ["fname", "lname"], ["Bob", "Smith"], 0, 30);
     * DELETE
     *     runQuery("DELETE", "table", null, null, ["fname", "lname"], ["Bob", "Smith"], 0, 30);
     *     
     * Notes:
     * - Please note that this method uses prepared statements to prevent SQL injection, which means that you must enumerate each column you wish to interact with. This means no SELECT * statments.
     * - Please also note that certain parameters have no use for certain CMDs.
     *    - @whereKeys, @whereValues, @limitStart, @limitCount are useless for INSERT
     *    - @keys and @values are useless are DELETE
     *    - @values is useless for SELECT
     * - Note that WHERE clauses only support AND cases right now.
     * 
     * @exception Exception
     *         Will contain a detailed explanation of the error.
     */
    private ResultSet runQuery(String cmd, String table, String[] keys, String[] values, String[] whereKeys, String[] whereValues, int limitStart, int limitCount) throws Exception
    {    
        PreparedStatement stmt;
        
        if(!(cmd.equals("INSERT") || cmd.equals("SELECT") || cmd.equals("UPDATE") || cmd.equals("DELETE") || cmd.equals("SELECTLIKE")))
        {
            throw new Exception("CMD UNSUPPORTED! CMD must be INSERT, SELECT, UPDATE, or DELETE.");
        }
        
        String sql = "";
        if(cmd.equals("INSERT"))
        {
            //Build Insert statement
            sql += "INSERT INTO `"+table+"` (";
            
            for(int i = 0; i < keys.length; i++)
            {
                sql += "`"+keys[i]+"`";
                if(i!=keys.length-1)
                    sql+=",";
            }
            
            sql += ") VALUES(";
            
            for(int i = 0; i < values.length; i++)
            {
                sql += "?";
                if(i!=values.length-1)
                    sql+=", ";
            }
            
            sql += ");";
        }
        else if(cmd.equals("SELECT"))
        {
            sql += "SELECT ";
            
            if((whereKeys != null && whereValues != null) && whereKeys.length != whereValues.length)
                throw new Exception("INVALID WHERE CLAUSE: Key/value pairs invalid. Make sure that you have the same number of keys as values.");
            
            for(int i = 0; i < keys.length; i++)
            {
                sql += "`"+keys[i]+"`";
                if(i!=keys.length-1)
                    sql+=",";
            }
            
            sql += "FROM `" + table + "`";
            
            if(whereKeys != null && whereKeys.length != 0)
            {
                sql += " WHERE ";
                
                for(int i = 0; i < whereKeys.length; i++)
                {
                    sql += "`" + whereKeys[i] + "`=? ";
                    
                    if(i != whereKeys.length-1)
                    {
                        sql += "AND ";
                    }
                }
            }
            
            //Test limit
            if(limitCount != 0)
            {
                sql += "LIMIT "+limitStart+","+limitCount;
            }
            
            sql += ";";
        }
        else if(cmd.equals("SELECTLIKE"))
        {
            sql += "SELECT ";
            
            if((whereKeys != null && whereValues != null) && whereKeys.length != whereValues.length)
                throw new Exception("INVALID WHERE CLAUSE: Key/value pairs invalid. Make sure that you have the same number of keys as values.");
            
            for(int i = 0; i < keys.length; i++)
            {
                sql += "`"+keys[i]+"`";
                if(i!=keys.length-1)
                    sql+=",";
            }
            
            sql += "FROM `" + table + "`";
            
            if(whereKeys != null && whereKeys.length != 0)
            {
                sql += " WHERE ";
                
                for(int i = 0; i < whereKeys.length; i++)
                {
                    sql += "`" + whereKeys[i] + "` LIKE ? ";
                    
                    if(i != whereKeys.length-1)
                    {
                        sql += "AND ";
                    }
                }
            }
            
            //Test limit
            if(limitCount != 0)
            {
                sql += "LIMIT "+limitStart+","+limitCount;
            }
            
            sql += ";";
        }
        
        
        
        else if(cmd.equals("UPDATE"))
        {
            sql += "UPDATE `"+table+"` SET ";
            
            for(int i = 0; i < keys.length; i++)
            {
                sql += "`"+keys[i]+"`=?";
                if(i!=keys.length-1)
                    sql+=",";
            }
            
            if(whereKeys.length != 0)
            {
                sql += " WHERE ";
                
                for(int i = 0; i < whereKeys.length; i++)
                {
                    sql += "`" + whereKeys[i] + "`=? ";
                    
                    if(i != whereKeys.length-1)
                    {
                        sql += "AND ";
                    }
                }
            }
            
            //Test limit
            if(limitCount != 0)
            {
                sql += "LIMIT "+limitCount;
            }
            
            sql += ";";
        }
        else if(cmd.equals("DELETE"))
        {
            sql += "DELETE FROM `"+table+"`";
            
            if(whereKeys.length != 0)
            {
                sql += " WHERE ";
                
                for(int i = 0; i < whereKeys.length; i++)
                {
                    sql += "`" + whereKeys[i] + "`=? ";
                    
                    if(i != whereKeys.length-1)
                    {
                        sql += "AND ";
                    }
                }
            }
            
            //Test limit
            if(limitCount != 0)
            {
                sql += "LIMIT "+limitCount;
            }
            
            sql += ";";
        }
        
        try
        {
            stmt = connect.prepareStatement(sql);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new Exception("SQL Error!");
        }
        
        if(stmt == null)
            throw new Exception("SQL Error!");
        
        System.out.println(sql);
        System.out.println(stmt.getParameterMetaData().getParameterCount());
        
        if(cmd.equals("INSERT"))
        {
            //Apply @values
            for(int i = 0; i < values.length; i++)
            {
                stmt.setString(i+1, values[i]);
            }
            
            stmt.executeUpdate();
            return null;
        }
        else if(cmd.equals("SELECT"))
        {
            //Apply @whereValues
            if(whereValues != null)
            {
                for(int i = 0; i < whereValues.length; i++)
                {
                    stmt.setString(i+1, whereValues[i]);
                }
            }
        }
        
        else if(cmd.equals("SELECTLIKE"))
        {
            //Apply @whereValues
            if(whereValues != null)
            {
                for(int i = 0; i < whereValues.length; i++)
                {
                    stmt.setString(i+1, whereValues[i]);
                }
            }
        }
        
        else if(cmd.equals("UPDATE"))
        {
            //Apply @values
            for(int i = 0; i < values.length; i++)
            {
                stmt.setString(i+1, values[i]);
            }
            
            //Apply @whereValues
            if(whereValues != null)
            {
                for(int i = 0; i < whereValues.length; i++)
                {
                    stmt.setString(i+values.length+1, whereValues[i]);
                }
            }
            
            stmt.executeUpdate();
            return null;
        }
        else if(cmd.equals("DELETE"))
        {
            //Apply @whereValues
            if(whereValues != null)
            {
                for(int i = 0; i < whereValues.length; i++)
                {
                    stmt.setString(i+1, whereValues[i]);
                }
            }
            
            stmt.executeUpdate();
            return null;
        }
        
        return stmt.executeQuery();
    }
    
    /*
     * ResultSet checkUser
     * @author David Worth
     * @param String user
     *         The username to check
     * 
     * @return ResultSet
     *         The user information
     * 
     * @exception Exception
     *         Throws exception if there is an error running the SQL query
     */
    /*public ResultSet checkUser(String user) throws Exception
    {
        //String[] keys = {"ID", "FName", "LName", "UName", "Password", "RegTime"};
        String[] keys = {"UName", "Password"};
        String[] whereKeys = {"UName"};
        String[] whereVals = {user};
        return runQuery("SELECT", "Users", keys, null, whereKeys, whereVals, 0, 1);
    }*/
    public ResultSet checkUser(String user) throws Exception
    {
        //String[] keys = {"ID", "FName", "LName", "UName", "Password", "RegTime"};
        String[] keys = {"UName", "Password"};
        String[] whereKeys = {"UName"};
        String[] whereVals = {user};
        return runQuery("SELECT", "Users", keys, null, whereKeys, whereVals, 0, 1);
    }
    /*
     * ResultSet checkAdmin
     * @author David Worth
     * @param String user
     *         The username to check
     * 
     * @return ResultSet
     *         The user information
     * 
     * @exception Exception
     *         Throws exception if there is an error running the SQL query
     */
    public ResultSet checkAdmin(String user) throws Exception
    {
        //String[] keys = {"ID", "FName", "LName", "UName", "Password", "RegTime"};
        String[] keys = {"UName", "Password"};
        String[] whereKeys = {"UName"};
        String[] whereVals = {user};
        return runQuery("SELECT", "Admins", keys, null, whereKeys, whereVals, 0, 1);
    }
    
    /*
     * boolean createUser
     * @author David Worth
     * @param String user
     *         The username to add
     * 
     * @param String pass
     *         The password to add
     * 
     * @param String first
     *         The first name of the user
     * 
     * @param String last
     *         The last name of the user
     * 
     * @param String email
     *         UNUSED: there is no column in the database for email
     *         The user's email address
     * 
     * @return boolean
     *         Returns true if the user is created successfully
     */
    public boolean createUser(String user, String pass, String first, String last)
    {
        String[] keys = {"FName", "LName", "UName", "Password"};
        String[] vals = {first, last, user, pass};
        
        try
        {
            runQuery("INSERT", "Users", keys, vals, null, null, 0, 30);
        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
        
        return true;
    }
    
    /*
     * boolean createAdmin
     * @author David Worth
     * @param String user
     *         The username to add
     * 
     * @param String pass
     *         The password to add
     * 
     * @param String first
     *         The first name of the user
     * 
     * @param String last
     *         The last name of the user
     * 
     * @param String email
     *         UNUSED: there is no column in the database for email
     *         The user's email address
     * 
     * @return boolean
     *         Returns true if the user is created successfully
     */
    public boolean createAdmin(String user, String pass, String first, String last)
    {
        String[] keys = {"FName", "LName", "UName", "Password"};
        String[] vals = {first, last, user, pass};
        
        try
        {
            runQuery("INSERT", "Admins", keys, vals, null, null, 0, 30);
        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
        
        return true;
    }
    
    /*
     * boolean createClass
     * @author David Worth
     * @modified 11/03/2014
     * @param String name
     *         the name of the class
     * 
     * @param int classID
     *         the classID of the class to create
     * 
     * @param int adminID
     *         the ID of the administrator of the class
     * 
     * @param String institution
     *         the string representation of the institution
     * 
     * @param String meetTimes
     *         the formatted string representation of the meet times
     * 
     * @return boolean
     *         Returns true if the class is created successfully.
     */
    /*
    public boolean createClass(int classID, String name, String admin, String institution, String meetTimes, String StartDate, String EndDate, String PublicIPAddress) throws Exception
    {
        int adminID = getAdminID(admin);
        
        String[] keys = {"ClassID", "ClassName", "AdminID", "Institution", "MeetTimes", "StartDate", "EndDate", "PublicIPAddress"};
        String[] vals = {Integer.toString(classID), name, Integer.toString(adminID), institution, meetTimes, StartDate, EndDate, PublicIPAddress};
        
        try {
            runQuery("INSERT", "Classes", keys, vals, null, null, 0, 30);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
        
        return true;
    }*/
    
    public boolean createClass(String className, String insitution, String startDate, String endDate, String ipAdress, String meetDOW) throws Exception
    {
        
        
        String[] keys = {"className", "insitution", "startDate", "endDate", "ipAdress", "meetDOW"};
        String[] vals = {className, insitution, startDate, endDate, ipAdress, meetDOW};
        
        try {
            runQuery("INSERT", "Classes", keys, vals, null, null, 0, 30);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
        
        return true;
    }
    
    
    
    /*
     * boolean deleteClass
     * @author David Worth
     * @param int classID
     *         the ID of the class to be deleted
     * 
     * @return boolean
     *         Returns true if the class is deleted successfully.
     */
    public boolean deleteClass(int classID)
    {
        String[] whereKeys = {"ClassID"};
        String[] whereVals = {Integer.toString(classID)};
        
        try
        {
            runQuery("DELETE", "Classes", null, null, whereKeys, whereVals, 0, 30);
        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
        
        return true;
    }
    
    /*
     * ResultSet searchClasses
     * @author David Worth
     * 
     * @Type 1:
     * @param int classID
     *         the ID of the class to search for
     * 
     * @Type 2:
     * @param int classID
     *         the ID of the class to search for
     * 
     * @param String className
     *         the string representationn of the class name
     * 
     * @Type 3:
     * @param int classID
     *         the ID of the class to search for
     * 
     * @param int adminID
     *         the ID of the administrator
     * 
     * @Type 4:
     * @param int classID
     *         the ID of the class to search for
     * 
     * @param String className
     *         the string representation of the class name
     * 
     * @param int adminID
     *         the ID of the administrator
     * 
     * @Type 5:
     * @param String className
     *         the string representation of the class name
     * 
     * @Type 6:
     * @param String className
     *         the string representation of the class name
     * 
     * @param int adminID
     *         the ID of the administrator
     * 
     * @return ResultSet
     *         Returns the result of the query, including all matched classes.
     */
    public ResultSet searchClasses(int classID)
    {
        String[] keys = {"ClassID", "ClassName", "MeetTimes", "AdminID", "Institution", "StartDate", "EndDate", "PublicIPAddress"};
        String[] whereKeys = {"ClassID"};
        String[] whereVals = {Integer.toString(classID)};
        
        try
        {
            return runQuery("SELECT", "Classes", keys, null, whereKeys, whereVals, 0, 30);
        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e);
            return null;
        }
    }
    
    public ResultSet searchClasses(int classID, String className)
    {
        String[] keys = {"ClassID", "ClassName", "MeetTimes", "AdminID", "Institution"};
        String[] whereKeys = {"ClassID", "ClassName"};
        String[] whereVals = {Integer.toString(classID), className};
        
        try
        {
            return runQuery("SELECT", "Classes", keys, null, whereKeys, whereVals, 0, 30);
        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e);
            return null;
        }
    }
    
    public ResultSet searchClassesWithClassAdmin(int classID, String admin) throws Exception
    {
        String[] keys = {"ClassID", "ClassName", "MeetTimes", "AdminID", "Institution"};
        String[] whereKeys = {"ClassID", "AdminID"};
        int adminID = getAdminID(admin);
        String[] whereVals = {Integer.toString(classID), Integer.toString(adminID)};
        
        try
        {
            return runQuery("SELECT", "Classes", keys, null, whereKeys, whereVals, 0, 30);
        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e);
            return null;
        }
    }
    
    public ResultSet searchClasses(int classID, String className, String admin) throws Exception
    {
        String[] keys = {"ClassID", "ClassName", "MeetTimes", "AdminID", "Institution"};
        String[] whereKeys = {"ClassID", "ClassName", "AdminID"};
        int adminID = getAdminID(admin);
        String[] whereVals = {Integer.toString(classID), className, Integer.toString(adminID)};
        
        try
        {
            return runQuery("SELECT", "Classes", keys, null, whereKeys, whereVals, 0, 30);
        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e);
            return null;
        }
    }
    
    public ResultSet searchClasses(String className)
    {
        String[] keys = {"ClassID", "ClassName", "MeetTimes", "AdminID", "Institution"};
        String[] whereKeys = {"ClassName"};
        String[] whereVals = {className};
        
        try
        {
            return runQuery("SELECT", "Classes", keys, null, whereKeys, whereVals, 0, 30);
        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e);
            return null;
        }
    }
    
    
    /*
    public ResultSet searchClasses(String className, String admin) throws Exception
    {
        String[] keys = {"ClassID", "ClassName", "MeetTimes", "AdminID", "Institution"};
        String[] whereKeys = {"ClassName", "AdminID"};
        int adminID = getAdminID(admin);
        String[] whereVals = {className, Integer.toString(adminID)};h
            return null;
        }
    }*/
    
    public ResultSet searchClasses(String className, String institution) throws Exception
    {
        String[] keys = {"ClassID", "ClassName", "MeetTimes", "AdminID", "Institution", "StartDate", "EndDate", "PublicIPAddress"};
        String[] whereKeys = {"ClassName", "institution"};
        
        String[] whereVals = {className, institution};
        
        try
        {
            return runQuery("SELECTLIKE", "Classes", keys, null, whereKeys, whereVals, 0, 0);
        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e);
            return null;
        }
    }
    
    /*
     * ResultSet searchClassesWithAdminID
     * @author David Worth
     * @param int adminID
     *         the ID of the administrator
     * 
     * @return ResultSet
     *         Returns the result of the query, including all matched classes.
     */
    public ResultSet searchClassesWithAdminUsername(String admin) throws Exception
    {
        String[] keys = {"ClassID", "ClassName", "MeetTimes", "AdminID", "Institution"};
        String[] whereKeys = {"AdminID"};
        int adminID = getAdminID(admin);
        String[] whereVals = {Integer.toString(adminID)};
        
        try
        {
            return runQuery("SELECT", "Classes", keys, null, whereKeys, whereVals, 0, 30);
        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e);
            return null;
        }
    }
    
    
    
    /*
     * boolean enrollStudent
     * @author David Worth
     * @param int classID
     *         The ID of the class to enroll the student in
     *
     * @param String Username
     *         The username of the student to enroll
     *
     * @return boolean
     *         Returns true if the student is enrolled in the class successfully
     *
     * @exception Exception
     *         Throws an exception if the database cannot be reached
     *         Throws an exception if invalid info is provided
     *         Throws an exception if no student with the given username exists
     */
    public boolean enrollStudent(int classID, String username, String deviceID) throws Exception
    {
        int userID = getUserID(username);
        
        //Add student to table
        String[] nKeys = {"UserID", "ClassID", "DeviceID"};
        String[] nVals = {Integer.toString(userID), Integer.toString(classID), deviceID};
        runQuery("INSERT", "Enrollment", nKeys, nVals, null, null, 0, 0);
        
        return true;
    }
    
    /*
     * boolean unenrollStudent
     * @author David Worth
     * @param int classID
     *         The ID of the class to remove the student from
     *
     * @param String username
     *         The username of the student to remove from the class
     *
     * @return boolean
     *         Returns true if the student is successfully removed from the class
     *
     * @exception Exception
     *         Throws an exception if the database cannot be reached
     *         Throws an exception if invalid info is provided
     *         Throws an exception if no student with the given username exists
     */
    public boolean unenrollStudent(int classID, String username) throws Exception
    {
        int userID = getUserID(username);
        
        //Remove student from table
        String[] nKeys = {"UserID", "ClassID"};
        String[] nVals = {Integer.toString(userID), Integer.toString(classID)};
        runQuery("DELETE", "Enrollment", null, null, nKeys, nVals, 0, 0);
        
        return true;
    }
    
    /*
     * ResultSet getStudentsInClass
     * @author David Worth
     * @param int classID
     *         The ID of the class to list
     *
     * @return ResultSet
     *         Returns a result set containing the user IDs of all of the enrolled students
     *
     * @exception Exception
     *         Throws an exception if the database cannot be reached
     *         Throws an exception if invalid info is provided
     */
    public ResultSet getStudentsInClass(int classID) throws Exception
    {
        String[] keys = {"UserID", "DeviceID"};
        String[] nKeys = {"ClassID"};
        String[] nVals = {Integer.toString(classID)};
        return runQuery("SELECT", "Enrollment", keys, null, nKeys, nVals, 0, 0);
    }
    
    public ResultSet getStudentsClasses(int userID) throws Exception
    {
        String[] keys = {"classID"};
        String[] nKeys = {"UserID"};
        String[] nVals = {Integer.toString(userID)};
        return runQuery("SELECT", "Enrollment", keys, null, nKeys, nVals, 0, 0);
    }
    public ResultSet getAdminClasses(int adminID) throws Exception
    {
        String[] keys = {"classID"};
        String[] nKeys = {"AdminID"};
        String[] nVals = {Integer.toString(adminID)};
        return runQuery("SELECT", "Classes", keys, null, nKeys, nVals, 0, 0);
    }
    /*
     * int getUserID
     * @author David Worth
     * @param String username
     *         The username of the user
     *
     * @return int
     *         Returns the user ID
     *
     * @exception Exception
     *         Throws an exception if no user with the given username exists
     */
    public int getUserID(String username) throws Exception
    {
        //Get Student ID
        String[] keys = {"id"};
        String[] whereKeys = {"UName"};
        String[] whereVals = {username};
        ResultSet rs = runQuery("SELECT", "Users", keys, null, whereKeys, whereVals, 0, 1);
        
        int userID = 0;
        while(rs.next())
        {
            String columnValue = rs.getString(1);
            userID = Integer.parseInt(columnValue);
            break;
        }
        
        if(userID == 0)
        {
            throw new Exception("ERROR: No user with that username exists!");
        }
        return userID;
    }
    public int getAdminsID(String username) throws Exception
    {
        //Get Student ID
        String[] keys = {"id"};
        String[] whereKeys = {"UName"};
        String[] whereVals = {username};
        ResultSet rs = runQuery("SELECT", "Admins", keys, null, whereKeys, whereVals, 0, 1);
        
        int userID = 0;
        while(rs.next())
        {
            String columnValue = rs.getString(1);
            userID = Integer.parseInt(columnValue);
            break;
        }
        
        if(userID == 0)
        {
            throw new Exception("ERROR: No user with that username exists!");
        }
        return userID;
    }
    
    /*
     * int getAdminID
     * @author David Worth
     * @param String username
     *         The username to search
     * 
     * @return int
     *         the ID of the admin
     * 
     * @exception Exception
     *         Throws an exception if no user with the given credentials exists
     */
    private int getAdminID(String username) throws Exception
    {
        //Get Student ID
        String[] keys = {"id"};
        String[] whereKeys = {"UName"};
        String[] whereVals = {username};
        ResultSet rs = runQuery("SELECT", "Admins", keys, null, whereKeys, whereVals, 0, 1);
        
        int userID = 0;
        while(rs.next())
        {
            String columnValue = rs.getString(1);
            userID = Integer.parseInt(columnValue);
            break;
        }
        
        if(userID == 0)
        {
            throw new Exception("ERROR: No admin with that username exists!");
        }
        return userID;
    }
    
    /*
     * boolean attendClass
     * @author David Worth
     * @param String username
     *         The username of the user
     * 
     * @param int classID
     *         The id of the class to attend
     * 
     * @param String attendanceTime
     *         The Timestamp of attendance
     *         FORMAT: YYYY-MM-DD 24H:MM:SS"
     * 
     * @return boolean
     *         Returns true if the attendance is successfully recorded
     * 
     * @exception Exception
     *         Throws an exception if the username is not valid
     *         Throws an exception if the database is unreachable
     *         Throws an exception if the data is not recorded
     */
    public boolean attendClass(String username, int classID, String attendanceTime) throws Exception
    {
        int userID = getUserID(username);
        String[] keys = {"UserID", "ClassID", "AttnTime"};
        String[] vals = {Integer.toString(userID), Integer.toString(classID), attendanceTime};
        runQuery("INSERT", "Attendance", keys, vals, null, null, 0, 0);
        
        return true;
    }
    
    /*
     * ResultSet getAttendanceForClass
     * @author David Worth
     * @param int classID
     *         The id of the class to check
     * 
     * @return ResultSet
     *         returns a result set of all of the attendances for the specified class ID
     * 
     * @exception Exception
     *         throws an exception if the database is unreachable
     *         throws an exception if the database cannot be read
     */
    public ResultSet getAttendanceForClass(int classID) throws Exception
    {
        String[] keys = {"UserID", "ClassID", "AttnTime"};
        String[] whereKeys = {"ClassID"};
        String[] whereVals = {Integer.toString(classID)};
        return runQuery("SELECT", "Attendance", keys, null, whereKeys, whereVals, 0, 0);
    }
    
    /*
     * ResultSet checkClass
     * @author David Worth
     * @param int classID
     *         The id of the class to check
     * 
     * @return ResultSet
     *         returns a result set of any classes associated with the given class ID
     * 
     * @exception Exception
     *         throws an exception if the database cannot be reached
     *         throws an exception if the database cannot be read
     */
    public ResultSet checkClass(int classID) throws Exception
    {
        String[] keys = {"ClassID", "ClassName", "MeetTimes", "AdminID", "Institution"};
        String[] whereKeys = {"ClassID"};
        String[] vals = {Integer.toString(classID)};
        return runQuery("SELECT", "Classes", keys, null, whereKeys, vals, 0, 0);
    }
    
    /*
     * boolean addDevice
     * @author David Worth
     * @param String username
     *         The student's username
     * 
     * @param int classID
     *         The ID of the class
     * 
     * @param String deviceID
     *         The MAC of the device
     * 
     * @return boolean
     *         returns true if the device is successfully added to the table
     * 
     * @exception Exception
     *         throws an exception if the database cannot be reached
     *         throws an exception if the arguments are invalid
     */
    public boolean addDevice(String username, int classID, String deviceID, String flag) throws Exception
    {
        int userID = getUserID(username);
        
        String[] keys = {"UserID", "ClassID", "DeviceID","DeviceChangeFlag"};
        String[] vals = {Integer.toString(userID), Integer.toString(classID), deviceID, flag};
        runQuery("INSERT", "Enrollment", keys, vals, null, null, 0, 0);
        
        return true;
    }
    
    /*
     * boolean changeDeviceForUser
     * @author David Worth
     * @param String username
     *         The student's username
     * 
     * @param int classID
     *         The ID of the class
     * 
     * @param String deviceID
     *         The new MAC of the device
     * 
     * @return boolean
     *         returns true if the device is successfully changed on the table
     * 
     * @exception Exception
     *         throws an exception if the database cannot be reached
     *         throws an exception if the arguments are invalid.
     */
    public boolean changeDeviceForUser(String username, int classID, String deviceID) throws Exception
    {
        int userID = getUserID(username);
        
        String[] keys = {"DeviceID"};
        String[] vals = {deviceID};
        String[] whereKeys = {"UserID", "ClassID"};
        String[] whereVals = {Integer.toString(userID), Integer.toString(classID)};
        runQuery("UPDATE", "Devices", keys, vals, whereKeys, whereVals, 0, 1);
        
        return true;
    }
    
    /*
     * ResultSet getUserInfo
     * @author David Worth
     * @param int userID
     *         the ID of the user
     * 
     * @return ResultSet
     *         returns a result set containing all of the stored user info for the @userID.
     * 
     * @exception Exception
     *         throws an exception if the database cannot be reached
     *         throws an exception if the user does not exist.
     */
    public ResultSet getUserInfo(int userID) throws Exception
    {
        String[] keys = {"ID", "FName", "LName", "UName", "Password", "RegTime"};
        String[] whereKeys = {"ID"};
        String[] whereVals = {Integer.toString(userID)};
        return runQuery("SELECT", "Users", keys, null, whereKeys, whereVals, 0, 1);
    }
    
    /*
     * static void dump
     * @author David Worth
     * @param ResultSet rs
     *         The result set to dump
     * 
     * @return void
     *         Prints the contents of the result set.
     * 
     * @exception Exception
     *         Throws an exception if the Resultset is invalid
     */
  public static void dump(ResultSet rs) throws SQLException
  {
      System.out.println("Dumping...");
      
      ResultSetMetaData rsmd = rs.getMetaData();
      int columnsNumber = rsmd.getColumnCount();
      while(rs.next())
      {
          for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = rs.getString(i);
                System.out.print(rsmd.getColumnName(i) + ": "+columnValue);
            }
          System.out.println();
      }
  }
  
  
  
  
  
  /*public static void main(String[] args)
  {
      MySQLAccess my = new MySQLAccess();
      
      try {
        my.connect("138.86.122.233", "se2014", "se2014", "STACDB");
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
     
      try{
         //my.createUser("newtest", "newpass", "New Test", "User", "newtest@user.com");
          //my.createAdmin("NewAdmin", "NewAdminPass", "New", "Admin", "new@admin.com");
          //my.createClass(20140113, "Test Class", "5", "UNC", "M:0900-1000");
                 //my.addDevice("g.house", 12992015, "D5:C8:FF:EF:A9:B8");
                 //my.changeDeviceForUser("g.house", 12992015, "BB:BB:BB:BB:BB:BB");
      }catch(Exception e)
      {
          System.out.println(e);
          e.printStackTrace();
      }
      
      try{
          String[] keys = {"ID",  "FName", "LName", "UName", "Password", "RegTime"};
          ResultSet rs = my.runQuery("SELECT", "Users", keys, null, null, null, 0, 0);
          dump(rs);
          
          String[] keys2 = {"ClassID", "ClassName", "Institution", "AdminID", "MeetTimes"};
          rs = my.runQuery("SELECT", "Classes", keys2, null, null, null, 0, 0);
          dump(rs);
          
          rs = my.runQuery("SELECT", "Admins", keys, null, null, null, 0, 0);
          dump(rs);
                  
                  String[] deviceKeys = {"ID", "UserID", "ClassID", "DeviceID"};
                  rs = my.runQuery("SELECT", "Devices", deviceKeys, null, null, null, 0, 0);
                  dump(rs);
                
                  //Dump Attendance info when ready...
                 /* String[] attnKeys = {"ID", "UserID", "ClassID", "AttnTime"};
                  rs = my.runQuery("SELECT", "Attendance", attnKeys, null, null, null, 0, 0);
                  dump(rs);
                 */
      /*}catch(Exception e)
      {
          System.out.println(e);
          e.printStackTrace();
      }
  }*/
}

