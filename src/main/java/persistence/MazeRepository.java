package persistence;

import mazeObj.Maze;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MazeRepository extends CrudRepository<Maze,Long> {
    Maze findById(long id);

}
