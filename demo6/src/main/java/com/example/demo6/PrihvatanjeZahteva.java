package com.example.demo6;

import java.util.StringJoiner;

public class PrihvatanjeZahteva extends Izmene{
    private int idPrihvacenogZahteva;

    public PrihvatanjeZahteva(Zaglavlje zaglavlje, String poruka, int idPrihvacenogZahteva){
        super(zaglavlje, poruka);
        this.idPrihvacenogZahteva = idPrihvacenogZahteva;
    }

    public PrihvatanjeZahteva(Zaglavlje zaglavlje, String poruka, int id, int idPrihvacenogZahteva){
        super(zaglavlje, poruka, id);
        this.idPrihvacenogZahteva = idPrihvacenogZahteva;
    }

    public int getIdPrihvacenogZahteva() {
        return idPrihvacenogZahteva;
    }

    @Override
    public String toString(){ return "[ipz] " + super.toString() + "za #" + idPrihvacenogZahteva + "\n" + getPoruka(); }

    @Override
    public String serijalizuj() {
        StringJoiner joiner = new StringJoiner(", ");
        joiner.add("ipz");
        joiner.add(getZaglavlje().getAutor());
        joiner.add(getId() + "");
        joiner.add(getZaglavlje().getVremenskaOznaka());
        joiner.add(getPoruka());
        joiner.add(getIdPrihvacenogZahteva() + "");
        return joiner.toString();
    }
}
