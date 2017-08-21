/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnections;

import bromandudeguyphd.sirbrobot.tokens;
import java.sql.*;  
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sx.blah.discord.handle.obj.IMessage;



/**
 *
 * @author Andrew
 */


public class queries {

    
    public static String sendDataDB(String query) throws SQLException {
        Connection con = DriverManager.getConnection(tokens.dbConnection(), tokens.dbUsername(), tokens.dbPassword() );  
        try{
        Class.forName("com.mysql.jdbc.Driver"); 
        Statement stmt = con.createStatement();  
        stmt.executeUpdate(query);
        
        
       

         }
         catch(ClassNotFoundException | SQLException err){
             System.out.println(err.getMessage());
             return "ERROR";
             
    }
        con.close();
        return "Success!";
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
        try{
        Class.forName("com.mysql.jdbc.Driver"); 
        Connection con = DriverManager.getConnection(tokens.dbConnection(), tokens.dbUsername(), tokens.dbPassword() );  
        stmt = con.createStatement();  
        
        return stmt.executeQuery(query);
       
         }
         catch(SQLException err){
             System.out.println(err.getMessage());
    }       catch (ClassNotFoundException ex) {
                Logger.getLogger(queries.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                return stmt.executeQuery(query);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            
            return null;
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
    System.out.println("Event Reached");
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
