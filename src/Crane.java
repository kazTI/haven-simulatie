import java.util.Random;

class Crane implements Runnable {
    private Thread t;
    private String threadName;
    private int holdingContainer = 0;
    private String container = "";
    private boolean loop = true;
    private int spots;
    private long millis = System.currentTimeMillis();
    Ship s;
    Quay q;

    Crane(String name) {
        threadName = name;
    }

    public void setShip(Ship s) {
        this.s = s;
    }
    public void setQuay(Quay q) {this.q = q;}

    public void run() {
        while (loop) {

            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
            }

            container = Ship.pickUp();


            if (container != "") {
                Random rand = new Random();
                System.out.println((System.currentTimeMillis() - millis) + ": " + threadName + " picking up " + container);
                holdingContainer = 1;
                try {
                    Thread.sleep(rand.nextInt(5001) + 1000);
                } catch (Exception e) {
                    System.out.println(e);
                }
                spots = q.getSpot();
                if(spots == 4)
                {
                    try {
                        synchronized (this) {
                            System.out.println((System.currentTimeMillis() - millis) + ": " + threadName + " waiting...");
                            wait();
                        }
                    } catch (InterruptedException e) {
                    }
                }
                System.out.println((System.currentTimeMillis() - millis) + ": " + threadName + " setting down " + container);
                s.lowerCounter();
                q.setSpot(container);
            }
        }
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}