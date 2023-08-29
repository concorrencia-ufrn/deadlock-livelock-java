package livelock;

/*
Based on https://www.baeldung.com/java-deadlock-livelock
 */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class LivelockSimpleExample {

    private Lock lock1 = new ReentrantLock(true);
    private Lock lock2 = new ReentrantLock(true);

    public static void main(String[] args) {
        LivelockSimpleExample livelock = new LivelockSimpleExample();
        new Thread(livelock::operation1, "T1").start();
        new Thread(livelock::operation2, "T2").start();
    }

    public void operation1() {
        String threadID = Thread.currentThread().getName();
        while (true) {
            lock1.tryLock();
            System.out.println("["+threadID+"] lock1 acquired, trying to acquire lock2.");
            try {
                sleep(50);

                if (lock2.tryLock()) {
                    System.out.println("["+threadID+"] lock2 acquired.");
                } else {
                    System.out.println("["+threadID+"] cannot acquire lock2, releasing lock1.");
                    lock1.unlock();
                    continue;
                }

                System.out.println("["+threadID+"] executing first operation.");
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lock2.unlock();
            lock1.unlock();
        }
    }


        public void operation2 () {
            String threadID = Thread.currentThread().getName();

            while (true) {
                lock2.tryLock();
                System.out.println("["+threadID+"] lock2 acquired, trying to acquire lock1.");
                try {
                    sleep(50);

                    if (lock1.tryLock()) {
                        System.out.println("["+threadID+"] lock1 acquired.");
                    } else {
                        System.out.println("["+threadID+"] cannot acquire lock1, releasing lock2.");
                        lock2.unlock();
                        continue;
                    }

                    System.out.println("["+threadID+"] executing second operation.");
                    break;


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                lock1.unlock();
                lock2.unlock();
            }
        }

}