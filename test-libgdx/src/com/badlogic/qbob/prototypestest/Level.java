package com.badlogic.qbob.prototypestest;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.SimpleTileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;


public class Level
{

	

	private TileMapRenderer tileMapRenderer;
	private TiledMap map;
	private TileAtlas atlas;
	private int[][] tilesModified;
	private SpriteBatch spriteBatch;
	private Animation[][] tilesAnimation;
	private int startX,startY;
	private player perso;
	private GestionaireItems items;
	private GestionaireGravity gravitys;
	private int status;
private float TileSize;
	public Level(String _urlMap,String _urlAssets,player _perso,GestionaireItems _items,GestionaireGravity _gravitys){
		items = _items;
		perso = _perso;
		gravitys = _gravitys;
		status=0;
		//perso.Get_BodyDef().position.x = startX;
		//perso.Get_BodyDef().position.y = startY;
		spriteBatch = new SpriteBatch();

		map = TiledLoader.createMap(Gdx.files.internal(_urlMap));
		
		TileSize = 	Utility.worldToScreen(map.tileWidth);
		atlas = new SimpleTileAtlas(map, Gdx.files.internal(""));
		tileMapRenderer = new TileMapRenderer(map, atlas, 32, 32);

		tilesModified = new int[map.height][map.width];
		tilesAnimation = new Animation[map.height][map.width];

		for (int y = 0; y < map.height; y++) {
			for (int x = 0; x < map.width; x++) { 
				tilesAnimation[y][x] = null;
				tilesModified[y][x] =20;
			}

		}
		makebody();
	}




	private void makebody(){



		BodyDef bodyDef = new BodyDef();

		bodyDef.type = BodyDef.BodyType.StaticBody;





		for (int y = 0; y < map.height; y++) {
			for (int x = 0; x < map.width; x++) { 
				int tileid = map.layers.get(0).tiles[y][x];
				int mecatileid = map.layers.get(2).tiles[y][x];
				String testBlock = map.getTileProperty(tileid, "block");

				if(testBlock != null)
				{
					if(testBlock.equals("1"))
					{

						bodyDef.position.set( (x * TileSize),(( TileSize * map.height) - y * TileSize));
						PolygonShape groundBox  = new PolygonShape();
						groundBox.setAsBox(TileSize/2, TileSize/2);

						Body body	= Utility.world.createBody(bodyDef);
						body.setUserData(Gravity.TILE);
						FixtureDef jumperFixtureDef = new FixtureDef();
						jumperFixtureDef.shape = groundBox;
						jumperFixtureDef.density = 0f;
						jumperFixtureDef.friction = 0.5f;
						jumperFixtureDef.restitution = 0.2f;
						
						body.createFixture(groundBox, 0.0f);
						body.createFixture(jumperFixtureDef);
						body.setAwake(true);
						body.setSleepingAllowed(true);


					}

				}
				String testStart = map.getTileProperty(tileid, "start");
				if(testStart != null)
				{
					if(testStart.equals("1"))
					{

						items.add(new Item(x,y,Item.START_OF_LEVEL,this));
						perso.Get_Body().setType(BodyType.StaticBody);
						perso.Get_Body().setTransform(x*TileSize+TileSize/2, map.height*TileSize -y *TileSize -TileSize/2, 0);
						perso.Get_Body().setType(BodyType.DynamicBody);


					}
				}


				String testEnd = map.getTileProperty(tileid, "end");
				if(testEnd != null)
				{
					if(testEnd.equals("1"))
					{
						items.add(new Item(x,y,Item.END_OF_LEVEL,this));
					}
				}
				String testRuban = map.getTileProperty(tileid, "ruban");
				if(testRuban != null)
				{
					if(testRuban.equals("1"))
					{
						items.add(new Item(x,y,Item.RUBAN,this));
					}
				}
				//
				String testDie = map.getTileProperty(mecatileid, "die");

				if(testDie != null)
				{
					if(testDie.equals("1"))
					{System.out.println("AJOUT D'un PIEGE");
					items.add(new Item(x,y,Item.KILL_PLAYER,this));

					}

				}
				//
			}
		}
	}


	public int getStatus()
	{
		return status;
	}

	public void setStatus(int _value)
	{
		status = _value;
	}
	public void unLoadAnimation(int _x, int _y)
	{
		tilesAnimation[_y][_x] =  null;
	}
	public void chargeAnimation(int _x,int _y,Animation _anim)
	{

		tilesAnimation[_y][_x] = _anim;
	}
	public void changeTile(int _x,int _y,int _tile)
	{


		tilesModified[_y][_x] = _tile;


	}
	public void draw(OrthographicCamera cam)
	{

		
		spriteBatch.begin();
		for (int y = 0; y < map.height; y++) {
			for (int x = 0; x < map.width; x++) { 

				//((x)- cam.position.x) +WIDTH/2 -32/2,(y- cam.position.y) +HEIGHT/2)-32/2)

				spriteBatch.draw( atlas.getRegion(tilesModified[y][x]),(x *Utility.HEIGHT )- cam.position.x +Utility.WIDTH/2 ,((y*Utility.WIDTH- cam.position.y) +Utility.HEIGHT/2));
				if(tilesAnimation[y][x] != null)
				{
					spriteBatch.draw(tilesAnimation[y][x].getImageByTime(),(x *Utility.HEIGHT )- cam.position.x +Utility.WIDTH/2 ,((y*Utility.WIDTH- cam.position.y)+Utility.HEIGHT/2));

				}

			}
		}
		spriteBatch.end();


	}


	public TileMapRenderer Get_tileMapRenderer()
	{
		return tileMapRenderer;	
	}
	public TiledMap Get_TiledMap()
	{
		return map;	
	}
	public int getStartX()
	{
		return startX;
	}
	public int getStartY()
	{
		return startY;
	}


	public void unLoadTiles() {
		for (int y = 0; y < map.height; y++) {
			for (int x = 0; x < map.width; x++) { 

				tilesModified[y][x] = 20;	

			}
		}

	}
	public void onDestroy() {
		unLoadTiles();


		for (Iterator<Body> iter = Utility.world.getBodies(); iter.hasNext();) {
			Body body = iter.next();
			if(body!=null) {
				if(body != perso.Get_Body())
				{
					Utility.world.destroyBody(body);
					body.setUserData(null);
					body = null;
				}
			}
		}

	}




}