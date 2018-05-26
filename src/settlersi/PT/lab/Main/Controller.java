package settlersi.PT.lab.Main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import settlersi.PT.lab.Main.characters.KingdomType;
import settlersi.PT.lab.Main.characters.Warrior;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

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
        fightButton.setDisable(true);
        blueKingdomEvents.setText("");
        redKingdomEvents.setText("");
        startSimulation();
        startMusic();
        statsRefresher.play();
    }

    private void startMusic() {
        URL resource = getClass().getResource("/music.mp3");
        MediaPlayer a = new MediaPlayer(new Media(resource.toString()));
        a.setOnEndOfMedia(() -> a.seek(Duration.ZERO));
        a.play();
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


}
