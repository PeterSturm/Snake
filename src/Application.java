import javax.swing.SwingUtilities;


public class Application
{

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
			{
			    public void run()
			    {
			    	Engine e = new Engine(400, 400);
			    }
			}
		
		);
	}

}
