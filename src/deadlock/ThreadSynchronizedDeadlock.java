package deadlock;

public class ThreadSynchronizedDeadlock extends Thread {
    private Object resource1;
    private Object resource2;


    public ThreadSynchronizedDeadlock(String threadId, Object o1, Object o2) {
        super(threadId);
        this.resource1 = o1;
        this.resource2 = o2;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + " trying to lock: " + resource1);

        synchronized (resource1) {

            System.out.println(name + " acquired lock on: " + resource1);
            work();
            System.out.println(name + " trying to lock: " + resource2);

            synchronized (resource2) {

                System.out.println(name + " acquired lock on: " + resource2);
                work();
            }

            System.out.println(name + " released lock on " + resource2);
        }

        System.out.println(name + " released lock on " + resource1);
        System.out.println(name + " finished execution.");
    }

    private void work() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}