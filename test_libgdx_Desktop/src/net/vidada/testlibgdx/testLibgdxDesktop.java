package net.vidada.testlibgdx;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.qbob.prototypes.*;
import com.badlogic.qbob.prototypestest.testLibgdx;



public class testLibgdxDesktop {

	public static void main(String[] args) {
		new LwjglApplication(new testLibgdx(), "Gravity Painter", 480, 320, false);
	}

}
