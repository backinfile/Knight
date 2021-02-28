package com.backinfile.client.screen;

import com.backinfile.client.stage.TestStage;
import com.backinfile.core.Const;
import com.backinfile.core.GameMessage;
import com.backinfile.game.net.GameClient;
import com.backinfile.game.net.GameServer;
import com.backinfile.gen.pb.Msg.SCConnect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class TestScreen extends ScreenAdapter {
	public GameServer gameServer;
	public GameClient gameClient;

	private int cnt = 0;
	private TestStage testStage;

	@Override
	public void show() {
		gameServer = new GameServer();
		gameServer.start();

		gameClient = new GameClient();
		gameClient.setAddr("localhost", Const.GAMESERVER_PORT);
		gameClient.start();

		testStage = new TestStage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getWidth()));
	}

	@Override
	public void render(float delta) {

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

		testStage.act();
		testStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		testStage.getViewport().update(width, height);
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		if (gameServer != null && gameServer.isAlive()) {
			gameServer.close();
			gameServer = null;
		}

		testStage.dispose();
	}
}
