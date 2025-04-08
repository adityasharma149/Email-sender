package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

@SpringBootApplication
public class SendSMS {

    private final static Logger logger = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args)  {

        SpringApplication.run(Application.class,args);
        logger.info("#### ------------------------ ####");
        logger.info("####     Message Gateway Service Started     ####");
        logger.info("#### ------------------------ ####");
        
        SendSMS.sendSMS("This is a Message for testing SMS API "+new Date().toLocaleString(),"********");
    }

    private static void sendSMS(String message,String number) {

        try
        {
            //Using FAST@SMS API Key
            String apikey="***************";

            String SenderId="TXTIND";

            //To encode the URl
            message= URLEncoder.encode(message,"UTF-8");

            String language="english";

            String route="v3";

            //Complete URl
            String myurl="https://www.fast2sms.com/dev/bulkV2?authorization="+apikey+"&sender_id="+SenderId+"&message="+message+"&route="+route+"&numbers="+number;

            //Sending Request using Java
            URL url=new URL(myurl);

            HttpsURLConnection conn=(HttpsURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent","Mozilla/5.0");
            conn.setRequestProperty("cache-control","no-cache");

            int code= conn.getResponseCode();
//            System.out.println("Response Code :"+code);
            StringBuffer response=new StringBuffer();

            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while(true)
            {
                String line=br.readLine();
                if(line==null)
                {
                    break;
                }
                response.append(line);
            }

            System.out.println("Response :"+response);

        }
        catch (UnsupportedEncodingException | MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
