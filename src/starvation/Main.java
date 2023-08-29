package starvation;

public class Main {
    public static void main(String[] args) {
        System.out.println("Main thread Started");
        MyThread t1 = new MyThread("T1");
        MyThread t2 = new MyThread("T2");
        t1.start();
        t2.start();
        System.out.println("Main thread Ended");
    }
}

class MyThread extends Thread {
    public MyThread(String name) {
        super(name);
    }

    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " Started.");
        synchronized(MyThread.class) {
            System.out.println(threadName + " synchronized block.");
            while(true) { } // infinite loop
        }
    }
}