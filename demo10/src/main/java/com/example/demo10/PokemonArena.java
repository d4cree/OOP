package com.example.demo10;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PokemonArena extends Application {

    private static final String separator = "------------------------------------------------------------------------\n";
    private static Pokemon pokemoni1 = null;
    private static Pokemon pokemoni2 = null;

    public static void main(String[] args) {
        launch(args);
    }
    private static List<Pokemon> pokemoni = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Button btUcitaj = new Button("Ucitaj");
        TextArea taPokemoni = new TextArea();
        TextArea taBorba = new TextArea();
        HBox hButton = new HBox(10);
        RadioButton rbMagija1 = new RadioButton("Magija 1");
        RadioButton rbMagija2 = new RadioButton("Magija 2");
        ToggleGroup tg = new ToggleGroup();
        rbMagija1.setToggleGroup(tg);
        rbMagija2.setToggleGroup(tg);
        rbMagija1.setSelected(true);

        Button btIzaberi = new Button("Izaberi Magiju");
        Button btSimuliraj = new Button("Simuliraj");
        hButton.getChildren().addAll(btIzaberi, btSimuliraj);

        root.getChildren().addAll(btUcitaj, taPokemoni, taBorba, rbMagija1, rbMagija2, hButton);

        btUcitaj.setOnAction(e -> {
            try {
                BufferedReader objReader = new BufferedReader(new FileReader("C:\\Users\\heheh\\demo10\\src\\main\\java\\com\\example\\demo10\\pokemoni.txt"));
                String linija;
                while((linija = objReader.readLine()) != null){
                    String[] tokeni = linija.split(",");
                    System.out.println(Arrays.toString(tokeni));
                    String tip = tokeni[0].trim();
                    String ime = tokeni[1].trim();
                    int nivo = Integer.parseInt(tokeni[2].trim());
                    String imeMagije1 = tokeni[3].trim();
                    int snaga1 = Integer.parseInt(tokeni[4].trim());
                    String imeMagije2 = tokeni[5].trim();
                    int snaga2 = Integer.parseInt(tokeni[6].trim());

                    if(tip.equals("p")){
                        pokemoni.add(new IgracPokemon(ime, nivo, new Magija(snaga1, imeMagije1), new Magija(snaga2, imeMagije2)));
                        System.out.println(ime);
                    } else if(tip.equals("n")) {
                        pokemoni.add(new CpuPokemon(ime, nivo, new Magija(snaga1, imeMagije1), new Magija(snaga2, imeMagije2)));
                        System.out.println(ime);
                    }
                    else {
                        System.out.println("Ovo ne sme da se desi! tip= " + tip);
                    }
                }
            } catch (IOException ex) {
                System.out.println("Datoteka nije uspesno ocitana!");
            }

            Collections.sort(pokemoni);

            for(Pokemon p: pokemoni)
                taPokemoni.appendText(p + "\n");

            pokemoni1 = pokemoni.get(0);

            for(Pokemon p: pokemoni){
                if(p instanceof CpuPokemon) {
                    pokemoni2 = p;
                    break;
                }
            }

            if(pokemoni2 == null){
                System.out.println("Ovo ne sme da se desi! :)");
            }

            rbMagija1.setText(pokemoni1.getMagija1().toString());
            rbMagija2.setText(pokemoni1.getMagija2().toString());
            taPokemoni.appendText("\nIzabrani pokemoni:\n" + pokemoni1 + "\n" + pokemoni2 + "\n");
        });

        btIzaberi.setOnAction(e -> {
            if(rbMagija1.isSelected()){
                ((IgracPokemon)pokemoni1).setIzabranaMagija(1);
                System.out.println("Izabrana je magija 1");
                taBorba.appendText("Igrac bira magiju " + pokemoni1.getMagija1() + "\n");
                taBorba.appendText(separator);
            } else if (rbMagija2.isSelected()) {
                ((IgracPokemon) pokemoni1).setIzabranaMagija(2);
                taBorba.appendText("Igrac bira magiju " + pokemoni1.getMagija2() + "\n");
                taBorba.appendText(separator);
            } else {
                System.out.println("Ovo ne sme da se desi :D!");
            }
        });

        btSimuliraj.setOnAction(e -> {
            if(pokemoni1.getZivot() <= 0){
                taBorba.appendText("Pobedio je CPU");
            } else if (pokemoni2.getZivot() <= 0){
                taBorba.appendText("Pobedio je igrac");
            } else {
                pokemoni1.baciMagiju(pokemoni2);
                pokemoni2.baciMagiju(pokemoni1);
                taBorba.appendText("[igrac] " + pokemoni1 + " je bacio " + pokemoni1.getPoslednjaBacena() + "\n");
                taBorba.appendText("[CPU] " + pokemoni2 + " je bacio " + pokemoni2.getPoslednjaBacena() + "\n");
                taBorba.appendText("[igrac] " + pokemoni1.getZivot() + "%\n");
                taBorba.appendText("[CPU] " + pokemoni2.getZivot() + "%\n");
                taBorba.appendText(separator);
            }
        });

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("Pokemon arena");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
