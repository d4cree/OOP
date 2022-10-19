package com.example.demo6;

public abstract class Izmene {
    private Zaglavlje zaglavlje;
    private String poruka;
    private int id;
    private static int sledeciSlobodanId = 1;

    public Izmene(Zaglavlje zaglavlje, String poruka){
        this.zaglavlje = zaglavlje;
        this.poruka = poruka;
        this.id = sledeciSlobodanId++;

    }

    public Izmene(Zaglavlje zaglavlje, String poruka, int id){
        this.zaglavlje = zaglavlje;
        this.poruka = poruka;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getPoruka() {
        return poruka;
    }

    public Zaglavlje getZaglavlje() {
        return zaglavlje;
    }

    public static void postaviSledeciId(int sledeci){
        sledeciSlobodanId = sledeci;
    }

    public abstract String serijalizuj();

    @Override
    public String toString(){return zaglavlje.toString() + "#" + id;}
}

