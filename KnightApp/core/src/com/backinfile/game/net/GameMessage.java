package com.backinfile.game.net;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.backinfile.support.ReflectionUtils;
import com.google.protobuf.Message;

public abstract class GameMessage {
	private Message message;

	public Message getMessage() {
		return message;
	}

	public byte[] getBytes() {
		return message.toByteArray();
	}

	public static GameMessage buildGameMessage(byte[] bytes, int offset, int len) {
		return null;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	private static final Map<Integer, Message.Builder> map = new HashMap<>();

	public static void collectAllMessage() {
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
