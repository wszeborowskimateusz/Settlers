package settlersi.PT.lab.Main.characters;

import settlersi.PT.lab.Main.Controller;
import settlersi.PT.lab.Main.Settlers;

public class Miner extends Character {
    private SteelWorker steelWorker;
    private int miningTime;
    private KingdomType kingdomType;

    @Override
    public void run() {
        Controller controller = Settlers.loader.getController();
        while (true) {
            if(getAmmountOfFood() > 0) {
                consumeFood();
                try {

                    controller.addEvent("Miner has received something to eat\nMiner started mining\n", this.kingdomType);
                    Thread.sleep(Settlers.rand.nextInt(miningTime) + miningTime);
                } catch (InterruptedException ignored) {
                    break;
                }


                int randomMineral = Settlers.rand.nextInt(MaterialType.values().length);
                if (randomMineral == MaterialType.COAL.ordinal())
                    steelWorker.getCoal();
                else if (randomMineral == MaterialType.ORE.ordinal())
                    steelWorker.getOre();
                else {
                    steelWorker.getGold();
                }


                synchronized (this.steelWorker) {
                    this.steelWorker.notify();
                }
            }
        }
    }

    public Miner(SteelWorker steelWorker, int miningTime, KingdomType kingdomType) {
        this.miningTime = miningTime;
        this.steelWorker = steelWorker;
        this.kingdomType = kingdomType;
    }
}
