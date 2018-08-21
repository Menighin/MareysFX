package com.gapso.mareysfx;

import com.gapso.mareysfx.com.gapso.mareysfx.MareysFX;
import com.gapso.mareysfx.entities.MareysStation;
import com.gapso.mareysfx.entities.TimeWindow;
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

        mareysFX.setStations(new ArrayList<MareysStation>(){{
            add(new MareysStation("s1", "Station Zero", 0));
            add(new MareysStation("s2", "Station Alpha", 33));
            add(new MareysStation("s3", "Station Bravo", 50));
        }});


        LocalDateTime now = LocalDateTime.now();
        mareysFX.setTimeWindow(new TimeWindow(now.minusHours(12), now.plusHours(12)));

        root.getChildren().add(mareysFX);

        mareysFX.initDrawing();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
