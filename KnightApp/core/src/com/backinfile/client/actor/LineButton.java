package com.backinfile.client.actor;

import com.backinfile.client.ResourceManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LineButton extends TextButton {

	private ShapeRenderer shapeRenderer;
	private boolean hover = false;

	private class LineButtonEventListener extends InputListener {
		@Override
		public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
			hover = true;
		}

		@Override
		public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
			hover = false;
		}

	}

	public LineButton(String text) {
		super(text, new TextButtonStyle(null, null, null, ResourceManager.DefaultFont));
		addListener(new LineButtonEventListener());

		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (hover) {
//			batch.draw(ResourceManager.White, getX(), getY(), getWidth(), getHeight() / 16);
			batch.end();

			shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
			shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(Color.WHITE);
			shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
			shapeRenderer.end();

			batch.begin();
		}

		super.draw(batch, parentAlpha);
	}
}
