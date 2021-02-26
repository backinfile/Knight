package com.backinfile.game.net;

import com.backinfile.world.room.Room;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.Socket;

public class GameServer {

	private Thread serverThread;
	private ServerSocket serverSocket;
	// 维护客户端连接
	private ConnectionMaintainer connectionMaintainer;
	private Room room;

	public GameServer() {

	}

	public void start() {
		serverSocket = Gdx.net.newServerSocket(Protocol.TCP, 93589, null);


		connectionMaintainer = new ConnectionMaintainer();
		serverThread = new Thread(this::run);
		serverThread.setDaemon(true);
		serverThread.start();
	}

	public void run() {
		Socket socket = serverSocket.accept(null);
	}
}
