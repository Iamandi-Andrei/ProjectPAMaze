# Project Description
Maze Generator- generarea de labirint 2D + posibilitatea de export (curent: CSV )  

# Project deployment

The project can be found here: https://calm-wave-66602.herokuapp.com/swagger-ui.html  
The link provided takes the user to the swagger documentation of the app to see all the available app commands


# Chosen Algorithm

In the beggining a random start Cell is picked and added to the stack.  
The next steps are recursive:    
1)Pick one of the current cell's neighbours randomly.   
2)Build a corridor between the chosen Cell and the current Cell  
3)Add the neighbour to the stack  
4)Go back to step 1 with the chosen neighbour as current Cell  
5)If the Cell has no unvisited neighbour, the algorithm will backtrack and grab a Cell from the stack  

# Project Structure

package app- contains the MainApplication class used to start the Spring application  
package controller- contains the class MazeController which maps all the operations this app can do.  
package mazeObj - contains the Maze, Cell and MazeBuilder class.  
Maze class is the main app entity, which contains a list of Cell objects.  
Initially, a Maze contained a 2d array of Cells and a Cell contained a 2d array of Integer but they were transformed to list due to persistence issues  
MazeBuilder class appeared as an adapter to keep the original maze generation working as the algorithm was built around the original 2d array data organization.  
package persistence - contains MazeService,MazeRepository,CellService,CellRepository classes, used by the controller to comunicate with the database.  

MazeTest- Testing class outside any package. At the moment it tests if the MazeBuilder's internal findCell method accurately returns the cell coordinates.  
The second test found here checks if the generated maze has less than 1% unbuilt/ inaccesible cells. The amount should actually be 0, but 1% out of totalCellCount is acceptable too.  
