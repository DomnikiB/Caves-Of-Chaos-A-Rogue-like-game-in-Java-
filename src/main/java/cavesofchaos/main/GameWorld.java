/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cavesofchaos.main;

import java.util.List;

/**
 *
 * @author domniki
 */
public class GameWorld { 
    GamePanel gp;
    
    private final List<String[][]> connectedCaves;
    private final int numLevels = 10;
    private int currentLevel;
    public String[][] gameMap;

    //constructor
    public GameWorld(GamePanel gp) {
        this.gp = gp;
        connectedCaves = GameMap.makeConnectedMaps(numLevels, gp.getMaxMapCol(), gp.getMaxMapRow());
        currentLevel = 0;
        gameMap = connectedCaves.get(currentLevel);
    }
    
    //getters
    public String[][] getCurrentMap() {
        return gameMap;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }
    
    //method to move to next level when stepping on exit
    public void goToNextLevel() {
        if (currentLevel < connectedCaves.size() - 1) {
            currentLevel++;
            gameMap = connectedCaves.get(currentLevel);
        }
    }
    
    //method to move to previous level when stepping on exit
    public void goToPreviousLevel() {
        if (currentLevel > 0) {
            currentLevel--;
            gameMap = connectedCaves.get(currentLevel);
        }
    }
    
    //method to get start possition on current level
    public GameMap.Possition getStartPossition() {
        return GameMap.findTile(gameMap, "start");
    }

    //getter for GameWorld List spesific map
    public String[][] getSpesificLevelMap(int i) {
        return connectedCaves.get(i);
    }

    public int getNumLevels() {
        return numLevels;
    }
    
    
    
}
