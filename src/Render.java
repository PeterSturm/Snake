import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Render extends JPanel
{
	private int width;
	private int height;
	private int blockSize;
	private ArrayList<Block> blocks;
	private HashMap<String, Image> textures;
	private State state;
	private MenuState menuState;
	private ArrayList<Score> scores;
	
	public Render(int width, int height, int blockSize)
	{
		this.width = width;
		this.height = height;
		this.blockSize = blockSize;
		
		blocks = new ArrayList<Block>();
		
		setPreferredSize(new Dimension(width, height));
		
		LoadImages();
		
		state = State.MENU;
	}
	
	public void LoadImages()
	{
		textures = new HashMap<String, Image>();
		textures.put("snake", new ImageIcon("snake.png").getImage());
		textures.put("apple", new ImageIcon("apple.png").getImage());
		textures.put("ground_stone", new ImageIcon("ground_stone.png").getImage());
		
	}
	
	public void updateBlocks(ArrayList<SnakeBody> snake, ArrayList<Item> items)
	{
		blocks.clear();
		blocks.addAll(snake);
		blocks.addAll(items);
	}
	
	public void setState(State s)
	{
		state = s;
	}
	
	public void setMenuState(MenuState ms)
	{
		menuState = ms;
	}
	
	public void setScores(ArrayList<Score> scores)
	{
		this.scores = scores;
	}
	
	public void paint(Graphics g)
	{
		g.setFont(new Font("Verdana", Font.BOLD, 20));
		
		switch(state)
		{
			case MENU:
				renderMenu(g);
				break;
			case GAME:
				renderGame(g);
				break;
			case HIGHSCORE:
				renderHighScore(g);
			break;
			case GAMEOVER:
				break;
		}
	}
	
	public void renderMenu(Graphics g)
	{
		renderMap("ground_stone", g);
		
		switch(menuState)
		{
			case GAME:
				g.drawString("Game", width/2 - 2 * 18, height/2 -10);
			break;
			case HIGHSCORE:
				g.drawString("HighScore", width/2 - 3 * 18, height/2 -10);
				break;
			case EXIT:
				g.drawString("Exit", width/2 - 1 * 18, height/2 -10);
				break;
		}
		
	}
	
	public void renderGame(Graphics g)
	{
		renderMap("ground_stone", g);
		
		for(int i = 0; i < blocks.size(); i++)
			renderBlock(blocks.get(i), g);
	}
	
	public void renderHighScore(Graphics g)
	{
		renderMap("ground_stone", g);
		
		String entry;
		for(int i = 0; i < scores.size(); i++)
		{
			entry = new String(scores.get(scores.size()-i-1).getName() + "  " + scores.get(scores.size()-i-1).getScore());
			g.drawString(entry, width / 2 - (entry.length()*13 / 2), 60 + 13 * i + i * 10);
		}
	}
	
	public void renderBlocks(ArrayList blocks, Graphics g)
	{
		for(int i = 0; i < blocks.size(); i++)
			renderBlock((Block)blocks.get(i), g);
	}
	
	public void renderBlock(Block block, Graphics g)
	{
		g.drawImage(textures.get(block.getTexture()), block.getPosX() * blockSize, block.getPosY() * blockSize, null);
	}
	
	public void renderMap(String type, Graphics g)
	{
		int imgSize = textures.get(type).getWidth(null);
		
		for(int i = 0; i < width/imgSize; i++)
			for(int j = 0; j < height/imgSize; j++)
				g.drawImage(textures.get(type), i*imgSize, j*imgSize, null);
	}
}