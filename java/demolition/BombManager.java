package demolition;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
 
public class BombManager {
 
    PImage[] bombIMG;
    PImage[] explosionIMG;
    PApplet app;
    Map map;
    BombGuy bg;
    ArrayList<Bomb> bombList = new ArrayList<Bomb>();
    int i = 0;
/**
 * Constructor for BombManager
 * @param map current map instance
 * @param app PApplet app instance
 * @param bg current bombguy instance
 */
    public BombManager(Map map, PApplet app, BombGuy bg) {
        this.map = map;
        this.bg = bg;
        this.app = app;
    }
/**
 * Sets sprites for bomb and explosion sprites.
 * @param bombIMG images of bombs before explosion
 * @param explosionIMG images of explosions
 */
    public void setSprites(PImage[] bombIMG, PImage[] explosionIMG) {
        this.bombIMG = bombIMG;
        this.explosionIMG = explosionIMG;
    }
/**
 * Returns list of all the current bombs
 * @return current list of all bombs
 */
    public ArrayList<Bomb> getbombList() {
        return this.bombList;
    }
    
/**
 * Loops through all current bombs and decides whether to explode them or delete them.
 * @param currentTime the current time in terms of milliseconds
 */
    public void tick(int currentTime) {
    
        ArrayList<Bomb> toDelete = new ArrayList<Bomb>();
    
        for (Bomb bomb : bombList) {
            if (bomb.getExploded()) {
                if (currentTime - bomb.getTime() >= 500) {
                    toDelete.add(bomb);
                }
    
            } else {
                bomb.setI((currentTime - bomb.getTime() ) / 250);
                if (bomb.getI() == 8) {
                    bomb.setTime(currentTime);
                    bomb.setExploded();
                    bomb.explode(this.map);
                }
            }
    
        }
    
        for (int i = 0; i < toDelete.size(); i++){
            bombList.remove(toDelete.get(i));
        }
    
    }
    
/**
 * Draws each bomb depending on if its exploded or not
 */
    public void draw() {
        for (Bomb bomb : bombList) {
    
            if (bomb.getExploded()) {
                bomb.explodeDraw(this.app);
            } else {
                bomb.draw(this.app);
            }
        }
    }
/**
 * When user presses space
 * @param bg current bombguy instance
 * @param time current time
 */
    public void pressSpace(BombGuy bg, int time) {
        bombList.add(new Bomb(this.bombIMG, this.explosionIMG, bg.getX(), bg.getY() + 16, time));
    }
 
}