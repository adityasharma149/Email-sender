package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

@SpringBootApplication
public class Application {

    private final static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class,args);
        logger.info("#### ------------------------ ####");
        logger.info("####     Spring Email-Sender Started     ####");
        logger.info("#### ------------------------ ####");

        String Message="Hi,I'm sending an Attachment using Springboot Java Mail API !!";
        String Subject="Email-Sender-Springboot : Confirmation";
        String To="xyz@gmail.com";
        String From="abc@gmail.com";

//        sendEmail(Message,Subject,To,From);
        sendAttachment(Message,Subject,To,From);

    }

    //Sending Email with Attachment......
    private static void sendAttachment(String message, String subject, String to, String from) {

        //Variable for gmail
        String host="smtp.gmail.com";

        Properties properties=System.getProperties();
        System.out.println("properties :"+properties);

        //Setting Important information to properties object

        //Host Set
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port",465);
        properties.put("mail.smtp.ssl.enable",true);
        properties.put("mail.smtp.auth",true);

        //Step 1: to get Session Object
        Session session=Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("abc@gmail.com","*******");
            }
        });
        session.setDebug(true);

        //Step 2: Compose the Message (text,Multi-media)
        MimeMessage mimeMessage=new MimeMessage(session);

        try{
            //setting from
            mimeMessage.setFrom(from);

            //setting recipient
            mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(to));

            //setting Subject
            mimeMessage.setSubject(subject);

            //Specifying File Path for the Attaching file
            String path="/home/user/Downloads/door-key.png";

            MimeMultipart multipart=new MimeMultipart();

            //For text
            MimeBodyPart textmime=new MimeBodyPart();
            //For File
            MimeBodyPart filemime=new MimeBodyPart();

            try{
                textmime.setText(message);
                File file=new File(path);
                filemime.attachFile(file);

                //Now Adding both in Multipart
                multipart.addBodyPart(textmime);
                multipart.addBodyPart(filemime);

            } catch (Exception e) {
                e.printStackTrace();
            }

            mimeMessage.setContent(multipart);

            //Step 3: Send message using Transport Class
            Transport.send(mimeMessage);

            System.out.println("**************** Sent Successfully ******************");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Sending Email with Message...
    private static void sendEmail(String message, String subject, String to, String from) {

        //Variable for gmail
        String host="smtp.gmail.com";

        Properties properties=System.getProperties();
        System.out.println("properties :"+properties);

        //Setting Important information to properties object

        //Host Set
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port",465);
        properties.put("mail.smtp.ssl.enable",true);
        properties.put("mail.smtp.auth",true);

        //Step 1: to get Session Object
        Session session=Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("abc@gmail.com","*******");
            }
        });
        session.setDebug(true);

        //Step 2: Compose the Message (text,Multi-media)
        MimeMessage mimeMessage=new MimeMessage(session);

        try{
            //setting from
            mimeMessage.setFrom(from);

            //setting recipient
            mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(to));

            //setting Subject
            mimeMessage.setSubject(subject);

            //setting text message to be send
            mimeMessage.setText(message);

            //Step 3: Send message using Transport Class
            Transport.send(mimeMessage);

            System.out.println("**************** Sent Successfully ******************");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
