package settlersi.PT.lab.Main.characters;

import settlersi.PT.lab.Main.Controller;
import settlersi.PT.lab.Main.Settlers;

public class King extends Thread {
    private KingdomType kingdomType;
    private int skillLevel;

    public int getSkillLevel() {
        return this.skillLevel;
    }

    @Override
    public void run() {
        Controller controller = Settlers.loader.getController();
        while (true) {
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    System.out.print("King has been interrupted\n");
                }
                this.skillLevel++;
                controller.addEvent("The King has just leveled up!\n", this.kingdomType);
            }
        }
    }

    public King(KingdomType kingdomType) {
        this.kingdomType = kingdomType;
        this.skillLevel = 0;
    }
}
