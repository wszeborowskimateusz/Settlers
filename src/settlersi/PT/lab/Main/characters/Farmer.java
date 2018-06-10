package settlersi.PT.lab.Main.characters;

import settlersi.PT.lab.Main.Settlers;

import java.util.ArrayList;

public class Farmer extends Thread {
    private int harvestingTime;
    private ArrayList<Character> characters;

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(Settlers.rand.nextInt(harvestingTime) + harvestingTime);
            } catch (InterruptedException ignored) {
                break;
            }

            if(characters.size() > 0) {
                Character character = characters.get(Settlers.rand.nextInt(characters.size()));
                int amount = Settlers.rand.nextInt(10) + 5;
                character.receiveFood(amount);
            }
        }
    }

    public Farmer(ArrayList<Character> characters, int harvestingTime) {
        this.harvestingTime = harvestingTime;
        this.characters = characters;
    }
}
