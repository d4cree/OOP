package com.example.demo7;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sanduce extends Application {

    private static Korisnik tweety = new Korisnik("Tweety");
    private Path datoteka = Paths.get("poruke.txt");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        HBox hTop = new HBox(10);
        Button btSve = new Button("Sve poruke");
        RadioButton rbPoslate = new RadioButton("Poslate");
        RadioButton rbPrimljene = new RadioButton("Primljene");
        ToggleGroup tg = new ToggleGroup();
        rbPoslate.setToggleGroup(tg);
        rbPrimljene.setToggleGroup(tg);
        rbPoslate.setSelected(true);
        Button btPrikazi = new Button("Prikazi");
        hTop.getChildren().addAll(btSve, rbPoslate, rbPrimljene, btPrikazi);
        hTop.setAlignment(Pos.CENTER);

        TextArea taLog = new TextArea();
        Label lbPrimalac = new Label("Primalac");
        TextField tfPrimalac = new TextField();
        Label lbDatum = new Label("Datum");
        TextField tfDatum = new TextField();
        Label lbPoruka = new Label("Poruka za slanje");
        TextArea taPoruka = new TextArea();

        HBox hBottom = new HBox(10);
        Button btPosalji = new Button("Posalji poruku");
        Button btSacuvaj = new Button("Sacuvaj poruke");
        hBottom.getChildren().addAll(btPosalji, btSacuvaj);

        root.getChildren().addAll(hTop, taLog, lbPrimalac, tfPrimalac, lbDatum, tfDatum, lbPoruka, taPoruka, hBottom);

        btSve.setOnAction(e -> {
            taLog.setText("Dobrodosli, " + tweety.getKorisnickoIme() + "!");
            try {
                List<String> linije = Files.readAllLines(datoteka);

                for (String linija : linije) {
                    tweety.novaPoruka(linija);
                }

                ispisiPorukeSortirano(taLog);

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        btPrikazi.setOnAction(e -> {
            taLog.clear();
            if (rbPoslate.isSelected()) {
                for (Poruka poruka : tweety.getPoruke()) {
                    if (poruka instanceof PoslataPoruka) {
                        taLog.appendText(poruka.toString());
                    }
                }
            }
            if (rbPrimljene.isSelected()) {
                for (Poruka poruka : tweety.getPoruke()) {
                    if (poruka instanceof PrimljenaPoruka) {
                        taLog.appendText(poruka.toString());
                    }
                }
            }

        });

        btPosalji.setOnAction(e -> {
            String Primalac = tfPrimalac.getText().trim();
            String Datum = tfDatum.getText().trim();
            String Poruka = taPoruka.getText().trim();

            if(!Primalac.equals("") || !Datum.equals("") || !Poruka.equals("")){
                PoslataPoruka poslataPoruka = new PoslataPoruka(Poruka, Datum, Primalac);
                tweety.novaPoruka(poslataPoruka.sacuvajPoruku());
                ispisiPorukeSortirano(taLog);
            }

        });

        btSacuvaj.setOnAction(e -> {
            List<String> zaCuvanje = new ArrayList<>();

            for(Poruka poruka: tweety.getPoruke()){
                zaCuvanje.add(poruka.sacuvajPoruku());
            }

            try {
                Files.write(datoteka, zaCuvanje);
                taLog.setText("Poruke su uspesno sacuvane!");

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        primaryStage.setScene(new Scene(root, 500, 600));
        primaryStage.setTitle("Sanduce");
        primaryStage.show();

    }

    public static void ispisiPorukeSortirano(TextArea taIspis) {
        Collections.sort(tweety.getPoruke());
        taIspis.clear();
        for (Poruka poruka : tweety.getPoruke()) {
            taIspis.appendText(poruka.toString());
        }
    }
}
