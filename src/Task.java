import Utility.BaseDriver;
import Utility.MyFunc;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Task extends BaseDriver {

    public static Actions actions = new Actions(driver);

    @Test
    public void transperfect() throws IOException {
        //1-Go to www.transperfect.com
        driver.get("https://www.transperfect.com/");
        wait.until(ExpectedConditions.urlToBe("https://www.transperfect.com/"));

        //2-Close the popup if needed
        List<WebElement> popUp = driver.findElements(By.xpath("//*[text()='Deny all']"));
        if (!popUp.isEmpty()) {
            popUp.get(0).click();
        }

        //3-Click on Industries in the top navigation bar
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Industries")));
        WebElement industriesButton = driver.findElement(By.partialLinkText("Industries"));
        industriesButton.click();

        //4-Click on Retail & E-commerce
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='t-page-cards-item-content'])[2]")));
        WebElement retailButton = driver.findElement(By.xpath("(//div[@class='t-page-cards-item-content'])[2]"));
        MyFunc.scrollElement(retailButton);
        actions.moveToElement(retailButton).click().build().perform();

        //5-Wait 5 seconds
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //6-Scroll down/move the screen until Client Stories are visible
        WebElement clientStories = driver.findElement(By.xpath("//div[@class='text-left']//div"));
        MyFunc.scrollElement(clientStories);

        try {
            Thread.sleep(1 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<WebElement> rejectAll = driver.findElements(By.xpath("//*[text()='Deny all']"));
        if (!rejectAll.isEmpty()) {
            rejectAll.get(0).click();
        }

        //7-Click on the search engine icon in the top navigation bar
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='t-search-link']")));
        WebElement searchIcon = driver.findElement(By.xpath("//span[@class='t-search-link']"));
        wait.until(ExpectedConditions.elementToBeClickable(searchIcon));
        searchIcon.click();
        Assert.assertTrue("Button inactive", searchIcon.isEnabled());

        //8-Enter text "translation" in the Search text... textbox
        WebElement searchText = driver.findElement(By.name("search_api_fulltext"));
        wait.until(ExpectedConditions.elementToBeClickable(searchText));
        searchText.click();
        searchText.sendKeys("translation");

        //9-Delete the text you just entered
        searchText.sendKeys(Keys.CONTROL + "a", Keys.DELETE);

        //10-Enter "quote" in the Search text... textbox
        searchText.click();
        searchText.sendKeys("quote" + Keys.ENTER);

        //11-Wait for the "Request a Free Quote" search result to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Request a Free Quote']")));
        WebElement requestText = driver.findElement(By.xpath("//a[text()='Request a Free Quote']"));
        Assert.assertTrue("Message not displayed", requestText.isDisplayed());

        //12-Click on Request a Free Quote
        requestText.click();

        //13-Hover the mouse button over Website Localization to cause the popup with the description to appear
        WebElement websiteHover = driver.findElement(By.xpath("(//div[@id='edit-your-interests']//label)[2]"));
        actions.moveToElement(websiteHover).perform();

        //14-Tick the boxes for Translation Services and Legal Services
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-your-interests-translation")));
        WebElement translationClick = driver.findElement(By.id("edit-your-interests-translation"));
        translationClick.click();
        WebElement legalClick = driver.findElement(By.id("edit-your-interests-legal"));
        legalClick.click();

        //15-Enter text into First Name text box
        WebElement firstName = driver.findElement(By.id("edit-first-name"));
        firstName.sendKeys("Ümmühan" + Keys.ENTER);
        MyFunc.scrollElement(firstName);

        //16-Generate a random number and enter it into Telephone text box
        WebElement telephoneNumber = driver.findElement(By.id("edit-phone-work"));
        long random = ThreadLocalRandom.current().nextLong(10000000000L, 100000000000L);
        String telephoneNo = String.valueOf(random);
        telephoneNumber.sendKeys(telephoneNo + Keys.ENTER);

        //17-Take a screenshot
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter imgFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.HH.mm.ss");

        TakesScreenshot ts = (TakesScreenshot) driver;
        File hafizadakiFile = ts.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(hafizadakiFile, new File("Img\\" + localDateTime.format(imgFormat) + "screenShot.jpg"));

        File fileInMemory = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String desktop = System.getProperty("user.home") + "/Desktop";

        try {
            FileUtils.copyFile(fileInMemory, new File(desktop + "\\screenShot.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //18-Change the website language from English to Italian
        WebElement language = driver.findElement(By.xpath("(//div[@class='content']//button)[11]"));
        language.click();
        WebElement languageItalian = driver.findElement(By.xpath("//a[text()='Italiano']"));
        languageItalian.click();

        //19-Open the Solutions (Soluzioni) page in a new tab
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Soluzioni")));
        WebElement solutionsPage = driver.findElement(By.partialLinkText("Soluzioni"));
        actions.keyDown(Keys.CONTROL).click(solutionsPage).keyUp(Keys.CONTROL).perform();

        //20-Switch to new opened TAB
        Set<String> windowHandle = driver.getWindowHandles();
        for (String newPage : windowHandle) {
            driver.switchTo().window(newPage);
        }

        try {
            Thread.sleep(1 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //21-Close the browser
        driver.close();
    }
}

