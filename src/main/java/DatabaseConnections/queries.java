/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnections;

import bromandudeguyphd.sirbrobot.tokens;
import com.mysql.fabric.xmlrpc.base.Data;
import com.sun.rowset.CachedRowSetImpl;
import java.sql.*;  
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import sx.blah.discord.handle.obj.IMessage;



/**
 *
 * @author Andrew
 */


public class queries {

    static Connection con;
    public static void sendDataDB(String query) throws SQLException {
        Connection con = DriverManager.getConnection(tokens.dbConnection(), tokens.dbUsername(), tokens.dbPassword() );
        Statement stmt = con.createStatement();  
        try{
        Class.forName("com.mysql.jdbc.Driver"); 
        stmt.executeUpdate(query);
        stmt.close();
        con.close();
        
       
         }
         catch(ClassNotFoundException | SQLException err){
             System.out.println(err.getMessage());
             
    }

    }
        
        
        
        
        public static void sendDBWithMessage(String query, IMessage messageEvent, String messageContent) throws SQLException {

        try{
        Class.forName("com.mysql.jdbc.Driver"); 
        Connection con = DriverManager.getConnection(tokens.dbConnection(), tokens.dbUsername(), tokens.dbPassword() );  
        Statement stmt = con.createStatement();  
        stmt.executeUpdate(query);
        
        messageEvent.reply(messageContent);
       
        con.close();
         }
         catch(ClassNotFoundException | SQLException err){
             messageEvent.reply("Error communicatring with database");
             System.out.println(err.getMessage());
             
    }
        
    }
        
        
        public static ResultSet getDataDB(String query) {
        Statement stmt = null;
        ResultSet results = null;
        CachedRowSet rowset = null;
        try{
        Class.forName("com.mysql.jdbc.Driver");

          
        stmt = con.createStatement();  
        
        results = stmt.executeQuery(query);
        
        rowset = new CachedRowSetImpl();
        rowset.populate(results);
        con.close();
        
         }
         catch(SQLException err){
             System.out.println("ERROR IN GETDB QUERY"+err.getMessage());
    }   catch (ClassNotFoundException ex) {
            Logger.getLogger(queries.class.getName()).log(Level.SEVERE, null, ex);
        }
           
            
            return rowset;
            
    }
        
public static String GuildCreateQuery(String guildID){
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    String list = "None";

    try {
        connection = DriverManager.getConnection(tokens.dbConnection(), tokens.dbUsername(), tokens.dbPassword() );
        statement = connection.prepareStatement("select guild_id from guilds where guild_id ='"+guildID+"';");
        resultSet = statement.executeQuery();
        while (resultSet.next()) {
            list = resultSet.getString("guild_id");
        }
    }   catch (SQLException ex) {
            Logger.getLogger(queries.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        if (resultSet != null) try { resultSet.close(); } catch (SQLException logOrIgnore) {}
        if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
        if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
    }

    return list;

}

public static String UserJoinQuery(String guildID){
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    String list = null;

    try {
        connection = DriverManager.getConnection(tokens.dbConnection(), tokens.dbUsername(), tokens.dbPassword() );
        statement = connection.prepareStatement("select welcome_status from guilds where guild_id='"+guildID+"';");
        resultSet = statement.executeQuery();
        while (resultSet.next()) {
            list = resultSet.getString("welcome_status");
        }
    }   catch (SQLException ex) {
            Logger.getLogger(queries.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        if (resultSet != null) try { resultSet.close(); } catch (SQLException logOrIgnore) {}
        if (statement != null) try { statement.close(); } catch (SQLException logOrIgnore) {}
        if (connection != null) try { connection.close(); } catch (SQLException logOrIgnore) {}
    }

    return list;

}

    
public static ArrayList userJoinQuery(String guildID){
    Connection con = null;
        try {
            con = (Connection) DriverManager.getConnection(tokens.dbConnection(), tokens.dbUsername(), tokens.dbPassword() );
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        Statement stmt = null;
        String query = "SELECT guild_id, welcome_channel_id, welcome_channel_message "
                + "from guilds "
                + "where guild_id = '" + guildID + "';";
        
        try {
            stmt = (Statement) con.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                
                String channelID = rs.getString("welcome_channel_id");
                String message = rs.getString("welcome_channel_message");
                
                ArrayList<String> data = new ArrayList<>(4);
                data.add(channelID);
                data.add(message);
                
                return data;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        return null;
        
}


public static ArrayList voiceJoinQuery(String guildID){
        Connection con = null;
        try {
            con = (Connection) DriverManager.getConnection(tokens.dbConnection(), tokens.dbUsername(), tokens.dbPassword() );
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        

        
        
        Statement stmt = null;
        String query = "select guild_id, voice_announce_channel_id, voicejoin_status "
                + "from guilds "
                + "where guild_id = '" + guildID + "';";
        
        try {
            stmt = (Statement) con.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                
                String channelID = rs.getString("voice_announce_channel_id");
                String status = rs.getString("voicejoin_status");
                
                ArrayList<String> data = new ArrayList<>(4);
                data.add(status);
                data.add(channelID);
                
                return data;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        return null;
        
    }

public static ArrayList voiceLeaveQuery(String guildID){

        Connection con = null;
        try {
            con = (Connection) DriverManager.getConnection(tokens.dbConnection(), tokens.dbUsername(), tokens.dbPassword());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        

        
        
        Statement stmt = null;
        String query = "select guild_id, voice_announce_channel_id, voiceleave_status "
                + "from guilds "
                + "where guild_id = '" + guildID + "';";
        
        try {
            stmt = (Statement) con.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                
                String channelID = rs.getString("voice_announce_channel_id");
                String status = rs.getString("voiceleave_status");
                
                ArrayList<String> data = new ArrayList<>(4);
                data.add(status);
                data.add(channelID);
                
                return data;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        return null;
        
    }


public static ArrayList voiceMoveQuery(String guildID){

        Connection con = null;
        try {
            con = (Connection) DriverManager.getConnection(tokens.dbConnection(), tokens.dbUsername(), tokens.dbPassword());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        

        
        
        Statement stmt = null;
        String query = "select guild_id, voice_announce_channel_id, voicemove_status "
                + "from guilds "
                + "where guild_id = '" + guildID + "';";
        
        try {
            stmt = (Statement) con.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                
                String channelID = rs.getString("voice_announce_channel_id");
                String status = rs.getString("voicemove_status");
                
                ArrayList<String> data = new ArrayList<>(4);
                data.add(status);
                data.add(channelID);
                
                return data;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        return null;
        
    }


public static ArrayList welcomeView(String guildID){
    Connection con = null;
        try {
            con = (Connection) DriverManager.getConnection(tokens.dbConnection(), tokens.dbUsername(), tokens.dbPassword());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        Statement stmt = null;
        String query = "SELECT welcome_status, welcome_channel_message "
                + "from guilds "
                + "where guild_id = '" + guildID + "';";
        
        try {
            stmt = (Statement) con.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                
                String welcomeStatus = rs.getString("welcome_status");
                String message = rs.getString("welcome_channel_message");
                
                ArrayList<String> data = new ArrayList<>();
                data.add(welcomeStatus);
                data.add(message);
                
                return data;
            
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        return null;
        
}


}
