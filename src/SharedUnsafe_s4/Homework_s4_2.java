package SharedUnsafe_s4;

import java.util.concurrent.atomic.AtomicInteger;

/*
Example Using only method lock-free
 */
public class Homework_s4_2 {

    private static AtomicInteger x = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Decrementer dec = new Decrementer();

        for(int i=0;i<2;i++){
            Thread t = new Thread(dec, "T"+i);
            t.start();
            t.join();
        }
        System.out.println(x.get());
    }

    public static class Decrementer implements Runnable {

        public Decrementer(){}

        @Override
        public void run() {
            for(int i=0;i<500000;i++){
                x.decrementAndGet();
            }
        }
    }

}
