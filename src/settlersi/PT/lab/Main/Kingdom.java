package settlersi.PT.lab.Main;

import settlersi.PT.lab.Main.characters.KingdomType;
import settlersi.PT.lab.Main.characters.Warrior;


class Kingdom {
    private static final int AMOUNT_OF_WARRIORS = 50;
    private KingdomType kingdomType;

    String showState() {
        int amountOfLivingWarriors;
        int kingsSkillLevel;
        int princessHappiness;
        if (this.kingdomType == KingdomType.BLUE) {
            amountOfLivingWarriors = Settlers.blueKingdomWarriors.size();
            kingsSkillLevel = Settlers.blueKingdomKing.getSkillLevel();
            princessHappiness = Settlers.blueKingdomPrincess.getHappiness();
        } else {
            amountOfLivingWarriors = Settlers.redKingdomWarriors.size();
            kingsSkillLevel = Settlers.redKingdomKing.getSkillLevel();
            princessHappiness = Settlers.redKingdomPrincess.getHappiness();
        }

        return "Amount of warriors: " + amountOfLivingWarriors + "\nKing's level: " +
                kingsSkillLevel + "\nPrincess happiness: " + princessHappiness;
    }

    Kingdom(KingdomType kingdomType) {
        this.kingdomType = kingdomType;

        if (kingdomType == KingdomType.BLUE) {
            for (int i = 0; i < AMOUNT_OF_WARRIORS; i++)
                Settlers.blueKingdomWarriors.add(new Warrior(kingdomType));
        } else {
            for (int i = 0; i < AMOUNT_OF_WARRIORS; i++)
                Settlers.redKingdomWarriors.add(new Warrior(kingdomType));
        }
    }

    void removeAllWarriors(){
        if (kingdomType == KingdomType.BLUE) {
            Settlers.blueKingdomWarriors.clear();
        } else {
            Settlers.redKingdomWarriors.clear();
        }
    }
}
