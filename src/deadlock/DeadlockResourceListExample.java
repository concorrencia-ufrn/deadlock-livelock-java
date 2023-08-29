package deadlock;

public class DeadlockResourceListExample {

    public static void main(String[] args) throws InterruptedException {

        final int THREAD_GROUP_SIZE = 10;

        Object[] resources = new Object[THREAD_GROUP_SIZE];

        for (int i = 1; i <= THREAD_GROUP_SIZE; i++) {
            resources[i-1] = "Resource" + i;
        }

        for (int i = 1; i <= THREAD_GROUP_SIZE; i++) {

            Thread thread = new ThreadSynchronizedDeadlock("thread" + i, resources[i-1], resources[i%THREAD_GROUP_SIZE]);
            thread.start();
            Thread.sleep(100);

        }


    }
}