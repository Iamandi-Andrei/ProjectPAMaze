package mazeObj;

import com.opencsv.CSVWriter;

import javax.persistence.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

@Entity
public class Maze {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @OneToMany(cascade=CascadeType.ALL)
    private List<Cell> maze;
    private int height;
    private int width;


    public Maze(int height, int width) {
        this.height = height;
        this.width = width;

        maze = new ArrayList<Cell>();

        for (int i = 0; i < this.height; i++)
            for (int j = 0; j < this.width; j++)
                maze.add(new Cell());

    }

    public Maze() {
    }

    public int getnonVisits() {
        int nr = 0;
        for (Cell cell : maze) if (!cell.isBuilt()) nr++;

        return nr;
    }

    public int getSizeTotal() {
        return this.height * this.width;
    }


    public Cell[][] getMaze() {
        Cell[][] result = new Cell[this.height][this.width];
        for (int i = 0; i < result.length; i++)
            for (int j = 0; j < result[0].length; j++)
                result[i][j] = maze.get(this.width * i + j);
        return result;
    }

    public List<Cell> getCells() {
        return this.maze;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Maze)) return false;

        Maze maze1 = (Maze) o;

        if (id != maze1.id) return false;
        if (getHeight() != maze1.getHeight()) return false;
        if (getWidth() != maze1.getWidth()) return false;
        return getMaze() != null ? getMaze().equals(maze1.getMaze()) : maze1.getMaze() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (getMaze() != null ? getMaze().hashCode() : 0);
        result = 31 * result + getHeight();
        result = 31 * result + getWidth();
        return result;
    }

    public StringBuilder printMaze() {
        StringBuilder response = new StringBuilder();
        for (int i = 0; i < height; i++)
            for (int k = 0; k < 3; k++) {
                for (int j = 0; j < width; j++)
                    response.append(maze.get(i * width + j).getCellLine(k));
                response.append("</br>");
            }
        return response;


    }


    public void printToCSV(PrintWriter writer) {

        for (int i = 0; i < height; i++) {

            for (int k = 0; k < 3; k++) {
                StringBuilder response = new StringBuilder();
                for (int j = 0; j < width; j++)
                    response.append(maze.get(i * width + j).getCellLineCSV(k));
                writer.println(response);
            }


        }
    }


}
