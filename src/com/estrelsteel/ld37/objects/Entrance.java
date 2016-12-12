package com.estrelsteel.ld37.objects;

import java.awt.Color;
import java.awt.Graphics2D;

import com.estrelsteel.engine2.image.Renderable;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.engine2.world.FrozenWorld;
import com.estrelsteel.engine2.world.World;
import com.estrelsteel.ld37.objects.furniture.Furniture;

public class Entrance implements Renderable {
	private double hp;
	private double maxHP;
	private Rectangle loc;
	private Rectangle hpBar;
	private Rectangle repair;
	private Furniture f;
	private boolean high;
	
	public Entrance(double maxHP, Rectangle loc, Rectangle repair, boolean high) {
		this.maxHP = maxHP;
		this.hp = maxHP;
		this.loc = loc;
		this.hpBar = loc;
		this.repair = repair;
		f = null;
		this.high = high;
	}
	
	public double getHealth() {
		return hp;
	}
	
	public double getMaxHealth() {
		return maxHP;
	}
	
	@Override
	public Rectangle getLocation() {
		return loc;
	}
	
	public Rectangle getHealthBar() {
		return hpBar;
	}
	
	public Rectangle getRepairZone() {
		return repair;
	}
	
	public Furniture getFurniture() {
		return f;
	}
	
	public boolean isHigh() {
		return high;
	}
	
	public void setHealth(double hp) {
		this.hp = hp;
	}
	
	public void setMaxHealth(double maxHP) {
		this.maxHP = maxHP;
	}
	
	@Override
	public void setLocation(Rectangle loc) {
		this.loc = loc;
	}
	
	public void setHealthBar(Rectangle hpBar) {
		this.hpBar = hpBar;
	}
	
	public void setRepairZone(Rectangle repair) {
		this.repair = repair;
	}
	
	public void setFurniture(Furniture f) {
		this.f = f;
	}
	
	public void setHigh(boolean high) {
		this.high = high;
	}
	
	private void updateHealthBar() {
		if(hp > maxHP) {
			hp = maxHP;
		}
		if(hp < 0) {
			hp = 0;
		}
		hpBar = QuickRectangle.location(loc.getX(), loc.getY(), loc.getWidth() * (hp / maxHP), loc.getHeight());
	}

	@Override
	public Graphics2D render(Graphics2D ctx, FrozenWorld world) {
		return simpleRender(ctx, world);
	}

	@Override
	public Graphics2D simpleRender(Graphics2D ctx, FrozenWorld world) {
		double x = 0;
		double y = 0;
		if(world instanceof World) {
			x = world.getGrid().moveToGrid(((World) world).getMainCamera().getLocation()).getX();
			y = world.getGrid().moveToGrid(((World) world).getMainCamera().getLocation()).getY();
		}
		if((loc.getX() - x >= 0 || loc.getX() + loc.getWidth() - x >= 0) && loc.getX() - x <= 640) {
			if((loc.getY() - y >= 0 || loc.getY() + loc.getHeight() - y >= 0) && loc.getY() - y <= 640) {
				updateHealthBar();
				if(f != null) {
					ctx.setColor(Color.YELLOW);
					ctx.fillRect((int) (hpBar.getX() - x), (int) (hpBar.getY() - y + hpBar.getHeight() + 4), (int) (loc.getWidth() * (f.getHealth() / f.getMaxHealth())), 12);
//					f.simpleRender(ctx, world);
				}
				ctx.setColor(Color.BLACK);
				ctx.fillRect((int) (loc.getX() - x - 2), (int) (loc.getY() - y - 2), (int) loc.getWidth() + 4, (int) loc.getHeight() + 4);
//				ctx.drawRect((int) (repair.getX() - x), (int) (repair.getY() - y), (int) repair.getWidth(), (int) repair.getHeight());
				ctx.setColor(Color.RED);
				ctx.fillRect((int) (loc.getX() - x), (int) (loc.getY() - y), (int) loc.getWidth(), (int) loc.getHeight());
				ctx.setColor(Color.GREEN);
				ctx.fillRect((int) (hpBar.getX() - x), (int) (hpBar.getY() - y), (int) hpBar.getWidth(), (int) hpBar.getHeight());
			}
		}
		return ctx;
	}

	@Override
	public boolean isSortable() {
		return false;
	}

	@Override
	public void setSortable(boolean sortable) {
		return;
	}
}
