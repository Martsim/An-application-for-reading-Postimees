package pack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

/**
 * The Requests class provides methods for requesting article data from the web.
 * 
 * @author Mart Simisker 
 */
public class Requests {

	/**
	 * Method for requesting the article headlines and URL-s.
	 * @return List of Pairs of Headlines and URL-s.
	 * @throws BadConnectionException - In case of connection failure.
	 */
	public static List<Pair> getArticleList() throws BadConnectionException {
		ArrayList<Pair> headlines = new ArrayList<Pair>(); 
		//Group 2 is href, group 4 is title		
		String pattern1 = "<article[^>]*>(.(?!a href))*<a href=" + '"' + "([^"+ '"' + "]*)" + '"' + "(.(?!headline name))*[^>]*>([^<]*)(.(?!/article>))*</article>";
		Pattern articlePattern = Pattern.compile(pattern1);
		
		String htmlText = "";
		try {
			URL postimees;
			postimees = new URL("http://www.postimees.ee/");
			URLConnection uc = postimees.openConnection();
	        BufferedReader in = new BufferedReader(
	        		new InputStreamReader(uc.getInputStream()));
	        
	        
	        String inreader;
	        
	        while ((inreader = in.readLine()) != null) {
	        	htmlText += inreader;
	        }            
	        in.close();
		} catch (Exception e) {
			throw new BadConnectionException("Bad connection");
			
		}
		
        Matcher m = articlePattern.matcher(htmlText);
        
		//Request ;
        String headline, link;
		while (m.find()) {
			
			headline = m.group(4);
			link = m.group(2);
			if (headline != null) {
				headlines.add(new Pair(headline, link));			
			}
		}
		
		return headlines;
	}
	
	/**
	 * Method for requesting an article.
	 * @param urlAddress - article address in URL form.
	 * @return The article.
	 * @throws BadConnectionException - In case of connection failure.
	 */
	public static String getArticle(String urlAddress) throws BadConnectionException {
		String article = ""; 
		String pattern1 = "<article(>| [^>]*>)(.(?!articleBody))*[^>]*>((.(?!/p></section>))*)(.(?!/article>))*</article";
		Pattern articlePattern = Pattern.compile(pattern1);
		
		String htmlText = "";
		try {
			URL postimees;
			postimees = new URL(urlAddress);
			System.out.println("Past the url address");
			URLConnection uc = postimees.openConnection();
	        BufferedReader in = new BufferedReader(
	        		new InputStreamReader(uc.getInputStream()));
	        	        
	        String inreader;
	        
	        while ((inreader = in.readLine()) != null) {
	        	htmlText += inreader;
	        }            
	        in.close();
		} catch (Exception e) {
			throw new BadConnectionException("Bad connection");
			
		}
        		
        Matcher m = articlePattern.matcher(htmlText);
        
		while (m.find()) {
			article = m.group(3);
			
		}
		
		//cleaning
		article = article.replaceAll("<[^>]*>", "");		
		return article;
	}
	
	
}
