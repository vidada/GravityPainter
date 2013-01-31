package com.badlogic.qbob.prototypestest;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

//animation


public class player {
	
	//Constantes

	
	public static final float HEIGHT = Utility.ScreenToWorld(32.0f);
	public static final float WIDTH = Utility.ScreenToWorld(32.0f);
	public static final float RADIUS = Utility.worldToScreen(16.0f);
	
	public static final int PLAYER = 10000;
	public static final int SENSOR_BOTTOM = 10001;
	public static final int SENSOR_LEFT = 10002;
	public static final int SENSOR_RIGHT = 10003;
	public static final int SENSOR_TOP = 10004;


	private int statusAnim;
	private Texture overallTexture;
	private Sprite playerSprite;
	private Animation animPerso;
	
	private BodyDef bodyDef;
	private BodyDef sensorFoot,sensorLeft,sensorRight,sensorTop;
	
	private Body body,bodyCapteurPlayerBottom,bodyCapteurPlayerLeft,bodyCapteurPlayerRight,bodyCapteurPlayerTop;
	
	private Fixture fixFooterSensor;
	private FixtureDef fixFooterSensorDEF;
	private boolean jump;
	private int angleGravitee;
	private boolean inContactWithSensor[];
	private float vitesse;
	private Animation animPersoWalkLeft,animPersoWalkRight,animPersoJumpIn,animPersoStay,animPersoJumpOut,animPersoPuseRight,animPersoPuseLeft;
	
	
	private float vitesseSaut;
	private Sound soundMarche,soundJump1,soundJump2,soundJump3;
	player()
	{
		vitesseSaut=500.0f;
		inContactWithSensor = new boolean[4];
		sensorFoot =  new BodyDef();
		sensorLeft =  new BodyDef();
		sensorRight =  new BodyDef();
		sensorTop =  new BodyDef();
		bodyDef = new BodyDef();
		angleGravitee = 0;
		
		

		//declaration des animations
		animPersoWalkLeft= new Animation("animationmarcheL.png", 32,  32, 300);
		animPersoWalkRight= new Animation("animationmarcheR.png",  32,  32, 300);
		animPersoJumpIn= new Animation("animationjumpin.png",  32,  32, 1000);
		animPersoStay =  new Animation("animationstay.png",  32,  32, 1000);
		animPersoJumpOut =  new Animation("animationjumpout.png",  32,  32, 1000);
		animPersoPuseRight =  new Animation("animationpousseR.png",  32,  32, 300);
		//sons
		soundJump1 = Gdx.audio.newSound(Gdx.files.internal("jump.ogg"));
		soundJump2 = Gdx.audio.newSound(Gdx.files.internal("jump2.ogg"));
		soundJump3 = Gdx.audio.newSound(Gdx.files.internal("jump3.ogg"));
		soundMarche = Gdx.audio.newSound(Gdx.files.internal("marche.ogg"));
	
		vitesse = 100;
		//animPersoPuseLeft =  new Animation("animationpousseL.png", 32, 32, 1000);

		PolygonShape groundFootBox  = new PolygonShape();
		groundFootBox.setAsBox( Utility.worldToScreen(5),Utility.worldToScreen(5),new Vector2(0, (int) Utility.worldToScreen(-15)),0);
	
		PolygonShape groundLeftBox  = new PolygonShape();
		groundLeftBox.setAsBox( Utility.worldToScreen(5),Utility.worldToScreen(5),new Vector2(Utility.worldToScreen(-15),0),0);
	
		PolygonShape groundRightBox  = new PolygonShape();
		groundRightBox.setAsBox( Utility.worldToScreen(5),Utility.worldToScreen(5),new Vector2(Utility.worldToScreen(15),0),0);
	
		PolygonShape groundTopBox  = new PolygonShape();
		groundTopBox.setAsBox( Utility.worldToScreen(5),Utility.worldToScreen(5),new Vector2(0,Utility.worldToScreen(15)),0);
	
		CircleShape groundBox  = new CircleShape();
		groundBox.setRadius(RADIUS);

		
	

	

		bodyDef.fixedRotation= true;
		bodyDef.type = BodyDef.BodyType.DynamicBody;
	
		sensorFoot.fixedRotation = true;
		sensorFoot.type = BodyDef.BodyType.DynamicBody;
		
	
		sensorLeft.fixedRotation = true;
		sensorLeft.type = BodyDef.BodyType.DynamicBody;
		
		
		sensorRight.fixedRotation = true;
		sensorRight.type = BodyDef.BodyType.DynamicBody;
		
		
		sensorTop.fixedRotation = true;
		sensorTop.type = BodyDef.BodyType.DynamicBody;
	 bodyCapteurPlayerBottom = Utility.world.createBody(sensorFoot);
	bodyCapteurPlayerLeft = Utility.world.createBody(sensorLeft);
	 bodyCapteurPlayerRight = Utility.world.createBody(sensorRight);
	 bodyCapteurPlayerTop = Utility.world.createBody(sensorTop);
	
	
	bodyCapteurPlayerBottom.setUserData(player.SENSOR_BOTTOM);
	bodyCapteurPlayerLeft.setUserData(player.SENSOR_LEFT);
	bodyCapteurPlayerRight.setUserData(player.SENSOR_RIGHT);
	bodyCapteurPlayerTop.setUserData(player.SENSOR_TOP);
	
	

	
	
	
		//Utility.world.createJoint(JoinPlayerToBottom);
		//TEST SENSOR FIN

	body = Utility.world.createBody(bodyDef);


	body.setLinearDamping(7.0f);
	body.setUserData(player.PLAYER);
	Fixture fixBottom = bodyCapteurPlayerBottom.createFixture(groundFootBox,0.0f);
	Fixture fixLeft = bodyCapteurPlayerLeft.createFixture(groundLeftBox,0.0f);
	Fixture fixRight = bodyCapteurPlayerRight.createFixture(groundRightBox,0.0f);
	Fixture fixTop = bodyCapteurPlayerTop.createFixture(groundTopBox,0.0f);
	Fixture fix = body.createFixture(groundBox,0.0f);
	
	RevoluteJointDef JoinPlayerToBottom = new RevoluteJointDef();
	RevoluteJointDef JoinPlayerToLeft = new RevoluteJointDef();
	RevoluteJointDef JoinPlayerToRight = new RevoluteJointDef();
	RevoluteJointDef JoinPlayerToTop = new RevoluteJointDef();
	
	JoinPlayerToBottom.initialize(body, bodyCapteurPlayerBottom, body.getWorldCenter());
	JoinPlayerToLeft.initialize(body, bodyCapteurPlayerLeft, body.getWorldCenter());
	JoinPlayerToRight.initialize(body, bodyCapteurPlayerRight, body.getWorldCenter());
	JoinPlayerToTop.initialize(body, bodyCapteurPlayerTop, body.getWorldCenter());
	
	
	
	
	
		
		JoinPlayerToBottom.collideConnected=true;
		JoinPlayerToLeft.collideConnected=true;
		JoinPlayerToRight.collideConnected=true;
		JoinPlayerToTop.collideConnected=true;
		
		Utility.world.createJoint(JoinPlayerToBottom);
		Utility.world.createJoint(JoinPlayerToLeft);
		Utility.world.createJoint(JoinPlayerToRight);
		Utility.world.createJoint(JoinPlayerToTop);
		fixBottom.setSensor(true);
		fixLeft.setSensor(true);
		fixRight.setSensor(true);
		fixTop.setSensor(true);

	}
	

	public void setAngleGravitee(int _Angle)
	{
		angleGravitee = _Angle;
	}
	public double getAngleGravitee()
	{
		return angleGravitee;
	}
	BodyDef Get_BodyDef()
	{
		return bodyDef;
	}

	Body Get_Body()
	{
		return body;
	}
	
	Body Get_SensorBody(int _idSensor)
	{
	
		if(_idSensor == SENSOR_BOTTOM)
		{return bodyCapteurPlayerBottom;}
		else if(_idSensor == SENSOR_LEFT)
		{return bodyCapteurPlayerLeft;}
		else if(_idSensor == SENSOR_RIGHT)
		{return bodyCapteurPlayerRight;}
		else if(_idSensor == SENSOR_TOP)
		{return bodyCapteurPlayerTop;}
		return null;
	}
	
	
	
	

	void moveRight()
	{
	
		if(canjump())
		{
		if (angleGravitee == 90)//OK
		{
			body.applyLinearImpulse(new Vector2(00.0f, 50.0f), body.getWorldCenter());
		}
		if (angleGravitee == 180)
		{
			body.applyLinearImpulse(new Vector2(-50.0f, 0.0f), body.getWorldCenter());
		}
		if (angleGravitee == -90)
		{
			body.applyLinearImpulse(new Vector2(0.0f, -50), body.getWorldCenter());
		}
		}
		if (angleGravitee == 0)//SOL OK
		{   
		
		body.applyLinearImpulse(new Vector2(50.0f, 0), body.getWorldCenter());
		//
		}
	
		
	}



	
	
	
	void moveLeft()
	{  
		

		if(canjump())
		{
		if (angleGravitee == 90)//OK
		{
			body.applyLinearImpulse(new Vector2(00.0f, 50.0f), body.getWorldCenter());
		}
		if (angleGravitee == 180)
		{
			body.applyLinearImpulse(new Vector2(50.0f, 0.0f), body.getWorldCenter());
		}
		if (angleGravitee == -90)
		{
			body.applyLinearImpulse(new Vector2(0.0f, 50), body.getWorldCenter());
		}
		}
		if (angleGravitee == 0)//SOL OK
		{   
		
		body.applyLinearImpulse(new Vector2(-50.0f, 0), body.getWorldCenter());
		//
		}

	}









	void Jump()
	{
		if(canjump())
		{
		int random	=1 + (int)(Math.random() * ((3 - 1) + 1));
				 if(random == 1)
				 {
					 soundJump1.play();	 
				 }else if(random == 2)
				 {
					 soundJump2.play();
				 }else if(random == 3)
				 {
					 soundJump3.play();
				 }
				 boolean[] SensorActif = getInContactWithSensor();	
				 
				
			if (angleGravitee == 90)//OK
		{
			body.applyLinearImpulse(new Vector2(-500.0f, 0.0f), body.getWorldCenter());
		}
		if (angleGravitee == 180)
		{
			body.applyLinearImpulse(new Vector2(0.0f, -500.0f), body.getWorldCenter());
		}
		if (angleGravitee == -90)
		{
			body.applyLinearImpulse(new Vector2(500.0f, 0), body.getWorldCenter());
		}
		if (angleGravitee == 0)//SOL OK
		{    			
			if(SensorActif[0] || (SensorActif[0] && SensorActif[1]) ||(SensorActif[0] && SensorActif[2]) || (SensorActif[0] && SensorActif[1] && SensorActif[2]))
			{
				body.applyLinearImpulse(new Vector2(0, vitesseSaut), body.getWorldCenter());
			}else if(SensorActif[3] || (SensorActif[3] && SensorActif[1]) ||(SensorActif[3] && SensorActif[2]) || (SensorActif[3] && SensorActif[1] && SensorActif[2]))
			{
				body.applyLinearImpulse(new Vector2(0, -vitesseSaut), body.getWorldCenter());
			}
			else if(SensorActif[1])
			{
				body.applyLinearImpulse(new Vector2(vitesseSaut, 0.0f), body.getWorldCenter());
			}	else if(SensorActif[2])
			{
				body.applyLinearImpulse(new Vector2(-vitesseSaut, 0.0f), body.getWorldCenter());
			}
				  	    
		}
		}
		
		
		
	}

	


	public boolean canjump()
	{
			return jump;
	}
	
	
	
	public boolean[] getInContactWithSensor()
	{
		return inContactWithSensor;
	}
	
	public void setInContactWithSensor(int _idSensor,boolean _status)
	{
			if(_idSensor == SENSOR_BOTTOM)
			{inContactWithSensor[0] = _status;}
			else if(_idSensor == SENSOR_LEFT)
			{inContactWithSensor[1] = _status;}
			else if(_idSensor == SENSOR_RIGHT)
			{inContactWithSensor[2] = _status;}
			else if(_idSensor == SENSOR_TOP)
			{inContactWithSensor[3] = _status;}
	}
	
	
	
	public TextureRegion draw() {
		TextureRegion imgReturn = null;

		
		imgReturn = animPersoStay.getImageByTime();
		
//		
if (angleGravitee == 90)//OK
		{
			if( body.getLinearVelocity().y < 0 && jump) // marche a gauche
			{imgReturn =  animPersoWalkLeft.getImageByTime();
			}else if( body.getLinearVelocity().y > 0 && jump)  // marche a Droite
			{
				imgReturn =  animPersoWalkRight.getImageByTime();

			}else if( body.getLinearVelocity().x < 0 )// marche en bas
			{
				imgReturn =  animPersoJumpOut.getImageByTime();
			}else if( body.getLinearVelocity().x > 0 ) // marche en haut
			{
				imgReturn =  animPersoJumpIn.getImageByTime();
			}else // stay
			{
				imgReturn = animPersoStay.getImageByTime();
			}
		}
		if (angleGravitee == 180)
		{
			if( body.getLinearVelocity().x > 0 && jump) // marche a gauche
			{imgReturn =  animPersoWalkLeft.getImageByTime();
			}else if( body.getLinearVelocity().x < 0 && jump)  // marche a Droite
			{
				imgReturn =  animPersoWalkRight.getImageByTime();

			}else if( body.getLinearVelocity().y > 0 )// marche en bas
			{
				imgReturn =  animPersoJumpOut.getImageByTime();
			}else if( body.getLinearVelocity().y < 0 ) // marche en haut
			{
				imgReturn =  animPersoJumpIn.getImageByTime();
			}else // stay
			{
				imgReturn = animPersoStay.getImageByTime();
			}
		}
		if (angleGravitee == -90)
		{
			if( body.getLinearVelocity().y > 0 && jump) // marche a gauche
			{imgReturn =  animPersoWalkLeft.getImageByTime();
			}else if( body.getLinearVelocity().y < 0 && jump)  // marche a Droite
			{
				imgReturn =  animPersoWalkRight.getImageByTime();

			}else if( body.getLinearVelocity().x > 0 )// marche en bas
			{
				imgReturn =  animPersoJumpOut.getImageByTime();
			}else if( body.getLinearVelocity().x < 0 ) // marche en haut
			{
				imgReturn =  animPersoJumpIn.getImageByTime();
			}else // stay
			{
				imgReturn = animPersoStay.getImageByTime();
			}
		}
		if (angleGravitee == 0)
		{
			 if( body.getLinearVelocity().y < -0 )
			{
				imgReturn =  animPersoJumpOut.getImageByTime();
			}else if( body.getLinearVelocity().y > 0 ) 
			{
				imgReturn =  animPersoJumpIn.getImageByTime();
			}
			else if( body.getLinearVelocity().x < 0 && jump) // marche a gauche
			{imgReturn =  animPersoWalkLeft.getImageByTime();
			}else if( body.getLinearVelocity().x > 0 && jump)  // marche a Droite
			{
				imgReturn =  animPersoWalkRight.getImageByTime();

			}
			else // stay
			{
				imgReturn = animPersoStay.getImageByTime();
			}
			}
		
		return imgReturn;
	}
	

	public void setCanJump(boolean _can)
	{
		jump = _can;
	}
}


