package com.automation.qa.ttafuicore.util;

import java.io.*;
import java.util.Properties;

/**
 * Created by DilshanF on 10/23/2018.
 */
public class FrameworkProperties {
    private Properties properties;


    /**
     * @param projectSettingsFile
     * @return
     * @throws Exception
     */
    public Properties readProjEnvConfig(String projectSettingsFile) throws Exception {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(projectSettingsFile));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + projectSettingsFile);
        }

        return properties;

    }
}