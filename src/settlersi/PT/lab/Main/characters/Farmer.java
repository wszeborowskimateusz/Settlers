package settlersi.PT.lab.Main.characters;

import settlersi.PT.lab.Main.Settlers;

public class Farmer extends Thread {
    private Miner miner;
    private int harvestingTime;

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(Settlers.rand.nextInt(harvestingTime) + harvestingTime);
            } catch (InterruptedException ignored) {
                break;
            }



            synchronized (this.miner) {
                this.miner.notify();
            }
        }
    }

    public Farmer(Miner miner, int harvestingTime) {
        this.harvestingTime = harvestingTime;
        this.miner = miner;
    }
}
