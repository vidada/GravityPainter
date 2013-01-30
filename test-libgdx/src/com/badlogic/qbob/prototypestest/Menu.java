package com.badlogic.qbob.prototypestest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Menu {
	int id;
	private Texture texture;
	private SpriteBatch batch;
	private  BitmapFont font;
	public Menu(){
		batch = new SpriteBatch();
		id = 1;
		font = new BitmapFont(Gdx.files.internal("fonts/defaultfont.fnt"), Gdx.files.internal("fonts/defaultfont.png"), false);
	}

	public int getId() {
		if(id == 1){
			if(Gdx.input.justTouched()){
				id = 0;
			}
			
		}
		return id;
	}
	
	public void chargeMenu(int id){
		if(id == 0)
		{
		//Game
			System.out.println("Jeu");	
		}
	if(id == 1)
	{
	//Start Screen
		texture = new Texture(Gdx.files.internal("start.png"));
		System.out.println("Ecran de Start");
		
		 //long initTime =  System.currentTimeMillis(); 
		
	
		//id= 0;
	}
	if(id == 2)
	{
	//Game Over	
		id = 0;
		texture = new Texture(Gdx.files.internal("end.png"));
		System.out.println("Ecran de Fin");
	}
	

	}

	public void draw() {
if(id== 1)
{Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);   

		batch.begin();
		batch.draw(texture,75,0);
		System.out.println(System.currentTimeMillis()/1000);
		if((System.currentTimeMillis()/1000) % 2 == 0)
		{
			 font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		}else
		{
			 font.setColor(0.0f, 0.0f, 0.0f, 1.0f);
		}
		 font.draw(batch, "touch to start !", 40, 75);
		batch.end();
	}

	if(id== 2)
	{
			batch.begin();
			batch.draw(texture,75,0);
			System.out.println(System.currentTimeMillis()/1000);
			if((System.currentTimeMillis()/1000) % 2 == 0)
			{
				 font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			}else
			{
				 font.setColor(0.0f, 0.0f, 0.0f, 1.0f);
			}
			 font.draw(batch, "touch to restart !", 40, 75);
			batch.end();
		}
	}
}
