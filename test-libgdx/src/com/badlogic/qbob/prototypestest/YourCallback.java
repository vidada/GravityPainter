package com.badlogic.qbob.prototypestest;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;

final class YourCallback implements QueryCallback {
	private player personnage;
	private Level map;
	private int typeDeQuery;
	private final int COLLISIONS = 1;
	private final int PAINTER = 2;
	GestionaireGravity gravitys;
	private Weightlessness gravitee;
	public YourCallback(player _perso, GestionaireGravity _gravitys,Weightlessness _gravitee)
	{
		typeDeQuery = COLLISIONS;
		personnage =  _perso;
		gravitys = _gravitys;
		gravitee = _gravitee;

	}



	public boolean reportFixture(final Fixture fixture)
	{
//		gravitee.setAngle(0);
//		personnage.setAngleGravitee(0);	
//
//		if (fixture.getBody() != null) {
//
//			Body BodyContact = fixture.getBody();
//
//			if(BodyContact.getUserData() == "TILE_DE_GRAVITE")
//			{
//				
//			}
//		}
//
//		return true;
//	}
		
return false;

	}
}
