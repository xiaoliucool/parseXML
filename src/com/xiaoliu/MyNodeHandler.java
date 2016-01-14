/**
 * ��SAX����XML��Handler
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
	// ��ʼ������Ԫ��
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
	// ��ʼ�����ĵ�������ʼ����XML��Ԫ��ʱ���ø÷���
	@Override
	public void startDocument() throws SAXException {
		return;
		// TODO Auto-generated method stub
		// System.out.println("--startDocument()--");
		// ��ʼ��Map
		// list = new ArrayList<Map<String, String>>();
	}

	// ��ʼ����ÿ��Ԫ��ʱ������ø÷���
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		// �ж����ڽ�����Ԫ���ǲ��ǿ�ʼ������Ԫ��
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

	// ������ÿ��Ԫ�ص�����ʱ����ô˷���
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		return;
	}

	// ÿ��Ԫ�ؽ�����ʱ�򶼻���ø÷���
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		return;
	}

	// ���������ĵ�����������Ԫ�ؽ�����ǩʱ���ø÷���
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		//System.out.println("�������");
	}
}