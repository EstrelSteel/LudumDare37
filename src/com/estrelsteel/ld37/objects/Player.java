package com.estrelsteel.ld37.objects;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Player extends Actor {

	private double walkspeed;
	private double hp;
	private double repair;
	
	public Player(String name, Rectangle loc) {
		super(name, loc);
		
		this.walkspeed = 5.0;
		this.hp = 100.0;
		this.repair = 1.0;
		
		setSortable(true);
		
		getAnimations().add(0, new Animation("PLAYER_DOWN", 0));
		getAnimations().get(0).setMaxWaitTime(15);
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/person_sheet.png", QuickRectangle.location(0, 0, 23, 23)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/person_sheet.png", QuickRectangle.location(23, 0, 23, 23)));
		
		getAnimations().add(1, new Animation("PLAYER_UP", 1));
		getAnimations().get(1).setMaxWaitTime(15);
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/person_sheet.png", QuickRectangle.location(0, 23, 23, 23)));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/person_sheet.png", QuickRectangle.location(23, 23, 23, 23)));
		
		getAnimations().add(2, new Animation("PLAYER_LEFT", 2));
		getAnimations().get(2).setMaxWaitTime(15);
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/person_sheet.png", QuickRectangle.location(0, 46, 23, 23)));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/person_sheet.png", QuickRectangle.location(23, 46, 23, 23)));
		
		getAnimations().add(3, new Animation("PLAYER_RIGHT", 3));
		getAnimations().get(3).setMaxWaitTime(15);
		getAnimations().get(3).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/person_sheet.png", QuickRectangle.location(0, 69, 23, 23)));
		getAnimations().get(3).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/person_sheet.png", QuickRectangle.location(23, 69, 23, 23)));
	}
	
	public double getWalkspeed() {
		return walkspeed;
	}
	
	public double getHP() {
		return hp;
	}
	
	public double getRepairSpeed() {
		return repair;
	}
	
	public void setWalkspeed(double walkspeed) {
		this.walkspeed = walkspeed;
	}
	
	public void setHP(double hp) {
		this.hp = hp;
	}

	public void setRepairSpeed(double repair) {
		this.repair = repair;
	}
}
