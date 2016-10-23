package maze;

import java.io.*;
import java.nio.*;
import java.util.*;
public class Maze {

	public static void main(String[] args)
	{
        try {
            BufferedReader r = new BufferedReader(new FileReader("maze.txt"));
            int height = Integer.parseInt(r.readLine());
            int width = Integer.parseInt(r.readLine());
            ArrayList<String> strings = new ArrayList<>(height);
            for (String line; (line = r.readLine()) != null; ) {
                strings.add(line);
            }
            Maze m = new Maze(strings.toArray(new String[height]), height, width);
            m.findExitFrom(3, 5);
        }catch(IOException e)
        {
            System.out.println("Unable to open maze data.");
        }

	}
	
	private MazeTile[][] Map;
	public Maze(String[] maze, int height, int width)
	{
		this.Map = new MazeTile[height][width];
		for(int i = 0; i < maze.length; i++)
		{
			for(int j = 0; j < maze[i].length(); j++)
			{
				char c = maze[i].charAt(j);
				this.Map[i][j] = MazeTile.valueOf(c);
			}
		}
	}

	private Maze(MazeTile[][] old)
	{
		MazeTile[][] map = new MazeTile[old.length][old[0].length];
		for(int i=0; i<old.length; i++)
			  for(int j=0; j<old[i].length; j++)
			    map[i][j] = old[i][j];
		this.Map = map;
	}
	
	public void findExitFrom(int x, int y)
	{
		Maze m = new Maze(this.Map);
		m.setTileAt(MazeTile.START, new Coordinates(x, y));
		boolean exitPossible = findExitFrom(m, new Coordinates(x, y));
		m.print();
        System.out.println("An exit was" +(exitPossible ? "" : "not") +" found.");
	}
	
	private static boolean findExitFrom(Maze m, Coordinates c)
	{
		if(m.getTileAt(c) == MazeTile.EXIT)
		    return true;
        if(m.getTileAt(c) == MazeTile.WALL || m.getTileAt(c) == MazeTile.INVALID)
            return false;
		boolean success = false;
		if(m.getTileAt(c) != MazeTile.START) m.setTileAt(MazeTile.TRIED, c);
		if(!success && (m.getTileNorthOf(c) != MazeTile.WALL && m.getTileNorthOf(c) != MazeTile.TRIED))
		{
			success = findExitFrom(m, m.getCoordsNorthOf(c));
		}
		if(!success && (m.getTileSouthOf(c) != MazeTile.WALL && m.getTileSouthOf(c) != MazeTile.TRIED))
		{
			success = findExitFrom(m, m.getCoordsSouthOf(c));
		}
		if(!success && (m.getTileEastOf(c) != MazeTile.WALL && m.getTileEastOf(c) != MazeTile.TRIED))
		{
			success = findExitFrom(m, m.getCoordsEastOf(c));
		}
		if(!success && (m.getTileWestOf(c) != MazeTile.WALL && m.getTileWestOf(c) != MazeTile.TRIED))
		{
			success = findExitFrom(m, m.getCoordsWestOf(c));
		}
		if(success  && (m.getTileAt(c) != MazeTile.START)) {
			m.setTileAt(MazeTile.GOOD, c);
		}
		return success;
	}

	public void print()
	{
		for(MazeTile[] _m : this.Map)
		{
			for(MazeTile m : _m){
				System.out.print(m);
			}
			System.out.println();
		}
	}

	public MazeTile getTileAt(Coordinates c)
	{
        try {
            return this.Map[c.getY()][c.getX()];
        }catch(ArrayIndexOutOfBoundsException e)
        {
            return MazeTile.INVALID;
        }
	}
	
	public void setTileAt(MazeTile m, Coordinates c)
	{
		this.Map[c.getY()][c.getX()] = m;
	}

	public Coordinates getCoordsNorthOf(Coordinates c)
	{
		return new Coordinates(c.getX(), c.getY() - 1);
	}

	public Coordinates getCoordsSouthOf(Coordinates c)
	{
		return new Coordinates(c.getX(), c.getY() + 1);
	}
	public Coordinates getCoordsEastOf(Coordinates c)
	{
		return new Coordinates(c.getX() + 1, c.getY());
	}

	public Coordinates getCoordsWestOf(Coordinates c)
	{
		return new Coordinates(c.getX() - 1, c.getY());
	}


	public MazeTile getTileNorthOf(Coordinates c)
	{
		return this.getTileAt(this.getCoordsNorthOf(c));
	}
	
	public MazeTile getTileSouthOf(Coordinates c)
	{
		return this.getTileAt(this.getCoordsSouthOf(c));
	}
	
	public MazeTile getTileEastOf(Coordinates c)
	{
		return this.getTileAt(this.getCoordsEastOf(c));
	}
	
	public MazeTile getTileWestOf(Coordinates c)
	{
		return this.getTileAt(this.getCoordsWestOf(c));
	}


}
