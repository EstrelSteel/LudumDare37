package com.estrelsteel.ld37.handler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {

	public int x;
	public int y;
	public boolean click;
	
	public MouseHandler() {
		this.x = 0;
		this.y = 0;
		this.click = false;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		click = false;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		click = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		click = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

}
