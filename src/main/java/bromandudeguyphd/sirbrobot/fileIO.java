package bromandudeguyphd.sirbrobot;

/**
 * @author aaf8553
 */
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import sx.blah.discord.handle.obj.IInvite;

public class fileIO {

    /**
     * Reads the specified file and returns the content as a String.
     *
     * @param list
     * @param fileName
     * @throws IOException thrown if an I/O error occurs opening the file
     */
    public static void readFile(ArrayList<String> list, String fileName) throws IOException {

        try {
            FileReader inputFile = new FileReader(fileName);
            try (
                    BufferedReader bufferReader = new BufferedReader(inputFile)) {
                String line;
                while ((line = bufferReader.readLine()) != null) {
                    list.add(line);
                }
                bufferReader.close();
            }

        } catch (Exception e) {
            System.out.println("Error while reading file line by line:" + e.getMessage());
        }
    }
    
    public static void htmlFile(ArrayList<String> list, String fileName) {

        try (PrintWriter writer = new PrintWriter("src/dataDocuments/HTMLfiles/"+fileName+".html")) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                String str = list.get(i);
                writer.println(str);
            }
            writer.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found");
            
        }

    }

    /**
     * Saves File
     *
     * @param list
     * @param fileName
     * @param imagePath
     */
    public static void save(ArrayList<String> list, String imagePath) {

        File fileOne = new File(imagePath);
        
        try (PrintWriter writer = new PrintWriter(fileOne)) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                String str = list.get(i);
                writer.println(str);
            }
            writer.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found");
            Logger.getLogger(fileIO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Saves Image from URL
     * 
     * @param imageUrls
     * @param imageName
     * @param imagePath
     * @throws java.net.MalformedURLException
     * @throws IOException
     */
    public static void saveImage(String imageUrls, String imageName, String imagePath) throws MalformedURLException, IOException {
        
        ArrayList fileErrors = new ArrayList<>();
        fileErrors.add("\\");fileErrors.add("/");fileErrors.add(":");fileErrors.add("?");fileErrors.add("\"");fileErrors.add("<");fileErrors.add(">");fileErrors.add("|");

        for(int i = 0; i < fileErrors.size(); i ++){
            if(imageName.contains(fileErrors.get(i).toString())){
                imageName.replace(fileErrors.get(i).toString(), "");
            }
        }
        
        File picutreFile = new File(imagePath + imageName);
        URL url = new URL(imageUrls);
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:31.0) Gecko/20100101 Firefox/31.0");
        conn.connect();
        FileUtils.copyInputStreamToFile(conn.getInputStream(), picutreFile);

    }
    

    /**
     * Saves Maps to file
     *
     * @param map
     * @param filename
     */

    public static void saveHash(Map<String, String> map, String filename) {
        try {
            File fileOne = new File("src/dataDocuments/"+filename);
            FileOutputStream fos = new FileOutputStream(fileOne);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(map);
            oos.flush();
            oos.close();
            fos.close();
        } catch (Exception e) {
        }

    }

    /**
     * Reads in Maps
     *
     * @param map
     * @param filename
     */
    public static void readHash(Map<String, String> map, String filename) {
        try {
            File toRead = new File("src/dataDocuments/"+filename);
            FileInputStream fis = new FileInputStream(toRead);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Map<String, String> mapInFile = (Map<String, String>) ois.readObject();

            ois.close();
            fis.close();
            //print All data in MAP
            for (Map.Entry<String, String> m : mapInFile.entrySet()) {
                map.put(m.getKey(), m.getValue());
            }
        } catch (Exception e) {
        }
    }  
    
}