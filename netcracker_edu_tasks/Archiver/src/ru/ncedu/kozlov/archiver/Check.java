package ru.ncedu.kozlov.archiver;

import java.io.IOException;

public class Check {

	public static void main(String[] arg) {
		
		String[] strs = {"testfile1", "testfolder", "testfile2"};
		try {
			Archiver.zipFiles("testzip.zip", strs, "novi pack");
			System.out.println("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("fail");
		}
		
		try {
			System.out.println(Archiver.readComment("testzip.zip"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("fail");
		}
		
		try {
			Archiver.unzipFiles("testzip.zip", "tounzip/");
			System.out.println("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("fail");
		}
		/*
		String[] addfls = {"testaddfile"};
		try {
			Archiver.addFileToArchive(strs[0], addfls);
			System.out.println("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("fail");
		}
		*/
	}

}
