import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.io.IOException;
import java.lang.Exception;

/*
 * Class WalmartWebScraper has functions to display results from querying the http://www.walmart.com/ site
 * Query 1: Print number of results for a given keyword
 * Query 2: Print product name and price for results from a given page
 * External library: Using library Jsoup for HTML parsing
*/
public class WalmartWebScraper
{
	// Input keyword given by user (used by both query 1 and 2)
	private static String keyword = "";
	// Input page number of results to display (used by query 2)
	private static int page_number = 1;

	public static void main(String[] args) throws IOException {
		if (args.length < 1)	{
			// If no arguements are provided, do nothing and exit
			System.out.println("No arguements specified! Exiting....");
			return;
		}
		keyword = args[0];
		// If 2 arguements are specified, perform query 2 else query 1
		if (args.length >= 2)	{
			System.out.println("Displaying product information....");
			page_number = Integer.parseInt(args[1]);
			productInformation(3);
		}
		else {
			System.out.println("Calculating number of results....");
			numberOfResults(3);
		}
	}

	// This function parses the walmart search results page to give number of results for a keyword
	// input argument 'tries_remaining' indicates the number of times the script will retry in case of a timeout
	public static void numberOfResults(int tries_remaining) throws IOException	{
		if (tries_remaining <= 0)	{
			System.out.println("Oops.. Request timed out! Exiting....");
			return;
		}
		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.walmart.com/search/?query="+keyword).get();
		}	catch (Exception e) {
			numberOfResults(tries_remaining-1);
			return;
		}

		Element summary = doc.getElementsByClass("result-summary-container").first();
		String[] summary_split = summary.text().split("\\s");
		System.out.println("********************************************");
		System.out.println("Number of results for '" + keyword + "' : " + summary_split[3]);
		System.out.println("********************************************");
	}

	// This function displays product information for all results for a keyword on a given page
	// Input argument 'tries_remaining' indicates the number of times the script will retry in case of a timeout
	public static void productInformation(int tries_remaining) throws IOException	{
		if (tries_remaining <= 0)	{
			System.out.println("Oops.. Request timed out! Exiting....");
			return;
		}
		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.walmart.com/search/?query="+keyword+"&page="+page_number+"&cat_id=0").get();
		}	catch (Exception e) {
			numberOfResults(tries_remaining-1);
			return;
		}

		Elements result_tiles = doc.getElementsByClass("tile-landscape");
		System.out.println("****************************************************************************************************************");
		System.out.println("KEYWORD: " + keyword + ", PAGE NUMBER: " + page_number + "\n\n");
		for (Element result:result_tiles)	{
			Element tile_heading = result.getElementsByClass("tile-heading").first();
			System.out.println("Product name/Title: " + tile_heading.text());
			Element tile_price = result.getElementsByClass("price-display").first();
			System.out.println("Price: " + tile_price.text() + "\n");
		}
		System.out.println("****************************************************************************************************************");
	}
}