package settlersi.PT.lab.Main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import settlersi.PT.lab.Main.characters.*;
import settlersi.PT.lab.Main.characters.Character;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ImageView blueKingdomWins;

    @FXML
    private ImageView redKingdomWins;

    @FXML
    private Button fightButton;

    @FXML
    private TextArea blueKingdomStats;

    @FXML
    private TextArea redKingdomStats;

    @FXML
    private TextArea blueKingdomEvents;

    @FXML
    private TextArea redKingdomEvents;

    private Timeline statsRefresher;
    private static Kingdom redKingdom;
    private static Kingdom blueKingdom;

    private MediaPlayer mediaPlayer;

    public synchronized void addEvent(String event, KingdomType kingdomType) {
        if (this.blueKingdomEvents.getText().length() > 5000 || this.redKingdomEvents.getText().length() > 5000) {
            this.blueKingdomEvents.clear();
            this.redKingdomEvents.clear();
        }
        if (kingdomType == KingdomType.BLUE)
            this.blueKingdomEvents.appendText(event);
        else
            this.redKingdomEvents.appendText(event);
    }

    @Override
    public synchronized void initialize(URL url, ResourceBundle rb) {

        blueKingdomStats.setText("This is blue Kingdom");
        redKingdomStats.setText("This is red Kingdom");

        statsRefresher = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                blueKingdomStats.setText(blueKingdom.showState());
                redKingdomStats.setText(redKingdom.showState());
            }
        }));
        statsRefresher.setCycleCount(Timeline.INDEFINITE);

    }

    @FXML
    void fightButtonClick(MouseEvent event) {
        redKingdomWins.setVisible(false);
        blueKingdomWins.setVisible(false);

        fightButton.setDisable(true);
        fightButton.setText("FIGHT!");
        blueKingdomEvents.setText("");
        redKingdomEvents.setText("");
        startSimulation();
        startMusic();
        statsRefresher.play();
    }

    private void startMusic() {
        URL resource = getClass().getResource("/music.mp3");
        mediaPlayer = new MediaPlayer(new Media(resource.toString()));
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.play();
    }

    private void startSimulation() {
        redKingdom = new Kingdom(KingdomType.RED);
        blueKingdom = new Kingdom(KingdomType.BLUE);

        Settlers.blueKingdomKing.start();
        Settlers.redKingdomKing.start();

        Settlers.blueKingdomPrincess.start();
        Settlers.redKingdomPrincess.start();

        Settlers.blueKingdomJeweller.start();
        Settlers.redKingdomJeweller.start();

        Settlers.blueKingdomSteelWorker.start();
        Settlers.redKingdomSteelWorker.start();

        Settlers.blueKingdomMiner.start();
        Settlers.redKingdomMiner.start();

        Settlers.blueKingdomFarmer.start();
        Settlers.redKingdomFarmer.start();

        Settlers.blueKingdomWarriorsAngel.start();
        Settlers.redKingdomWarriorsAngel.start();

        Settlers.blueKingdomArmourer.start();
        Settlers.redKingdomArmourer.start();

        for (Warrior w : Settlers.blueKingdomWarriors)
            w.start();
        for (Warrior w : Settlers.redKingdomWarriors)
            w.start();

        synchronized (Settlers.lock) {
            Settlers.FIGHT = true;
            Settlers.lock.notifyAll();
        }
    }

    public synchronized void endSimulation(KingdomType kingdomType){
        if(kingdomType == KingdomType.RED)
            redKingdomWins.setVisible(true);
        else
            blueKingdomWins.setVisible(true);

        mediaPlayer.stop();
        stopAllThreads();

        blueKingdom.removeAllWarriors();
        redKingdom.removeAllWarriors();

        Platform.runLater(() -> {
            fightButton.setText("RESTART!");
            fightButton.setDisable(false);

            blueKingdomEvents.clear();
            redKingdomEvents.clear();
        });


        restartAllThreads();
    }

    private void stopAllThreads(){
        Settlers.blueKingdomKing.interrupt();
        Settlers.redKingdomKing.interrupt();

        Settlers.blueKingdomPrincess.interrupt();
        Settlers.redKingdomPrincess.interrupt();

        Settlers.blueKingdomJeweller.interrupt();
        Settlers.redKingdomJeweller.interrupt();

        Settlers.blueKingdomSteelWorker.interrupt();
        Settlers.redKingdomSteelWorker.interrupt();

        Settlers.blueKingdomMiner.interrupt();
        Settlers.redKingdomMiner.interrupt();

        Settlers.blueKingdomFarmer.interrupt();
        Settlers.redKingdomFarmer.interrupt();

        Settlers.blueKingdomWarriorsAngel.interrupt();
        Settlers.redKingdomWarriorsAngel.interrupt();

        Settlers.blueKingdomArmourer.interrupt();
        Settlers.redKingdomArmourer.interrupt();

        for (Warrior w : Settlers.blueKingdomWarriors)
            w.interrupt();
        for (Warrior w : Settlers.redKingdomWarriors)
            w.interrupt();
    }

    private void restartAllThreads(){
        Settlers.blueKingdomWarriors = new ArrayList<>();
        Settlers.redKingdomWarriors = new ArrayList<>();
        Settlers.blueKingdomKing = new King(KingdomType.BLUE);
        Settlers.redKingdomKing = new King(KingdomType.RED);
        Settlers.blueKingdomPrincess = new Princess(Settlers.blueKingdomKing, KingdomType.BLUE);
        Settlers.redKingdomPrincess = new Princess(Settlers.redKingdomKing, KingdomType.RED);
        Settlers.blueKingdomJeweller = new Jeweller(Settlers.blueKingdomPrincess, KingdomType.BLUE, 3000);
        Settlers.redKingdomJeweller = new Jeweller(Settlers.redKingdomPrincess, KingdomType.RED, 3000);
        Settlers.blueKingdomArmourer = new Armourer(Settlers.blueKingdomWarriors, KingdomType.BLUE, 300);
        Settlers.redKingdomArmourer = new Armourer(Settlers.redKingdomWarriors, KingdomType.RED, 300);
        Settlers.blueKingdomSteelWorker = new SteelWorker(Settlers.blueKingdomJeweller, Settlers.blueKingdomArmourer, KingdomType.BLUE, 1000);
        Settlers.redKingdomSteelWorker = new SteelWorker(Settlers.redKingdomJeweller, Settlers.redKingdomArmourer, KingdomType.RED, 1000);
        Settlers.blueKingdomMiner = new Miner(Settlers.blueKingdomSteelWorker, 1000, KingdomType.BLUE);
        Settlers.redKingdomMiner = new Miner(Settlers.redKingdomSteelWorker, 500, KingdomType.RED);

        Settlers.blueKingdomCharacters = new ArrayList<>();
        Settlers.blueKingdomCharacters.add(Settlers.blueKingdomKing);
        Settlers.blueKingdomCharacters.add(Settlers.blueKingdomPrincess);
        Settlers.blueKingdomCharacters.add(Settlers.blueKingdomArmourer);
        Settlers.blueKingdomCharacters.add(Settlers.blueKingdomJeweller);
        Settlers.blueKingdomCharacters.add(Settlers.blueKingdomMiner);
        Settlers.blueKingdomCharacters.add(Settlers.blueKingdomSteelWorker);

        Settlers.redKingdomCharacters = new ArrayList<>();
        Settlers.redKingdomCharacters.add(Settlers.redKingdomKing);
        Settlers.redKingdomCharacters.add(Settlers.redKingdomPrincess);
        Settlers.redKingdomCharacters.add(Settlers.redKingdomArmourer);
        Settlers.redKingdomCharacters.add(Settlers.redKingdomJeweller);
        Settlers.redKingdomCharacters.add(Settlers.redKingdomMiner);
        Settlers.redKingdomCharacters.add(Settlers.redKingdomSteelWorker);

        Settlers.blueKingdomFarmer = new Farmer(Settlers.blueKingdomCharacters, 300);
        Settlers.redKingdomFarmer = new Farmer(Settlers.redKingdomCharacters, 300);

        Settlers.blueKingdomWarriorsAngel = new WarriorsAngel(Settlers.blueKingdomWarriors, 90, KingdomType.BLUE);
        Settlers.redKingdomWarriorsAngel = new WarriorsAngel(Settlers.redKingdomWarriors, 100, KingdomType.RED);
    }
}
