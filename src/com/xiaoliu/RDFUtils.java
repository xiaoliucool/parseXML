package com.xiaoliu;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;

/**
 * @author 作者 E-mail: xiaoliucool@126.com
 * @date 创建时间：2015年12月6日 上午9:52:46
 * @version 1.0
 */
public class RDFUtils {
	final static String inputFileName = "f:\\xml\\test\\11.rdf";
	final static String RDF = "http://www.w3.org/1999/02/22-fdf-syntax-ns";
	final static String RDF_TYPE = RDF + "#" + "type";
	final static String fileDir = "f:\\xml\\object";// "f:\\xml\\obs_data_rdf";
	static int i = 0;

	public static void run() {
		File[] files = getFiles(fileDir);

		for (File file : files) {
			Map<String, List<Map<String, String>>> senderMap = new HashMap<String, List<Map<String, String>>>();
			// List<Map<String, String>> list = new
			// ArrayList<Map<String,String>>();
			HashSet<String> senderSet = new HashSet<String>();
			String fileName = file.getAbsolutePath();
			System.out.println(fileName);
			getWantedItems(readRDF(fileName), senderMap, senderSet);
			WriteToExcelUtils.writeExcel("f:\\xml\\out7", senderMap, senderSet);
			// WriteToExcelUtils.writeToDifferExcel("f:\\xml\\out", list);
			// System.out.println(list);
			// System.out.println(list.size());
			System.out.println("*******************************************");
		}
		// SaxService.createXML(list);
	}

	public static void start() {
		File[] files = getFiles(fileDir);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (File file : files) {
			String fileName = file.getAbsolutePath();
			System.out.println(fileName);
			// getWantedItems(readRDF(fileName), list);
			// WriteToExcelUtils.writeExcel("f:\\xml\\xy.xls", list);
			// System.out.println(list);
			System.out.println(list.size());
			System.out.println("*******************************************");
		}
		SaxService.createXML(list);
	}

	public static File[] getFiles(String dir) {
		File root = new File(dir);
		File[] files = root.listFiles();
		return files;
	}

	public static Model readRDF(String fileName) {
		Model model = ModelFactory.createDefaultModel();
		InputStream in = FileManager.get().open(fileName);
		if (in == null) {
			throw new IllegalArgumentException("File: " + fileName
					+ " not found");
		}

		// 读取RDF/XML 文件
		return model.read(in, com.hp.hpl.jena.vocabulary.RDF.getURI(),
				"RDF/XML-ABBREV");

		// model.write(System.out);
	}

	/**
	 * 获取经纬度以及发送者ID
	 * 
	 * @param model
	 *            数据
	 * @param list
	 *            存储结果
	 */
	public static void getWantedItems(Model model,
			Map<String, List<Map<String, String>>> senderMap,
			HashSet<String> nameSet) {
		ResIterator subjects = model.listSubjects();
		while (subjects.hasNext()) {
			Resource subject = subjects.next();
			// Property property = model.createProperty();
			// System.out.println(subject.getLocalName());
			StmtIterator properties = subject.listProperties();
			Map<String, String> item = new HashMap<String, String>();
			String sender = null;
			while (properties.hasNext()) {
				Statement stmt = properties.nextStatement();
				Property predicate = stmt.getPredicate();
				RDFNode object = stmt.getObject();
				String val = null;
				String name = predicate.getLocalName().trim();
				// System.out.println(name);
				val = object.toString().split("\\^\\^")[0];
				if (name.equals("hasLongitude")) {
					item.put("longitude", val);
				} else if (name.equals("hasLatitude")) {
					item.put("latitude", val);
				} else if (name.equals("hasDateTime")) {
					item.put("dateTime", val);
					String[] time = val.substring(11).split("\\:");
					int tmp = Integer.parseInt(time[0]) * 3600
							+ Integer.parseInt(time[1]) * 60
							+ Integer.parseInt(time[2]);
					item.put("seconds", tmp+"");
				} else if (name.equals("hasSender")) {
					sender = val.trim();
					if(sender.length()>7){
						item.put("sender", val);
						nameSet.add(sender);
					}
				}
			}

			if (senderMap.get(sender) != null) {
				senderMap.get(sender).add(item);
			} else {
				//System.out.println("新车到来：" + sender + "创建list成功");
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				list.add(item);
				senderMap.put(sender, list);
			}
			properties.close();
		}
		subjects.close();
		// System.out.println(list);
	}

	public static void main(String[] args) {
		// start();
		/*
		 * getWantedItems(readRDF(inputFileName), new ArrayList<Map<String,
		 * String>>());
		 */
	}
}
