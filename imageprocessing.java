// basic logic 
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.*;

public class imageprocessing {
    static BufferedImage img;
    static int width;
    static int height;
    static int no_of_threads = 20;

    public static void main(String args[]) throws IOException, InterruptedException {
        img = null;
        File f = null;
        // read image
        try {
            f = new File("E:\\Inp.jpg");//file where your image resides 
            img = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println(e);
        }
        // Get image width and height
        width = img.getWidth();
        height = img.getHeight();
        ArrayList<Thread> th = new ArrayList<>();
        for (int i = 0; i < no_of_threads; i++) {
            for (int j = 0; j < no_of_threads; j++) {
                Thread object = new Thread(new MultithreadingDemo(i, j,height,width,img,no_of_threads));
                th.add(object);// Inbuilt Function from arraylist Package
                object.start();// Runs “RUN” Function in the thread
            }
        }
        for (Thread thread : th) {
            thread.join();
        }
        // write image
        try {
            f = new File("E:\\Out.jpg");//path of the output file
            ImageIO.write(img, "jpg", f);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

class MultithreadingDemo implements Runnable{
    int i, j,height,width,no_of_threads;
    BufferedImage img;

    public MultithreadingDemo(int i, int j,int height,int width,BufferedImage img,int no_of_threads) {
        this.i = i;
        this.j = j;
        this.height = height;
        this.width = width;
        this.img = img;
        this.no_of_threads = no_of_threads;
    }

    @Override
    public void run() {
        int x = 0, y = 0;
        try {
            for (x = (int) (i * (float) (width / ((double)no_of_threads))); x < (int) ((i + 1) * (float) (width /((double)no_of_threads))); x++) {
                for (y = (int) (j * (float) (height /((double)no_of_threads))); y < (int) ((j + 1) * (float) (height / ((double)no_of_threads))); y++) {
                    int p = img.getRGB(x, y);
                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;
                    // subtract RGB from 255
                    r = 255 - r;
                    g = 255 - g;
                    b = 255 - b;
                    // set new RGB value
                    p = (a << 24) | (r << 16) | (g << 8) | b;
                    img.setRGB(x, y, p);
                }
            }
        } catch (Exception e) {
            // Throwing an exception
            e.printStackTrace();
        }
    }

}

