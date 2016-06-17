import org.junit.Assert;
import org.junit.Test;


public class BlockTest
{

	@Test
	public void testPos()
	{
		Block b = new Block();
		b.setPosX(10);
		b.setPosY(2);
		
		int x = b.getPosX();
		int y = b.getPosY();
		
		Assert.assertEquals(10, x, 0);
		Assert.assertEquals(2, y, 0);
	}

}
