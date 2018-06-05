package settlersi.PT.lab.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import settlersi.PT.lab.Main.characters.*;

import java.util.ArrayList;
import java.util.Random;

public class Settlers extends Application {

    public static FXMLLoader loader;
    public static Random rand = new Random();
    public static boolean FIGHT = false;
    public static final Object lock = new Object();

    //Characters
    public  static ArrayList<Warrior> blueKingdomWarriors = new ArrayList<>();
    public  static ArrayList<Warrior> redKingdomWarriors = new ArrayList<>();
    public  static King blueKingdomKing = new King(KingdomType.BLUE);
    public  static King redKingdomKing = new King(KingdomType.RED);
     static Princess blueKingdomPrincess = new Princess(blueKingdomKing, KingdomType.BLUE);
     static Princess redKingdomPrincess = new Princess(redKingdomKing, KingdomType.RED);
     static Jeweller blueKingdomJeweller = new Jeweller(blueKingdomPrincess, KingdomType.BLUE, 3000);
     static Jeweller redKingdomJeweller = new Jeweller(redKingdomPrincess, KingdomType.RED, 3000);
     static Armourer blueKingdomArmourer = new Armourer(blueKingdomWarriors, KingdomType.BLUE, 300);
     static Armourer redKingdomArmourer = new Armourer(redKingdomWarriors, KingdomType.RED, 300);
     static SteelWorker blueKingdomSteelWorker = new SteelWorker(blueKingdomJeweller, blueKingdomArmourer, KingdomType.BLUE, 1000);
     static SteelWorker redKingdomSteelWorker = new SteelWorker(redKingdomJeweller, redKingdomArmourer, KingdomType.RED, 1000);
     static Miner blueKingdomMiner = new Miner(blueKingdomSteelWorker, 1000, KingdomType.BLUE);
     static Miner redKingdomMiner = new Miner(redKingdomSteelWorker, 500, KingdomType.RED);
     static Farmer blueKingdomFarmer = new Farmer(blueKingdomMiner, 300);
     static Farmer redKingdomFarmer = new Farmer(redKingdomMiner, 300);
     static WarriorsAngel blueKingdomWarriorsAngel = new WarriorsAngel(blueKingdomWarriors, 90, KingdomType.BLUE);
     static WarriorsAngel redKingdomWarriorsAngel = new WarriorsAngel(redKingdomWarriors, 100, KingdomType.RED);

    @Override
    public void start(Stage primaryStage) throws Exception {
        loader = new FXMLLoader(getClass().getResource("kingdomMap.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Clash of Kingdoms");
        primaryStage.getIcons().add(new Image("/icon.png"));
        primaryStage.setScene(new Scene(root, 600, 472));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
