package com.example.demo7;

public class PoslataPoruka extends Poruka {
    String primalac;

    public String getPrimalac() {
        return primalac;
    }

    public PoslataPoruka(String tekstPoruke, String datum, String primalac){
        super(tekstPoruke, datum);
        this.primalac = primalac;
    }

    @Override
    public String sacuvajPoruku() {
        return "s; " + getDatum() + "; " + getPrimalac() + "; " + getTekstPoruke();
    }

    @Override
    public String toString() {
        return "Primalac: " + primalac + " " + super.toString();
    }

    @Override
    public int compareTo(Poruka o) {
        if (o instanceof PrimljenaPoruka){
            return 1;
        }

        return this.getDatum().compareTo(o.getDatum());
    }

}
