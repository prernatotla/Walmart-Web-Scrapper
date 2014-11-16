Walmart Web-Scrapper
====================

This web scrapper was written as an assignment for school.
This scrapper when given a keyword would return the number of results associated with that keyword.
If it is given a page number in addition to the keyword, it would return the title and price of each element on that page for that keyword.

This code was written in Java.
The final file is packaged as a jar.
The Jsoup library (http://jsoup.org/) was used for HTML parsing.

Usage:
Display number of results:
java -jar WalmartWebScraper.jar <input_keyword>

Display product information:
java -jar WalmartWebScraper.jar <input_keyword> <page_number>
