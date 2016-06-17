import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;


public class FrameTest
{
	Frame f;

	@Before
	public void setUp() throws Exception
	{
		Render r = new Render(400, 400, 10);
		f = new Frame(r, 400, 400);
	}

	@Test
	public void speed()
	{
		int result = f.getSpeed();
		Assert.assertEquals(1700000, result, 0);
	}

}
