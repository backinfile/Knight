package com.backinfile.game.net;

public class GameMessage {
	public byte[] getBytes() {
		return new byte[] { 0, 0, 0, 0 };
	}

	public static GameMessage buildGameMessage(byte[] bytes, int offset, int len) {
		return null;
	}
}
