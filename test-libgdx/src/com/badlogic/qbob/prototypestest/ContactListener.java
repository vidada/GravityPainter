package com.badlogic.qbob.prototypestest;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

final class contactListener implements ContactListener {


	private player personnage;
	private GestionaireGravity gravitys;
	private Weightlessness gravitee;
	private int nbcontactencour;



	public contactListener(player _personnage, GestionaireGravity _gravitys,Weightlessness _gravitee) {
		personnage = _personnage;
		gravitys = _gravitys;
		gravitee = _gravitee;

	}

	public void preSolve(Contact contact, Manifold oldManifold) 
	{

	}


	public void postSolve(Contact contact, ContactImpulse impulse)
	{

	}


	public void endContact(Contact contact) 
	{

	}


	public void beginContact(Contact contact) 
	{


	}
	public void Bettween2ContactCollision() 
	{
		boolean CanJump = false;
		boolean isInContactWithGravityGlobal = false;
		Iterator<Contact> iContact = Utility.world.getContactList().iterator();
		while(iContact.hasNext()){

			Contact contact = iContact.next();
			Body a = contact.getFixtureA().getBody();
			Body b =  contact.getFixtureB().getBody();
			int aData =  (Integer) a.getUserData();
			int bData =  (Integer) b.getUserData();
			



			

			for (int iSensor = 10001; iSensor <= 10004; iSensor++) {
				{

					if((aData == iSensor && bData == Gravity.TILE) || (bData ==  iSensor && aData == Gravity.TILE))
					{
						// Si un des capteur est en contact avec une tile on peux sauter
						CanJump = true;
						
						boolean isInContactWithGravity = false;
						if(aData == iSensor)
						{
							Gravity grav = gravitys.bodyTile(b);
							if(grav != null)
							{
								isInContactWithGravity = grav.isInContact(b.getPosition());
							}
						}
						else
						{
							Gravity grav = gravitys.bodyTile(a);
							grav.isInContact(a.getPosition());
							if(grav != null)
							{
								isInContactWithGravity = grav.isInContact(a.getPosition());
							}

						}	

						if(isInContactWithGravity)  
						{
							isInContactWithGravityGlobal = true;
							if(iSensor == player.SENSOR_LEFT)
							{
								personnage.setAngleGravitee(-90);
							}else if(iSensor == player.SENSOR_RIGHT)
							{
								personnage.setAngleGravitee(90);
							}else if(iSensor == player.SENSOR_TOP)
							{
								personnage.setAngleGravitee(180);
							}else
							{
								personnage.setAngleGravitee(0);
							}
						}
						
							if(iSensor == player.SENSOR_LEFT)
							{
								personnage.setInContactWithSensor(player.SENSOR_LEFT,true);
							}else if(iSensor == player.SENSOR_RIGHT)
							{
								personnage.setInContactWithSensor(player.SENSOR_RIGHT,true);
							}else if(iSensor == player.SENSOR_TOP)
							{
								personnage.setInContactWithSensor(player.SENSOR_TOP,true);
							}else
							{
								personnage.setInContactWithSensor(player.SENSOR_BOTTOM,true);
							}
						
						
					}			
				}
			}
		}
		
		
		if(!isInContactWithGravityGlobal)
		{
		//si aucun capteur ne touche une tile on revien a l'angle par defaut
			personnage.setAngleGravitee(0);

		}
		if(!CanJump)
		{
			personnage.setInContactWithSensor(player.SENSOR_BOTTOM,false);
			personnage.setInContactWithSensor(player.SENSOR_LEFT,false);
			personnage.setInContactWithSensor(player.SENSOR_RIGHT,false);
			personnage.setInContactWithSensor(player.SENSOR_TOP,false);
		}
		personnage.setCanJump(CanJump);
	}
}