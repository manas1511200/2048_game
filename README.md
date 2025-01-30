# 2048 Game - Java Swing Implementation

## Overview
This is a Java Swing-based implementation of the classic 2048 game. The game provides a graphical user interface with smooth animations, keyboard controls, and a high score saving system. Players can move tiles in different directions to combine numbers and try to reach 2048
![Alt Text](https://github.com/user-attachments/assets/2454e523-7f57-4ca0-8162-32e59dca4d0c)

## Features
- **Dynamic Grid Sizes**: Play on a 3x3, 4x4, or 5x5 grid.
- **Smooth UI Updates**: Grid updates dynamically when resizing the window.
- **Score Management**: Tracks and displays current scores.
- **High Score Saving**: Saves previous high scores to a file for later viewing.
- **Game Over Handling**: Displays a message when the game ends and allows restarting.
- **Scrollable Score History**: Prevents overflowing score display with a scrollable dialog.
- **Keyboard Controls**: Arrow keys allow moving tiles in different directions.
- **Color Coded Tiles**: Provides a visually appealing experience.

## How to Play
1. Use the arrow keys to slide the tiles in the desired direction.
2. When two tiles with the same number collide, they merge into one with their sum.
3. The goal is to reach the 2048 tile.
4. The game ends when no more moves are possible.

## Installation & Running
1. Clone the repository:
   ```sh
   [ -d "2048_game" ] && rm -rf "2048_game"
   git clone https://github.com/manas1511200/2048_game.git
   ```
   Then just run the jar file if you have jdk installed
   2.If  you run linux directly use this command
    ```sh
   [ -d "2048_game" ] && rm -rf "2048_game"
   git clone https://github.com/manas1511200/2048_game.git
   cd 2048_game || exit
   chmod +x ./run.sh && ./run.sh
    ```

## Future Improvements
- Add animations for tile movements.
- Implement an AI solver.
- Add a leaderboard system for top scores.
- Introduce themes and custom styling.

## Contributions
Contributions are welcome! Feel free to open issues or submit pull requests.

## License
This project is licensed under the MIT License.

