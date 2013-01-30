package com.badlogic.qbob.prototypestest;
//Class qui gere les animation sous deux forme sois par raport au temps 
//sois par raport a l'id de l'image
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {
	
    private int        frameRows;         
    private int        frameCols;         
    private Texture                         textureAnimation;              
    private TextureRegion[]                 framesAnimation;                            
    private TextureRegion                   currentFrame;  
    private double startTime;
    private  float tempsParFrame;
	public Animation(String _url,int _tailleTileX,int _tailleTileY,float _tempsParFrame){
		
	
		startTime = System.currentTimeMillis();
		tempsParFrame = _tempsParFrame;
	    textureAnimation = new Texture(Gdx.files.internal(_url));   
	    frameRows = textureAnimation.getWidth() / _tailleTileX;
	    frameCols = textureAnimation.getHeight() / _tailleTileY;
	    
	    
	      
	    TextureRegion[][] tmp = TextureRegion.split(textureAnimation,32 ,32);                                
	    framesAnimation = new TextureRegion[frameCols * frameRows];
	    int index = 0;
	    for (int i = 0; i < frameRows; i++) {
	            for (int j = 0; j < frameCols; j++) {
	            	framesAnimation[index++] = tmp[i][j];
	            }
	    }



	  }
   
	public TextureRegion getImageByTime()
    	{
    	int iteratorAnimation;
    		double tempsEcoule =  System.currentTimeMillis()- startTime; 
    		if ((int)Math.floor(tempsEcoule / tempsParFrame) >  framesAnimation.length-1)
    		{
    			 startTime = System.currentTimeMillis();
    			 iteratorAnimation = 0;
    		}else
    		{
    			 iteratorAnimation = (int)Math.floor(tempsEcoule / tempsParFrame);
    		}
    		
    		return framesAnimation[iteratorAnimation];
    	}  	
	
    public TextureRegion getImageById(int _id)
    {
    	
		return framesAnimation[_id];
	}  	
    }
  

