package org.example;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        String startingUrl = "https://www.halifaxexaminer.ca/"; // Replace with the starting URL
        String searchWord = "a little background"; // Replace with the word to search for

        Queue<String> urlQueue = new LinkedList<>();
        urlQueue.add(startingUrl);

        while (!urlQueue.isEmpty()) {
            String currentUrl = urlQueue.poll();
            try {
                Document doc = Jsoup.connect(currentUrl).get();
                Elements links = doc.select("a[href]");

                // Search for content containing the search word
                if (doc.text().contains(searchWord)) {
                    System.out.println("Found on: " + currentUrl);
                    System.out.println(doc.text());
                    System.out.println("-------------");
                }

                // Enqueue internal links for further crawling
                for (Element link : links) {
                    String href = link.attr("href");
                    if (href.startsWith(startingUrl)) {
                        urlQueue.add(href);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}