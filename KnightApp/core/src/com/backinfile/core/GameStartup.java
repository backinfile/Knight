package com.backinfile.core;

import com.backinfile.client.ResourceManager;
import com.backinfile.client.screen.TestScreen;
import com.backinfile.event.RoomEvent;
import com.backinfile.game.net.GameClient;
import com.backinfile.game.net.GameServer;
import com.backinfile.gen.pb.Msg.SCConnect;
import com.backinfile.log.Logger;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class GameStartup extends Game {

	@Override
	public void create() {
		Logger.initLogFile();
		GameMessage.collectAllMessage();
		RoomEvent.collectEventListener();
		ResourceManager.init();

		TestScreen testScreen = new TestScreen();
		testScreen.init();
		setScreen(testScreen);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

	@Override
	public void dispose() {
		Log.game.info("game exit");
		super.dispose();
	}

}
