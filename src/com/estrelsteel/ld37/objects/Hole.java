package com.estrelsteel.ld37.objects;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Hole extends Actor {

	private int spawnSpeed;
	private long lastSpawn;
	
	public Hole(Rectangle loc, int spawnSpeed) {
		super("HOLE", loc);
		
		this.spawnSpeed = spawnSpeed;
		this.lastSpawn = 0;
		
		getAnimations().add(0, new Animation("HOLE", 0));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/tiles.png", QuickRectangle.location(6 * 16, 0, 16, 16)));
	}
	
	public int getSpawnSpeed() {
		return spawnSpeed;
	}
	
	public long getLastSpawn() {
		return lastSpawn;
	}
	
	public void setSpawnSpeed(int spawnSpeed) {
		this.spawnSpeed = spawnSpeed;
	}
	
	public void setLastSpawn(long lastSpawn) {
		this.lastSpawn = lastSpawn;
	}
	
}
