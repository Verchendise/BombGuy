package demolition;
 
import processing.core.PApplet;
import processing.core.PImage;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
 

public class Map  {
    private char[][] map = new char[13][15];
    private int level = 1;
    private int timer;
    private int lives;
    private String path;
    public int pxCoOrd;
    public int pyCoOrd;
    public int gxCoOrd;
    public int gyCoOrd;
    public int maxLevel;
    public PImage broken;
    public PImage empty;
    public PImage goal;
    public PImage wall;
    private ArrayList<String> pathList;
    private ArrayList<Integer> timersList;
    public ArrayList<RedEnemy> reArray = new ArrayList<RedEnemy>();
    public ArrayList<YellowEnemy> yeArray = new ArrayList<YellowEnemy>();
    public PApplet app;

    PImage[] leftR;
    PImage[] rightR;
    PImage[] upR;
    PImage[] downR;
    PImage[] leftY;
    PImage[] rightY;
    PImage[] upY;
    PImage[] downY;
 
/**
 * Constructor for Map.
 * @param timersList list of all timers
 * @param pathList list of all paths
 * @param lives number of initial lives
 * @param broken broken wall image
 * @param empty empty wall image
 * @param goal goal image
 * @param wall solid wall image
 * @param app PApplet app instance
 * @param leftR left facing red enemy images
 * @param rightR right facing red enemy images
 * @param upR up facing red enemy images
 * @param downR down facing red enemy images
 * @param leftY left facing red enemy images
 * @param rightY right facing red enemy images
 * @param downY down facing red enemy images
 * @param upY up facing red enemy images
 */
    
    public Map(ArrayList<Integer> timersList, ArrayList<String> pathList, int lives, PImage broken, PImage empty, PImage goal, PImage wall, PApplet app, 
    PImage[] leftR, PImage[] rightR, PImage[] upR, PImage[] downR, PImage[] leftY, PImage[] rightY, PImage[] upY, PImage[] downY) {
        this.pathList = pathList;
        this.lives = lives;
        this.timersList = timersList;
        this.broken = broken;
        this.empty = empty;
        this.goal = goal;
        this.wall = wall;
        this.app = app;

        this.leftR = leftR;
        this.rightR = rightR;
        this.upR = upR;
        this.downR = downR;

        this.leftY = leftY;
        this.rightY = rightY;
        this.upY = upY;
        this.downY = downY;
        this.maxLevel = this.pathList.size();

    }
/**
 * Reads in specific path, timer for every level
 */
    private void configRead() {
        this.path = this.pathList.get(this.level - 1);
        this.timer = this.timersList.get(this.level - 1);
    }
    
/**
 * Timer decreases every second
 */
    public void tick() {
        this.timer--;
    }
/**
 * Resets map level
 */
    public void reset() {
        this.setupMap();
    }
    
/**
 * Increments the map level
 */
    public void incLevel() {
        this.level += 1;
        if (this.level <= this.maxLevel)
            this.setupMap();
    }
    
/**
 * Setup map locations and instantiates red and yellow enemys
 */
    
    public void setupMap() {

        map = new char[13][15];
    
        this.configRead();

        if (this.reArray.size() > 0){
            this.reArray.clear();
        }

        if (this.yeArray.size() > 0){
            this.yeArray.clear();
        }
    
        File text = new File(path);
        try {
            Scanner scnr = new Scanner(text);
            while(scnr.hasNextLine()){
                for (int i = 0; i < 13; i++) {
                    String line = scnr.nextLine();
                    for (int j = 0; j < 15; j++) {
                        map[i][j] = line.charAt(j);
                        
                        if (map[i][j] == 'P'){
                            this.pxCoOrd = 32*j;
                            this.pyCoOrd = 32*i+48;
                        } else if (map[i][j] == 'G') {
                            this.gxCoOrd = 32*j;
                            this.gyCoOrd = 32*i+64;
                        } else if (map[i][j] == 'R') {
                            this.reArray.add(new RedEnemy(32*j, 32*i + 48));
                        } else if (map[i][j] == 'Y') {
                            this.yeArray.add(new YellowEnemy(32*j, 32*i + 48));
                        }
                    }
                }
            }
            scnr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (RedEnemy re : this.getreArray()) {
            re.setSprites(leftR, rightR, upR, downR);
        }
        for (YellowEnemy ye : this.getyeArray()) {
            ye.setSprites(leftY, rightY, upY, downY);
        }
    }
/**
 * Draws all the walls, empty and borken tiles on the map
 * @param app PApplet required for drawing
 */
    public void draw(PApplet app) {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 15; j++) {
                PImage image = empty;
                
                if (map[i][j] == 'W') {
                    image = wall;
                } else if (map[i][j] == 'B') {
                    image = broken;
                } else if (map[i][j] == 'G'){
                    image = goal;
                }
                app.image(image, 32*j, 32*i+64);
            }
        }
    
    }
/**
 * Gets the red enemy array
 * @return List of all red enemy's
 */
    public ArrayList<RedEnemy> getreArray() {
        return this.reArray;
    }
/**
 * Gets the yellow enemy array
 * @return List of all yellow enemy's
 */
    public ArrayList<YellowEnemy> getyeArray() {
        return this.yeArray;
    }   
/**
 * Gets bombguy's x co-ordinates
 * @return x co-ordinate of player
 */
    public int getxPCoOrdinates() {
        return this.pxCoOrd;
    }
/**
 * Gets bombguy's y co-ordinates
 * @return y co-ordinate of player
 */
    public int getyPCoOrdinates() {
        return this.pyCoOrd;
    }
/**
 * Gets goal's x co-ordinates
 * @return x co-ordinate of goal
 */
    public int getxGCoOrdinates() {
        return this.gxCoOrd;
    }
/**
 * Gets goal's y co-ordinates
 * @return y co-ordinate of goal
 */
    public int getyGCoOrdinates() {
        return this.gyCoOrd;
    }
/**
 * Gets map character array
 * @return array of map
 */
    public char[][] getMap() {
        return this.map;
    }
/**
 * Sets an index inside map array to specific character
 * @param x x co-ordinate to set
 * @param y y co-ordiante to set
 * @param c character to set
 */
    public void setMap(int x, int y, char c){
        this.map[x][y] = c;
    }
/**
 * Gets current level
 * @return current level 
 */
    public int getLevel() {
        return this.level;
    }
/**
 * Gets maximum level
 * @return maximum level
 */
    public int getmaxLevel() {
        return this.maxLevel;
    }
/**
 * Gets current time
 * @return current time
 */
    public int getTimer() {
        return this.timer;
    }

/**
 * Gets the current lives remaning
 * @return current lives remaining
 */
    public int getLives() {
        return this.lives;
    }
/**
 * Sets current lives to a specific number
 * @param l lives to set
 */
    public void setLives(int l) {
        this.lives = l;
    }
}