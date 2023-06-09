/* This Java program sets up the basic structure for scraping Weibo using the user ID and cookie provided. 
*/
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class WeiboScraper {
    private static final int USER_ID = 123456; // Replace with your user ID
    private static final String COOKIE = "YOUR_COOKIE"; // Replace with your cookie
    private static final Pattern ORIPIC_PATTERN = Pattern.compile("^http://weibo.cn/mblog/oripic");
    private static final Pattern PICALL_PATTERN = Pattern.compile("^http://weibo.cn/mblog/picAll");

    public static void main(String[] args) {
        scrapeWeibo(USER_ID, COOKIE);
    }

    public static void scrapeWeibo(int userId, String cookie) {
        String url = "http://weibo.cn/u/" + userId + "?filter=1&page=1";
        String html = getHtml(url, cookie);

        Document doc = Jsoup.parse(html);
        int pageNum = Integer.parseInt(doc.select("input[name=mp]").attr("value"));

        // ... (continue with the rest of the code)
    }

    private static String getHtml(String url, String cookie) {
        // Create an HTTP client with the given cookie
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build();

        // Set up the HTTP GET request
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Cookie", cookie);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000)
                .build();
        httpGet.setConfig(requestConfig);

        // Execute the HTTP GET request and retrieve the content
        String content = null;
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            content = new String(response.getEntity().getContent().readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }
}
