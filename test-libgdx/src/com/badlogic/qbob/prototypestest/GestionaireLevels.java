package com.badlogic.qbob.prototypestest;





public class  GestionaireLevels {


	private Level niveau;
	private GestionaireItems items;
	private GestionaireGravity gravitys;
	private Menu menu;

	private int id = 0;
	
	private player perso;
	public  GestionaireLevels(GestionaireItems _items, player _perso,GestionaireGravity _gravitys,Menu _menu)
	{
		perso = _perso;
		items = _items;
		gravitys = _gravitys;
		menu = _menu;
		
	}
	
	public void restart(){
		
		niveau.onDestroy();
		items.delAll();
		gravitys.delAll();
		niveau=null;
		
		niveau = new Level("maps/niveau"+id+".tmx", "", perso,items,gravitys);
		gravitys.addMap(niveau);
		
		
	
	}	
	public int next(){
		id++;
if(id == 1)
{
	niveau = new Level("maps/niveau"+id+".tmx", "", perso,items,gravitys);
	gravitys.addMap(niveau);	

}else if(id <= 5)
		{
			niveau.onDestroy();
			items.delAll();
			gravitys.delAll();
			niveau=null;
			niveau = new Level("maps/niveau"+id+".tmx", "", perso,items,gravitys);
			gravitys.addMap(niveau);	
		}else
		{
			menu.chargeMenu(2);
		}
		return id;
	}
	
		public Level getLevel() {
			return niveau;
		}

	}