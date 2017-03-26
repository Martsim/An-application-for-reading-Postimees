package pack;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {

	int HEIGHT = 500;
	int WIDTH = 400;
	//Primary Stage
	Stage pstage;
	Scene articleList;
	
	public static void main(String[] args) {
	    /*try {
			//Requests.getArticleList();
	    	Requests.getArticle("http://majandus24.postimees.ee/4058527/saksa-pank-kandis-ekslikult-miljardeid-teistesse-pankadesse?_ga=1.98437851.574480388.1454928314");
			System.out.println("And done");
		} catch (BadConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
	    pstage = primaryStage;
		primaryStage.setTitle("Postimehe luger");
	    articleList = ArticleListView.generate(this, HEIGHT, WIDTH);
	    primaryStage.setScene(articleList);
	    primaryStage.show();

	}

	/**
	 * Function for opening an article.
	 */
	public void openLink(String link) {
		pstage.setScene(ArticleReadView.generate(this, HEIGHT, WIDTH, link));
	    pstage.show();
	}
	
	/**
	 * Function for returning to article view
	 */
	public void backToListView() {
		pstage.setScene(articleList);
	    pstage.show();
	}

}