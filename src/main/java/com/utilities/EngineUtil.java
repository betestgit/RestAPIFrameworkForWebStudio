package com.utilities;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class EngineUtil {


	public static void startEngine(String command, String[] envp, String dir)  {
		/*try {
			Process process = Runtime.getRuntime().exec(command, envp, new File(dir));
			InputStream errorStream = process.getErrorStream();
			BufferedOutputStream outputstream = (BufferedOutputStream) process.getOutputStream();
			
			
			
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			  String line;
			while ((line = input.readLine()) != null) {
			    System.out.println(line);
			  }
			  input.close();
			process.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		try {
			Runtime.getRuntime().exec(command, envp, new File(dir));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void killEngine(String processName) {
		try {
			Runtime.getRuntime().exec("taskkill /F /IM "+processName+".exe");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	

}
