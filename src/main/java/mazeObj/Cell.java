package mazeObj;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cell {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public long idd;
    @ElementCollection(fetch = FetchType.LAZY)
    List<Integer> walls;
    boolean built = false;

    public long getIdd() {
        return idd;
    }

    public Cell() {
        this.walls = new ArrayList<>();
        for (int i = 0; i <= 2; i++)
            for (int j = 0; j <= 2; j++)
                this.walls.add(1);
        this.walls.set(4, 0);
    }

    public void breakWall(String direction) {

        switch (direction) {
            case "N":
                this.walls.set(1, 0);
                built = true;
                break;
            case "S":
                this.walls.set(7, 0);
                built = true;
                break;
            case "W":
                this.walls.set(3, 0);
                built = true;
                break;
            case "E":
                this.walls.set(5, 0);
                built = true;
                break;
        }

    }

    public void print(int i, int fin) {


        for (int j = 0; j < 3 - fin; j++) {

            if (walls.get(i * 3 + j) == 0)
                System.out.print(walls.get(i * 3 + j) + " ");
            else
                System.out.print("\u25A0 ");

        }
    }

    public boolean isBuilt() {
        return built;
    }

    public int[][] getWalls() {
        int[][] result = new int[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                result[i][j] = walls.get(i * 3 + j);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder print = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                if (walls.get(i * 3 + j) == 0)
                    print.append(" 0 ");
                else
                    print.append(" \u25A0 ");
                print.append("\n");
        }
        return print.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;

        Cell cell = (Cell) o;

        if (idd != cell.idd) return false;
        if (isBuilt() != cell.isBuilt()) return false;
        return getWalls() != null ? getWalls().equals(cell.getWalls()) : cell.getWalls() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (idd ^ (idd >>> 32));
        result = 31 * result + (getWalls() != null ? getWalls().hashCode() : 0);
        result = 31 * result + (isBuilt() ? 1 : 0);
        return result;
    }

    public StringBuilder getCellLine(int line){
        StringBuilder response=new StringBuilder();
        for(int i=0;i<3;i++)
            if(walls.get(i+line*3)==1)
                response.append("\u25A0");
            else response.append("\u25A1");
        return response;

    }
    public String getCellLineCSV(int line){
        StringBuilder response=new StringBuilder();
        for(int i=0;i<3;i++)
            if(walls.get(i+line*3)==1)

                response.append("1,");

            else
                response.append("0,");

        return response.toString();

    }
}
