package com.estrelsteel.ld37.objects.furniture;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.ld37.objects.Entrance;

public class Furniture extends Actor {
	
	private double hp;
	private double maxHP;
	private Entrance e;
	private FurnitureType type;
	
	public Furniture(Rectangle loc, FurnitureType type) {
		super("FURNITURE", loc);
		
		this.type = type;
		
		getAnimations().add(0, new Animation("CLOCK", 0));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/tiles.png", QuickRectangle.location(0, 16, 16, 32)));
		
		getAnimations().add(1, new Animation("CHAIR_A", 1));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/tiles.png", QuickRectangle.location(16, 16, 16, 16)));
		
		getAnimations().add(2, new Animation("CHAIR_B", 2));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/tiles.png", QuickRectangle.location(32, 16, 16, 16)));
		
		getAnimations().add(3, new Animation("BOOKCASE", 3));
		getAnimations().get(3).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/tiles.png", QuickRectangle.location(48, 16, 16, 32)));
		
		getAnimations().add(4, new Animation("TABLE", 4));
		getAnimations().get(4).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/tiles.png", QuickRectangle.location(64, 16, 16, 16)));
		
		setRunningAnimationNumber(type.ordinal());
		
		setSortable(true);
		
		hp = 100;
		maxHP = 100;
		
		e = null;
	}
	
	public double getHealth() {
		return hp;
	}
	
	public double getMaxHealth() {
		return maxHP;
	}
	
	public Entrance getEntrance() {
		return e;
	}
	
	public FurnitureType getType() {
		return type;
	}
	
	public void setHealth(double hp) {
		this.hp = hp;
	}
	
	public void setMaxHealth(double maxHP) {
		this.maxHP = maxHP;
	}
	
	public void setEntrance(Entrance e) {
		this.e = e;
	}
	
	public void setFurnitureType(FurnitureType type) {
		this.type = type;
	}
}