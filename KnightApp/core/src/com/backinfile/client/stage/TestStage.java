package com.backinfile.client.stage;

import com.backinfile.client.GridTexture;
import com.backinfile.client.ImageManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TestStage extends Stage {

	private Actor actor;

	public TestStage(Viewport viewport) {
		super(viewport);

		init();
	}

	private void init() {
		actor = new Image(ImageManager.CHAP1_HEROS.getTexture(1, 1));
		addActor(actor);
	}
}
