import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.transformation.SortedList;

 

/**
 * Created by Sarah on 5/2/17.
 * @author Sarah Higgins and Ying Sun
 * ScoreBoardPane class is the class the displays the Scoreboard from the Main GUI.
 * It shows all of the Player's names and their scores.
 */
public class ScoreBoardTable {

    /**
     * attributes
     */
    private Map<Bracket, Integer> scores;
    private static final int MAX_PLAYER_NUMBER = 16;
    private TableView<Bracket> table;
    private ObservableList<Bracket> data;

    /**
     * ScoreBoardPane constructor
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public <T> ScoreBoardTable() {
        table = new TableView<>();
        data = FXCollections.observableArrayList();
        
        scores = new HashMap<>();

        /**
         * TableColumn userNameCol is the column on the left side of the table.
         * userNameCol.setCellValueFactory() passes the data to the TableView object, which is
         *                                   automatically sorted with the TableColumn.SortType.DESCENDING
         *                                   code line.
         */
        TableColumn<Bracket, String> userNameCol = new TableColumn<>("Username");
        userNameCol.setMinWidth(140);
        userNameCol.setMaxWidth(140);
        userNameCol.setStyle("-fx-border-width: 3px");
        userNameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Bracket, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Bracket, String> b) {
                return new SimpleStringProperty(b.getValue().getPlayerName());
            }
        });
        userNameCol.setSortable(true);
        
        //userNameCol.setSortType(TableColumn.SortType.DESCENDING); //sorts column from highest to lowest
        //table.sort();
        /**
         * TableColumn totalPtsCol is the column on the right side of the table
         * totalPtsCol.setCellValueFactory() passes the data to the TableView object, which is
         *                                   automatically sorted with the TableColumn.SortType.DESCENDING
         *                                   code line.
         */
        TableColumn<Bracket, Number> totalPtsCol = new TableColumn<>("Total Points");
        totalPtsCol.setEditable(true);
        totalPtsCol.setSortType(SortType.DESCENDING);
        totalPtsCol.setMinWidth(140);
        totalPtsCol.setMaxWidth(140);
        totalPtsCol.setStyle("-fx-border-width: 3px");
        totalPtsCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Bracket, Number>, ObservableValue<Number>>() {
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<Bracket, Number> b) {
                return new SimpleIntegerProperty(scores.get(b.getValue()));
            }
        });
        totalPtsCol.setSortable(true);
        table.getSortOrder().sorted();
         
        /*
         * TableView table_view is what the user sees in the GUI. This creates the table.
         *
         */
        //Shane Callahan Additions
        TableColumn<Bracket, String> correctCol = new TableColumn<>("Correct Teams");
        //correctCol.setMinWidth(140);
       // correctCol.setMaxWidth(140);
        //correctCol.setStyle("-fx-border-width: 3px");
        correctCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Bracket, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Bracket, String> b) {
                return new SimpleStringProperty(b.getValue().getCorrectTeams());
            }
        });
        //END

	
         //writen by JOHN
	 SortedList<Bracket> sortedData = new SortedList<>(data);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
        table.getSortOrder().add(totalPtsCol);
        table.setEditable(false);
        table.getColumns().setAll(userNameCol, totalPtsCol);
        
        //End
    }

    public TableView<Bracket> start() {
                
        return table;
    }

    //Ying's code, method addPlayer adds a player to the Bracket
    
	public void addPlayer(Bracket name, int score) {
        try {
            if (scores == null) {
                scores = new HashMap<Bracket, Integer>();
            }
            //only allow to update the existing player score or add new player if there
            //is less than 16 players
            if (scores.get(name) != null || scores.size() < MAX_PLAYER_NUMBER) {
            	
            	scores.put(name, score);
 		                data.add(name);
			     
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Ying's code, method clears the players from the Bracket
    public void clearPlayers() {
        scores = new HashMap<Bracket, Integer>();
        data = FXCollections.observableArrayList();
    }
}