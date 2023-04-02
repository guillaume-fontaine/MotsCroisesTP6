package fr.trollgun.motcroises;

public class MotCroisesGrille {

    private int numGrille;
    private String nomGrille;

    public MotCroisesGrille(int numGrille, String nomGrille) {
        this.numGrille = numGrille;
        this.nomGrille = nomGrille;
    }

    @Override
    public String toString() {
        return nomGrille;
    }

    public int getNumGrille() {
        return numGrille;
    }
}
