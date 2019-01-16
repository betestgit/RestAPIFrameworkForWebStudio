package com.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;

public class JsonFileReader {
	String jSonResponse;
	
	public String read(String filePath) {
		try {
			InputStream is = getClass().getClassLoader()
	                .getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			jSonResponse = IOUtils.toString(br);
			} catch (IOException e) {

				e.printStackTrace();
			}
		return jSonResponse;
	}

}
