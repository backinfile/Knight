package com.backinfile.core;

import com.backinfile.event.RoomEvent;
import com.backinfile.game.net.GameClient;
import com.backinfile.game.net.GameServer;
import com.backinfile.gen.pb.Msg.SCConnect;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class GameStartup extends Game {
	public GameServer gameServer;
	public GameClient gameClient;

	private int cnt = 0;

	@Override
	public void create() {

		GameMessage.collectAllMessage();
		RoomEvent.collectEventListener();
		gameServer = new GameServer();
		gameServer.start();

		gameClient = new GameClient();
		gameClient.setAddr("localhost", Const.GAMESERVER_PORT);
		gameClient.start();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();

		if (gameServer != null && gameServer.isAlive()) {
			gameServer.pulse();
		}
		if (gameClient != null && gameServer.isAlive()) {
			gameClient.pulse();
		}

		if (cnt++ == 300) {
			if (gameClient != null) {
				gameClient.getConnection().sendGameMessage(new GameMessage(SCConnect.newBuilder().build()));
			}
		}

	}

	@Override
	public void dispose() {
		super.dispose();
		if (gameServer != null && gameServer.isAlive()) {
			gameServer.close();
			gameServer = null;
		}

		Log.game.info("game exit");
	}

}
