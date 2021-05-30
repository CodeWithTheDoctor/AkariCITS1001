PROJECT TITLE: Designing an Akari Game
PURPOSE OF PROJECT: To Design a fully playable Akari game that allows for the 
                    create the grid to be created using a textfile.
DATE: 21/05/2021

HOW TO START THIS PROJECT: Right click the Akariviewer class, and type the following
                            new Akari("Puzzles/{filename}"), where filename is the name
                            of the file you wrote the grid layout in. They should be placed
                            in the puzzles folder in this case. Notice there are already
                            files in the Puzzles folder such as P14-e1
                            so: new Akari("Puzzles/P14-e1") would work as an input.
AUTHORS: Sean and Ash

USER INSTRUCTIONS: Place a lightbulb on the empty spaces in the grid by clicking on
                   them.
                   To check if you have completed the puzzle, click on solved.
                   Broken number of bulbs indicates that there are incorrect number
                   of bulbs around a certain black block or blocks.
                   Unlit square indicates that there is a place on the grid where
                   none of the bulbs' light is reaching.
                   Clashing bulbs indicates there exist pair(or pairs) of bulbs which
                   are within each other's light path.
