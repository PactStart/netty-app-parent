package com.pactrex.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pactrex.client.swing.Swingclient;

public class ClientMain {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		Swingclient swing = applicationContext.getBean(Swingclient.class);
		swing.setVisible(true);
	}

}
