package pack;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.text.Text;


public class ArticleReadView extends Scene {

	static int buttonWidth = 60; 
	static int fontSize = 10;
	public ArticleReadView(Parent arg0) {
		super(arg0);
	}
	
	public ArticleReadView(Parent arg0, int h, int w) {
		super(arg0, h, w);
	}

	public static ArticleReadView generate(Main main, int height, int width, String link) {
		HBox firstLevel = new HBox(2);
		
		Button backButton = new Button("Tagasi");
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {				
				main.backToListView();
			}							
			
		});
		ScrollPane sp = new ScrollPane();
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		sp.setPrefSize(width - buttonWidth, height);
		Text articlearea = new Text();
		
		try {
			String articleText = Requests.getArticle(link);
			Double textwd = (width - buttonWidth) / 10.0;
			String formatedText = formatText(articleText, textwd.intValue());
			
			articlearea.setText(formatedText);
			articlearea.autosize();
						
		} catch (BadConnectionException e) {
			articlearea.setText("Probleem artikliga. Kasutatud link oli\n" + link);
		}
		sp.setContent(articlearea);
	    firstLevel.getChildren().setAll(backButton, sp);
		
		return new ArticleReadView(firstLevel, height, width);
	}

	private static String formatText(String text, int width) {
		int beginningOfArea = 0;
		boolean split = false;
		String ntext = "";
		if (text.length() < width) {
			return text;
		}
		while(beginningOfArea < text.length() - width + 2){
			split = false;
			if (text.charAt(beginningOfArea + width) == ' ') {
				ntext += text.substring(beginningOfArea, beginningOfArea + width) + "\n";
				beginningOfArea += width + 1;
				
			} 
			else {
				for (int i = 0; i < 15; i++) {
					if (text.charAt(beginningOfArea + width - i) == ' ') {
						ntext += text.substring(beginningOfArea, beginningOfArea + width - i) + "\n";
						beginningOfArea += width - i + 1;
						split = true;
						break;
					}
				}
				if (!split) {
					for (int i = 14; i < width; i++) {
						if (text.charAt(beginningOfArea + width - i) == ' ') {
							ntext += text.substring(beginningOfArea, beginningOfArea + width - i) + "\n";
							beginningOfArea += width - i + 1;
							break;
						}
					}	
				}
			}
			ntext += text.substring(beginningOfArea);
				
		}
		System.out.println(ntext);
		return ntext;
	}
	
}
