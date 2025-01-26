package Utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stepDefinition.Hooks;

public class CommonUtility extends Hooks {

    public static Logger Log=LogManager.getLogger(CommonUtility.class);

    public void getSiteURL() {
        driver.get(AppURL);
        driver.manage().window().maximize();
        Log.info("Opening the site: {}", AppURL);
    }

    }

