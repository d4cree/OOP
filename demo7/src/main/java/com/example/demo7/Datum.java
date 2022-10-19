package com.example.demo7;

public class Datum implements Comparable<Datum> {
    private int dan;
    private int mesec;
    private int godina;

    public Datum(String Datum){
        dan = Integer.parseInt(Datum.substring(0, 2));
        mesec = Integer.parseInt(Datum.substring(3, 5));
        godina = Integer.parseInt(Datum.substring(6, 10));
    }

    public int getMesec() {
        return mesec;
    }

    public int getGodina() {
        return godina;
    }

    public int getDan() {
        return dan;
    }

    public String dvocifrenBroj(int i){
        if (i > 9)
            return "" + i;
        else
            return  "0" + i;
    }

    @Override
    public String toString() { return dvocifrenBroj(dan) + "." + dvocifrenBroj(mesec) + "." + godina + "."; }

    @Override
    public int compareTo(Datum o) {
        if(this.godina != o.godina) {
            return  o.godina - this.godina;
        }
        else if(this.mesec != o.mesec) {
            return  o.mesec - this.mesec;
        }
        else
            return  o.dan - this.dan;
    }
}
