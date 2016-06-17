
public class Item extends Block
{
	public Item()
	{
		super();
		setTexture("apple");
	}
	
	public Item(int x, int y)
	{
		super();
		setTexture("apple");
		setPosX(x);
		setPosY(y);
	}
}
