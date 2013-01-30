package com.badlogic.qbob.prototypestest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Mouse {
	private SpriteBatch spriteBatch;
	private Texture texturePen;
	private Animation animPen;
	private int rotation;
	public Mouse(){
		animPen = new Animation("pinceau.png",32,32,100);
		spriteBatch = new SpriteBatch();
		Texture texturePen = new Texture(Gdx.files.internal("pinceau.png"));
		rotation = 0;
	}
public void onMovePress(int _x, int _y)
{
	
	
	spriteBatch.begin();
	spriteBatch.draw(animPen.getImageById(0), _x-16, _y+32, 32, 32,32,32,1,1,rotation);
	spriteBatch.end();
}

}
