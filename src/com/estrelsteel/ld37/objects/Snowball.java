package com.estrelsteel.ld37.objects;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Snowball extends Actor {

	private double speed;
	private long spawnTime;
	private boolean destroy;
	
	public Snowball(Rectangle loc) {
		super("SNOWBALL", loc);
		
		speed = 4;
		spawnTime = System.currentTimeMillis();
		destroy = false;
		
		getAnimations().add(0, new Animation("SNOWBALL", 0));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/tiles.png", QuickRectangle.location(7 * 16, 0, 16, 16)));
		
		getCollision().setCollide(true);
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public long getSpawnTime() {
		return spawnTime;
	}
	
	public boolean doDestroy() {
		return destroy;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public void setSpawnTime(long spawnTime) {
		this.spawnTime = spawnTime;
	}
	
	public void setDestroy(boolean destroy) {
		this.destroy = destroy;
	}

}
