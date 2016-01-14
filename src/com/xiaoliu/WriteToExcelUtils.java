package com.xiaoliu;
/**
 * 将way node 映射为edge
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class WriteToExcelUtils {
	public static LinkedList<String> queue = new LinkedList<String>();
	private static final int MAXROWS = 60000;

	public static void writeExcel(String fileName,
			Map<String, List<Map<String, String>>> senderMap,
			HashSet<String> nameSet) {
		List<Map<String, String>> list = null;
		System.out.println("当前文件包含：" + nameSet.size() + "辆车。");
		for (String name : nameSet) {
			list = senderMap.get(name);
			int index = 0;
			int rows = list.size();
			int cols = list.get(0).size();

			Workbook wb = null;
			WritableWorkbook wwb = null;
			boolean flag = false;
			try {
				File is = new File(fileName + "\\" + name + ".xls");
				if (!is.exists()) {
					wwb = Workbook.createWorkbook(is);
					flag = true;
				} else {
					wb = Workbook.getWorkbook(is);
					Sheet sheet = wb.getSheet(0);
					// 获取行
					int length = sheet.getRows();
					if (length >= MAXROWS) {
						index++;
						flag = true;
					}
					wwb = Workbook.createWorkbook(is, wb);
					// 首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象
					// wwb = Workbook.createWorkbook(new File(fileName));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			if (wwb != null) {
				// 创建一个可写入的工作表
				// Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置
				// System.out.println(index);
				WritableSheet ws = null;
				if (flag) {
					ws = wwb.createSheet("vehicle", 0);
				} else
					ws = wwb.getSheet(0);

				// 下面开始添加单元格
				for (int i = 0; i < rows; i++) {
					Vehicle vh = SaxService.setVehicle(list.get(i));
					String sender = vh.getSender();
					String longitude = vh.getLongitude();
					String latitude = vh.getLatitude();
					String dateTime = vh.getDateTime();
					String seconds = vh.getSeconds();
					List<String> items = new ArrayList<String>();
					items.add(sender);
					items.add(longitude);
					items.add(latitude);
					items.add(dateTime);
					items.add(seconds);
					for (int j = 0; j < cols; j++) {
						write2Cell(ws, j, i, items.get(j));
					}
				}
				// index += rows;
				try {
					// 从内存中写入文件中
					wwb.write();
					// 关闭资源，释放内存
					wwb.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (WriteException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void writeNode2Excel(String fileName,
			List<HashMap<String, String>> list) {
		int rows = list.size();
		int cols = list.get(0).size();
		System.out.println(list.size());
		Workbook wb = null;
		WritableWorkbook wwb = null;
		boolean flag = false;
		try {
			File is = new File("f:\\xml\\des3\\" + fileName);
			if (!is.exists()) {
				wwb = Workbook.createWorkbook(is);
				flag = true;
			} else {
				wb = Workbook.getWorkbook(is);
				Sheet sheet = wb.getSheet(0);
				// 获取行
				int length = sheet.getRows();
				if (length >= MAXROWS) {
					flag = true;
				}
				wwb = Workbook.createWorkbook(is, wb);
				// 首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象
				// wwb = Workbook.createWorkbook(new File(fileName));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (wwb != null) {
			// 创建一个可写入的工作表
			// Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置
			// System.out.println(index);
			WritableSheet ws = null;
			if (flag) {
				ws = wwb.createSheet("edge", 0);
			} else
				ws = wwb.getSheet(0);

			// 下面开始添加单元格
			for (int i = 0; i < rows; i++) {
				List<String> items = new ArrayList<String>();
				items.add(list.get(i).get("sender"));
				items.add(list.get(i).get("nodeID"));
				items.add(list.get(i).get("wayID"));
				items.add(list.get(i).get("edgeID"));
				items.add(list.get(i).get("depart"));
				for (int j = 0; j < cols; j++) {
					write2Cell(ws, j, i, items.get(j));
				}
			}
			// index += rows;
			try {
				// 从内存中写入文件中
				wwb.write();
				System.out.println("写入完成");
				// 关闭资源，释放内存
				wwb.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
	}

	public static void write2Cell(WritableSheet ws, int c, int r, String item) {
		// System.out.println(index+r);
		// 这里需要注意的是，在Excel中，第一个参数表示列，第二个表示行
		Label labelC = new Label(c, r, item);
		try {
			// 将生成的单元格添加到工作表中
			ws.addCell(labelC);
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读Excel
	 * 
	 * @param pathname
	 */
	public static LinkedList<HashMap<String, String>> readExcel(String pathname) {
		LinkedList<HashMap<String, String>> list = new LinkedList<HashMap<String, String>>();
		File file = new File(pathname);
		String uri = "f:\\xml\\map\\edge.xml";
		Workbook wb = null;
		try {
			wb = Workbook.getWorkbook(file);
			Sheet sheet = wb.getSheet(0);
			int rows = sheet.getRows();
			// ThreadPoolExecutorTest threadPool = new
			// ThreadPoolExecutorTest(queue);
			for (int i = 0; i < rows; i++) {
				HashMap<String, String> map = new HashMap<String, String>();
				// System.out.println("第"+(i+1)+"条数据正在执行");
				Cell[] cols = sheet.getRow(i);
				String sender = cols[0].getContents();
				String nodeID = cols[1].getContents();
				String wayID = cols[2].getContents();
				String depart = cols[3].getContents();
				

				String edgeID = SaxService.getWayIdFromNodeXML(uri, nodeID, wayID);
				map.put("sender", sender);
				map.put("nodeID", nodeID);
				map.put("wayID", wayID);
				map.put("edgeID", edgeID);
				map.put("depart", depart);
				list.add(map);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void write2Txt2(LinkedList<String> queue) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter("f:\\xml\\des\\node2.txt", true);
			bw = new BufferedWriter(fw);
			for (String st : queue) {
				bw.write(st);
				bw.write("\r\n");
				bw.flush();
			}
			System.out.println("个数：" + queue.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void write2Txt(HashSet<String> set) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter("f:\\xml\\des\\node.txt", true);
			bw = new BufferedWriter(fw);
			for (String st : set) {
				bw.write(st);
				bw.write("\r\n");
				bw.flush();
			}
			System.out.println("个数：" + set.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		File[] files = RDFUtils.getFiles("f:\\xml\\des2");
		for (File file : files) {
			String fileName = file.getAbsolutePath();
			System.out.println(file.getName());
			writeNode2Excel(file.getName(), readExcel(fileName));
		}

	}
}
