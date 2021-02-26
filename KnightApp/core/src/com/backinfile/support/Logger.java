package com.backinfile.support;

import com.badlogic.gdx.Gdx;

public class Logger {
	private String tag;

	public Logger(String tag) {
		this.tag = tag.toUpperCase();
	}

	public void info(String message) {
		Gdx.app.log(tag, message);
	}

	public void debug(String message) {
		Gdx.app.debug(tag, message);
	}

	public void error(String message) {
		Gdx.app.error(tag, message);
	}

	public void error(String message, Throwable throwable) {
		Gdx.app.error(tag, message, throwable);
	}
}
