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

public class MyNodeHandler extends DefaultHandler {
	// 开始解析的元素
	String nodeName = null;
	//
	String edgeID = "";
	String wayID = "";
	String nodeID;
	boolean flag = false;

	public MyNodeHandler(String nodeName, String nodeID, String wayID) {
		// TODO Auto-generated constructor stub
		this.nodeName = nodeName;
		this.nodeID = nodeID;
		this.wayID = wayID;
	}
	public String getEdgeID() {
		return edgeID;
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
		return;
		// TODO Auto-generated method stub
		// System.out.println("--startDocument()--");
		// 初始化Map
		// list = new ArrayList<Map<String, String>>();
	}

	// 开始解析每个元素时都会调用该方法
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		// 判断正在解析的元素是不是开始解析的元素
		// System.out.println("--startElement()--" + qName);
		if (flag) {
			return;
		}
		if (qName.equals("edge")) {
			if (attributes != null) {
				if (attributes.getValue("id").contains(wayID)) {
					if (attributes.getValue("from").equals(nodeID)
							|| attributes.getValue("to").equals(nodeID)) {
						edgeID = attributes.getValue("id");
						flag = true;
					}
				}
			}

		}
	}

	// 解析到每个元素的内容时会调用此方法
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		return;
	}

	// 每个元素结束的时候都会调用该方法
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		return;
	}

	// 结束解析文档，即解析根元素结束标签时调用该方法
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		//System.out.println("解析完成");
	}
}