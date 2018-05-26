package settlersi.PT.lab.Main.characters;

import settlersi.PT.lab.Main.Controller;
import settlersi.PT.lab.Main.Settlers;

public class Jeweller extends Thread {
    private KingdomType kingdomType;
    private int producingTime;
    private Princess princess;

    @Override
    public void run() {
        Controller controller = Settlers.loader.getController();
        while (true) {
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException ignored) {
                }
            }
            controller.addEvent("Jeweller has just received gold\n" +
                    "Jeweller is starting jewellery production!\n", this.kingdomType);
            try {
                Thread.sleep(Settlers.rand.nextInt(producingTime) + producingTime);
            } catch (InterruptedException ignored) {
            }

            synchronized (this.princess) {
                this.princess.notify();
            }
        }
    }

    public Jeweller(Princess k, KingdomType kingdomType, int producingTime) {
        this.kingdomType = kingdomType;
        this.producingTime = producingTime;
        this.princess = k;
    }
}
