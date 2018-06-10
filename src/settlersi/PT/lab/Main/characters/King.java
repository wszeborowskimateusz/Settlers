package settlersi.PT.lab.Main.characters;

import settlersi.PT.lab.Main.Controller;
import settlersi.PT.lab.Main.Settlers;

public class King extends Character {
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
                    break;
                }
                if(getAmmountOfFood() > 0) {
                    this.skillLevel++;
                    controller.addEvent("The King has just leveled up!\n", this.kingdomType);
                }
            }
        }
    }

    public King(KingdomType kingdomType) {
        this.kingdomType = kingdomType;
        this.skillLevel = 0;
    }
}
