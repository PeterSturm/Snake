import org.junit.Assert;

import org.junit.Test;


public class EngineTest
{

	@Test
	public void testCreate()
	{
		Engine e = new Engine(400, 400);
	}
	
	@Test
	public void testGame()
	{
		Engine e = new Engine(400, 400);
		e.setState(State.INITGAME);
		e.start();
	}
	
	@Test
	public void testHighScore()
	{
		Engine e = new Engine(400, 400);
		e.setState(State.INITHIGHSCORE);
		e.start();
	}
	
	@Test
	public void testGameOver()
	{
		Engine e = new Engine(400, 400);
		e.setState(State.GAMEOVER);
		e.start();
	}

}
