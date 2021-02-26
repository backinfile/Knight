package com.backinfile.game.net;

import com.backinfile.core.Log;
import com.backinfile.support.Utils2;
import com.backinfile.world.room.Room;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

public class GameServer {

	private Thread serverThread;
	private ServerSocket serverSocket;
	// 维护客户端连接
	private ConnectionMaintainer connectionMaintainer;
	private volatile boolean abort = false;

	private long connectionIdMax = 0;

	public GameServer() {

	}

	public void start() {
		serverSocket = Gdx.net.newServerSocket(Protocol.TCP, 9660, null);
		connectionMaintainer = new ConnectionMaintainer();
		connectionMaintainer.startUp();
		serverThread = new Thread(this::run);
		serverThread.setDaemon(true);
		serverThread.start();
	}

	public void run() {
		SocketHints hints = new SocketHints();
		hints.socketTimeout = 1;
		while (!abort) {
			Log.game.info("loop");
			pulse();
			Socket socket = serverSocket.accept(hints);
			if (socket != null) {
				connectionMaintainer.addConnnect(new Connection(connectionIdMax++, socket));
			}
		}
	}

	private void pulse() {

	}

	public void close() {
		Log.game.info("GameServer关闭中。。。");
		connectionMaintainer.abort();
		abort = true;
		try {
			serverThread.join();
		} catch (InterruptedException e) {
			Log.game.error("error on GameServer close", e);
		}
		serverSocket.dispose();
		Log.game.info("GameServer关闭");
	}
}
