import mazeObj.Cell;
import mazeObj.Maze;
import mazeObj.MazeBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MazeTest {

    private Maze maze;
    private MazeBuilder builder;

    @BeforeEach
    public void setUp() throws Exception {
        maze = new Maze(30, 30);
        builder=new MazeBuilder(maze);
    }

    @RepeatedTest(500)
    @DisplayName("Cell finder should hopefully work")
    public void testFindAccuracy() {
        Random rand = new Random();


        int n = rand.nextInt(maze.getHeight());
        int m = rand.nextInt(maze.getWidth());
        Cell current = maze.getMaze()[n][m];
        int nr = builder.findCell(current);
        int i = nr / maze.getWidth();
        int j = nr % maze.getWidth();
        assertEquals(n, (builder.findCell(current) / maze.getWidth()),
                "First coordinate should be good");
        assertEquals(m, (builder.findCell(current) % maze.getWidth()),
                "Second coordinate should be good");
    }

    @RepeatedTest(500)
    @DisplayName("Ensure the maze is perfect")
    public void testBlockedCells() {
        builder.generateRecursive();
        double percentage=(maze.getnonVisits()*100)/maze.getSizeTotal();
        assertEquals(true,(percentage<1));

    }
}
