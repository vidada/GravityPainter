package com.badlogic.qbob.prototypestest;
//Class qui gere les directions et affiche les fleches de déplacement
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Controls {


	private int pressed;
	private int posX;
	private int posY;
	private Texture textureFleches;
	private SpriteBatch spriteBatch;
	private TextureRegion Frame;
	private Texture textureAnimation;
	private final int largeurFleche = 128;
	private final int hauteurFleche = 128;

	public Controls(){
		int orientation;
		
		pressed = 0;
		posX = 0;
		posY = 0;
		spriteBatch = new SpriteBatch();
		 textureFleches = new Texture(Gdx.files.internal("fleches.png"));
	
	}

	public Texture draw(){
		
		//String url =  "";
		//switch (pressed)
		//{
		////case Utility.NOTHING: url = ""; break;
		//case Utility.LEFT: url = "flecheLeftPut.png"; break;
		//case Utility.RIGHT: url = "flecheRightPut.png"; break;
		//case Utility.UP_LEFT:  break;
		//case Utility.UP_RIGHT: break;
		//case Utility.UP: url = "flechesUpPut.png"; break;
	
		//}
		spriteBatch.begin();
			Frame = new TextureRegion(textureFleches,0,0,345,128);
			spriteBatch.draw(Frame, posX, posY);
			spriteBatch.end();
		return textureAnimation;
	}
	
	public int getPosX() {
		return posX;
	}
	public int getPosY() {
		return posY;
	}
	public void testBoutonClicked(){
		pressed = Utility.NOTHING;
	}
	public void testBoutonClicked(int _x,int _y){
		boolean left = false;
		boolean up = false;
		boolean right = false;
		if(_x < largeurFleche && _y < 320 && _x > 0 && _y > 320-hauteurFleche){left = true;}// leftArrow
		if(_x < largeurFleche*2 && _y < 320 && _x > largeurFleche && _y > 320-hauteurFleche){up = true;}// rightArrow
		if(_x < largeurFleche*3 && _y < 320 && _x > largeurFleche*2 && _y > 320-hauteurFleche){right = true;}// UpArrow
        
		if((left) && !(up)&& !(right))
		{
			pressed = Utility.LEFT; 
		}else
		if(!(left) && (up)&& !(right))
		{
			pressed = Utility.UP;
		}else
		if(!(left) && !(up)&& (right))
		{
			pressed = Utility.RIGHT;
		}else
		if((left) && (up)&& !(right))
		{
			pressed = Utility.UP_LEFT;
		}else
		if(!(left) && (up)&& (right))
		{
			pressed = Utility.UP_RIGHT;
		}else
		{	pressed = Utility.NOTHING;
			
		}
	//System.out.println("x:"+_x+"y:"+ _y + left + up + right + pressed);
	}

	public int getCombiTouches() {
	return pressed;	
	}

	
}
