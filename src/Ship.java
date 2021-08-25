class Ship implements Runnable {
    private Thread t;
    private String threadName;
    private int numberOfContainers;
    private int counter = 0;
    public String[] containers;
    public static int amountOfCranes;
    public static String[] containerSpots;
    private boolean loop = true;
    public static boolean[] test;
    private static boolean test2 = true;
    private static int counter2 = 0;
    private int counter3 = 0;
    private long millis = System.currentTimeMillis();
    Crane[] arrayOfCranes;

    Ship(String name, int containers, int cranes) {
        threadName = name;
        numberOfContainers = containers;
        amountOfCranes = cranes;
        arrayOfCranes = new Crane[amountOfCranes];
    }


    public void setCrane(Crane c) {
        this.arrayOfCranes[counter2] = c;
        counter2++;
    }


    public void run() {
        containers = new String[numberOfContainers];
        containerSpots = new String[amountOfCranes];
        test = new boolean[amountOfCranes];
        for (int i = 1; i <= numberOfContainers; i++) {
            containers[i - 1] = "container " + i;
        }

        for (int i = 0; i < amountOfCranes; i++) {
            containerSpots[i] = "";
            test[i] = false;
        }

        giveUp();

        while (loop) {
            for (int i = 0; i < amountOfCranes; i++) {
                if (containerSpots[i] != "") {
                    synchronized (arrayOfCranes[i]) {
                        arrayOfCranes[i].notify();
                    }
                }
            }


            if (counter == 99) {
                System.out.println((System.currentTimeMillis() - millis) + ": " + "all containers given up for pickup");
                loop = false;
            }
        }
    }

    public void lowerCounter() {
        counter3--;
        giveUp();
    }

    public void giveUp() {
        for (int i = 0; i < amountOfCranes; i++) {
            if (counter3 != 2 && containerSpots[i] == "") {
                containerSpots[i] = containers[counter];
                test[i] = true;
                containers[counter] = "";
                System.out.println((System.currentTimeMillis() - millis) + ": " + containerSpots[i] + " being given up for pickup by " + threadName);
                counter++;
                counter3++;
            }
        }
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    public synchronized static String pickUp() {

        String returnStr = "";
        for (int i = 0; i < amountOfCranes; i++) {
            if (containerSpots[i] != "") {
                returnStr = containerSpots[i];
                containerSpots[i] = "";
                test[i] = false;
                return returnStr;
            }
        }
        return returnStr;
    }

    public synchronized static boolean ready() {
        boolean returnBool = false;
        for (int i = 0; i < amountOfCranes; i++) {
            if (test[i]) {
                returnBool = true;
            }
        }
        return returnBool;
    }
}
