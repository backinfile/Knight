package com.backinfile.core;

import com.backinfile.event.RoomEvent;
import com.backinfile.gen.pb.Msg.CSConnect;
import com.backinfile.gen.pb.Msg.SCConnect;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameStartup extends Game {
	SpriteBatch batch;
	Texture img;

	@Override
	public void create() {
		GameMessage.collectAllMessage();
		RoomEvent.collectEventListener();

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render() {
		super.render();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
		img.dispose();
	}
}
