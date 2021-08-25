
class Truck implements Runnable {

    Quay q;
    private Thread t;
    private String threadName;
    private int timeToUnload;
    private int quaySpots;
    private String[] containerNames;
    private int loaded = 0;
    private String container;
    private long millis = System.currentTimeMillis();
    Truck(String name, int unload, int spots) {
        threadName = name;
        timeToUnload = unload;
        quaySpots = spots;
        containerNames = new String[quaySpots];
    }

    public void setQuay(Quay q) {
        this.q = q;
    }

    public void run() {
        while (true) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
            }

            for (int i = 0; i < quaySpots; i++) {
                containerNames[i] = q.getContainer(i);
                if (containerNames[i] != "" && loaded == 0) {
                    loaded = 1;
                    container = containerNames[i];
                    q.lowerCounter();
                    q.setFreeSpot(i);
                }
            }

            if (container != "") {
                System.out.println((System.currentTimeMillis() - millis) + ": " + threadName + " loading up " + container);
                try {
                    Thread.sleep(timeToUnload * 1000);
                } catch (Exception e) {
                    System.out.println(e);
                }
                System.out.println((System.currentTimeMillis() - millis) + ": " + threadName + " returning");

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
