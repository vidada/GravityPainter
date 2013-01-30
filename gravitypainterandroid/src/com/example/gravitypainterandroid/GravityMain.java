package com.example.gravitypainterandroid;


import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.qbob.prototypestest.testLibgdx;

public class GravityMain extends AndroidApplication {
        public void onCreate (android.os.Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                initialize(new testLibgdx(), false);
        }
}