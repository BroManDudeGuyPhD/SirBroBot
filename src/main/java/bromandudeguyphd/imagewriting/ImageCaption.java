package bromandudeguyphd.imagewriting;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

/**
 * A simple class that adds subtitle to an image 
 * @author BroManDudeGuyPhD on 10.3.2017
 */
public class ImageCaption{
static BufferedImage bi;
static String workingDirectory;
static String caption;
static IGuild guild;
public static File output;

public ImageCaption(){
    workingDirectory = null;
    caption = null;
}

    
    public static File create(String directory, String message, IUser sender) {

        workingDirectory = directory;
        caption = message;
        
        try {
            //Randomly choose image from directory
            File dir = new File(directory);
            File[] files = dir.listFiles();
            Random rand = new Random();
            File f = files[rand.nextInt(files.length)];
            
            //Read image in
            bi = ImageIO.read(f);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        
        //Size of Displayed Image
        

        //Message
        String quotedMessage = "\""+message+" \" -"+sender.getName()+" ";
        
        //Breaks message into words and keeps words together on new lines with wrapText class
        String ParsedMessage = wrapText(bi.getWidth()/11, quotedMessage).replaceAll("  +"," ").trim();
        
        //Breaks lines int array
        String [] lines = ParsedMessage.split("\n");
        
        
        //Adds white space for longer messages
        
        
        return drawTextOnImage(ParsedMessage, bi, 30*lines.length, lines);
        

    }
    
   
    
    public static String wrapText(int textviewWidth, String message) {

        String temp = "";
        String sentence = "";

        String[] array = message.split(" "); // split by space

        for (String word : array) {

            if ((temp.length() + word.length()) < textviewWidth) {  // create a temp variable and check if length with new word exceeds textview width.

                temp += " "+word;

            } else {
                sentence += temp+"\n"; // add new line character
                temp = word;
            }

        }

        return (sentence.replaceFirst(" ", "")+temp);

    }
    
    private static File drawTextOnImage(String text, BufferedImage image, int space, String[] lines) {
        BufferedImage Image = new BufferedImage(image.getWidth(), image.getHeight() + space, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) Image.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setColor(Color.black);
        g2d.drawImage(image, 0, 0, null);
        
        
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Calibri", Font.BOLD, 25));
        FontMetrics fm = g2d.getFontMetrics();
        int lineHeight = g2d.getFontMetrics().getHeight();

        for (int lineCount = 0; lineCount < lines.length; lineCount++) { //lines from above
            int xPos = 10;
            int yPos = Image.getHeight()-space+20 + lineCount * lineHeight;
            String line = lines[lineCount];
            g2d.drawString(line, xPos, yPos);
            
        }
        g2d.dispose();

        return saveImageActionPerformed(Image);
        
        
    }
    
    private static File saveImageActionPerformed(BufferedImage image) {
     
         //save In a new directory 
         File saveFile = new File(workingDirectory+"/results/"+ caption +".png");
         try {
            ImageIO.write(image, "png", saveFile);
         } catch (IOException e) {
            e.printStackTrace();
         }
      return saveFile;
   }

    
    
}