package com.estrelsteel.ld37.world;

import java.util.ArrayList;

import com.estrelsteel.engine2.chunk.ImageChunk;
import com.estrelsteel.engine2.file.GameFile;
import com.estrelsteel.engine2.file.Saveable;
import com.estrelsteel.engine2.image.Renderable;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.tile.Tile;
import com.estrelsteel.engine2.tile.TileType;
import com.estrelsteel.engine2.world.FrozenWorld;
import com.estrelsteel.ld37.objects.Entrance;
import com.estrelsteel.ld37.objects.Hole;
import com.estrelsteel.ld37.objects.furniture.Furniture;
import com.estrelsteel.ld37.objects.furniture.FurnitureType;

public class Level implements Saveable {

	private FrozenWorld w;
	private double length;
	private int zombies;
	
	public Level(FrozenWorld w) {
		this.w = w;
	}
	
	public double getLevelLength() {
		return length;
	}
	
	public int getZombies() {
		return zombies;
	}
	
	public void setLevelLength(double length) {
		this.length = length;
	}
	
	public void setZombies(int zombies) {
		this.zombies = zombies;
	}
	
	@Override
	public String getIdentifier() {
		return "LVL";
	}

	@Override
	public FrozenWorld load(GameFile file, int line) {
		ArrayList<Renderable> tiles = new ArrayList<Renderable>();
		double sx = 0;
		double sy = 0;
		double width = 64;
		double height = 64;
		double maxWidth = 0;
		double maxHeight = 0;
		String[] args = file.getLines().get(line).split(" ");
		if(args[0].equalsIgnoreCase(getIdentifier())) {
			sx = Double.parseDouble(args[1]);
			sy = Double.parseDouble(args[2]);
			width = Double.parseDouble(args[3]);
			height = Double.parseDouble(args[4]);
			length = Double.parseDouble(args[5]);
			zombies = Integer.parseInt(args[6]);
			int i = 0;
			for(i = line + 1; i < file.getLines().size(); i++) {
				args = file.getLines().get(i).split(" ");
				if(args[0].equalsIgnoreCase("###")) {
					break;
				}
				for(int j = 0; j < args.length; j++) {
					if(Integer.parseInt(args[j]) >= 0) {
						tiles.add(new Tile(TileType.types.get(Integer.parseInt(args[j])), QuickRectangle.location(sx + (width * j), sy + (height * (i - line -1)), width, height)).setHideOffScreen(false));
						if((width * j) > maxWidth) {
							maxWidth = (width * j);
						}
						if((height * (i - line -1)) > maxHeight) {
							maxHeight = (height * (i - line -1));
						}
					}
					
				}
			}
			ImageChunk ic = new ImageChunk(QuickRectangle.location(sx, sy, (maxWidth + width), maxHeight + height));
			ic.setObjects(tiles);
			ic.generateImage();
			ArrayList<Renderable> other = new ArrayList<Renderable>();
			w.getObjects().add(ic);
			for(i++; i < file.getLines().size(); i++) {
				args = file.getLines().get(i).split(" ");
				if(args[0].equalsIgnoreCase("###")) {
					break;
				}
				if(args[0].equalsIgnoreCase("ENT")) {
					if(w instanceof RoomWorld) {
						((RoomWorld) w).getEntrances().add(new Entrance(Double.parseDouble(args[1]), QuickRectangle.location(Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5])),
								QuickRectangle.location(Double.parseDouble(args[6]), Double.parseDouble(args[7]), Double.parseDouble(args[8]), Double.parseDouble(args[9])), Boolean.parseBoolean(args[10])));
					}
					else {
						other.add(new Entrance(Double.parseDouble(args[1]), QuickRectangle.location(Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5])),
								QuickRectangle.location(Double.parseDouble(args[6]), Double.parseDouble(args[7]), Double.parseDouble(args[8]), Double.parseDouble(args[9])), Boolean.parseBoolean(args[10])));
					}
				}
				else if(args[0].equalsIgnoreCase("FUR")) {
					w.getObjects().add(new Furniture(QuickRectangle.location(Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4])), FurnitureType.values()[Integer.parseInt(args[5])]));
				}
				else if(args[0].equalsIgnoreCase("HOL")) {
					w.getObjects().add(new Hole(QuickRectangle.location(Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4])),
							Integer.parseInt(args[5])));
				}
			}
			return w;
		}
		return w;
	}

	@Override
	public GameFile save(GameFile file) {
		return null;
	}

}
