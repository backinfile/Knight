package com.backinfile.game.net;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.backinfile.core.DispatchThreads;
import com.backinfile.core.Log;
import com.backinfile.support.Utils2;

public class NetManager {
	private final ConcurrentLinkedQueue<Connection> waitForRun = new ConcurrentLinkedQueue<>();
	private final ConcurrentLinkedQueue<Connection> waitForReschedule = new ConcurrentLinkedQueue<>();
	private final ConcurrentHashMap<String, Connection> allConnections = new ConcurrentHashMap<>();
	private DispatchThreads dispatchThreads;
	private static final int DEFAULT_THREAD_NUM = 4;

	public void startUp() {
		startUp(DEFAULT_THREAD_NUM);
	}

	public void startUp(int threadNum) {
		Thread.currentThread().setName("Thread-NetManager");
		dispatchThreads = new DispatchThreads(threadNum, this::dispatchRun);
		dispatchThreads.start();
	}

	public void abort() {
		Log.core.info("node 中断中.....");
		dispatchThreads.abortSync();
		Log.core.info("node 中断结束");
	}

	public void addConnnect(Connection connection) {
		waitForRun.add(connection);
		allConnections.put(connection.name, connection);
	}

	public boolean isConnectExist(String connectionName) {
		return allConnections.contains(connectionName);
	}

	private void dispatchRun() {
		Connection connection = waitForRun.poll();
		if (connection == null) {
			reSchedule(DEFAULT_THREAD_NUM);
			Utils2.sleep(1);
			return;
		}
		pulse(connection);
	}

	private void pulse(Connection connection) {
		if (connection.isAlive()) {
			connection.pulse();
			waitForReschedule.add(connection);
		} else {
			allConnections.remove(connection.name);
		}
	}

	// 将已经被执行过的port重新放入执行队列
	private void reSchedule(int num) {
		for (int i = 0; i < num; i++) {
			Connection connection = waitForReschedule.poll();
			if (connection == null) {
				break;
			}
			waitForRun.add(connection);
		}
	}

}
