package com.backinfile.client;

import com.backinfile.core.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ResourceManager {
	public static GridTexture Chap1Heros;
	public static BitmapFont DefaultFont;
	public static TextureRegion DefaultButtonUp;
	public static TextureRegion DefaultButtonDown;

	public static void init() {
		Log.game.info("start loading resource...");

		Chap1Heros = new GridTexture("card/chap1/hero.png", 7, 2);
		DefaultFont = new BitmapFont(Gdx.files.internal("font/sarasa/sarasa.fnt"), false);

		Pixmap up = new Pixmap(10, 10, Format.RGBA8888);
		up.setColor(Color.LIGHT_GRAY);
		up.fill();
		DefaultButtonUp = new TextureRegion(new Texture(up));
		up.dispose();

		Pixmap down = new Pixmap(10, 10, Format.RGBA8888);
		down.setColor(Color.LIGHT_GRAY);
		down.fill();
		DefaultButtonDown = new TextureRegion(new Texture(down));
		down.dispose();

		Log.game.info("resource loading complete");
	}

	public static void dispose() {

	}
}
