//this program is to get the graph of relation between time and no_of_threads.
    // 1) The main objective here is to create a table of no_of_threads vs time of execution 
    // 2) Next the objective is to select the best possible thread_number for that image with that perticular
    //    system configuration .
    // 3) Next objective is to have multiple data , Apply ml on it so you can determine best possble thread number before hand 
    // 4) I might not be able to include system configuration as a variable while applying ml .
    // 5) For now let us assume we take the system configuration as constant .

    // things i may not be able to implement 
    //1) system capacity as a variable in ml model
    //2) try out different models to find which one fits our case the best 

    // things i am implementing 
    //1) determine best possible thread no in O(1) after having ml model

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.image.*;

public class imageprocessing_thread {
    static int width;
    static int height;
    static int threads = 20;
    static List<Integer> no_of_threads = new ArrayList<>();
    static List<Long> time_of_execution = new ArrayList<>();

    public static void main(String args[]) throws IOException, InterruptedException {
        // Get image width and height
        width = read_file().getWidth();  // read_file() is a function which is implemented below
        height = read_file().getHeight();
        ArrayList<Thread> th ;
        for (int thread = 1 ; thread <= threads; thread++) {
            long start_time = System.currentTimeMillis();    // starting timer
            BufferedImage tempimage = read_file();
            th = new ArrayList<>();
            for( int i = 0 ; i < thread ; i++ ){
                Thread object = new Thread(new MultithreadingDemo(i, height, width, tempimage , thread));
                th.add(object);  // Inbuilt Function from arraylist Package
                object.start();  // Runs run Function in the thread
            }
            
            for (Thread i : th) {
                i.join();// waiting for all threads to execute 
            }
            // write image  
            long end_time = System.currentTimeMillis(); // stop the timer 
            time_of_execution.add(end_time-start_time);
            no_of_threads.add(thread);
            if(thread == threads){
                write_file(threads, tempimage); // print for threada = thread_no;
            }            
        }
        System.out.println(no_of_threads);
        System.out.println(time_of_execution);     
    }
    //function which reads the file and returns buffer image.
    static BufferedImage read_file(){
        try {
            // file where your image resides  
            File f = new File("E:\\Inp.jpg");//set your input path here  
            BufferedImage img = ImageIO.read(f);  
            return img;          
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    //function which writes the file .
    static void write_file(int thread ,BufferedImage tempimage){
        try {
             
            // set your output path here 
            File f = new File("E:\\Out using "+thread+"threads"+".jpg");// path of the output file
            ImageIO.write(tempimage, "jpg", f);
        } catch (IOException e) {
            System.out.println(e);
        }

    }
}

class MultithreadingDemo implements Runnable {
    int i, j, height, width,total_thread;
    BufferedImage img;

    public MultithreadingDemo(int i, int height, int width, BufferedImage img ,int total_thread) {
        this.i = i;
        this.total_thread = total_thread;
        this.height = height;
        this.width = width;
        this.img = img;
    }

    @Override
    public void run() {
        int x = 0, y = 0;
        try {
            for (x = (int) (i * (float) (width / ((double) total_thread))); x < (int) ((i + 1)
                    * (float) (width / ((double)total_thread))); x++) {
                for (y = 0; y < height ; y++) {
                    int p = img.getRGB(x,y);
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

