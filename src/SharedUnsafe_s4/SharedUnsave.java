package SharedUnsafe_s4;

import java.util.concurrent.atomic.AtomicInteger;

public class SharedUnsave {
    private static int x;
    private static AtomicInteger x2 = new AtomicInteger(0);
    public SharedUnsave() {}



    ///////////////////////////////MAIN/////////////////////////////

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Inici");

        for (int i = 0; i < 2; i++) {
            Adder adder = new Adder();
            Thread t = new Thread(adder , "T"+i);
            t.start();
            t.join();//Esperant a que acabi el primer thread
        }
        System.out.println("Resultat: "+ x2.get() );
    }

    public static class Adder implements Runnable {


        @Override
        public void run() {
            try {
                for (int i = 0; i < 500000; i++){
                    x += 1;
                    x2.incrementAndGet();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
