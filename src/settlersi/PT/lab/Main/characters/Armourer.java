package settlersi.PT.lab.Main.characters;

import settlersi.PT.lab.Main.Controller;
import settlersi.PT.lab.Main.Settlers;

import java.util.ArrayList;

public class Armourer extends Character {
    private KingdomType kingdomType;
    private int timeForMakingArmor;
    private ArrayList<Warrior> warriors;

    @Override
    public void run() {
        Controller controller = Settlers.loader.getController();
        while (true) {
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException ignored) {
                    break;
                }
            }

            if(getAmmountOfFood() > 0) {
                consumeFood();
                try {
                    controller.addEvent("Armourer has just got a steal!\nStarting production of armor\n", this.kingdomType);
                    Thread.sleep(Settlers.rand.nextInt(timeForMakingArmor) + timeForMakingArmor);
                } catch (InterruptedException ignored) {
                }

                if (warriors.size() <= 0)
                    break;

                Warrior warrior = warriors.get(Settlers.rand.nextInt(warriors.size()));

                warrior.receiveArmor();
            }

        }
    }

    public Armourer(ArrayList<Warrior> warriors, KingdomType kingdomType, int timeForMakingArmor) {
        this.kingdomType = kingdomType;
        this.timeForMakingArmor = timeForMakingArmor;
        this.warriors = warriors;
    }
}
