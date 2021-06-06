package Controller;


import app.MainApplication;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import mazeObj.Cell;
import mazeObj.Maze;
import mazeObj.MazeBuilder;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import persistence.CellService;
import persistence.MazeService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/maze")
public class MazeController {
    private Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);
    @Autowired
    MazeService repository;
    @Autowired
    CellService cellRepository;

    @PostMapping("/create/{height}/{width}")
    public ResponseEntity<String> createMaze(@PathVariable("height") int height,
                                             @PathVariable("width") int width) {


        Maze obj = new Maze(height, width);


        MazeBuilder maze = new MazeBuilder(obj);
        try {
            LOGGER.info("Started generating maze with sizes: {} {}, id not known yet",height,width);
            maze.generateRecursive();
        }catch (StackOverflowError e){

            LOGGER.error("OverFlowError for sizes {} , {}",height,width);
            return new ResponseEntity<>("Internal generation error",HttpStatus.INTERNAL_SERVER_ERROR);

        }

//        for (Cell c : obj.getCells())
//            cellRepository.saveOrUpdate(c);
        repository.saveOrUpdate(obj);

        LOGGER.info("Generated a maze with {} {} sizes..id: {}", height, width, obj.getId());

        return new ResponseEntity<>("The maze id is: " + obj.getId(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getMazeId(@PathVariable("id") long id) {
        Maze m = repository.findByID(id);
        HttpHeaders respHeaders = new HttpHeaders();
        if (m != null) {
            //respHeaders.add("Maze", m.printMaze().toString());
            LOGGER.info("Maze {} was found in the database", id);
            return new ResponseEntity<>(m.printMaze().toString(), HttpStatus.OK);
        } else {
            //respHeaders.add("MazeStatus", "Maze not found");
            LOGGER.error("Maze {} not found ", id);
            return new ResponseEntity<>("Maze not found", HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMazeId(@PathVariable("id") long id) {

        Maze m = repository.findByID(id);

        if (m != null) {
            repository.delete(id);
            LOGGER.info("Maze {} deleted", id);
            return new ResponseEntity<>("Maze " + id + " deleted",HttpStatus.OK);

        } else {
            LOGGER.error("Maze {} not found but requested", id);
            return new ResponseEntity<>("No maze found with id: " + id,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{id}/save", produces = "text/csv")
    public void CSVMaze(@PathVariable Long id, HttpServletResponse response) throws IOException {

        String filename = "maze.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");


        PrintWriter out = response.getWriter();
        Maze m = repository.findByID(id);
        if (m != null) {
            LOGGER.info("Maze {} was requested for download", id);
            m.printToCSV(out);
        } else
            LOGGER.error("Maze {} not found but requested for download", id);

    }

}
