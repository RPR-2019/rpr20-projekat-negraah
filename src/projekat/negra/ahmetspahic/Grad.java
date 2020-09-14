package projekat.negra.ahmetspahic;

import java.util.TreeSet;

public class Grad implements Comparable{
    private int id;
    private String naziv;
    private int brojStanovnika;
    private Drzava drzava;
    private TreeSet<Grad> letovi = new TreeSet<>();

    public Grad(int id, String naziv, int brojStanovnika, Drzava drzava) {
        this.id = id;
        this.naziv = naziv;
        this.brojStanovnika = brojStanovnika;
        this.drzava = drzava;
        letovi = new TreeSet<>();
    }
    public Grad(int id, String naziv, int brojStanovnika, Drzava drzava, TreeSet<Grad> letovi) {
        this.id = id;
        this.naziv = naziv;
        this.brojStanovnika = brojStanovnika;
        this.drzava = drzava;
        this.letovi = letovi;
    }

    public TreeSet<Grad> getLetovi() {
        return letovi;
    }

    public void setLetovi(TreeSet<Grad> letovi) {
        this.letovi = letovi;
    }

    public Grad() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getBrojStanovnika() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;
    }

    @Override
    public String toString() { return naziv; }

    @Override
    public int compareTo(Object o) {
        if(((Grad)o).getId()>this.getId()) return 1;
        else return -1;
    }
}
