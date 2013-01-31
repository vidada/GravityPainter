package com.badlogic.qbob.prototypestest;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;

public class Gravity {
	public static final int TILE = 20001;
	public static final int GRAVIY = 20000;
	
	private int x,y;
	float r;
	private double timer = 0;	
	private int tempsPeinture = 5000;
	BodyDef bodyDef;
	CircleShape groundBox ;
	Body body,bodyTileClicked;
	Fixture fix;
	ShapeRenderer shapeRenderer;
	public  Gravity(int _x,int _y,int _r, Body _bodyTileClicked) { //crée une gravitée x y et le rayon
		x = _x;
		y = _y;
		r= Utility.worldToScreen(_r);
		setTimer();
		groundBox  = new CircleShape();
		bodyTileClicked =_bodyTileClicked;
		groundBox.setRadius(r*Utility.worldToScreen(16));
		
		//groundBox.setPosition(new Vector2(x*32+16,y*32+16));

		bodyDef = new BodyDef();

		body = Utility.world.createBody(bodyDef);

		fix = body.createFixture(groundBox,0.0f);
		fix.setRestitution(0);//rebond
		fix.setDensity(0);
		fix.setSensor(true);

		body.setTransform(Utility.worldToScreen(x*32+16), Utility.worldToScreen(y*32+16),0);
		body.setUserData(Gravity.GRAVIY);

		//	 
		//	 shapeRenderer = new ShapeRenderer();
		//	 shapeRenderer.begin(ShapeType.Circle);
		//	 shapeRenderer.circle(x*16, y*16, r*16);
		//	 shapeRenderer.end();
	}

	public boolean restartTimer() 
	{
		setTimer();
		return true;
	}
	public boolean getTimerElapsed()
	{
		double current = System.currentTimeMillis();

		if(current > timer + tempsPeinture )
		{
			return true;
		}
		return false;
	}
	public void setTimer()
	{
		timer =  System.currentTimeMillis(); 
	}

	public boolean onDestroy() {
		//TODO
		
		return true;
	}
	
	public int getX() {

		return x;
	}
	public int getY() {

		return y;
	}

	public Body getBody() {

		return body;

	}
	public Body getBodyTile() {

		return bodyTileClicked;

	}
	public CircleShape getGroundBox()
	{
		return groundBox;	
	}




	public boolean isInContact(Vector2 _with) 
	{


		boolean inContact = false;
		if(!getTimerElapsed()){



			Vector2 boxDistance = new Vector2(0,0);//distance entre la boxe et le joueur
			Vector2 boxPosition = this.getBody().getPosition();
			Vector2 playerPosition = _with;
			boxDistance.add(playerPosition);

			boxDistance.sub(boxPosition);
			float finalDistance = boxDistance.len();
			if(finalDistance <= this.groundBox.getRadius())
			{
				return inContact = true;
			}
		}			

		return inContact;

	}



}

