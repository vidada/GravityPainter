package com.badlogic.qbob.prototypestest;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;


class GravityCallback implements QueryCallback {


	private Body bodyClicked;
	private Level map;
	private Map<Integer, Item> gravitys;
	private int idGravity;
	private GestionaireGravity gestGravity;

	public GravityCallback(GestionaireGravity _gestGravity,Level _map)
	{

		gestGravity =_gestGravity;
		map = _map;

		
	}

	public boolean reportFixture(Fixture fixture) 
	{
		bodyClicked = fixture.getBody();

		if (bodyClicked != null && bodyClicked.getUserData() != null)
		{
			System.out.println("FIXCLICKED" +bodyClicked.getUserData());
			
			int bData = (Integer) bodyClicked.getUserData();
			if(bData == Gravity.TILE)
		gestGravity.add((int)(Utility.worldToScreen(bodyClicked.getPosition().x/ 32)),(int)(Utility.worldToScreen(bodyClicked.getPosition().y/32)),(int)Utility.worldToScreen(4.0F),bodyClicked);
		//	gestGravity.add(new Gravity((int)((bodyClicked.getPosition().x)/32),(int)(bodyClicked.getPosition().y/32),4));
		}
		return true;
	}



}
