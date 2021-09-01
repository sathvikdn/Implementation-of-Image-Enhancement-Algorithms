package imageprocessing;
import static imageprocessing.Main.height;
import static imageprocessing.Main.img;
import static imageprocessing.Main.width;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.imageio.ImageIO;
public class Main
{
static BufferedImage img;
static int width;
static int height;
public static void main(String args[])throws IOException, InterruptedException
{
img = null;
File f = null;
// read image
try
{
f = new File("F:\\Inp.jpg");
img = ImageIO.read(f);
}
catch(IOException e)
{
System.out.println(e);
}
// Get image width and height
width = img.getWidth();
height = img.getHeight();
ArrayList<Thread> th = new ArrayList<>();
for(int i =0;i<3;i++){
for(int j=0;j<3;j++){
Thread object = new Thread(new MultithreadingDemo(i,j));
th.add(object);//Inbuilt Function from arraylist Package
object.start();//Runs “RUN” Function in the thread
}
}
for (Thread thread : th) {
thread.join();
}
// write image
try
{
f = new File("F:\\Out.jpg");
ImageIO.write(img, "jpg", f);
}
catch(IOException e)
{
System.out.println(e);
}
}
}
class MultithreadingDemo implements Runnable
{
int i,j;
public MultithreadingDemo(int i ,int j) {
this.i = i;
this.j = j;
}
@Override
public void run()
{
int x=0,y=0;
try
{
for ( x = (int)(i*(float)(width/3.0)); x < (int)((i+1)*(float)(width/3.0)); x++)
{
for ( y = (int)(j*(float)(height/3.0)); y < (int)((j+1)*(float)(height/3.0)); y++)
{
int p = img.getRGB(x,y);
int a = (p>>24)&0xff;
int r = (p>>16)&0xff;
int g = (p>>8)&0xff;
int b = p&0xff;
//subtract RGB from 255
r = 255 - r;
g = 255 - g;
b = 255 - b;
//set new RGB value
p = (a<<24) | (r<<16) | (g<<8) | b;
img.setRGB(x, y, p);
}
}
}
catch (Exception e)
{
// Throwing an exception
e.printStackTrace();
}
}
}