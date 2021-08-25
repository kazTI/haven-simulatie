class Quay implements Runnable {
    private Thread t;
    private String threadName;
    private int amountOfSpots;
    public String[] spots;
    private int counter = 0;
    boolean loop = true;
    Truck[] arrayOfTrucks;
    private int counter2 = 0;
    private int counter3 = 0;
    private int amountOfTrucks;
    private int[] freeSpots;
    private boolean atleastOne = false;

    Quay(String name, int quaySpots, int trucks) {
        threadName = name;
        amountOfSpots = quaySpots;
        amountOfTrucks = trucks;
        spots = new String[amountOfSpots];
        arrayOfTrucks = new Truck[amountOfTrucks];
        freeSpots = new int[amountOfSpots];
        for (int i = 0; i < amountOfSpots; i++) {
            spots[i] = "";
        }
        for (int i = 0; i < amountOfSpots; i++) {
            freeSpots[i] = 0;
        }
    }

    public void setTruck(Truck t) {
        this.arrayOfTrucks[counter2] = t;
        counter2++;
    }

    public void run() {

        while (loop) {
            notifyTruck();
        }
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }


    public void setSpot(String name) {
        int foundSpot = 0;
        boolean loop2 = true;
        while(loop2) {
            for (int i = 0; i < amountOfSpots; i++) {
                if (freeSpots[i] == 0) {
                    counter++;
                    spots[i] = name;
                    foundSpot = 1;
                    freeSpots[i] = 1;
                    atleastOne = true;
                    foundSpot = 1;
                    notifyTruck();
                    break;
                }
            }
            if(foundSpot == 1)
            {
                loop2 = false;
            }

        }
    }

    public int getSpot(){
        return counter;
    }

    public String getContainer(int spot){
        return spots[spot];
    }

    public void lowerCounter() {
        counter--;
    }

    public void setFreeSpot(int var) {
        freeSpots[var] = 0;
    }

    public void notifyTruck()
    {
        for (int i = 0; i < amountOfSpots; i++) {
            if (atleastOne) {
                synchronized (arrayOfTrucks[counter3]) {
                    arrayOfTrucks[counter3].notify();
                    atleastOne = false;
                    counter3++;
                    if(counter3 == (amountOfTrucks - 1))
                    {
                        counter3 = 0;
                    }
                }
            }
        }
    }

    public void notifyCrane()
    {

    }

}