package com.backinfile.game.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.backinfile.core.GameMessage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.net.Socket;

public class Connection {
	public static final String TAG = Connection.class.getSimpleName();

	private Socket socket;
	private ConcurrentLinkedQueue<GameMessage> sendList = new ConcurrentLinkedQueue<GameMessage>();
	private ConcurrentLinkedQueue<GameMessage> reciveList = new ConcurrentLinkedQueue<GameMessage>();
	private InputStream inputStream;
	private OutputStream outputStream;

	public final String name;

	private final byte[] readBytes = new byte[1024];

	public Connection(Socket socket, String name) {
		this.socket = socket;
		this.inputStream = socket.getInputStream();
		this.outputStream = socket.getOutputStream();
		this.name = name;
	}

	public GameMessage getGameMessage() {
		return reciveList.poll();
	}

	public ConcurrentLinkedQueue<GameMessage> getReciveList() {
		return reciveList;
	}

	public void sendGameMessage(GameMessage gameMessage) {
		sendList.add(gameMessage);
	}

	public void pulse() {
		if (!socket.isConnected()) {
			return;
		}
		try {
			while (!sendList.isEmpty()) {
				GameMessage sendMsg = sendList.poll();
				outputStream.write(sendMsg.getBytes());
				outputStream.flush();
			}
			while (inputStream.available() > 0) {
				int len = inputStream.read(readBytes);
				GameMessage gameMessage = GameMessage.buildGameMessage(readBytes, 0, len);
				if (gameMessage != null) {
					reciveList.add(gameMessage);
				}
			}
		} catch (IOException e) {
			Gdx.app.log(TAG, e.getMessage(), e);
		}
	}

	public boolean isAlive() {
		return socket.isConnected();
	}
}
