


public class example {
    public static void main(String[] args) {     
        for(int i = 0 ; i < Integer.MAX_VALUE ; i++)       {
            Thread th = new Thread(new superclass(i));
            th.start();
            System.out.println(i);
        }
    }
}


class superclass implements Runnable {
    int i =0;
    superclass(int i){
        this.i = i;
    }
    @Override
    public void run(){
        try {
            Thread.sleep(Integer.MAX_VALUE);
            System.out.println("thread "+i+" here ");
        } catch (Exception e) {
            System.out.println(i);
            e.printStackTrace();

            //TODO: handle exception
        } 
    }

} 