package com.example.demo6;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SistemTig extends Application {

    private List<Izmene> izmene = new ArrayList<>();

    private void azurirajIzmene(TextArea taLog){
        taLog.clear();
        izmene.forEach(i -> {
            taLog.appendText(i.toString() + "\n\n");
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10,10,10,10));

        HBox hbTop = new HBox(10);
        Button btUcitaj = new Button("Ucitaj");
        Button btSacuvaj = new Button("Sacuvaj");
        hbTop.getChildren().addAll(btUcitaj, btSacuvaj);

        TextArea taLog = new TextArea();
        taLog.setPrefRowCount(60);

        HBox hbBottom = new HBox(10);

        VBox vbLeft = new VBox(10);
        Label lbAutor = new Label("Autor");
        TextField tfAutor = new TextField();
        Label lbPoruka = new Label("Poruka");
        TextField tfPoruka = new TextField();
        Label lbDatum = new Label("Datum");
        TextField tfDatum = new TextField();
        vbLeft.getChildren().addAll(lbAutor, tfAutor, lbPoruka, tfPoruka, lbDatum, tfDatum);

        VBox vbRight = new VBox(10);
        RadioButton rbFunkcionalnost = new RadioButton("Nova funkcionalnost");
        RadioButton rbBag = new RadioButton("Ispravljen bag");
        RadioButton rbBaterija = new RadioButton("Baterija testova");
        ToggleGroup tg = new ToggleGroup();
        rbFunkcionalnost.setToggleGroup(tg);
        rbBag.setToggleGroup(tg);
        rbBaterija.setToggleGroup(tg);
        rbFunkcionalnost.setSelected(true);
        Button btDodaj = new Button("Dodaj");
        vbRight.getChildren().addAll(rbFunkcionalnost, rbBag, rbBaterija, btDodaj);

        hbBottom.getChildren().addAll(vbLeft, vbRight);

        root.getChildren().addAll(hbTop, taLog, hbBottom);

        btUcitaj.setOnAction(e -> {
            izmene.clear();
            taLog.clear();
            Izmene.postaviSledeciId(1);

            try {
                List<String> linije = Files.readAllLines(Paths.get("izmene.txt"));
                for(String linija: linije){
                    System.out.println("Parsiram '" + linija + "'");
                    String[] tokeni = linija.split(",");
                    String tip = tokeni[0].trim();
                    String autor = tokeni[1].trim();
                    int id = Integer.parseInt(tokeni[2].trim());
                    System.out.println("id=" + id);
                    String datum = tokeni[3].trim();
                    String poruka = tokeni[4].trim();

                    System.out.println();

                    Zaglavlje z = new Zaglavlje(autor, datum);

                    Izmene i = null;
                    if(tip.equals("ir")){
                        int tipIzmene = Integer.parseInt(tokeni[5].trim());
                        i = new IzmenaRegularna(z, poruka, id, TipRegularneIzmene.izBroja(tipIzmene));
                        izmene.add(i);
                    }
                    else if(tip.equals("iz")){
                        i = new IzmenaZahtev(z, poruka, id);
                        izmene.add(i);
                    }
                    else if(tip.equals("ipz")){
                        int idZahteva = Integer.parseInt(tokeni[5].trim());
                        i = new PrihvatanjeZahteva(z, poruka, id, idZahteva);
                        izmene.add(i);
                    }
                    else {
                        System.out.println("Greska pi parisanju linije '" + linija + "'");
                    }

                }

                izmene.sort((i1, i2) -> Integer.compare(i2.getId(), i1.getId()));

                azurirajIzmene(taLog);

                // Azuriramo poslednji dostupni id
                int poslednjId = izmene.get(0).getId();
                Izmene.postaviSledeciId(poslednjId + 1);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        btDodaj.setOnAction(e -> {
            String autor, datum, poruka;
            if(tfAutor.getText().isEmpty() || tfDatum.getText().isEmpty() || tfDatum.getText().isEmpty())
                return;

            autor = tfAutor.getText().trim();
            datum = tfDatum.getText().trim();
            poruka = tfPoruka.getText().trim();

            Zaglavlje z = new Zaglavlje(autor, datum);

            TipRegularneIzmene tipRegularneIzmene = null;
            if(rbFunkcionalnost.isSelected()) tipRegularneIzmene = tipRegularneIzmene.NovaFunkcionalnost;
            else if(rbBag.isSelected()) tipRegularneIzmene = tipRegularneIzmene.IspravljenBag;
            else if(rbBaterija.isSelected()) tipRegularneIzmene = tipRegularneIzmene.BaterijaTestova;
            else {
                System.out.println("Problem pri odabiru radio button komponente.");
                return;
            }

            Izmene i = new IzmenaRegularna(z, poruka, tipRegularneIzmene);
            izmene.add(0, i);
            azurirajIzmene(taLog);
        });

        btSacuvaj.setOnAction( e -> {
            StringBuilder sb = new StringBuilder();
            izmene.forEach(i -> sb.append(i.serijalizuj()).append('\n'));
            Path outputPath = Paths.get("izmene.txt");

            try {
                Files.write(outputPath, sb.toString().getBytes());
                taLog.setText("Podaci sacuvani u izmene.txt");
            } catch (IOException ioException) {
                System.out.println("Neuspelo Pisanje!");
            }
        });

        Scene scene = new Scene(root, 500, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("The tig");
        primaryStage.show();
    }
}
