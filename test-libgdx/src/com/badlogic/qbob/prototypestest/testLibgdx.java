


package com.badlogic.qbob.prototypestest;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class testLibgdx implements ApplicationListener {

	public static final int NOTHING = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP_LEFT = 3;
	public static final int UP_RIGHT = 4;
	public static final int UP = 5;



	private player personnage; 
	private 	GestionaireLevels gestMap;
	private 	Level map;
	private SpriteBatch spriteBatch;

	private Controls controls;
	private GravityCallback gravityCallback;
	private GestionaireGravity gravitys;
	private contactListener ContactListeners ;
	private Mouse mouse;
	public boolean needsGL20() {
		return false;
	}

	private OrthographicCamera cam;
	
	private Box2DDebugRenderer debugRenderer;
	private GestionaireItems items;
	private Weightlessness gravitee;
	private Menu menu;
	public void create () {

		menu = new Menu();
		menu.chargeMenu(1);

		gravitee = new Weightlessness();
		personnage = new player();

		items = new GestionaireItems();
		gravitys = new GestionaireGravity(personnage);
		gestMap = new GestionaireLevels(items,personnage,gravitys,menu);
		gestMap.next();
		map = gestMap.getLevel();
		gravitys.addMap(map);
		controls = new Controls();
		spriteBatch = new SpriteBatch();
		debugRenderer = new Box2DDebugRenderer();
		cam = new OrthographicCamera(Utility.WIDTH * Utility.PIXELS_PER_METER,Utility.HEIGHT * Utility.PIXELS_PER_METER);            
		//utile pour bonne coordon��es   cam.setToOrtho(true,WIDTH / 2, HEIGHT / 2);
		//todo refaire les cam

		mouse = new Mouse();
		gravityCallback = new GravityCallback(gravitys,map);

	

	 ContactListeners = new contactListener(personnage,gravitys,gravitee);

	}



	public void render () {
		
		if( menu.getId() == 0)
		{
			gravitys.render();
			if(personnage.Get_Body().getPosition().y < -100)
			{
				gestMap.restart();
			}
			if(Gdx.input.isTouched())
			{
				Utility.world.setContactListener(ContactListeners);
				
			
				for (int x = 0; x < 3; x++)  
				{
					boolean clickedBouton=false;
					controls.testBoutonClicked(Gdx.input.getX(x), Gdx.input.getY(x));

					//test si une des fleches a �t� toucher dans ce tour de boucle
					switch(controls.getCombiTouches())
					{
					case NOTHING: clickedBouton = false; break;
					case LEFT: personnage.moveLeft(); clickedBouton = true; break;
					case RIGHT:  personnage.moveRight(); clickedBouton = true; break;
					case UP_LEFT: personnage.Jump(); personnage.moveLeft();  clickedBouton = true; break;
					case UP_RIGHT: personnage.Jump(); personnage.moveRight();  clickedBouton = true; break;
					case UP: personnage.Jump();  clickedBouton = true; break;

					}

					if( clickedBouton == false)
					{
						Utility.world.QueryAABB(gravityCallback, 
								(Gdx.input.getX()+cam.position.x -(Utility.WIDTH / 2)), 
								(((-Gdx.input.getY()+cam.position.y)+(Utility.HEIGHT / 2))),
								((Gdx.input.getX()+cam.position.x -(Utility.WIDTH / 2))+ 1),
								(((-Gdx.input.getY()+cam.position.y)+(Utility.HEIGHT / 2) +1 )));
					
		
					}

				}
			
			
			
				//	test 1.0.1
			
			//
		
			}
			//Zelse
			//{
			//controls.testBoutonClicked();
			//}	


			//Utility.world.destroyBody(body);
			//body.setUserData(null);
			//body = null;




			ContactListeners.Bettween2ContactCollision();

			int itemLePlusProche = items.FirstItemIninContact(personnage);


			if (itemLePlusProche != -1)
			{

				System.out.println("ITEM TROUVE ; "+itemLePlusProche);
				

				System.out.println( "+"+ items.get(itemLePlusProche).getType());
				if(items.get(itemLePlusProche).getType() == Item.END_OF_LEVEL)
				{
					System.out.println("FIN DU NIVEAU");
					gestMap.next();
					map = gestMap.getLevel(); 
				}
				if(items.get(itemLePlusProche).getType() == Item.KILL_PLAYER)
				{
					System.out.println("MORT");
					gestMap.restart();
					map = gestMap.getLevel(); 

				}
			}






		
			
			boolean isPressedA = Gdx.input.isKeyPressed(Keys.A);
			if(isPressedA)
			{

				personnage.moveLeft();
			}
			boolean isPressedW = Gdx.input.isKeyPressed(Keys.W);
			if(isPressedW)
			{

				personnage.Jump();
			}
			boolean isPressedD = Gdx.input.isKeyPressed(Keys.D);
			if(isPressedD)
			{

				personnage.moveRight();
			}
			boolean isPressedS = Gdx.input.isKeyPressed(Keys.S);
			if(isPressedS)
			{


				System.out.println("PRINTTTT !!!"+ personnage.Get_Body().getLinearVelocity().x);
			}




			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);   
		









			map.Get_tileMapRenderer().render(cam);

			debugRenderer.render(Utility.world,cam.combined);

		
			spriteBatch.begin();
			spriteBatch.draw(personnage.draw(),((personnage.Get_Body().getPosition().x)- cam.position.x) +Utility.WIDTH/2 -32/2,((personnage.Get_Body().getPosition().y- cam.position.y) +Utility.HEIGHT/2)-32/2,16,16,32,32,1,1,(int)personnage.getAngleGravitee());
			spriteBatch.end();	


			map.draw(cam);  
			controls.draw();
			mouse.onMovePress(Gdx.input.getX(),-Gdx.input.getY() + Utility.HEIGHT);









			cam.position.set(personnage.Get_Body().getPosition().x,personnage.Get_Body().getPosition().y,0);
			cam.update();	

			
			//bug majeur
//			Utility.world.QueryAABB(callbackCollisions, 
//					personnage.Get_Body().getPosition().x-16 -1, 
//					personnage.Get_Body().getPosition().y-16 -1,
//					personnage.Get_Body().getPosition().x+16+1,
//					personnage.Get_Body().getPosition().y+16 +1 );


			Utility.world.step(1/60f, 8, 3);
			Utility.world.clearForces();

			



			gravitys.draw(cam);
			


		}else
		{
			menu.draw();
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
}

