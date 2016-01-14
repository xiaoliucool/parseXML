package com.xiaoliu;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 作者 E-mail: xiaoliucool@126.com
 * @date 创建时间：2015年12月28日 下午4:51:42
 * @version 1.0
 */
public class ThreadPoolExecutorTest {
	public static LinkedList<String> queue;
	public static ExecutorService threadPool;

	public ThreadPoolExecutorTest(LinkedList<String> queue) {
		this.queue = queue;
		// 产生一个 ExecutorService 对象，这个对象带有一个大小为 poolSize 的线程池，若任务数量大于 poolSize
		// ，任务会被放在一个 queue 里顺序执行。
		threadPool = Executors.newFixedThreadPool(2);
	}

	public  void multiThreadService(final String uri, final float lon, final float lat) {
		threadPool.execute(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName()+"开启");
				try {
					String id = SaxService.getNodeIdFromNodeXML(uri, lon, lat);
					System.out.println(id);
					synchronized (queue) {
						queue.add(id);
					}
					System.out.println(Thread.currentThread().getName()+"执行完成");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
