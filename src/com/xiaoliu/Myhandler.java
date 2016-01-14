/**
 * 用SAX解析XML的Handler
 */
package com.xiaoliu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Myhandler extends DefaultHandler {
	// 存储正在解析的元素的数据
	private Map<String, String> map = null;
	// 存储所有解析的元素的数据
	private List<Map<String, String>> list = null;
	// 正在解析的元素的名字
	String currentTag = null;
	// 正在解析的元素的元素值
	String currentValue = null;
	// 开始解析的元素
	String nodeName = null;
	//
	String id = "";
	float lon = 0.0f;
	float lat = 0.0f;
	float minD = 100.0f;

	public Myhandler(String nodeName, float lon, float lat) {
		// TODO Auto-generated constructor stub
		this.nodeName = nodeName;
		this.lon = lon;
		this.lat = lat;
	}
	public String getId() {
		return id;
	}
	public List<Map<String, String>> getList() {
		return list;
	}

	/*
	 * <Observation ID="18"> <Time>2013-04-18T08:00:00+00:00</Time>
	 * <Area>90268</Area> <Coordinates X="2521.4661" Y="6507.9541" />
	 * <Velocity>60</Velocity> <Direction>169</Direction>
	 * <ManualInfo>0</ManualInfo> <Sender>134569370</Sender> </Observation>
	 */
	// 开始解析文档，即开始解析XML根元素时调用该方法
	@Override
	public void startDocument() throws SAXException {
		return ;
		// TODO Auto-generated method stub
		//System.out.println("--startDocument()--");
		// 初始化Map
		// list = new ArrayList<Map<String, String>>();
	}

	// 开始解析每个元素时都会调用该方法
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		// 判断正在解析的元素是不是开始解析的元素
		currentTag = null;
		//System.out.println("--startElement()--" + qName);
		if (qName.equals(nodeName)) {
			if (attributes != null) {
				String tmpId = attributes.getValue(0);
				float tmpLat = Float.parseFloat(attributes.getValue(1));
				float tmpLon = Float.parseFloat(attributes.getValue(2));
				float tmpD = (tmpLat - lat) * (tmpLat - lat) + (tmpLon - lon)
						* (tmpLon - lon);
				//System.out.println(tmpD);
				if (tmpD < minD) {
					id = tmpId;
					//System.out.println("minD:"+minD);
					minD = tmpD;
				}
			}
		}
		// 判断正在解析的元素是否有属性值,如果有则将其全部取出并保存到map对象中，如:<person id="00001"></person>

		currentTag = qName; // 正在解析的元素
	}

	// 解析到每个元素的内容时会调用此方法
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		return;
		/*
		 * // TODO Auto-generated method stub
		 * System.out.println("--characters()--"); if (currentTag != null && map
		 * != null) { currentValue = new String(ch, start, length); //
		 * 如果内容不为空和空格，也不是换行符则将该元素名和值和存入map中 if (currentValue != null &&
		 * !currentValue.trim().equals("") && !currentValue.trim().equals("\n"))
		 * { map.put(currentTag, currentValue); System.out.println("-----" +
		 * currentTag + " " + currentValue); } // 当前的元素已解析过，将其置空用于下一个元素的解析
		 * currentTag = null; currentValue = null; }
		 */
	}

	// 每个元素结束的时候都会调用该方法
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		//System.out.println("--endElement()--" + qName);
		/*// 判断是否为一个节点结束的元素标签
		if (qName.equals(nodeName)) {
			list.add(map);
			map = null;
		}*/
	}

	// 结束解析文档，即解析根元素结束标签时调用该方法
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		//System.out.println("--endDocument()--");
		super.endDocument();
	}
}