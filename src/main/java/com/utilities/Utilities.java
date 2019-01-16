package com.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class Utilities {

	String jSonResponse;
	
	public static void copy(String source, String destination) {
		File srcDir = new File(source);
		File destDir = new File(destination);

		recursiveCopy(srcDir, destDir);

	}

	private static void recursiveCopy(File fSource, File fDest) {
		try {
			if (fSource.isDirectory()) {
				// A simple validation, if the destination is not exist then create it
				if (!fDest.exists()) {
					fDest.mkdirs();
				}

				// Create list of files and directories on the current source
				// Note: with the recursion 'fSource' changed accordingly
				String[] fList = fSource.list();

				for (int index = 0; index < fList.length; index++) {
					File dest = new File(fDest, fList[index]);
					File source = new File(fSource, fList[index]);

					// Recursion call take place here
					recursiveCopy(source, dest);
				}
			} else {
				// Found a file. Copy it into the destination, which is already created in 'if'
				// condition above

				// Open a file for read and write (copy)
				FileInputStream fInStream = new FileInputStream(fSource);
				FileOutputStream fOutStream = new FileOutputStream(fDest);

				// Read 2K at a time from the file
				byte[] buffer = new byte[2048];
				int iBytesReads;

				// In each successful read, write back to the source
				while ((iBytesReads = fInStream.read(buffer)) >= 0) {
					fOutStream.write(buffer, 0, iBytesReads);
				}

				// Safe exit
				if (fInStream != null) {
					fInStream.close();
				}

				if (fOutStream != null) {
					fOutStream.close();
				}
			}
		} catch (Exception ex) {
			// Please handle all the relevant exceptions here
		}
	}

	public static XmlPath rawToXml(Response res) {
		String response=res.asString();
		XmlPath path=new XmlPath(response);
		return path;
	}

	public static JsonPath rawToJson(Response res) {
		String response=res.asString();
		JsonPath path=new JsonPath(response);
		return path;
	}




}
