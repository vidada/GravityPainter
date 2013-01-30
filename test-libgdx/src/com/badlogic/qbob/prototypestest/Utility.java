package com.badlogic.qbob.prototypestest;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Utility {
	//Create a JBox2D world.
    public static final World world = new World(new Vector2(0.0f, -100.0f), true);
    static final int WIDTH  = 480;
    static final int HEIGHT = 320;
   public static final float PIXELS_PER_METER = 1.0f;
	 public static final int NOTHING = 0;
	 public static final int LEFT = 1;
	 public static final int RIGHT = 2;
	 public static final int UP_LEFT = 3;
	 public static final int UP_RIGHT = 4;
	 public static final int UP = 5;

	 public static final int PUSE_RIGHT = 6;
	 public static final int PUSE_LEFT= 7;

	 static Vector2 AngleToVector(double _angle)
	 {
	     return new Vector2((float)Math.cos(_angle), (float)Math.sin(_angle));
	 }
	 static double VectorToAngle(Vector2 _vector)
	 {
		 return Math.atan2(-_vector.y, _vector.x) * 180 / Math.PI;
	 }

}
