package deadlock;/*
Based on https://www.baeldung.com/java-deadlock-livelock
 */


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockConcurrentAPIExample {

    private Lock lock1 = new ReentrantLock(true);
    private Lock lock2 = new ReentrantLock(true);

    public static void main(String[] args) {

        DeadlockConcurrentAPIExample deadlockExample = new DeadlockConcurrentAPIExample();
        new Thread(deadlockExample::operation1, "T1").start();
        new Thread(deadlockExample::operation2, "T2").start();
    }

    public void operation1() {
        String thread = Thread.currentThread().getName();
        lock1.lock();
        System.out.println(thread +": lock1 acquired, waiting to acquire lock2.");
        try {
            Thread.sleep(50);

            lock2.lock();
            System.out.println("lock2 acquired");

            System.out.println("executing first operation.");

            lock2.unlock();
            lock1.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void operation2() {
        String thread = Thread.currentThread().getName();

        lock2.lock();
        System.out.println(thread +": lock2 acquired, waiting to acquire lock1.");
        try {
            Thread.sleep(50);

            lock1.lock();
            System.out.println("lock1 acquired");

            System.out.println("executing second operation.");

            lock1.unlock();
            lock2.unlock();
        } catch (
                InterruptedException e) {
            e.printStackTrace();
        }
    }

}

