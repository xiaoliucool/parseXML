/**
 * ³ÌÐòÈë¿Ú
 */
package com.xiaoliu;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class XmlSaxTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Map<String, String>> list = (ArrayList<Map<String, String>>) SaxService
				.ReadXML("f:" + File.separator + "xml" + File.separator
						+ "test.xml", "observations");
		/*
		 * for(int i=0;i<list.size();i++){ HashMap<String, String>
		 * temp=(HashMap<String, String>) list.get(i); Iterator<String>
		 * iterator=temp.keySet().iterator(); while(iterator.hasNext()){ String
		 * key=iterator.next().toString(); String value=temp.get(key);
		 * System.out.print(key+" "+value+"--"); } }
		 */
		System.out.println(list.toString());
		SaxService.createXML(list);
		// WriteToExcelUtils.writeExcel("f:\\xml\\stu.xls", list);
		System.out.println("completed");
	}

}