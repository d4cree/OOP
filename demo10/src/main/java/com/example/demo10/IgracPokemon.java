package com.example.demo10;

public class IgracPokemon extends Pokemon{
    private int izabranaMagija;

    public Magija getIzabranaMagija() {
        if(izabranaMagija == 1){
            return getMagija1();
        }
            return getMagija2();
    }

    public void setIzabranaMagija(int i) {
        if(i != 1 || i != 2)
            return;
        izabranaMagija = i;
    }

    public IgracPokemon(String ime, int nivo, Magija magija1, Magija magija2) {
        super(ime, nivo, magija1, magija2);
    }

    @Override
    public void baciMagiju(Pokemon neprijatelj) {
        if(izabranaMagija == 1){
            neprijatelj.nanesiStetu(getMagija1().getSnaga());
            setPoslednjaBacena(getMagija1());
        } else {
           neprijatelj.nanesiStetu(getMagija1().getSnaga());
           setPoslednjaBacena(getMagija2());
        }
    }

    @Override
    public String toString() {
        return "[p] " + super.toString();
    }

}
