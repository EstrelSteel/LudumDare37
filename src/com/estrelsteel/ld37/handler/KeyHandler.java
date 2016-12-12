package com.estrelsteel.ld37.handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.estrelsteel.ld37.LD37;

public class KeyHandler implements KeyListener {

	private LD37 ld37;
	
	public boolean up;
	public boolean down;
	public boolean right;
	public boolean left;
	public boolean repair;
	
	public KeyHandler(LD37 ld37) {
		up = false;
		down = false;
		right = false;
		left = false;
		repair = false;
		
		this.ld37 = ld37;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case 87: // W
			up = true;
			break;
		case 83: // S
			down = true;
			break;
		case 65: // A
			left = true;
			break;
		case 68: // D
			right = true;
			break;
		case 32: // SPACE
			repair = true;
			if(ld37.lvlNum == 0 && ld37.complete) {
				ld37.gs = GameState.LOADING;
				ld37.lvlNum = 10;
				ld37.loadLevel(ld37.lvlNum);
			}
			break;
		case 10: // ENTER
			if(ld37.lvlNum == 0) {
				ld37.gs = GameState.LOADING;
				ld37.lvlNum++;
				ld37.loadLevel(ld37.lvlNum);
				break;
			}
			if(ld37.gs == GameState.LOSE) {
				if(ld37.endless) {
					ld37.endless = false;
					ld37.gs = GameState.LOADING;
					ld37.lvlNum = 12;
					ld37.loadLevel(ld37.lvlNum);
				}
				else {
					ld37.gs = GameState.LOADING;
					ld37.loadLevel(ld37.lvlNum);
				}
			}
			else if(ld37.gs == GameState.WIN) {
				if(ld37.lvlNum >= 12) {
					ld37.lvlNum = 0;
				}
				ld37.gs = GameState.LOADING;
				ld37.lvlNum++;
				ld37.loadLevel(ld37.lvlNum);
			}
			else if(ld37.gs == GameState.MESSAGE) {
				ld37.gs = GameState.RUNNING;
			}
			break;
		case 116: // F5
//			ld37.start();
//			System.out.println("resetting game assets...");
			break;
		case 38:
			up = true;
			break;
		case 40:
			down = true;
			break;
		case 37:
			left = true;
			break;
		case 39:
			right = true;
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case 87: // W
			up = false;
			break;
		case 83: // S
			down = false;
			break;
		case 65: // A
			left = false;
			break;
		case 68: // D
			right = false;
			break;
		case 32: // SPACE
			repair = false;
			break;
		case 38:
			up = false;
			break;
		case 40:
			down = false;
			break;
		case 37:
			left = false;
			break;
		case 39:
			right = false;
			break;
		default:
			break;
		}
	}

}
