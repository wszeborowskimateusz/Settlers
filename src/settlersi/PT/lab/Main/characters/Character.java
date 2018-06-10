package settlersi.PT.lab.Main.characters;

public abstract class Character extends Thread{
    int ammountOfFood = 0;

    synchronized void receiveFood(int amount){
        ammountOfFood+=amount;
    }
    synchronized void consumeFood(){
        ammountOfFood--;
    }
    synchronized int getAmmountOfFood(){return ammountOfFood;}
}
