package com.pactrex.io.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class BioClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("localhost", 7777);
		OutputStream out = socket.getOutputStream();
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
		Scanner sc = new Scanner(System.in);   
		String line;
		while(true){
			line = sc.next();
			pw.println(line);
			pw.flush();
		}
	}
}
