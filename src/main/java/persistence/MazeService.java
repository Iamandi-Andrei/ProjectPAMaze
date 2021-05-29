package persistence;

import mazeObj.Cell;
import mazeObj.Maze;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MazeService {

    @Autowired
    MazeRepository mazeRepository;
    @Autowired
    CellRepository cellRepository;


    @Autowired
    public MazeService(MazeRepository mazeRepository) {
        this.mazeRepository = mazeRepository;
    }


    public void saveOrUpdate(Maze maze) {

        mazeRepository.save(maze);
    }

    public Maze findByID(long id){
        return mazeRepository.findById(id);
    }

    public void delete(long id){

        Maze m=mazeRepository.findById(id);
        mazeRepository.delete(m);}
}
