package com.xiaoliu;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ���� E-mail: xiaoliucool@126.com
 * @date ����ʱ�䣺2015��12��28�� ����4:51:42
 * @version 1.0
 */
public class ThreadPoolExecutorTest {
	public static LinkedList<String> queue;
	public static ExecutorService threadPool;

	public ThreadPoolExecutorTest(LinkedList<String> queue) {
		this.queue = queue;
		// ����һ�� ExecutorService ��������������һ����СΪ poolSize ���̳߳أ��������������� poolSize
		// ������ᱻ����һ�� queue ��˳��ִ�С�
		threadPool = Executors.newFixedThreadPool(2);
	}

	public  void multiThreadService(final String uri, final float lon, final float lat) {
		threadPool.execute(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName()+"����");
				try {
					String id = SaxService.getNodeIdFromNodeXML(uri, lon, lat);
					System.out.println(id);
					synchronized (queue) {
						queue.add(id);
					}
					System.out.println(Thread.currentThread().getName()+"ִ�����");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
