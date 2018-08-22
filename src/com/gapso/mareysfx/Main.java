package com.gapso.mareysfx;

import com.gapso.mareysfx.com.gapso.mareysfx.MareysFX;
import com.gapso.mareysfx.entities.MareysStation;
import com.gapso.mareysfx.entities.MareysTrain;
import com.gapso.mareysfx.entities.TimeWindow;
import com.gapso.mareysfx.entities.TrainSchedule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();

        primaryStage.setTitle("Marey's Chart test");

        Group root = new Group();
        Scene scene = new Scene(root, 1280, 800);
        primaryStage.setScene(scene);


        MareysFX mareysFX = new MareysFX(1280, 600);

        ArrayList<MareysStation> stations = new ArrayList<MareysStation>() {{
            add(new MareysStation("s1", "Station Zero", 0));
            add(new MareysStation("s2", "Station Alpha", 33));
            add(new MareysStation("s3", "Station Bravo", 70));
        }};
        mareysFX.setStations(stations);

        mareysFX.setTrains(getRandomTrains(stations));

        LocalDateTime now = LocalDateTime.now();
        mareysFX.setTimeWindow(new TimeWindow(now.minusHours(12), now.plusHours(12)));

        root.getChildren().add(mareysFX);

        mareysFX.initDrawing();

        primaryStage.show();
    }

    private ArrayList<MareysTrain> getRandomTrains(ArrayList<MareysStation> stations) {
        ArrayList<MareysTrain> result = new ArrayList<>();

        for (int i = 0; i < 300; i++) {

            MareysTrain t = new MareysTrain("t" + i, "Train " + i, "g1", new ArrayList<>());

            LocalDateTime currTime = LocalDateTime.now().minusHours(12);

            for (MareysStation s : stations) {
                int hours = (int) (1 + (Math.random() * (10 - 1)));
                currTime = currTime.plusHours(hours);
                t.getSchedule().add(new TrainSchedule(currTime.plusHours(0), s.getDistance()));
            }

            result.add(t);
//            System.out.println(t);
        }

        return result;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
