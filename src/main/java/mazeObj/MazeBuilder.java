package mazeObj;

import java.util.*;

/**
 * MazeBuilder class receives an unbuilt Maze object and generates it randomly using the generateRecursive Method
 */
public class MazeBuilder {
    private Cell[][] maze;
    private int height;
    private int width;
    private Maze mainMaze;
    private Deque<Cell> stack;

    public MazeBuilder(Maze mainMaze) {
        this.maze = mainMaze.getMaze();
        this.height = mainMaze.getHeight();
        this.width = mainMaze.getWidth();
    }

    public void build() {
        this.generateRecursive();


    }

    /**
     * Main algorithm entry point, it picks a random cell ,adds it to the stack
     * and calls the recursive pickRandom method
     */
    public void generateRecursive() {
        Random rand = new Random();

        stack = new ArrayDeque<>();
        int n = rand.nextInt(this.height);
        int m = rand.nextInt(this.width);
        Cell current = maze[n][m];
        //System.out.println("start: "+n+" "+m);
        stack.add(current);
        this.pickRandom(current);

        n = rand.nextInt(this.height);
        m = rand.nextInt(this.width);
        current = maze[n][m];
        current.getWalls()[1][1] = 2;
        n = rand.nextInt(this.height);
        m = rand.nextInt(this.width);
        current = maze[n][m];
        current.getWalls()[1][1] = 3;

    }

    /**
     * Recursive method, it receives a Cell object and adds its "unbuilt" neighbours in a List
     * If there are neighbours in the list, it randomly picks one,
     * builds a corridor between the current cell and the neighbour
     * adds the neighbour to the stack to come back later
     * then calls pickRandom with the neighbour cell
     * if a cell has no unbuilt neighbours, the method just backtracks and calls pickRandom with a cell from the stack
     */
    public void pickRandom(Cell c) {
        int nr = this.findCell(c);
        int i = nr / this.width;
        int j = nr % this.width;


        List<Cell> neighbours = new ArrayList<>();
        try {
            if (i > 0)
                if (!maze[i - 1][j].isBuilt())
                    neighbours.add(maze[i - 1][j]);
            if (i < this.height)
                if (!maze[i + 1][j].isBuilt())
                    neighbours.add(maze[i + 1][j]);
            if (j > 0)
                if (!maze[i][j - 1].isBuilt())
                    neighbours.add(maze[i][j - 1]);
            if (j < this.width)
                if (!maze[i][j + 1].isBuilt())
                    neighbours.add(maze[i][j + 1]);
        } catch (IndexOutOfBoundsException e) {


        }
        if (neighbours.size() > 0) {

            Random rand = new Random();
            Cell n = neighbours.get(rand.nextInt(neighbours.size()));
            this.buildCorridor(n, c);
            n.built = true;
            stack.add(n);
            this.pickRandom(n);
        } else {
            if (stack.size() > 0)
                this.pickRandom(stack.pop());
        }

    }

    /**
     * The method receives 2 Cell onjects and marks the corresponding walls to 0 so that a corridor is created
     * between the cells
     */
    public void buildCorridor(Cell a, Cell b) {
        int nr = this.findCell(a);
        int i = nr / this.width;
        int j = nr % this.width;
        nr = this.findCell(b);
        int x = nr / this.width;
        int y = nr % this.width;
        switch (i - x) {
            case -1:
                a.breakWall("S");
                b.breakWall("N");
                break;
            case 1:
                a.breakWall("N");
                b.breakWall("S");
                break;
            case 0:
                switch (j - y) {
                    case -1:
                        a.breakWall("E");
                        b.breakWall("W");
                        break;
                    case 1:
                        a.breakWall("W");
                        b.breakWall("E");
                        break;
                }
        }


    }

    /**
     *
     * The method is used to get the exact coordinates of the received Cell parameter
     * the i and j coordinates can be extracted from the returned value using position/this.width and position%this.width
     */
    public int findCell(Cell c) {
        int pozition = -1;
        for (int i = 0; i < maze.length; i++)
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j].equals(c)) {
                    pozition = i * this.width + j;
                    break;
                }
            }
        return pozition;
    }

    public void print() {
        int k;
        int x = 2;
        for (int i = 0; i < maze.length; i++) {
            k = 0;
            if (i == maze.length - 1) x = 3;
            while (k != x) {

                for (int j = 0; j < maze[i].length; j++) {
                    if (j == maze[i].length - 1)
                        maze[i][j].print(k, 0);
                    else
                        maze[i][j].print(k, 1);

                }
                System.out.println();
                k++;
            }

        }
    }

}
