import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Engine implements Runnable
{
	private boolean isRunning;
	private State state;
	private Frame fr;
	private Render r;
	private int width;
	private int height;
	private int blockSize;
	
	private ArrayList<Score> highScores;
	
	private ArrayList<SnakeBody> snake;
	private ArrayList<Item> items;
	private int headX;
	private int headY;
	private int length;
	
	private int tick;
	
	private Direction direction;
	
	private Random rand;
	
	private Thread thread;
	
	private KeyPress keyPress;
	
	private MenuState menuState;
	
	private int score;
	
	public Engine(int width, int height)
	{
		isRunning = true;
		
		state = State.MENU;
		menuState = MenuState.GAME;
		
		blockSize = 10;
		this.width = width/blockSize;
		this.height = height/blockSize;
		
		r = new Render(width, height, blockSize);
		fr = new Frame(r, width, height);
		
		keyPress = new KeyPress();
		fr.addKeyListener(keyPress);
		
		rand = new Random();
		
		loadScores();
		
		start();
	}
	
	public void initGame()
	{
		snake = new ArrayList<SnakeBody>();
		items = new ArrayList<Item>();
		
		snake.add(new SnakeBody(3,1));
		length = snake.size();
		headX = 3;
		headY = 1;
		direction = Direction.RIGHT;
		
		items.add(new Item(6,10));
		
		r.updateBlocks(snake, items);
		
		tick = 0;
		
		score = 0;
	}
	
	private void loadScores()
	{
		try
		{
			highScores = new ArrayList<Score>();
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("scores.sc"));
			highScores = (ArrayList<Score>)ois.readObject();
			ois.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void saveScores()
	{
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("scores.sc"));
			oos.writeObject(highScores);
			oos.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	private String getPlayerName()
	{
		String name = new String();
		
		boolean valid = false;
		
		while(!valid)
		{
			name = JOptionPane.showInputDialog("Enter your name: ");
			if(name == null)
				name = "_player_";
			if(name.length() > 0)
				valid = true;
		}
		
		return name;
	}
	
	private void addScore(Score sc)
	{
		if(highScores.size() < 10)
		{
			highScores.add(sc);
			Collections.sort(highScores);
		}
		else
		{
			highScores.remove(0);
			highScores.add(sc);
			Collections.sort(highScores);
		}
		
		saveScores();
	}
	
	
	private boolean isNewScore(int sc)
	{
		if(highScores.size() < 10)
			return true;
		else
			for(int i= 0; i < highScores.size(); i++)
				if(sc > highScores.get(i).getScore())
					return true;
		
		return false;
	}
	
	public void loop()
	{
		switch(state)
		{
			case MENU:
			break;
			case INITGAME:
				initGame();
				state = State.GAME;
				break;
			case GAME:
				gameLogic();
			break;
			case INITHIGHSCORE:
				r.setScores(highScores);
				state = State.HIGHSCORE;
				break;
			case GAMEOVER:
				if(isNewScore(score))
				{
					String name = getPlayerName();
					Score s = new Score(name, score);
					addScore(s);
				}
				
				state = State.MENU;
			break;
		}
	}
	
	public void gameLogic()
	{
		tick++;
		
		// Set new direction and snake move
		if(tick > fr.getSpeed())
		{
			switch(direction)
			{
				case LEFT:
					headX--;
				break;
				case RIGHT:
					headX++;
				break;
				case UP:
					headY--;
				break;
				case DOWN:
					headY++;
				break;
			}
			
			
			snake.add(new SnakeBody(headX, headY));
			
			if(snake.size() > length)
				snake.remove(0);
			
			tick = 0;
			
			// Item add if there isn't
			if(items.size() == 0)
				items.add(new Item(rand.nextInt(width), rand.nextInt(height)));
			
			// Item pickup and snake grow
			for(int i = 0; i < items.size(); i++)
			{
				if(items.get(i).getPosX() == headX && items.get(i).getPosY() == headY)
				{
					items.remove(i);
					length++;
					score++;
				}
			}
			
			// Collision test
			for(int i = 0; i < snake.size()-1; i++)
				if(headX == snake.get(i).getPosX() && headY == snake.get(i).getPosY())
					state = State.GAMEOVER;
			
			// Out of map test
			if(headX < 0 || headX > width || headY < 0 || headY > height)
				state = State.GAMEOVER;
			
			
			
			r.updateBlocks(snake, items);
			
		}
	}
	
	public void start()
	{
		thread = new Thread(this, "Loop");
		thread.start();
	}
	
	public void stop()
	{
		isRunning = false;
		
		try
		{
			thread.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		while(isRunning)
		{
			r.setState(state);
			r.setMenuState(menuState);
			
			loop();
			
			r.repaint();
		}
		
	}
	
	public void setState(State s)
	{
		state = s;
	}
	
	public State getState()
	{
		return state;
	}
	
	public class KeyPress implements KeyListener
	{
		@Override
		public void keyPressed(KeyEvent kEvent)
		{
			int key = kEvent.getKeyCode();
			
			switch(state)
			{
				case MENU:
					if(key == KeyEvent.VK_UP && menuState == MenuState.GAME)
						menuState = MenuState.EXIT;
					else if(key == KeyEvent.VK_DOWN && menuState == MenuState.GAME)
						menuState = MenuState.HIGHSCORE;
					else if(key == KeyEvent.VK_UP && menuState == MenuState.HIGHSCORE)
						menuState = MenuState.GAME;
					else if(key == KeyEvent.VK_DOWN && menuState == MenuState.HIGHSCORE)
						menuState = MenuState.EXIT;
					else if(key == KeyEvent.VK_UP && menuState == MenuState.EXIT)
						menuState = MenuState.HIGHSCORE;
					else if(key == KeyEvent.VK_DOWN && menuState == MenuState.EXIT)
						menuState = MenuState.GAME;
					
					if(key == KeyEvent.VK_ENTER)
						switch(menuState)
						{
							case GAME:
								state = State.INITGAME;
							break;
							case HIGHSCORE:
								state = State.INITHIGHSCORE;
								break;
							case EXIT:
								System.exit(0);
								break;
						}
				break;
				case GAME:
					if(key == KeyEvent.VK_LEFT && direction != Direction.RIGHT)
						direction = Direction.LEFT;
					if(key == KeyEvent.VK_RIGHT && direction != Direction.LEFT)
						direction = Direction.RIGHT;
					if(key == KeyEvent.VK_UP && direction != Direction.DOWN)
						direction = Direction.UP;
					if(key == KeyEvent.VK_DOWN && direction != Direction.UP)
						direction = Direction.DOWN;
				break;
				case HIGHSCORE:
					state = State.MENU;
					break;
				case GAMEOVER:
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e)
		{
			// TODO Auto-generated method stub
			
		}
	}

}

















