import com.gargoylesoftware.htmlunit.html.Keyboard;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.*;

/**
 * Created by Iphone on 4/15/2018.
 */
public class DownloadPostcodesLonLat {

    public static void main(String[] args) throws IOException, InterruptedException {
        FirefoxDriver driver = new FirefoxDriver();
        driver.get("https://www.latlong.net/");

        try (BufferedReader br = new BufferedReader(new FileReader("Postcodes.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                Thread.sleep(3000);
                try {
                    driver.findElementByXPath("//input[@placeholder='Type a place name']").sendKeys(line.substring(0,4) + " " + line.substring(4,6) + " NL");
                    driver.findElementsByClassName("button").get(0).click();
                    String latitude = driver.findElementById("lat").getAttribute("value");
                    String longitude = driver.findElementById("lng").getAttribute("value");
                    BufferedWriter output = new BufferedWriter(new FileWriter("result.csv", true));
                    output.write(line + "," + latitude + "," + longitude + "\n");
                    output.close();
                } catch (Exception e) {
                    BufferedWriter output = new BufferedWriter(new FileWriter("result.csv", true));
                    output.write(line + "," + "null" + "," + "null" + "\n");
                    output.close();
                }
                driver.findElementByXPath("//input[@placeholder='Type a place name']").sendKeys("\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008\u0008");
            }
        }
        driver.close();
    }
}
