package com.backinfile.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.backinfile.support.ReflectionUtils;
import com.backinfile.support.Utils2;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;

public class GameMessage {
	private Message message;
	private static final byte[] contentBytes = new byte[1024];
	private int length;

	private GameMessage() {

	}

	public Message getMessage() {
		return message;
	}

	/**
	 * ！！重复利用了byte[]！！
	 */
	public byte[] getBytes() {
		byte[] byteArray = message.toByteArray();
		Utils2.int2bytes(byteArray.length + 8, contentBytes, 0);
		Utils2.int2bytes(message.getClass().getName().hashCode(), contentBytes, 4);
		System.arraycopy(byteArray, 0, contentBytes, 8, byteArray.length);
		return contentBytes;
	}

	public int getLength() {
		return length;
	}

	public static GameMessage buildGameMessage(byte[] bytes, int offset, int len) {
		if (len < 8 || bytes.length < 8)
			return null;
//		int byteSize = Utils2.bytes2Int(bytes, 0);
		int msgHash = Utils2.bytes2Int(bytes, 4);
		if (map.containsKey(msgHash)) {
			Message.Builder builder = map.get(msgHash);
			builder.clear();
			try {
				builder.mergeFrom(bytes, 8, len - 8);
				GameMessage gameMessage = new GameMessage();
				gameMessage.message = builder.build();
				return gameMessage;
			} catch (InvalidProtocolBufferException e) {
				Log.core.error("decode game message failed class=" + builder.getClass().getName(), e);
			}
		}

		return null;
	}

	@Override
	public String toString() {
		if (message == null) {
			return "null";
		}
		return message.getClass().getSimpleName();
	}

	private static final Map<Integer, Message.Builder> map = new HashMap<>();

	public static void collectAllMessage() {
		if (!map.isEmpty()) {
			return;
		}
		Set<Class<?>> classes = ReflectionUtils.getClassesExtendsClass(Message.class);
		for (Class<?> clazz : classes) {
			try {
				Method method = clazz.getDeclaredMethod("newBuilder");
				Object value = method.invoke(null);
				map.put(clazz.getName().hashCode(), (Message.Builder) value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
