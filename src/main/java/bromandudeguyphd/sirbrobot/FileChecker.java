/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bromandudeguyphd.sirbrobot;

import java.io.*;

public class FileChecker {

   private static final String FILE_DIR = "E:\\Andrew\\Documents\\NetBeansProjects\\SirBroBotNEW";
   private static final String FILE_TEXT_EXT = ".m4a";
   private static final String FILE_TEXT_EXT2 = ".webm";
   private static final String FILE_TEXT_EXT3 = ".mp3";
    private static final String FILE_TEXT_EXT4 = ".wav";
     private static final String FILE_TEXT_EXT5 = ".part";
     private static final String FILE_TEXT_EXT6 = ".ogg";
     private static final String FILE_TEXT_EXT7 = ".mkv";
     private static final String FILE_TEXT_EXT8 = ".txt";


   public static void purge() {
	new FileChecker().deleteFile(FILE_DIR,FILE_TEXT_EXT);
        new FileChecker().deleteFile(FILE_DIR,FILE_TEXT_EXT2);
        new FileChecker().deleteFile(FILE_DIR,FILE_TEXT_EXT3);
        new FileChecker().deleteFile(FILE_DIR,FILE_TEXT_EXT4);
        new FileChecker().deleteFile(FILE_DIR,FILE_TEXT_EXT5);
        new FileChecker().deleteFile(FILE_DIR,FILE_TEXT_EXT6);
        new FileChecker().deleteFile(FILE_DIR,FILE_TEXT_EXT7);
        new FileChecker().deleteFile(FILE_DIR,FILE_TEXT_EXT8);
   }

   public void deleteFile(String folder, String ext){

     GenericExtFilter filter = new GenericExtFilter(ext);
     File dir = new File(folder);

     //list out all the file name with .txt extension
     String[] list = dir.list(filter);

     if (list.length == 0) return;

     File fileDelete;

     for (String file : list){
   	String temp = new StringBuffer(FILE_DIR)
                      .append(File.separator)
                      .append(file).toString();
    	fileDelete = new File(temp);
    	boolean isdeleted = fileDelete.delete();
    	System.out.println("file : " + temp + " is deleted : " + isdeleted);
     }
   }

   //inner class, generic extension filter
   public class GenericExtFilter implements FilenameFilter {

       private String ext;

       public GenericExtFilter(String ext) {
         this.ext = ext;
       }

       public boolean accept(File dir, String name) {
         return (name.endsWith(ext));
       }
    }
}
