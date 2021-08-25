
public class thread {
    private static int cranes = 2;
    private static int ships = 1;
    private static int trucks = 3;
    private static int quays = 1;
    private static int timeToUnload = 3;
    private static int containers = 100;
    private static int quaySpots = 5;

    public static void main(String args[]) {
        Crane[] Cranes;
        Ship[] Ships;
        Truck[] Trucks;
        Quay[] Quays;
        Cranes = new Crane[cranes];
        Ships = new Ship[ships];
        Trucks = new Truck[trucks];
        Quays = new Quay[quays];


        for (int q = 0; q < quays; q++)
        {
            String quayName = "Quay-" + (q+1);
            Quays[q] = new Quay(quayName, quaySpots, trucks);
            for (int s = 0; s < ships; s++)
            {
                String shipName = "Ship-" + (s+1);
                Ships[s] = new Ship(shipName, containers, cranes);
                for (int c = 0; c < cranes; c++)
                {
                    String craneName = "Crane-" + (c+1);
                    Cranes[c] = new Crane(craneName);
                    Cranes[c].setShip(Ships[s]);
                    Cranes[c].setQuay(Quays[q]);
                    Cranes[c].start();
                    Ships[s].setCrane(Cranes[c]);
                }

                for (int t = 0; t < trucks; t++)
                {
                    String truckName = "Truck-" + (t+1);
                    Trucks[t] = new Truck(truckName, timeToUnload, quaySpots);
                    Trucks[t].setQuay(Quays[q]);
                    Trucks[t].start();
                    Quays[q].setTruck(Trucks[t]);
                }
                Ships[s].start();
            }
            Quays[q].start();
        }
    }
}