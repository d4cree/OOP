package com.example.demo10;

public class Magija {
    private int snaga;
    private String ime;

    public String getIme() {
        return ime;
    }

    public int getSnaga() {
        return snaga;
    }

    public Magija(int snaga, String ime){
        this.snaga = snaga;
        this.ime = ime;
    }

    @Override
    public String toString(){
        return ime + " " + snaga;
    }
}
