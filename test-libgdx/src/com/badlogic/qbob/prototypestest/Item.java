package com.badlogic.qbob.prototypestest;


public class Item 
{

	static final int TILE = 0;
	static final int KILL_PLAYER = 1;
	static final int START_OF_LEVEL = 2;
	static final int END_OF_LEVEL = 3;
	static final int RUBAN = 4;
	private Level map;
	private int x,y,type;
	private Animation anim;
	private boolean inContact;
	private int id = 0;

	public Item(int _x,int _y,int _type,Level _map)
	{

		
		map = _map;
		x = _x;
		y = _map.Get_TiledMap().height -_y-1 ;

		type = _type;

		if(type == RUBAN)
		{
			anim =  new Animation("ruban.png", 32, 32, 300);
			map.chargeAnimation(x, y, anim);
			//TODO HitBox
			System.out.println("Ruban X:" +x +"Y:"+y) ;

		}else if(type ==START_OF_LEVEL)
		{
			System.out.println("Start X:" +x +"Y:"+y) ;
		}else if(type ==END_OF_LEVEL)
		{
			anim =  new Animation("animationvidadette.png", 32, 32, 300);
			map.chargeAnimation(x, y, anim);
		}else if(type ==KILL_PLAYER)
		{System.out.println("Piege at X:" +x +"Y:"+y) ;}


		id++;
	}

	public boolean isInContact(player _perso)
	{


		int testXmin = (int) Math.floor(_perso.Get_Body().getPosition().x /player.WIDTH);
		int testXmax = (int) Math.ceil(_perso.Get_Body().getPosition().x*player.WIDTH);
		int testYmin = (int) Math.floor((_perso.Get_Body().getPosition().y /player.HEIGHT));
		int testYmax = (int) Math.ceil((_perso.Get_Body().getPosition().y*player.HEIGHT));

		//System.out.println("MOIWW X: "+testXmin +"Y:"+testXmax + "RUBAN x:"+x+"y:"+y);


		if((testXmin == x || testXmax == x)&&( testYmin== y || testYmax == y))
		{
			inContact = true;
		}else
		{
			inContact = false;
		}
		return inContact;
	}



	public boolean getInContact()
	{
		return inContact;
	}

	public boolean onDestroy() {
		map.unLoadAnimation(x, y);
		return true;
	}
	public int getType() {

		return type;
	}
	public int getX() {

		return x;
	}
	public int getY() {

		return y;
	}
}
