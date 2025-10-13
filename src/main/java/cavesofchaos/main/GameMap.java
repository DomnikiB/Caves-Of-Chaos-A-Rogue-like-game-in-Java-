/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cavesofchaos.main;

import static java.lang.Math.abs;
import java.util.*;

/**
 *
 * @author domniki
 */
public class GameMap {
    GamePanel gp;
        
    private static final double percentage=0.4; //percentage of coverage
    
    private static final String WALL = "wall";
    private static final String FLOOR = "floor";
    private static final String START = "start";
    private static final String STAIRS = "stairs";
    
    public String[][] gameMap;

    //constructor
    public GameMap(GamePanel gp) { 
        this.gp = gp;
        this.gameMap = makeMap(gp.getMaxMapCol(), gp.getMaxMapRow());
        
    }

    //getters
    public double getPercentage() {
        return percentage;
    }

    public String getWALL() {
        return WALL;
    }

    public String getFLOOR() {
        return FLOOR;
    }

    public String getSTART() {
        return START;
    }

    public String getSTAIRS() {
        return STAIRS;
    }
    
    //helper class for coordinates inside map
    public static class Possition {
        public int x, y;
        
        Possition(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    //method to generate connected caves
    public static List<String[][]> makeConnectedMaps(int levels, int M, int N) {
        List<String[][]> caves = new ArrayList<>();
                
        for (int level = 0; level < levels; level++) {
            String[][] map;
            map = makeMap(M, N);
            if (level < levels - 1) {
                Possition startTile = findTile(map, "start");
                placeExit(map, startTile);
            }
            caves.add(map);
        }
        return caves;
    }
    
    //method to make single map
    public static String[][] makeMap(int M, int N) {
        String[][] gameMap = new String[M][N];
        
        //fill map with walls
        for (int i = 0; i < M; ++i) {
            for (int j = 0; j < N; ++j) {
                gameMap[i][j] = WALL;
            }
        }
        
        //random start
        Random random = new Random();
        int curX, curY;
        int startX, startY;
        
        curX = startX = random.nextInt(M);
        curY = startY = random.nextInt(N); 
        
        gameMap[startX][startY] = START;
        
        //fill with floor while floors < dimensions*perc
        int filled = 1;
        
        while ((double) filled / (M * N) < percentage) {
            int[] dir = selectPointInRandomDirection(curX, curY, M, N);
            curX += dir[0];
            curY += dir[1];
            
            if (gameMap[curX][curY].equals(WALL)) {
                gameMap[curX][curY] = FLOOR;
                filled += 1;
            }
        }
        return gameMap;
    }
    
    //method to select random direction
    private static int[] selectPointInRandomDirection(int x, int y, int M, int N) {
        Random random = new Random();
        int ran = random.nextInt(4);
        
        switch (ran) {
            case 0 -> { //up direction y + 1
                if (y + 1 < N) { //checks if in boundaries
                    return new int[]{0,1};
                }
            }
            case 1 -> { //down direction y - 1
                if (y - 1 >= 0) {
                    return new int[]{0,-1};
                }
            }
            case 2 -> { //right direction x + 1
                if (x + 1 < M) {
                    return new int[]{1,0};
                }
            }
            case 3 -> { //left direction x - 1
                if (x - 1 >= 0) {
                    return new int[]{-1,0};
                }
            }
            default -> {
            }
        }
        return new int[]{0,0};
    }
    
    //method to place exit in map at max distance from start
    public static void placeExit(String[][] gameMap, Possition start) {
        int rows = gameMap.length;
        int cols = gameMap[0].length;
        int maxDist = 0;
        int exitX = start.x, exitY = start.y;
        
        //find tile largest distance that is floor
        for (int i=0; i<rows; ++i){
            for (int j=0; j<cols; ++j) {
                int newDist = abs(i - start.x) + abs(j - start.y);
                if (gameMap[i][j].equals(FLOOR) && newDist > maxDist) {
                    maxDist = newDist;
                    exitX = i;
                    exitY = j;
                }
            }
        }
        gameMap[exitX][exitY] = STAIRS;
        
    }
    
    //method to find a tile of spesific kind
    public static Possition findTile(String[][] gameMap, String tileKind) {
        int rows = gameMap.length;
        int cols = gameMap[0].length;
        Possition defaultTile = new Possition(0,0);
        
        for (int i=0; i<rows; ++i){
            for (int j=0; j<cols; ++j) {
                if (gameMap[i][j].equals(tileKind)) {
                    Possition tile = new Possition(i, j);
                    return tile;
                }
            }
        }
        return defaultTile;
    }
}
