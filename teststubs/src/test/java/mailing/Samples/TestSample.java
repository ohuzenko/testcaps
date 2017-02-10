package mailing.Samples;


import mailing.MailSender;
import mailing.MessageData;
import mailing.ReportOnError;
import mailing.SMTPProperties;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Arrays;


public class TestSample {

    @Test
    @ReportOnError(testDeveloperMail = "[testdevelmail]", testAnalystMail = "[testanalystmail]")
    public void testMailSenderToAnalyst() {
        Assert.assertTrue(false);
    }

    @Test
    @ReportOnError(testDeveloperMail = "[testdevelmail]", testAnalystMail = "[testanalystmail]")
    public void testMailSenderToDeveloper() {
        int x = 5/0;
    }


    @AfterMethod
    public void AnalyzeResult(Method m, ITestResult testResult){


        if(testResult.isSuccess()) return;
        if(!m.isAnnotationPresent(ReportOnError.class)) return;

        MessageData message;

        ReportOnError report = m.getAnnotation(ReportOnError.class);

        String text =  m.toString() +   Arrays.toString(m.getParameters()) +": " + testResult.getThrowable().toString();

        if(testResult.getThrowable().toString().contains("AssertionError")){
            if(report.testAnalystMail().equals("")){
                return;
            }else{

                message = new MessageData("[sender-mail]", "[sender-password]", report.testAnalystMail(), "ASSERTION FAILED NOTIFICATION: "+ m.getName() , text);
            }

        }else{

            message = new MessageData("[sender-mail]", "[sender-password]", report.testDeveloperMail(), "TEST FAILED NOTIFICATION: "+ m.getName() , text);
        }

        SMTPProperties prop = new SMTPProperties("smtp.gmail.com", "true", "465", "javax.net.ssl.SSLSocketFactory");
        MailSender sm = new MailSender();
        sm.sendNotification(prop, message);

    }
}
