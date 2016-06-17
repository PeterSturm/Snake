import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


public class RenderTest
{
	Render r;
	
	@Before
	public void setUp() throws Exception
	{
		r = new Render(400, 400, 10);
	}

	@Test
	public void updateBlocksTest()
	{
		ArrayList<SnakeBody> s = new ArrayList<SnakeBody>();
		s.add(new SnakeBody(2, 3));
		s.add(new SnakeBody(1, 5));
		ArrayList<Item> i = new ArrayList<Item>();
		i.add(new Item(2, 3));
		i.add(new Item(1, 5));
		
		r.updateBlocks(s, i);
	}
	
	@Test
	public void setStateTest()
	{
		r.setState(State.GAME);
	}
	
	@Test
	public void setMenuStateTest()
	{
		r.setMenuState(MenuState.EXIT);
	}
	
	@Test
	public void setScoresTest()
	{
		ArrayList<Score> sc = new ArrayList<Score>();
		sc.add(new Score("name", 10));
		
		r.setScores(sc);
	}
	
	

}
