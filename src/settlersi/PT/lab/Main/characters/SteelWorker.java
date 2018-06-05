package settlersi.PT.lab.Main.characters;

import settlersi.PT.lab.Main.Controller;
import settlersi.PT.lab.Main.Settlers;

public class SteelWorker extends Thread {

    private KingdomType kingdomType;
    private int productionTime;
    private Jeweller jeweller;
    private Armourer armourer;
    private boolean isCoal;
    private boolean isOre;
    private boolean isGold;

    void getCoal() {
        this.isCoal = true;
    }

    void getOre() {
        this.isOre = true;
    }

    void getGold() {
        this.isGold = true;
    }

    @Override
    public void run() {
        int previousDelivery = -1;
        Controller controller = Settlers.loader.getController();
        while (true) {
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException ignored) {
                    break;
                }
            }

            if (isGold) {
                try {
                    controller.addEvent("Steel worker has just received gold from Miner!\n" +
                            "Steel worker started gold production!\n", this.kingdomType);
                    this.isGold = false;
                    Thread.sleep(Settlers.rand.nextInt(productionTime * 2) + (productionTime * 2));
                } catch (InterruptedException ignored) {
                }

                synchronized (this.jeweller) {
                    this.jeweller.notify();
                }
                continue;
            }

            if (!isCoal && isOre) {
                controller.addEvent("Steel worker has just received ore from Miner!\n", this.kingdomType);
                previousDelivery = 0;
            } else if (isCoal && !isOre) {
                controller.addEvent("Steel worker has just received coal from Miner!\n", this.kingdomType);
                previousDelivery = 1;
            } else {
                if (previousDelivery == 0)
                    controller.addEvent("Steel worker has just received coal from Miner!\n", this.kingdomType);
                else
                    controller.addEvent("Steel worker has just received ore from Miner!\n", this.kingdomType);
                previousDelivery = -1;
            }

            if (!isCoal || !isOre) {
                continue;
            }

            try {
                controller.addEvent("Steel worker started steel production!\n", this.kingdomType);
                this.isOre = false;
                this.isCoal = false;
                Thread.sleep(Settlers.rand.nextInt(productionTime) + productionTime);
            } catch (InterruptedException ignored) {
            }
            synchronized (this.armourer) {
                this.armourer.notify();
            }

        }
    }

    public SteelWorker(Jeweller j, Armourer p, KingdomType kingdomType, int productionTime) {
        this.kingdomType = kingdomType;
        this.productionTime = productionTime;
        this.jeweller = j;
        this.armourer = p;
        this.isOre = false;
        this.isCoal = false;
    }

}
