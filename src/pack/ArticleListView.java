package pack;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.util.Callback;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.MouseEvent;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;


public class ArticleListView extends Scene {

	public ArticleListView(Parent arg0) {
		super(arg0);
	}
	
	public ArticleListView(Parent arg0, int h, int w) {
		super(arg0, h, w);
	}

	public static ArticleListView generate(Main main, int height, int width) {
		HBox firstLevel = new HBox(2);
		
		Button refreshButton = new Button();
		
		//TableView<TableRow<String>> articleTable = new TableView<TableRow<String>>();
		TableView<Pair> articleTable = new TableView<Pair>();
		
		TableColumn<Pair, String> col = new TableColumn<Pair, String>("Pealkirjad");
		TableColumn<Pair, Button> openCell = new TableColumn<Pair, Button>("");
		
		
		col.setCellValueFactory(new Callback<CellDataFeatures<Pair, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Pair, String> param) {			
				return param.getValue().headline;
			}
		});

		
		openCell.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pair,Button>, ObservableValue<Button>>() {
			
			@Override
			public ObservableValue<Button> call(CellDataFeatures<Pair, Button> param) {
				SimpleObjectProperty<Button> op = new SimpleObjectProperty<Button>();
				
				Button b = new Button("Ava");
				b.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						main.openLink(param.getValue().link);
					}
				});
				op.setValue(b);
				return op;
			}
		});

		articleTable.getColumns().addAll(col, openCell);
		refreshButton.setOnAction(new EventHandler<ActionEvent>()  {
			
			@Override
			public void handle(ActionEvent event) {
				
				try {
					ObservableList<Pair> olist = FXCollections.observableArrayList();
					olist.addAll(Requests.getArticleList());										
					articleTable.setItems(olist);										
					articleTable.setVisible(true);

				} catch (BadConnectionException e) {
					//TODO: Bad connection notification					
				}
			
			}
		});
		
		
	    firstLevel.getChildren().addAll( refreshButton, articleTable);
	    
		return new ArticleListView(firstLevel, height, width);
	}
}
