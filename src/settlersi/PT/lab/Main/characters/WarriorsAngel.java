package settlersi.PT.lab.Main.characters;

import settlersi.PT.lab.Main.Controller;
import settlersi.PT.lab.Main.Settlers;

import java.util.ArrayList;

public class WarriorsAngel extends Thread {

    private ArrayList<Warrior> warriors;
    private int channelingPowerTime;
    private KingdomType kingdomType;

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(Settlers.rand.nextInt(channelingPowerTime) + channelingPowerTime);
            } catch (InterruptedException ignored) {
            }


            if (warriors.size() <= 0) {
                Controller controller = Settlers.loader.getController();
                controller.endSimulation(kingdomType);
                break;
            }

            Warrior warrior = warriors.get(Settlers.rand.nextInt(warriors.size()));


            synchronized (warrior) {
                warrior.notify();
            }
        }
    }

    public WarriorsAngel(ArrayList<Warrior> warriors, int channelingPowerTime, KingdomType kingdomType) {
        this.channelingPowerTime = channelingPowerTime;
        this.warriors = warriors;
        this.kingdomType = kingdomType;
    }
}
