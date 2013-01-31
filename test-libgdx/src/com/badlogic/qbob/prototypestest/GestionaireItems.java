package com.badlogic.qbob.prototypestest;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;


public class GestionaireItems {
	
	private Sound soundGetItem;
	private Map<Integer, Item> items;
	private int itemId;
	public	GestionaireItems()
	{
	soundGetItem = Gdx.audio.newSound(Gdx.files.internal("getitem.wav"));
		items = new HashMap<Integer, Item>();
		itemId=0;
	}

	public	boolean add(Item _item)
	{
		items.put(itemId, _item);
		itemId++;
		return true;
	}
	public	boolean del(int _id)
	{
		items.remove(_id);
		return true;
	}
	public boolean delAll() 
	{
		items.clear();
		itemId = 0;
		return true;
	}
	public Item get(int _id) 
	{
		return items.get(_id);
	}
	public int FirstItemIninContact(player _perso) 
	{

		for (int mapKey : items.keySet()) 
		{
		
			if (items.get(mapKey).isInContact(_perso) == true){
		
				if(items.get(mapKey).getType() == Item.RUBAN)
				{
					soundGetItem.play();
					items.get(mapKey).onDestroy();
					this.del(mapKey);
					
						return -1;
				}else
				if(items.get(mapKey).getType() == Item.END_OF_LEVEL)
				{
					return mapKey;
				}if(items.get(mapKey).getType() == Item.KILL_PLAYER)
				{
					
					return mapKey;
				}

			}
		
	}
		
		
		return -1;
	}
	
	}


