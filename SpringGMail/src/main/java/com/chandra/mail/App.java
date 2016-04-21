package com.chandra.mail;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App 
{
    public static void main( String[] args )
    {
    	ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");
    	 
    	MailMail mm = (MailMail) context.getBean("mailMail");
        mm.sendMail("Chandrasekar J", "This is mail body");
        mm.sendMail("from@gmail.com",
     		   "to@gmail.com",
     		   "Mail Subject", 
     		   "Hi \n\n My First Spring Email");
        
    }
}
