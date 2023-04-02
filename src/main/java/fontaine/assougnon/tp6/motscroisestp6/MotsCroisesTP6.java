package fr.trollgun.motcroises;

import javafx.beans.property.StringProperty;

public class MotsCroisesTP6 {

    private Grille<CaseMC> caseMotsCroises;

    private String nomGrille;

    public MotsCroisesTP6(int hauteur, int largeur, String nomGrille){
        this.caseMotsCroises = new Grille<>(hauteur, largeur);
        this.nomGrille = nomGrille;
    }

    public int getHauteur(){
        return this.caseMotsCroises.getHauteur();
    }

    public int getLargeur(){
        return this.caseMotsCroises.getLargeur();
    }

    public boolean coordCorrectes(int lig, int col) {
        return this.caseMotsCroises.coordCorrectes(lig, col);
    }

    public boolean estCaseNoire(int lig, int col) {
        verifierCoordCorrectes(lig, col);
        return this.caseMotsCroises.getCellule(lig, col).estCaseNoire();
    }

    public void setCaseNoire(int lig, int col, boolean noire) {
        verifierCoordCorrectes(lig, col);
        this.caseMotsCroises.setCellule(lig, col, new CaseMC(noire));
    }

    public Character getSolution(int lig, int col) {
        verifierCaseNoire(lig, col);
        return this.caseMotsCroises.getCellule(lig,col).getSolution();
    }
    public void setSolution(int lig, int col, Character sol) {
        verifierCaseNoire(lig, col);
        this.caseMotsCroises.getCellule(lig,col).setSolution(sol);
    }

    public Character getProposition(int lig, int col) {
        verifierCaseNoire(lig, col);
        return this.caseMotsCroises.getCellule(lig,col).getProposition().getValue().charAt(0);
    }

    public StringProperty getStringProperty(int lig, int col) {
        verifierCaseNoire(lig, col);
        return this.caseMotsCroises.getCellule(lig,col).getProposition();
    }
    public void setProposition(int lig, int col, Character prop) {
        verifierCaseNoire(lig, col);
        this.caseMotsCroises.getCellule(lig, col).getProposition().setValue(prop.toString());
    }

    public String getDefinition(int lig, int col, boolean horiz) {
        verifierCaseNoire(lig, col);
        return caseMotsCroises.getCellule(lig, col).getDefinition(horiz);
    }


    public void setDefinition(int lig, int col, boolean horiz, String def) {
        verifierCaseNoire(lig, col);
        caseMotsCroises.getCellule(lig, col).setDefinition(horiz, def);
    }

    private void verifierCaseNoire(int lig, int col){
        if (estCaseNoire(lig, col)) throw new IllegalArgumentException("Impossible cause : Case noir : ligne = " + lig + " | colonne = " + col);
    }

    private void verifierCoordCorrectes(int lig, int col){
        if (!coordCorrectes(lig, col)) throw new IllegalArgumentException("Coordonées non valides : ligne = " + lig + " | colonne = " + col);
    }

    @Override
    public String toString() {
        return caseMotsCroises.toString();
    }

    public String getNomGrille() {
        return nomGrille;
    }

    public String getVerboseDefinition(int lig, int col){
        verifierCaseNoire(lig, col);
        return caseMotsCroises.getCellule(lig, col).getVerboseDefinition();
    }
}
