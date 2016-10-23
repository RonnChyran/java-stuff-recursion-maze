package maze;

public enum MazeTile {


	WALL('W', ":black,black:"),
	HALL('.', ":blue,blue:"),
	EXIT('X', ":red,n:"),
	GOOD('+', ":green,green:"),
	TRIED('-', ":red,red:"),
	START('@', ":green,n:"),
	INVALID('*', ":red,red:");


	private char tile;
    private String color;
	MazeTile(char c, String color){
		this.tile = c;
        this.color = color;
	}
	
	public boolean equals(char c)
	{
		return c == tile;
	}
	public String toString(){
		return ColorCodes.parseColors(this.color + Character.toString(this.tile) + "[RC]");
	}
	public static MazeTile valueOf(char c)
	{
		switch(c)
		{
		case 'W':
			return MazeTile.WALL;
		case '.':
			return MazeTile.HALL;
		case 'X':
			return MazeTile.EXIT;
		case '+': 
			return MazeTile.GOOD;
		case '-': 
			return MazeTile.TRIED;
		default:
			return MazeTile.INVALID;
		}
	}
}
