/**
 *
 */
package de.sulzer.utils.screenshots;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.maven.shared.utils.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * @author Sulzer GmbH
 *
 */
public class ScreenshotsSelenium implements ScreenshotTaker {

    @Override
    public void takeScreenshot(WebDriver webDriver) {

        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.sss");
            String stringDate  = dateFormat.format(new Date());

            File source = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
            String path = "error-" + stringDate + ".png";
            FileUtils.copyFile(source, new File(path));

        } catch (IOException e) {
            System.out.println("taking screenshot not possible");
            e.printStackTrace();
        }

    }

    @Override
    public void takeScreenshot(String filename, String path, WebDriver webDriver) {

        try {

            if (! filename.endsWith(".png")) {
                filename = filename + ".png";
            }

            File source = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(source, new File(path + "/" + filename));

        } catch (IOException e) {
            System.out.println("taking screenshot not possible");
            e.printStackTrace();
        }

    }

}
