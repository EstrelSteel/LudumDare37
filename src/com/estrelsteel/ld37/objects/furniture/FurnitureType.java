package com.estrelsteel.ld37.objects.furniture;

public enum FurnitureType {
	CLOCK(true), CHAIR_A(false), CHAIR_B(false), BOOKCASE(true), TABLE(false);
	
	private boolean tall;
	
	FurnitureType(boolean tall) {
		this.tall = tall;
	}
	
	public boolean isTall() {
		return tall;
	}
}
