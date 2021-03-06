# Sudoku Solver
This program allows a user to check if a Sudoku puzzle solution is valid, or automatically solve an incomplete puzzle. The program will notify the user if inputs are invalid or no solution is found. The puzzle is solved using recursive backtracking. The program will guess what each tile's value is from left to right, top to bottom, until a full solution is found. If an invalid board state is reached, the program will backtrack to a valid state and continue exploring options.

Sudoku is a logic game. A board contains a 9x9 grid of tiles, also divided into 3x3 sub-grids. Each row, column, and sub-grid must contain all numbers between 1 and 9 inclusive with no duplicates.

![ezgif com-gif-maker](https://user-images.githubusercontent.com/56368354/124191254-65931200-da78-11eb-8dde-b3c2d9204850.gif)
