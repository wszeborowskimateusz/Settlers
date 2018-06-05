package settlersi.PT.lab.Main.characters;

import settlersi.PT.lab.Main.Settlers;

import java.util.ArrayList;

public class Warrior extends Thread {
    private KingdomType kingdomType;
    private int healthPoints;
    private int morale;
    private boolean alive;
    private King king;

    void receiveArmor() {
        this.healthPoints += 300;
    }

    private synchronized void receiveHit(int healthPointsLoss) {
        this.healthPoints -= healthPointsLoss;
        if (this.healthPoints <= 0) {
            this.alive = false;
            if (this.kingdomType == KingdomType.BLUE)
                Settlers.blueKingdomWarriors.remove(this);
            else
                Settlers.redKingdomWarriors.remove(this);
        }
    }


    private void hit(Warrior enemy) {
        if (alive)
            enemy.receiveHit(this.morale + king.getSkillLevel());
    }

    @Override
    public void run() {
        ArrayList<Warrior> enemies;
        if (this.kingdomType == KingdomType.BLUE) {
            enemies = Settlers.redKingdomWarriors;
            king = Settlers.blueKingdomKing;
        } else {
            enemies = Settlers.blueKingdomWarriors;
            king = Settlers.redKingdomKing;
        }

        synchronized (Settlers.lock) {
            while (!Settlers.FIGHT) {
                try {
                    Settlers.lock.wait();
                } catch (InterruptedException e) {
                    System.out.print("warrior interrupted\n");
                }
            }
        }

        try {
            Thread.sleep(Settlers.rand.nextInt(500) + 500 - this.morale);
        } catch (InterruptedException ignored) {
        }

        while (alive && enemies.size() > 0) {
            hit(enemies.get(Settlers.rand.nextInt(enemies.size())));

            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException ignored) {
                    break;
                }
            }
        }

    }

    public Warrior(KingdomType kingdomType) {
        super();
        this.kingdomType = kingdomType;
        this.morale = 10;
        this.healthPoints = 100;
        this.alive = true;
    }
}
