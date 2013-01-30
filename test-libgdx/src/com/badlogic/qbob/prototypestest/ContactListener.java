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
		Iterator<Contact> iContact = Utility.world.getContactList().iterator();
		while(iContact.hasNext()){
			
			Contact contact = iContact.next();
			Body a = contact.getFixtureA().getBody();
			Body b =  contact.getFixtureB().getBody();
			int aData =  (Integer) a.getUserData();
			int bData =  (Integer) b.getUserData();

		


			// SI LES SENSOR SONT EN CONTACT AVEC UNE TILE DE GRAVITE

			for (int iSensor = 10001; iSensor <= 10004; iSensor++) {
				{

					if((aData == iSensor && bData == Gravity.TILE) || (bData ==  iSensor && aData == Gravity.TILE))
					{
						CanJump = true;
						System.out.println("Can Jump = true: "+ iSensor);
						boolean isInContactWithGravity = false;
						if(aData == iSensor)
						{

							Gravity grav = gravitys.thisBodyTile(b);
							if(grav != null)
							{
								isInContactWithGravity = grav.isInContact(b.getPosition());
							}
						}
						else
						{

							Gravity grav = gravitys.thisBodyTile(a);
							grav.isInContact(a.getPosition());
							if(grav != null)
							{
								isInContactWithGravity = grav.isInContact(a.getPosition());
							}

						}	

						if(isInContactWithGravity)  
						{
							
						 if(iSensor == player.SENSOR_RIGHT)
							{
								personnage.setAngleGravitee(90);
							}else if(iSensor == player.SENSOR_LEFT)
							{
								personnage.setAngleGravitee(-90);
							}else if(iSensor == player.SENSOR_TOP)
							{
								personnage.setAngleGravitee(180);
							}else
							{
								personnage.setAngleGravitee(0);
							}
						}
						
						
					}			

				
				}

			}



		}
		if(!CanJump)
		{
			personnage.setAngleGravitee(0);
		}
			personnage.setCanJump(CanJump);
	}
}