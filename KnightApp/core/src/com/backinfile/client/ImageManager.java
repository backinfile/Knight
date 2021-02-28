package com.backinfile.client;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ImageManager {
	public static GridTexture CHAP1_HEROS;

	public static void init() {
		CHAP1_HEROS = new GridTexture("chap1/hero.png", 7, 2);
	}
}
