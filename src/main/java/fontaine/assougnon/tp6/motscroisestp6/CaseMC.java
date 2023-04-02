package fr.trollgun.motcroises;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CaseMC {

    private Character solution;
    private StringProperty proposition;
    private String horizontal;
    private String vertical;
    private boolean status; //true = case noire

    public CaseMC() {
    }

    public CaseMC(boolean noire) {
        this.setCaseNoire(noire);
        this.proposition = new SimpleStringProperty();
    }

    public boolean estCaseNoire(){
        return this.status;
    }

    public void setCaseNoire(boolean noire){
         this.status = noire;
    }

    public Character getSolution() {
        return solution;
    }

    public void setSolution(Character solution) {
        this.solution = solution;
    }

    public StringProperty getProposition() {
        return proposition;
    }

    public void setProposition(StringProperty proposition) {
        this.proposition = proposition;
    }

    public String getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(String horizontal) {
        this.horizontal = horizontal;
    }

    public String getVertical() {
        return vertical;
    }

    public void setVertical(String vertical) {
        this.vertical = vertical;
    }

    public String getDefinition(boolean horizontal){
        return horizontal ? this.horizontal : vertical;
    }

    public void setDefinition(boolean horizontal, String def){
       if(horizontal)this.horizontal = def;
       else this.vertical = def;
    }

    public String getVerboseDefinition(){
        return (horizontal == null ? "" : ("Horizontal : "+horizontal))
                + (horizontal == null || vertical == null ? "" : (" || "))
                + (vertical == null ? "" : ("Vertical : "+vertical));
    }


    @Override
    public String toString() {
        return solution == null ? " " : solution+"";
    }
}
