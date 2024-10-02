## VERSION 2
This is the 2nd version implementing chess GUI using JFrame

##### Main runner program for this is "ChessGUI.java"

Program currently uses chess symbols from UNICODE to represent Pieces, so make sure your IDE supports UNICODE characters before running the program.

### Updates
- Implemented GUI based code eliminating the need of terminal
- Undo and Reset board added 

### Things left
- En passant doesn't correctly check that the adjacent pawn was moved previously or not. 
- Need to add different Game End conditions like 3-fold repetition, insufficient material, 50 move rule.
- Need to a simple evaluation bar and a timer for both players.
- PGN generator doesn't generate PGN correctly hence is unused.
