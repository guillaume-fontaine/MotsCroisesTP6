package fr.trollgun.motcroises;


import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ChargerGrille {

    private static final String tableGrille = "TP5_GRILLE";
    private static final String tableMot = "TP5_MOT";
    private static final String url = "jdbc:mysql://localhost/prga";

    private Connection connexion;

    public ChargerGrille() {
        try {
            connexion = connecterBD();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection connecterBD() throws SQLException {
        Connection connect;
        connect = DriverManager.getConnection(url, "root", "");
        return connect;
    }

    // Ainsi "Français débutants (7x6)" devrait être associé à la clé 10
    public Map<Integer, String> grillesDisponibles() throws SQLException {
        Map<Integer, String> map = new HashMap<>();
        PreparedStatement statement = connecterBD().prepareStatement("SELECT * FROM TP5_GRILLE");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            map.put(resultSet.getInt(1), resultSet.getString(2)+" ("+resultSet.getInt(4)+"x"+resultSet.getInt(3)+")");
        }
        statement.close();
        return map;
    }

    public MotsCroisesTP6 extraireGrille(int numGrille) throws Exception {
        MotsCroisesTP6 motsCroises = null;
        PreparedStatement statement = connecterBD().prepareStatement("SELECT `largeur`, `hauteur`, `nom_grille` FROM TP5_GRILLE where num_grille=?");
        statement.setString(1, numGrille+"");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            motsCroises = new MotsCroisesTP6(resultSet.getInt(2), resultSet.getInt(1), resultSet.getString(3));
        }
        resultSet.close();
        statement.close();
        for (int i = 1; i <= motsCroises.getHauteur(); i++) {
            for (int j = 1; j <= motsCroises.getLargeur() ; j++) {
                motsCroises.setCaseNoire(i,j, true);
            }
        }
        //SELECT `num_mot`, `definition`, `horizontal`, `ligne`, `colonne`, `solution`, `num_grille` FROM `tp5_mot` WHERE 1
        statement = connexion.prepareStatement("SELECT * FROM TP5_MOT where num_grille=?");
        statement.setString(1, numGrille+"");
        resultSet = statement.executeQuery();
        while (resultSet.next()){
            int lig = resultSet.getInt(4);
            int col = resultSet.getInt(5);
            boolean horizontal = resultSet.getBoolean(3);
            String solution = resultSet.getString(6);
            if(motsCroises.estCaseNoire(lig, col)) motsCroises.setCaseNoire(lig, col, false);
            motsCroises.setDefinition(lig, col, horizontal, resultSet.getString(2));
            for (int i = 0; i < solution.length(); i++) {
                int lig2 = lig + (horizontal ? 0 : i);
                int col2 = col + (horizontal ? i : 0);
                if(motsCroises.estCaseNoire(lig2, col2)){
                    motsCroises.setCaseNoire(lig2, col2, false);
                    motsCroises.setSolution(lig2, col2, solution.charAt(i));
                }else{
                    if(motsCroises.getSolution(lig2, col2) == null)motsCroises.setSolution(lig2, col2, solution.charAt(i));
                    else if(motsCroises.getSolution(lig2, col2) != solution.charAt(i)){
                        throw new Exception("CPT chef");
                    }
                }
            }
        }
        resultSet.close();
        statement.close();
        return motsCroises;
    }

}
