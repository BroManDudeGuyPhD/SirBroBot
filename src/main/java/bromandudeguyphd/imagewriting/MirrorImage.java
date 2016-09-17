/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bromandudeguyphd.imagewriting;

/**
 *
 * @author aaf8553
 */
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class MirrorImage {

static String imageName;

    public static void run() throws IOException {

        //BufferedImage for source image
        BufferedImage simg = null;
        //File object
        File f = null;
        //read source image file
        try {
            f = new File("src/images/imageWriting/imagesToManipulate/"+imageName);
            simg = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        //get source image dimension
        int width = simg.getWidth();
        int height = simg.getHeight();
        //BufferedImage for mirror image
        BufferedImage mimg = new BufferedImage(width * 2, height, BufferedImage.TYPE_INT_ARGB);
        //create mirror image pixel by pixel
        for (int y = 0; y < height; y++) {
            for (int lx = 0, rx = width * 2 - 1; lx < width; lx++, rx--) {
                //lx starts from the left side of the image
                //rx starts from the right side of the image
                //get source pixel value
                int p = simg.getRGB(lx, y);
                //set mirror image pixel value - both left and right
                mimg.setRGB(lx, y, p);
                mimg.setRGB(rx, y, p);
            }
        }
        try {
            f = new File("src/images/imageWriting/reversedImages/reversed"+imageName);
            ImageIO.write(mimg, "png", f);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
public static void setImage(String Name){
    MirrorImage.imageName = Name;
    
    

}
}
