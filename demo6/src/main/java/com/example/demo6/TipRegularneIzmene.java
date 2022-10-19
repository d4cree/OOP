package com.example.demo6;

public enum TipRegularneIzmene {
    NovaFunkcionalnost, IspravljenBag, BaterijaTestova;

    public static TipRegularneIzmene izBroja(int i){
        if (i == 1) return NovaFunkcionalnost;
        else if(i == 2) return IspravljenBag;
        else if(i == 3) return BaterijaTestova;
        else return NovaFunkcionalnost;
    }

    public int uBroj(){

        switch (this){
            case NovaFunkcionalnost: return 1;
            case IspravljenBag: return 2;
            case BaterijaTestova: return 3;

            default: return 1;
        }
    }
}
