package persistence;

import mazeObj.Cell;
import mazeObj.Maze;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CellService {
    @Autowired
    CellRepository cellRepository;


    @Autowired
    public CellService(CellRepository mazeRepository) {
        this.cellRepository = mazeRepository;
    }


    public void saveOrUpdate(Cell cell) {
        cellRepository.save(cell);
    }
}
