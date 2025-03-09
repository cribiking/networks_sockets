package SharedUnsafe_s4;

/*
Example Using only Synchronized
 */

public class Homework_s4 {
    private static int x1 = 0;

    public static void main(String[] args)
            throws InterruptedException {

        Decrementer dec = new Decrementer();

        Thread t1 = new Thread(dec, "T1");
        Thread t2 = new Thread(dec, "T2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Resultat: "+x1);
    }

    public static class Decrementer implements Runnable{

        public Decrementer(){
        }

        @Override
        public synchronized void run() {
            for (int i = 0;i<500000;i++){
                x1--;
            }
        }
    }

}
