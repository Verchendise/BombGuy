package demolition;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
import processing.data.JSONArray;
import processing.data.JSONObject;
 

public class App extends PApplet {
 
    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;
    public static final int FPS = 60;
    private Map map;
    private BombGuy bombguy;
    private BombManager bombmanager;
    private Manager manager;
    int startTime1;
    int startTime2;
    int startTime3;
    ArrayList<Integer> timersList = new ArrayList<Integer>();
    ArrayList<String> pathList = new ArrayList<String>();
    int lives;
    ArrayList<YellowEnemy> yeArray = new ArrayList<YellowEnemy>();
    ArrayList<RedEnemy> reArray = new ArrayList<RedEnemy>();
    String configPath = "config.json";
    boolean flag = true;
/**
 * Gets the current map. Used for testing. 
 * @return current map
 */
    public Map getMap() { //used for testing
        return this.map;
    }
/** Gets the current bombguy object. Used for testing.
 *  @return current bombguy
 */
    public BombGuy getBG() { //used for testing
        return this.bombguy;
    }
/** Sets the size of the window. 
 */
    public void settings() {
        size(WIDTH, HEIGHT);
    }
/** Sets the config directory.
 * @param path path to set to
 */
    public void setConfig(String path) { //used for testing
        this.configPath = path;
    }

/** Sets up framerate, config files, loads images and setups initial map. 
 */
    public void setup() {
        frameRate(FPS);
        
        config(this.configPath);
        
        PImage[] left = {this.loadImage("src/main/resources/player/player_left1.png"), this.loadImage("src/main/resources/player/player_left2.png"), this.loadImage("src/main/resources/player/player_left3.png"), this.loadImage("src/main/resources/player/player_left4.png")};
        PImage[] right = {this.loadImage("src/main/resources/player/player_right1.png"), this.loadImage("src/main/resources/player/player_right2.png"), this.loadImage("src/main/resources/player/player_right3.png"), this.loadImage("src/main/resources/player/player_right4.png")};
        PImage[] up = {this.loadImage("src/main/resources/player/player_up1.png"), this.loadImage("src/main/resources/player/player_up2.png"), this.loadImage("src/main/resources/player/player_up3.png"), this.loadImage("src/main/resources/player/player_up4.png")};
        PImage[] down = {this.loadImage("src/main/resources/player/player1.png"), this.loadImage("src/main/resources/player/player2.png"), this.loadImage("src/main/resources/player/player3.png"), this.loadImage("src/main/resources/player/player4.png")};
        
        PImage[] leftR = {this.loadImage("src/main/resources/red_enemy/red_left1.png"), this.loadImage("src/main/resources/red_enemy/red_left2.png"), this.loadImage("src/main/resources/red_enemy/red_left3.png"), this.loadImage("src/main/resources/red_enemy/red_left4.png")};
        PImage[] rightR = {this.loadImage("src/main/resources/red_enemy/red_right1.png"), this.loadImage("src/main/resources/red_enemy/red_right2.png"), this.loadImage("src/main/resources/red_enemy/red_right3.png"), this.loadImage("src/main/resources/red_enemy/red_right4.png")};
        PImage[] upR = {this.loadImage("src/main/resources/red_enemy/red_up1.png"), this.loadImage("src/main/resources/red_enemy/red_up2.png"), this.loadImage("src/main/resources/red_enemy/red_up3.png"), this.loadImage("src/main/resources/red_enemy/red_up4.png")};
        PImage[] downR = {this.loadImage("src/main/resources/red_enemy/red_down1.png"), this.loadImage("src/main/resources/red_enemy/red_down2.png"), this.loadImage("src/main/resources/red_enemy/red_down3.png"), this.loadImage("src/main/resources/red_enemy/red_down4.png")};
    
        PImage[] leftY = {this.loadImage("src/main/resources/yellow_enemy/yellow_left1.png"), this.loadImage("src/main/resources/yellow_enemy/yellow_left2.png"), this.loadImage("src/main/resources/yellow_enemy/yellow_left3.png"), this.loadImage("src/main/resources/yellow_enemy/yellow_left4.png")};
        PImage[] rightY = {this.loadImage("src/main/resources/yellow_enemy/yellow_right1.png"), this.loadImage("src/main/resources/yellow_enemy/yellow_right2.png"), this.loadImage("src/main/resources/yellow_enemy/yellow_right3.png"), this.loadImage("src/main/resources/yellow_enemy/yellow_right4.png")};
        PImage[] upY = {this.loadImage("src/main/resources/yellow_enemy/yellow_up1.png"), this.loadImage("src/main/resources/yellow_enemy/yellow_up2.png"), this.loadImage("src/main/resources/yellow_enemy/yellow_up3.png"), this.loadImage("src/main/resources/yellow_enemy/yellow_up4.png")};
        PImage[] downY = {this.loadImage("src/main/resources/yellow_enemy/yellow_down1.png"), this.loadImage("src/main/resources/yellow_enemy/yellow_down2.png"), this.loadImage("src/main/resources/yellow_enemy/yellow_down3.png"), this.loadImage("src/main/resources/yellow_enemy/yellow_down4.png")};
    
        PImage[] bombIMG = {this.loadImage("src/main/resources/bomb/bomb.png"), this.loadImage("src/main/resources/bomb/bomb1.png"), this.loadImage("src/main/resources/bomb/bomb2.png"), this.loadImage("src/main/resources/bomb/bomb3.png"), this.loadImage("src/main/resources/bomb/bomb4.png"), this.loadImage("src/main/resources/bomb/bomb5.png"), this.loadImage("src/main/resources/bomb/bomb6.png"), this.loadImage("src/main/resources/bomb/bomb7.png"), this.loadImage("src/main/resources/bomb/bomb8.png")};
    
        PImage[] explosionIMG = {this.loadImage("src/main/resources/explosion/centre.png"), this.loadImage("src/main/resources/explosion/end_bottom.png"), this.loadImage("src/main/resources/explosion/end_left.png"), this.loadImage("src/main/resources/explosion/end_right.png"), this.loadImage("src/main/resources/explosion/end_top.png"), this.loadImage("src/main/resources/explosion/horizontal.png"), this.loadImage("src/main/resources/explosion/vertical.png")};
        
        PImage broken = this.loadImage("src/main/resources/broken/broken.png");
        PImage empty = this.loadImage("src/main/resources/empty/empty.png");
        PImage goal = this.loadImage("src/main/resources/goal/goal.png");
        PImage solid = this.loadImage("src/main/resources/wall/solid.png");
    
        

        map = new Map(this.timersList, this.pathList, this.lives, broken, empty, goal, solid, this, leftR, rightR, upR, downR, leftY, rightY, upY, downY);
        
        this.map.setupMap();
        this.map.draw(this);

        int startTime1 = millis();
        int startTime2 = millis();
    
        this.bombguy = new BombGuy(this.map.getxPCoOrdinates(), this.map.getyPCoOrdinates());
        this.bombguy.setSprites(left,right,up,down);

        this.bombmanager =  new BombManager(this.map, this, this.bombguy);
        this.bombmanager.setSprites(bombIMG, explosionIMG);

        this.manager = new Manager(this, this.map);
    }

/** Main loop of function. That checks for win/lose condition, and calculates current time to update different ticks for objects. 
 */
    public void draw() {

        if (this.manager.display()) {
            int deltaTime1 = millis() - startTime1;
            int deltaTime2 = millis() - startTime2;

            if (deltaTime1 >= 1000) {
                this.map.tick();
                for (RedEnemy re : this.map.reArray){
                    re.tick(this.bombmanager,this.map);
                }
                for (YellowEnemy ye : this.map.yeArray){
                    ye.tick(this.bombmanager,this.map);
                }
                startTime1 = millis();
            }
            this.bombmanager.tick(millis());
            if (deltaTime2 >= 200) {
                this.map.draw(this);
                this.bombmanager.draw();
                this.bombguy.goalCollisionCheck(this.map);
                this.bombguy.tick(this.bombmanager, this.map);
                this.bombguy.draw(this);
                for (RedEnemy re : this.map.reArray){
                    re.bombCollisionCheck(this.bombmanager.getbombList());
                    re.draw(this);
                }
                for (YellowEnemy ye : this.map.yeArray) {
                    ye.bombCollisionCheck(this.bombmanager.getbombList());
                    ye.draw(this);
                }
                startTime2 = millis();
            }
        }

        
    
        
    
    }
/** keyReleased function to detect when a key is released.
 */
    public void keyReleased() {
        flag = true;
    }

/** keyPressed function for detecting a key press and assinging what actions to do when a specific key is pressed.
 */
    public void keyPressed() {
        if (flag) {
            if (this.keyCode == 37) {
                this.bombguy.pressLeft();
            } else if (this.keyCode == 38) {
                this.bombguy.pressUp();
            } else if (this.keyCode == 39) {
                this.bombguy.pressRight();
            } else if (this.keyCode == 40) {
                this.bombguy.pressDown();
            } else if (this.keyCode == 32) {
                this.bombmanager.pressSpace(this.bombguy, millis());
            }
            flag = false;
        }
        
    
    }
/** Loads in paths and timers from json config file. Appends all paths and timers to lists to be passed through to the map.
 * @param configPath config path to be used to load timers, paths and lives from. 
 */
    public void config(String configPath) {
        JSONObject values;
        values = loadJSONObject(configPath);
        this.lives = values.getInt("lives");
        JSONArray s = values.getJSONArray("levels");
    
        for (int i = 0; i < s.size(); i++) {
            JSONObject x = s.getJSONObject(i);
            int timer = x.getInt("time");
            String path = x.getString("path");
            timersList.add(timer);
            pathList.add(path);
        }
        
    }
    
    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
