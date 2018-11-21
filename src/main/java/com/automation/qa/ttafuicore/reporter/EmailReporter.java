package com.automation.qa.ttafuicore.reporter;

import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.utils.Writer;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.List;


/**
 * This class houses logic to generate the emailable report
 */
public class EmailReporter extends ExtentHtmlReporter {
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(EmailReporter.class));
    private List<Test> parsedTestCollection;

    private class Marker {
    }

    public EmailReporter(String filePath) {
        super(filePath);
    }

    public EmailReporter(File file) {
        this(file.getAbsolutePath());
    }

    @Override
    public synchronized void flush() {
        if (testList == null || testList.size() == 0) {
            return;
        }
        if (parsedTestCollection != null && parsedTestCollection.size() > 0) {
            for (int ix = 0; ix < parsedTestCollection.size(); ix++) {
                testList.add(ix, parsedTestCollection.get(ix));
            }
        }
        parsedTestCollection = null;
        setEndTime(Calendar.getInstance().getTime());
        String extentSource = null;

        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
            cfg.setClassForTemplateLoading(Marker.class.getEnclosingClass(), "/views");
            cfg.setDefaultEncoding("UTF-8");
            Template template = cfg.getTemplate("email.ftl");

            StringWriter out = new StringWriter();
            template.process(templateMap, out);
            extentSource = out.toString();
            out.close();
        } catch (IOException | TemplateException e) {
            LOGGER.error("Template not found: ", e);
        }
        Writer.getInstance().write(new File(filePath), extentSource);
    }
}
