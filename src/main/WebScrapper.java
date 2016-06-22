package main;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.jetty.html.Page;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Goes to www.forecastapp.com, logs in, downloads a data CSV file, converts the
 * file to a JSON File which is then output in the directory mentioned
 *
 * @author alam
 *
 */
public final class WebScrapper {

    //private JBrowserDriver driver = null;
    private WebDriver driver = null;
    private String fileDirectory = null;
    private String username = new String();
    private String password = new String();
    private String startDate = new String();

    public WebScrapper(String downloadDirectory, String username, String password) {

        /*FirefoxProfile profile = new FirefoxProfile();
        
        //Set the preferences to download to a specific folder 
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.download.dir", downloadDirectory);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv");
        driver = new FirefoxDriver(profile);*/
        fileDirectory = new String("");
        fileDirectory = downloadDirectory;
        this.username = username;
        this.password = password;
        
        System.setProperty("webdriver.chrome.driver", "/Users/alam/Documents/AuthX/AuthX-ForecastProject/ExternalJars/chromedriver");

        //System.setProperty("webdriver.chrome.driver", "ExternalJars/chromedriver");
        
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", fileDirectory);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        cap.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(cap);
         
 /*
        String[] cli_args = new String[]{ "--ignore-ssl-errors=true" };
        
        Capabilities caps = new DesiredCapabilities();
                ((DesiredCapabilities) caps).setJavascriptEnabled(true);                
                ((DesiredCapabilities) caps).setCapability("takesScreenshot", true);  
                ((DesiredCapabilities) caps).setCapability(
                        PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                        "/Users/alam/Downloads/phantomjs-2.1.1-macosx/bin/phantomjs"
                    );
                ((DesiredCapabilities) caps).setCapability("download.default_directory", fileDirectory);
                ((DesiredCapabilities) caps).setCapability( PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cli_args );
         */
 /*
        String[] cli_args = new String[]{ "--ignore-ssl-errors=true" };
        DesiredCapabilities caps = DesiredCapabilities.phantomjs();
        caps.setCapability( PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cli_args );
        caps.setCapability( PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/Users/alam/Downloads/phantomjs-2.1.1-macosx/bin/phantomjs");
        driver =  new PhantomJSDriver( caps );

        //driver = new PhantomJSDriver(caps);
        
         */
 
        /*
        DesiredCapabilities jBrowser = DesiredCapabilities.htmlUnit();

        Capabilities caps = new DesiredCapabilities();
        ((DesiredCapabilities) caps).setJavascriptEnabled(true);
        ((DesiredCapabilities) caps).setCapability("download.default_directory", fileDirectory);
        driver = new JBrowserDriver(jBrowser);

        //webClient.addWebWindowListener(new WebWindowListener(
        */
    }

    /**
     * Open the Download website.
     */
    private void openTestSite() {
        driver.navigate().to("https://id.getharvest.com/forecast/sign_in");
    }

    /**
     *
     *
     * Logins into the website, by entering provided username and password
     *
     * @throws InterruptedException
     */
    private void login() throws InterruptedException {

        //Set the username and password to login into Forecastapp
        WebElement userName_editbox = driver.findElement(By.id("email"));
        WebElement password_editbox = driver.findElement(By.id("password"));
        WebElement submit_button = driver.findElement(By.xpath("//*[@id='log-in']"));

        //Send details in the fields and click on submit button to login 
        userName_editbox.sendKeys(this.username);
        password_editbox.sendKeys(this.password);
        submit_button.click();

        //System.out.println("Login Successful");
    }

    /**
     * Navigate to the exports page and download the file to the computer at the
     * link specific in the construction of the driver
     */
    private void getData() throws MalformedURLException, IOException {
        //wait for the page after the login page to be loaded 
        WebDriverWait wait = new WebDriverWait(driver, 500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ember-view dropdown-button app-nav-dropdown']")));
        
        //Page after login page loaded!
        //Find the schedule button and click it to open the drop down menu 
        WebElement schedule = driver.findElement(By.xpath("//*[@class='ember-view dropdown-button app-nav-dropdown']"));
        schedule.click(); //clicks on schedule button 
        
        //System.out.print(driver.findElement(By.xpath("//*[@id='ember883']")).getText());
        //wait for the page after the login page to be loaded 
        wait = new WebDriverWait(driver, 500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[.='Export']")));

        //Find export button and click on it 
        WebElement export = driver.findElement(By.xpath("//*[.='Export']")); //clicks on export button in drop down
        export.click();

        //Wait for the Export page to be loaded!
        wait = new WebDriverWait(driver, 500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='button button-primary button-active-like-disabled']")));
        
        //We are on the exports page now
        //Wait for the Export page to be loaded!
        wait = new WebDriverWait(driver, 500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='range-start ']")));

        //Get the start date from this page 
        WebElement startDateContent = driver.findElement(By.xpath("//*[@class='range-start ']"));
        startDate = startDateContent.getText();

        WebElement downloadData = driver.findElement(By.xpath("//button[@class='button button-primary button-active-like-disabled']")); //clicks on export button in drop down
        downloadData.click();
                          
    }

    /**
     * Close the browser
     */
    private void closeBrowser() {
        driver.close();
    }

    /**
     * Converts a CSV File to an equivalent JSON file
     *
     * @param csvFileAddr The File we want to convert to a JSON
     * @return The JSON Array that we want to return, as a string
     * @throws JSONException
     * @throws FileNotFoundException
     */
    private static String convertToJSON(File csvFileAddr) throws JSONException, FileNotFoundException {

        //Parse the CSV file and convert it to a string 
        String csvFile = new String("");
        Scanner scan = new Scanner(csvFileAddr);
        scan.useDelimiter("\n");
        int numLines = 0;

        while (scan.hasNext()) {
            String s1 = scan.next();
            numLines++;
            csvFile = csvFile + s1 + '\n';
        }
        scan.close();

        JSONArray array = CDL.toJSONArray(csvFile);
        String JSONObject = array.toString(numLines - 1);
        return JSONObject;
    }

    /**
     * Exports the JSON String as a .JSON file
     *
     * @throws JSONException
     * @throws IOException
     */
    private void exportJSON(File csvFile) throws JSONException, IOException {
        String path = fileDirectory + "/JSONFile.json";
        //String jsonFile = convertToJSON(csvFile);
        String jsonFile = convertToJSON(csvFile);
        //System.out.println("Converted stream is " + jsonFile);
        FileWriter writer = new FileWriter(new File(path));
        writer.append(jsonFile);
        writer.close();
    }

    /**
     * Gets the latest file in a specific directory, ignoring all hidden files
     *
     * @param dirPath The path of the directory in which we are searching
     * @return The latest file in that directory
     */
    private static File getLatestFilefromDir(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }

        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
            if (lastModifiedFile.lastModified() < files[i].lastModified() || lastModifiedFile.isHidden()) {
                lastModifiedFile = files[i];
            }
        }

        //return lastModifiedFile; //DO NOT COMMENT OUT IF USING RAHUL FILE 
        /*COMMENT OUT BOTTOM LINE IF USING FORECASTAPP.COM INSTEAD OF RAHUL's FILE */
        return new File("/Users/alam/Documents/Forecast-0613.csv");
    }

    /**
     * This takes the JSON File previously downloaded and converts it into an
     * array for future processing
     *
     * @return A JSON Array that can be manipulated in order to use tuples
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParseException
     */
    private org.json.simple.JSONArray parseJSONFile() throws FileNotFoundException, IOException, ParseException {
        String path = fileDirectory + "/JSONFile.json";
        JSONParser parser = new JSONParser();
        org.json.simple.JSONArray contentArray = (org.json.simple.JSONArray) parser.parse(new FileReader(
                path));
        return contentArray;
    }

    /**
     * stem. This is the driver method for this class. Calling this with the
     * right parameters will successfully download a JSON file in the directory
     * specified
     *
     * @return A JSON Array that can then be used to access tuples
     * @throws InterruptedException -
     * @throws JSONException -
     * @throws IOException -
     * @throws ParseException -
     */
    public org.json.simple.JSONArray downloadJSONArray() throws InterruptedException, JSONException, IOException, ParseException {

        //Open website and download data to named directory 
        System.out.println("Started System");
        openTestSite();
        System.out.println("Website opened");
        login();
        System.out.println("Log in Completed");
        getData();
        System.out.println("Data Acquisiton Completed");

        //Put thread to sleep so that library can update itself 
        try {
            Thread.sleep(2000);                 //1000 milliseconds is one second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        closeBrowser();

        //Convert the CSV to JSON
        File csvFileAddr = getLatestFilefromDir(fileDirectory + "/");
        System.out.println("Address of latest File is + " + csvFileAddr.getPath());

        //Put JSON In folder
        exportJSON(csvFileAddr);

        //Extract a JSON Array from the JSON File 
        return parseJSONFile();

    }

    /**
     * Get the start date from a string that is a start date
     *
     * @return The date object resembling the start date string
     * @throws java.text.ParseException -
     */
    public Date getStartDate() throws java.text.ParseException {

        DateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        Date date = format.parse(startDate);
        return date;
    }

    public static void main(String[] args) throws IOException, InterruptedException, JSONException, ParseException, java.text.ParseException {

        WebScrapper webSrcapper = new WebScrapper("/Users/alam/Documents/GRE/", "theoriginalsine@gmail.com", "forecast"); //this will be given as a parameter 
        webSrcapper.downloadJSONArray();
        System.out.println(webSrcapper.getStartDate());

        //String jSONResult = convertToJSON(new File("/Users/alam/Documents/forecast-project-export-from-2016-06-13-to-2017-06-30.csv"));
        //System.out.println(jSONResult);
    }
}
