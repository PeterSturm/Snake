import java.io.Serializable;


public class Score implements Comparable<Score>, Serializable
{
	private String name;
	private int score;
	
	public Score(String name, int score)
	{
		this.name = name;
		this.score = score;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setScore(int score)
	{
		this.score = score;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getScore()
	{
		return score;
	}

	@Override
	public int compareTo(Score sc)
	{
		if(score < sc.getScore())
			return -1;
		else if(score == sc.getScore())
			return 0;
		else
			return 1;
	}
}
