package fr.trollgun.motcroises;

public class Grille<T> {


    private int hauteur;
    private int largeur;
    private T[][] tab;

    public Grille(int hauteur, int largeur) {
        if (hauteur < 0) throw new IllegalArgumentException("Hauteur incorrecte : " + hauteur);
        if (largeur < 0) throw new IllegalArgumentException("Largeur incorrecte : " + largeur);
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.tab = (T[][]) new Object[hauteur][largeur];
    }

    public int getHauteur() {
        return this.hauteur;
    }

    public int getLargeur() {
        return this.largeur;
    }

    public boolean coordCorrectes(int lig, int col) {
        return lig > 0 && lig <= getHauteur() && col > 0 && col <= getLargeur();
    }

    public T getCellule(int lig, int col) {
        if (!coordCorrectes(lig, col)) throw new IllegalArgumentException("Coordonées non valides : ligne = " + lig + " | colonne = " + col);
        return tab[lig-1][col-1];
    }

    public void setCellule(int lig, int col, T ch) {
        if (!coordCorrectes(lig, col)) throw new IllegalArgumentException("Coordonées non valides : ligne = " + lig + " | colonne = " + col);
        this.tab[lig-1][col-1] = ch;
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        for (int lig = 0; lig < this.getHauteur(); lig++) {
            for (int col = 0; col < this.getLargeur(); col++) {
                toString.append(tab[lig][col]);
                if (col != this.getLargeur()) toString.append("|");
            }
            if (lig != this.getHauteur()) toString.append("\n");
        }
        return toString.toString();
    }
}