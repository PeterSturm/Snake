
public class Block
{
	private int posX;
	private int posY;
	private String texture;
	
	public Block()
	{
	}
	
	public int getPosX()
	{
		return posX;
	}
	
	public int getPosY()
	{
		return posY;
	}
	
	public void setPosX(int value)
	{
		posX = value;
	}
	
	public void setPosY(int value)
	{
		posY = value;
	}
	
	public void setTexture(String image)
	{
		texture = image;
	}
	
	public String getTexture()
	{
		return texture;
	}
}
