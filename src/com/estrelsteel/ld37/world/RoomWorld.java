package com.estrelsteel.ld37.world;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.estrelsteel.engine2.grid.Grid;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.engine2.world.World;
import com.estrelsteel.ld37.objects.Entrance;

public class RoomWorld extends World {

	private ArrayList<Entrance> e;
	private RectangleCollideArea a;
	private double length;
	private double start;
	private int zombies;
	private int ae;
	
	public RoomWorld(Grid grid) {
		super(grid);
		this.e = new ArrayList<Entrance>();
	}
	
	public ArrayList<Entrance> getEntrances() {
		return e;
	}
	
	public double getLevelLength() {
		return length;
	}
	
	public double getStartTime() {
		return start;
	}
	
	public int getZombies() {
		return zombies;
	}
	
	public void runZombies() {
		if(e.size() == 0) {
			return;
		}
		for(int i = 0; i < zombies; i++) {
			ae = (int) (Math.random() * e.size());
			if(e.get(ae).getFurniture() == null) {
				e.get(ae).setHealth(e.get(ae).getHealth() - 0.01);
			}
			else {
				e.get(ae).getFurniture().setHealth(e.get(ae).getFurniture().getHealth() - 0.01);
				if(e.get(ae).getFurniture().getHealth() <= 0) {
					e.get(ae).setFurniture(null);
				}
			}
		}
	}
	
	public Entrance collideWithEntrance(Rectangle area) {
		for(Entrance r : e) {
			a = new RectangleCollideArea(r.getRepairZone());
			if(a.checkCollision(area)) {
				return r;
			}
		}
		return null;
	}
	
	public Graphics2D renderWorld(Graphics2D ctx) {
		ctx = super.renderWorld(ctx);
		for(int i = 0; i < e.size(); i++) {
			ctx = e.get(i).render(ctx, this);
		}
		return ctx;
	}
	
	public Graphics2D simpleRenderWorld(Graphics2D ctx) {
		ctx = super.simpleRenderWorld(ctx);
		for(int i = 0; i < e.size(); i++) {
			ctx = e.get(i).simpleRender(ctx, this);
		}
		return ctx;
	}
	
	public void setEntrances(ArrayList<Entrance> e) {
		this.e = e;
	}
	
	public void setLevelLength(double length) {
		this.length = length;
	}
	
	public void setStartTime(double start) {
		this.start = start;
	}
	
	public void setZombies(int zombies) {
		this.zombies = zombies;
	}

}
