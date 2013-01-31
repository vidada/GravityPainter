package com.badlogic.qbob.prototypestest;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Utility {
	//Create a JBox2D world.
    public static final World world = new World(new Vector2(0.0f, -100.0f), true);
    static final float WIDTH  = worldToScreen(480);
    static final float HEIGHT = worldToScreen(320);
   public static final float PIXELS_PER_METER = 32.0f;
	 public static final int NOTHING = 0;
	 public static final int LEFT = 1;
	 public static final int RIGHT = 2;
	 public static final int UP_LEFT = 3;
	 public static final int UP_RIGHT = 4;
	 public static final int UP = 5;

	 public static final int PUSE_RIGHT = 6;
	 public static final int PUSE_LEFT= 7;
	
	 static float worldToScreen(float _nb)
	 {
	     return _nb / PIXELS_PER_METER;
	 }
	 static float ScreenToWorld(float _nb)
	 {
	     return _nb * PIXELS_PER_METER;
	 }
	 static double VectorToAngle(Vector2 _vector)
	 {
		 return Math.atan2(-_vector.y, _vector.x) * 180 / Math.PI;
	 }

}
