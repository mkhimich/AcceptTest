package com.accept.qa.testtask.util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Util that takes screenshot after failure.
 * Created by mkhimich on 30.03.2017.
 */
public class ScreenshotManager {
    public static final String SNAPSHOT_DIRECTORY = "snapshot.directory";
    public static final String USER_DIR = "user.dir";
    public static final String USER_HOME = "user.home";
    public static final String OS_NAME = "os.name";
    public static final String STORABLE_SCREENSHOTS = "storableScreenshots";

    private static SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy__HH_mm_ss");

    public static String takeScreenshot(String snapshotName, WebDriver driver) {
        File tempScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String basePath;
        String storeSnapshotsInBasedir = System.getProperty(STORABLE_SCREENSHOTS);
        if (storeSnapshotsInBasedir != null && storeSnapshotsInBasedir.equals("true")) {
            String basedir = System.getProperty(USER_DIR);
            if (basedir == null || basedir.isEmpty()) {
                basedir = ".";
            }
            basePath = basedir + File.separator + "snapshots";
        } else if (System.getProperty(OS_NAME).contains("OS X")) {
            basePath = System.getProperty(USER_HOME) + File.separator + "snapshots";
        } else if ((System.getenv("WORKSPACE") != null) && (!System.getenv("WORKSPACE").isEmpty())) {
            basePath = "." + File.separator + "snapshots";
        } else {
            basePath = PropertiesContext.getInstance().getProperty(SNAPSHOT_DIRECTORY);
        }
        String pathname = basePath + File.separator + snapshotName + "---" + format.format(new Date()) + ".jpg";
        try {
            FileUtils.copyFile(tempScreenshot, new File(pathname));
        } catch (IOException e) {
            throw new RuntimeException("Can't write file " + pathname + ", are permissions OK?", e);
        }
        return pathname;
    }
}
