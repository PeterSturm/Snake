import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;


public class Frame extends JFrame
{
	private int width;
	private int height;
	
	private JRadioButtonMenuItem menu_item_speed01;
	private JRadioButtonMenuItem menu_item_speed02;
	private JRadioButtonMenuItem menu_item_speed03;
	
	int speed;
	
	public Frame(Render r, int width, int height)
	{
		this.width = width;
		this.height = height;
		initUI(r);
	}

	private void initUI(Render r)
	{
		setTitle("Snake");
		
		setResizable(false);
		
		ButtonGroup btngroup = new ButtonGroup();
		
		ActionListener sl = new SpeedListener();
		
		JMenuItem menu_item_exit = new JMenuItem("Exit");
		menu_item_exit.addActionListener(new ExitListener());
		menu_item_speed01 = new JRadioButtonMenuItem("Slow");
		menu_item_speed01.addActionListener(sl);
		menu_item_speed02 = new JRadioButtonMenuItem("Medium");
		menu_item_speed02.addActionListener(sl);
		menu_item_speed03 = new JRadioButtonMenuItem("Fast");
		menu_item_speed03.addActionListener(sl);
		
		menu_item_speed02.setSelected(true);
		speed = 1700000;
		
		
		JMenu menu_file = new JMenu("File");
		menu_file.add(menu_item_exit);
		
		JMenu menu_speed = new JMenu("Speed");
		menu_speed.add(menu_item_speed01);
		menu_speed.add(menu_item_speed02);
		menu_speed.add(menu_item_speed03);
		btngroup.add(menu_item_speed01);
		btngroup.add(menu_item_speed02);
		btngroup.add(menu_item_speed03);
		JMenu menu_options = new JMenu("Options");
		menu_options.add(menu_speed);
		
		JMenuBar bar = new JMenuBar();
		bar.add(menu_file);
		bar.add(menu_options);
		
		setJMenuBar(bar);
		
		add(r);
		
		pack();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	public int getSpeed()
	{
		return speed;
	}
	
	public class ExitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	}
	
	public class SpeedListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(menu_item_speed01.isSelected())
				speed = 2500000;
			if(menu_item_speed02.isSelected())
				speed = 1700000;
			if(menu_item_speed03.isSelected())
				speed = 1000000;
		}
		
	}
}
