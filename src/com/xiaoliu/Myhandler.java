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

public class Myhandler extends DefaultHandler {
	// �洢���ڽ�����Ԫ�ص�����
	private Map<String, String> map = null;
	// �洢���н�����Ԫ�ص�����
	private List<Map<String, String>> list = null;
	// ���ڽ�����Ԫ�ص�����
	String currentTag = null;
	// ���ڽ�����Ԫ�ص�Ԫ��ֵ
	String currentValue = null;
	// ��ʼ������Ԫ��
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
	// ��ʼ�����ĵ�������ʼ����XML��Ԫ��ʱ���ø÷���
	@Override
	public void startDocument() throws SAXException {
		return ;
		// TODO Auto-generated method stub
		//System.out.println("--startDocument()--");
		// ��ʼ��Map
		// list = new ArrayList<Map<String, String>>();
	}

	// ��ʼ����ÿ��Ԫ��ʱ������ø÷���
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		// �ж����ڽ�����Ԫ���ǲ��ǿ�ʼ������Ԫ��
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
		// �ж����ڽ�����Ԫ���Ƿ�������ֵ,���������ȫ��ȡ�������浽map�����У���:<person id="00001"></person>

		currentTag = qName; // ���ڽ�����Ԫ��
	}

	// ������ÿ��Ԫ�ص�����ʱ����ô˷���
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		return;
		/*
		 * // TODO Auto-generated method stub
		 * System.out.println("--characters()--"); if (currentTag != null && map
		 * != null) { currentValue = new String(ch, start, length); //
		 * ������ݲ�Ϊ�պͿո�Ҳ���ǻ��з��򽫸�Ԫ������ֵ�ʹ���map�� if (currentValue != null &&
		 * !currentValue.trim().equals("") && !currentValue.trim().equals("\n"))
		 * { map.put(currentTag, currentValue); System.out.println("-----" +
		 * currentTag + " " + currentValue); } // ��ǰ��Ԫ���ѽ������������ÿ�������һ��Ԫ�صĽ���
		 * currentTag = null; currentValue = null; }
		 */
	}

	// ÿ��Ԫ�ؽ�����ʱ�򶼻���ø÷���
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		//System.out.println("--endElement()--" + qName);
		/*// �ж��Ƿ�Ϊһ���ڵ������Ԫ�ر�ǩ
		if (qName.equals(nodeName)) {
			list.add(map);
			map = null;
		}*/
	}

	// ���������ĵ�����������Ԫ�ؽ�����ǩʱ���ø÷���
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		//System.out.println("--endDocument()--");
		super.endDocument();
	}
}