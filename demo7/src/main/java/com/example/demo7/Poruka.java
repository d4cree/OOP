package com.example.demo7;

public abstract class Poruka implements Comparable<Poruka> {
    private Datum datum;
    private String tekstPoruke;

    public Poruka(String tekstPoruke, String Datum){
        this.tekstPoruke = tekstPoruke;
        this.datum = new Datum(Datum);
    }

    public Poruka(Poruka p){
        this(p.tekstPoruke, p.datum.toString());
    }

    public String getTekstPoruke() {
        return tekstPoruke;
    }

    public Datum getDatum() {
        return datum;
    }

    public abstract String sacuvajPoruku();

    @Override
    public String toString(){ return  datum + "\n" + tekstPoruke + "\n----------------------------------------------\n"; }

    public abstract int compareTo(Poruka o);
}
