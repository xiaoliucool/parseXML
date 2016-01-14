package com.xiaoliu;

/**
 * 封装解析业务类
 */

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class SaxService {

	public static String getNodeIdFromNodeXML(String uri, float lon, float lat) {
		try {
			// 创建一个解析XML的工厂对象
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			// 创建一个解析XML的对象
			SAXParser parser = parserFactory.newSAXParser();
			// 创建一个解析助手类
			Myhandler myhandler = new Myhandler("node", lon, lat);
			parser.parse(uri, myhandler);
			// System.out.println("检索完成");
			return myhandler.getId();
		} catch (Exception e) {
			System.out.println("找到。。。。。");
			//e.printStackTrace();
		} finally {

		}
		return null;
	}

	/**
	 * 
	 * @param uri
	 * @param lon
	 * @param lat
	 * @return
	 */
	public static String getWayIdFromNodeXML(String uri, String nodeID, String wayID) {
		try {
			// 创建一个解析XML的工厂对象
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			// 创建一个解析XML的对象
			SAXParser parser = parserFactory.newSAXParser();
			// 创建一个解析助手类
			MyNodeHandler handler = new MyNodeHandler("edge", nodeID, wayID);
			parser.parse(uri, handler);
			return handler.getEdgeID();
			// System.out.println("检索完成");
		} catch (Exception e) {
			System.out.println("找到。。。。。");
			//e.printStackTrace();
		} finally {

		}
		return null;
	}

	// public static Set<String> nodeSet = new HashSet<String>();
	/**
	 * 读取xml文件，使用SAXParser解析
	 * 
	 * @param uri
	 * @param NodeName
	 * @return
	 */
	public static List<Map<String, String>> ReadXML(String uri, String NodeName) {
		try {
			// 创建一个解析XML的工厂对象
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			// 创建一个解析XML的对象
			SAXParser parser = parserFactory.newSAXParser();
			// 创建一个解析助手类
			Myhandler myhandler = new Myhandler("Observation", 0f, 0f);
			parser.parse(uri, myhandler);
			return myhandler.getList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return null;

	}

	/**
	 * 将list容器里的内容写入到新的xml文件
	 * 
	 * @param vanet
	 */
	public static void createXML(List<Map<String, String>> vanet) {

		try {
			// 创建工厂
			SAXTransformerFactory factory = (SAXTransformerFactory) SAXTransformerFactory
					.newInstance();
			TransformerHandler handler = factory.newTransformerHandler();
			Transformer info = handler.getTransformer();
			// 是否自动添加额外的空白
			info.setOutputProperty(OutputKeys.INDENT, "yes");
			// 设置字符编码
			info.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			info.setOutputProperty(OutputKeys.VERSION, "1.0");
			info.setOutputProperty(OutputKeys.STANDALONE, "no");
			// 保存创建的saxvanet.xml
			StreamResult result = new StreamResult(new FileOutputStream(
					"f:\\xml\\map2.gpx"));
			handler.setResult(result);
			// 开始xml
			handler.startDocument();
			AttributesImpl impl = new AttributesImpl();
			impl.clear();
			addAttr(impl);
			handler.startElement("", "", "gpx", impl);
			for (int i = 0; i < vanet.size(); i++) {
				/*
				 * <wpt lat="25.55" lon="99.1666666666667"> <ele>123</ele>
				 * <name>矿1</name> <desc>test</desc> <sym>unistrong:104</sym>
				 * </wpt>
				 */
				Map<String, String> map = vanet.get(i);
				Vehicle vh = setVehicle(map);
				// 生成<wpt lat="xx" lon="xx">
				impl.clear(); // 清空属性
				impl.addAttribute("", "", "lat", "", vh.getLatitude());
				impl.addAttribute("", "", "lon", "", vh.getLongitude());
				handler.startElement("", "", "wpt", impl);
				// 生成<name>xx</name>元素
				impl.clear();
				handler.startElement("", "", "name", impl);
				String name = vh.getSender();
				// System.out.println("name:"+name);
				handler.characters(name.toCharArray(), 0, name.length()); // 为name元素添加文本
				handler.endElement("", "", "name");
				impl.clear();
				handler.endElement("", "", "wpt");
			}

			// 生成</class>
			handler.endElement("", "", "gpx");
			handler.endDocument();
			System.out
					.println("complete.................................................");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void addAttr(AttributesImpl impl) {
		impl.addAttribute("", "", "xmlns", "",
				"http://www.topografix.com/GPX/1/1");
		impl.addAttribute("", "", "creator", "", "MapSource 6.5");
		impl.addAttribute("", "", "version", "", "1.1");
		impl.addAttribute("", "", "xmlns:xsi", "",
				"http://www.w3.org/2001/XMLSchema-instance");
		impl.addAttribute("", "", "xsi:schemaLocation", "",
				"http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd");
		System.out.println("添加属性成功");
	}

	public static Vehicle setVehicle(Map<String, String> map) {
		Vehicle vh = new Vehicle();
		vh.setLatitude(map.get("latitude"));
		vh.setLongitude(map.get("longitude"));
		vh.setSender(map.get("sender"));
		vh.setDateTime(map.get("dateTime"));
		vh.setSeconds(map.get("seconds"));
		return vh;
	}

	public static Vanet setVanet(Map<String, String> map) {
		Vanet vanet = new Vanet();
		vanet.setSender(map.get("Sender"));
		vanet.setX(map.get("X"));
		vanet.setY(map.get("Y"));
		return vanet;
	}

	/**
	 * 将map数据映射成Student对象，以便于操作
	 * 
	 * @param map
	 * @return
	 */
	public static Student setStudent(Map<String, String> map) {
		Student stu = new Student();
		stu.setId(map.get("id"));
		stu.setAge(map.get("age"));
		stu.setName(map.get("name"));
		stu.setSex(map.get("sex"));
		return stu;
	}
}
