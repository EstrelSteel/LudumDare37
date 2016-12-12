package com.estrelsteel.ld37;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.Launcher;
import com.estrelsteel.engine2.actor.Camera;
import com.estrelsteel.engine2.events.listener.RenderListener;
import com.estrelsteel.engine2.events.listener.StartListener;
import com.estrelsteel.engine2.events.listener.StopListener;
import com.estrelsteel.engine2.events.listener.TickListener;
import com.estrelsteel.engine2.file.GameFile;
import com.estrelsteel.engine2.grid.PixelGrid;
import com.estrelsteel.engine2.image.Image;
import com.estrelsteel.engine2.image.Renderable;
import com.estrelsteel.engine2.point.Point2;
import com.estrelsteel.engine2.shape.collide.Collision;
import com.estrelsteel.engine2.shape.collide.PerspectiveRectangleArea;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.engine2.shape.rectangle.AbstractedRectangle;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.engine2.tile.TileType;
import com.estrelsteel.engine2.window.WindowSettings;
import com.estrelsteel.ld37.handler.GameState;
import com.estrelsteel.ld37.handler.KeyHandler;
import com.estrelsteel.ld37.handler.MouseHandler;
import com.estrelsteel.ld37.objects.Entrance;
import com.estrelsteel.ld37.objects.Hole;
import com.estrelsteel.ld37.objects.Player;
import com.estrelsteel.ld37.objects.Snowball;
import com.estrelsteel.ld37.objects.furniture.Furniture;
import com.estrelsteel.ld37.world.Level;
import com.estrelsteel.ld37.world.RoomWorld;

public class LD37 implements StartListener, StopListener, TickListener, RenderListener {
	
	/*
	 * 
	 * SNOWMEN DEFENSE
	 * 
	 * BY: ESTRELSTEEL
	 * LUDUM DARE 37 - COMPO
	 * 
	 * THEME: ONE ROOM
	 * 
	 * (11.12.2016)
	 * 
	 */
	
	private Launcher l;
	private RoomWorld w;
	private Player p;
	public int lvlNum;
	public GameState gs;
	private GameFile msg;
	public Furniture moving;
	
	private KeyHandler key;
	private MouseHandler mouse;
	private Image title;
	
	private Rectangle pLoc;
	private Rectangle npLoc;
	private Entrance e;
	private RectangleCollideArea a;
	private AbstractedRectangle click;
	private Renderable r;
	private Renderable r2;
	private Rectangle sLoc;
//	private ArrayList<Renderable> noChunk;
	public boolean endless;
	private int round;
	private int highscore;
	public boolean complete;
	private GameFile settings;
	
	private double shortErr;
	
	public static void main(String[] args) {
		new LD37();
	}
	
	@SuppressWarnings("static-access")
	public LD37() {
		l = new Launcher();
		Rectangle size;
		if(System.getProperty("os.name").startsWith("Windows")) {
			size = QuickRectangle.location(0, 0, 630, 630);
		}
		else {
			size = QuickRectangle.location(0, 0, 640, 640);
		}
		l.getEngine().setWindowSettings(new WindowSettings(size, "Snowmen Defense - EstrelSteel", "v1.0a", 0));
		
		key = new KeyHandler(this);
		mouse = new MouseHandler();
		lvlNum = 0;
		complete = false;
		
		l.getEngine().START_EVENT.addListener(this);
		l.getEngine().STOP_EVENT.addListener(this);
		l.getEngine().RENDER_EVENT.addListener(this);
		l.getEngine().TICK_EVENT.addListener(this);
		l.getEngine().addKeyListener(key);
		l.getEngine().addMouseListener(mouse);
		l.getEngine().addMouseMotionListener(mouse);
		
		l.getEngine().development = true;
//		l.getEngine().devPath = System.getProperty("user.home") + "/Documents/usb/LD37/LD37";
		l.getEngine().devPath = GameFile.getCurrentPath();
		
		l.boot();
	}

	@Override
	public void start() {
//		w = new RoomWorld(new PixelGrid());
//		w.getCameras().add(new Camera("main", new Point2(0, 0, new PixelGrid())));
//		p = new Player("player", QuickRectangle.location(640, 640, 64, 64));
//		p.setCollision(new Collision(true, new PerspectiveRectangleArea(QuickRectangle.location(0, 0, 64, 64))));
		
		TileType tt = new TileType(-1, "null");
		tt.load(new GameFile(Engine2.devPath + "/res/tiles.txt"), 0);
		
		endless = false;
		round = 0;
		
		title = new Image(Engine2.devPath + "/res/img/title.png");
		
		settings = new GameFile(Engine2.devPath + "/res/settings.txt");
		try {
			settings.setLines(settings.readFile());
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		if(settings.getLines().size() > 0) {
			complete = Boolean.parseBoolean(settings.getLines().get(0));
			highscore = Integer.parseInt(settings.getLines().get(1));
		}
		loadLevel(lvlNum);
	}
	
	public void loadLevel(int lvlNum) {
		w = new RoomWorld(new PixelGrid());w = new RoomWorld(new PixelGrid());
		w.getCameras().add(new Camera("main", new Point2(0, 0, new PixelGrid())));
		p = new Player("player", QuickRectangle.location(640, 640, 64, 64));
		p.setCollision(new Collision(true, new PerspectiveRectangleArea(QuickRectangle.location(0, 0, 64, 64))));


		if(lvlNum == 10) {
			complete = true;
			lvlNum = 10;
			endless = true;
			round++;
		}
		else if(lvlNum > 10 && endless) {
			lvlNum = 11;
			round++;
		}
		
		Level lvl = new Level(w);
		GameFile lvlF = new GameFile(Engine2.devPath + "/res/lvl/lvl" + lvlNum + ".txt");
		try {
			lvlF.setLines(lvlF.readFile());
		} 
		catch (IOException e) {
			loadLevel(-1);
			return;
		}
		msg = new GameFile(Engine2.devPath + "/res/lvl/lvl" + lvlNum + "-text.txt");
		try {
			msg.setLines(msg.readFile());
		} 
		catch (IOException e) {
			System.err.println("ERR: No message text!");
		}
		
		w = (RoomWorld) lvl.load(lvlF, 0);
		w.getObjects().add(p);
		
		w.setStartTime(System.currentTimeMillis());
		w.setLevelLength(lvl.getLevelLength());
		w.setZombies(lvl.getZombies());
		w.setZombies(w.getZombies() + (10 * round));
		shortErr = 0;
		moving = null;
		gs = GameState.MESSAGE;
		
		
//		noChunk = new ArrayList<Renderable>();
//		for(int i = 0; i < w.getObjects().size(); i++) {
//			if(!(w.getObjects().get(i) instanceof Chunk)) {
//				noChunk.add(w.getObjects().get(i));
//			}
//		}
	}

	@Override
	public void stop() {
		if(settings.getLines().size() > 0) {
			settings.getLines().set(0, complete + "");
			if(round > Integer.parseInt(settings.getLines().get(1))) {
				settings.getLines().set(1, round + "");
			}
		}
		try {
			settings.saveFile();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Graphics2D render(Graphics2D ctx) {
		if(lvlNum == 0) {
			if(!title.isImageLoaded()) {
				title.loadImage();
			}
			ctx.drawImage(title.getImage(), 0, 0, 640, 640, null);
			if(!complete) {
				ctx.setColor(Color.WHITE);
				ctx.fillRect(0, 560, 640, 80);
			}
			return ctx;
		}
		w.sortObjects();
		w.renderWorld(ctx);
		if(System.currentTimeMillis() - shortErr <= 3000) {
			ctx.setColor(Color.WHITE);
			ctx.fillRect(220, 240, 200, 45);
			ctx.setFont(new Font("Menlo", Font.BOLD, 12));
			ctx.setColor(Color.BLACK);
			ctx.drawString("That object is too short", 235, 260);
			ctx.drawString("to help defend there.", 250, 275);
		}
		if(w.getLevelLength() - (System.currentTimeMillis() - w.getStartTime()) <= 0 && gs == GameState.RUNNING) {
			gs = GameState.WIN;
		}
		if(gs == GameState.WIN) {
			if(!endless) {
				ctx.setColor(Color.WHITE);
				ctx.fillRect(85, 200, 475, 90);
				ctx.setFont(new Font("Menlo", Font.BOLD, 48));
				ctx.setColor(Color.BLACK);
				ctx.drawString("Level Complete!", 105, 240);
				ctx.setFont(new Font("Menlo", Font.BOLD, 16));
				ctx.drawString("Press [enter] to continue...", 180, 280);
			}
			else {
				ctx.setColor(Color.WHITE);
				ctx.fillRect(85, 200, 475, 90);
				ctx.setFont(new Font("Menlo", Font.BOLD, 48));
				ctx.setColor(Color.BLACK);
				ctx.drawString("Round Complete!", 107, 240);
				ctx.setFont(new Font("Menlo", Font.BOLD, 16));
				ctx.drawString("Press [enter] to continue...", 180, 280);
			}
		}
		else if(gs == GameState.LOSE) {
			if(!endless) {
				ctx.setColor(Color.WHITE);
				ctx.fillRect(125, 200, 395, 90);
				ctx.setFont(new Font("Menlo", Font.BOLD, 48));
				ctx.setColor(Color.BLACK);
				ctx.drawString("Level Failed!", 135, 240);
				ctx.setFont(new Font("Menlo", Font.BOLD, 16));
				ctx.drawString("Press [enter] to restart...", 185, 280);
			}
			else {
				ctx.setColor(Color.WHITE);
				ctx.fillRect(125, 200, 395, 90);
				ctx.setFont(new Font("Menlo", Font.BOLD, 48));
				ctx.setColor(Color.BLACK);
				ctx.drawString("Round Failed!", 135, 240);
				ctx.setFont(new Font("Menlo", Font.BOLD, 16));
				ctx.drawString("Completed Rounds: " + round, 225, 260);
				ctx.drawString("Press [enter] to continue to credits.", 136, 280);
			}
		}
		else if(gs == GameState.RUNNING) {
			ctx.setColor(Color.WHITE);
			ctx.fillRect(20, 20, 150, 40);
			ctx.setColor(Color.BLACK);
			ctx.drawString("Time Remaining: " + (int) ((w.getLevelLength() - (System.currentTimeMillis() - w.getStartTime())) / 1000), 30, 45);
			
			if(endless) {
				ctx.setColor(Color.WHITE);
				ctx.fillRect(540, 20, 80, 40);
				ctx.fillRect(270, 20, 100, 40);
				ctx.setColor(Color.BLACK);
				ctx.drawString("Round: " + round, 550, 45);
				ctx.drawString("Highscore: " + highscore, 280, 45);
			}
		}
		else if(gs == GameState.MESSAGE) {
			ctx.setColor(Color.WHITE);
			ctx.fillRect(16, 8, 608, 20 * msg.getLines().size() + 16);
			ctx.setColor(Color.BLACK);
			ctx.setFont(new Font("Menlo", Font.BOLD, 16));
			for(int i = 0; i < msg.getLines().size(); i++) {
				ctx.drawString(msg.getLines().get(i), 20, 20 * (i + 1) + 8);
			}
			w.setStartTime(System.currentTimeMillis());
		}
		
		if(moving != null) {
			moving.setLocation(QuickRectangle.location(mouse.x + w.getMainCamera().getLocation().getX() - moving.getLocation().getWidth() / 2, 
					mouse.y + w.getMainCamera().getLocation().getY() - moving.getLocation().getHeight() / 2, moving.getLocation().getWidth(), moving.getLocation().getHeight()));
			moving.render(ctx, w);
		}
		return ctx;
	}

	@Override
	public void tick() {
		if(gs == GameState.RUNNING) {
			pLoc = p.getLocation();
			if(key.left) {
				npLoc = QuickRectangle.location(pLoc.getX() - p.getWalkspeed(), pLoc.getY(), pLoc.getWidth(), pLoc.getHeight());
				p.setRunningAnimationNumber(2);
				if(w.checkCollide(new PerspectiveRectangleArea(npLoc), p) == null) {
					p.setLocation(npLoc);
					pLoc = p.getLocation();
				}
				
			}
			if(key.right) {
				npLoc = QuickRectangle.location(pLoc.getX() + p.getWalkspeed(), pLoc.getY(), pLoc.getWidth(), pLoc.getHeight());
				p.setRunningAnimationNumber(3);
				if(w.checkCollide(new PerspectiveRectangleArea(npLoc), p) == null) {
					p.setLocation(npLoc);
					pLoc = p.getLocation();
				}
			}
			if(key.up) {
				npLoc = QuickRectangle.location(pLoc.getX(), pLoc.getY() - p.getWalkspeed(), pLoc.getWidth(), pLoc.getHeight());
				p.setRunningAnimationNumber(1);
				if(w.checkCollide(new PerspectiveRectangleArea(npLoc), p) == null) {
					p.setLocation(npLoc);
					pLoc = p.getLocation();
				}
			}
			if(key.down) {
				npLoc = QuickRectangle.location(pLoc.getX(), pLoc.getY() + p.getWalkspeed(), pLoc.getWidth(), pLoc.getHeight());
				p.setRunningAnimationNumber(0);
				if(w.checkCollide(new PerspectiveRectangleArea(npLoc), p) == null) {
					p.setLocation(npLoc);
					pLoc = p.getLocation();
				}
			}
			if(key.repair) {
				e = w.collideWithEntrance(p.getLocation());
				if(e != null) {
					e.setHealth(e.getHealth() + p.getRepairSpeed());
				}
			}
			if(mouse.click && moving == null) {
				click = QuickRectangle.location(mouse.x - 5 + w.getMainCamera().getLocation().getX(), mouse.y - 5 + w.getMainCamera().getLocation().getY(), 10, 10);
				for(int i = 0; i < w.getObjects().size(); i++) {
					r = w.getObjects().get(i);
					if(r instanceof Furniture) {
						a = new RectangleCollideArea(r.getLocation());
						if(((Furniture) r).getEntrance() == null && a.checkCollision(click)) {
							moving = (Furniture) w.getObjects().get(i);
							w.getObjects().remove(i);
							mouse.click = false;
							break;
						}
					}
				}
			}
			else if(mouse.click && moving != null) {
				click = QuickRectangle.location(mouse.x - 5 + w.getMainCamera().getLocation().getX(), mouse.y - 5 + w.getMainCamera().getLocation().getY(), 10, 10);
				boolean found = false;
				for(int i = 0; i < w.getEntrances().size(); i++) {
					e = w.getEntrances().get(i);
					a = new RectangleCollideArea(e.getRepairZone());
					if(a.checkCollision(click) && e.getFurniture() == null) {
						if(e.isHigh() && !moving.getType().isTall()) {
							shortErr = System.currentTimeMillis();
						}
						else {
							moving.setLocation(QuickRectangle.location(e.getRepairZone().getX(), e.getRepairZone().getY() + e.getRepairZone().getHeight() - moving.getLocation().getHeight() - 32,
									moving.getLocation().getWidth(), moving.getLocation().getHeight()));
							moving.setEntrance(e);
							e.setFurniture(moving);
							w.getObjects().add(moving);
							moving = null;
							mouse.click = false;
							found = true;
						}
						break;
					}
				}
				if(!found) {
//					w.getObjects().add(moving);
//					moving = null;
//					mouse.click = false;
				}
			}
			w.runZombies();

			for(Entrance e1 : w.getEntrances()) {
				if(e1.getHealth() <= 0) {
					gs = GameState.LOSE;
				}
			}
			for(int i = 0; i < w.getObjects().size(); i++) {
				r = w.getObjects().get(i);
				if(r instanceof Furniture) {
					if(((Furniture) r).getEntrance() != null && ((Furniture) r).getEntrance().getFurniture() == null) {
						w.getObjects().remove(i);
						i--;
					}
//					r2 = FrozenWorld.checkCollide(noChunk, new RectangleCollideArea(r.getLocation()), r);
//					System.out.println(r2);
//					if(r2 instanceof Snowball) {
//						((Snowball) r2).setDestroy(true);
//					}
				}
				else if(r instanceof Hole) {
					if(System.currentTimeMillis() - ((Hole) r).getLastSpawn() >= ((Hole) r).getSpawnSpeed()) {
						((Hole) r).setLastSpawn(System.currentTimeMillis());
						w.getObjects().add(new Snowball(QuickRectangle.location(r.getLocation().getX() + 16, r.getLocation().getY() + 16, 32, 32)));
					}
				}
				else if(r instanceof Snowball) {
					if(((Snowball) r).doDestroy()) {
						w.getObjects().remove(i);
						i--;
					}
					else {
						sLoc = QuickRectangle.location(r.getLocation().getX(), ((Snowball) r).getSpeed() + r.getLocation().getY(), r.getLocation().getWidth(), r.getLocation().getHeight());
						r2 = w.checkCollide(new RectangleCollideArea(sLoc), r);
						if(r2 instanceof Player) {
							gs = GameState.LOSE;
						}
						if(System.currentTimeMillis() - ((Snowball) r).getSpawnTime() >= 1000) {
							if(r2 == null) {
								r.setLocation(sLoc);
							}
							else {
								w.getObjects().remove(i);
								i--;
							}
						}
						else {
							r.setLocation(sLoc);
						}
					}
				}
			}
		}
		w.getMainCamera().setLocation(new Point2(p.getLocation().getX() - 288, p.getLocation().getY() - 288, new PixelGrid()));
		p.getRunningAnimation().run();
	}
}
