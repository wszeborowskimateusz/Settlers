package settlersi.PT.lab.Main.characters;

import settlersi.PT.lab.Main.Controller;
import settlersi.PT.lab.Main.Settlers;

public class Princess extends Character {
    private KingdomType kingdomType;
    private final King king;
    private int amountOfJewelery;

    public int getHappiness() {
        return this.amountOfJewelery;
    }

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
                controller.addEvent("Princess has just received a fine piece of jewelery!\n", this.kingdomType);
                synchronized (this.king) {
                    amountOfJewelery++;
                    this.king.notify();
                }
            }
        }
    }

    public Princess(King king, KingdomType kingdomType) {
        this.kingdomType = kingdomType;
        this.king = king;
        this.amountOfJewelery = 0;
    }
}
