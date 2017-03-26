package pack;

import javafx.beans.property.SimpleStringProperty;

public class Pair {

	SimpleStringProperty  headline;
	String  link;
	
	Pair (String hin, String lin) {
		headline = new SimpleStringProperty(hin); 
		if (!lin.matches("^http.*")) {
			link = "http:" + lin;
		} else {
			link = lin;	
		}
				
	}
	
	
	
}
