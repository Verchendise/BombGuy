package demolition;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

/**
 * Features of person characters
 */

interface features {
    enum direction {
        left, right, up, down
    }
    public boolean move(Map map);
    boolean wallCollisionCheck(Map map);
    void dead();
    int getX();
    int getY();
    void setSprites(PImage[] left, PImage[] right, PImage[] up, PImage[] down);
}

public class Person implements features{
    private int index = 0;
    protected boolean dead;
    protected int x;
    protected int y;
    protected PImage[] directionIMG;
    protected direction direc;
    protected PImage[] leftIMG = new PImage[4];
    protected PImage[] rightIMG = new PImage[4];
    protected PImage[] downIMG = new PImage[4];
    protected PImage[] upIMG = new PImage[4];
/**
 * Constructor for Person. Defaults direction to right. Sets x and y co-ordinates. Sets dead to false.
 * @param x initial x co-ordinate
 * @param y initial y co-ordinate
 */
    public Person(int x, int y) {
        this.x = x;
        this.y = y;
        this.dead = false;
        this.direc = direction.right;
        this.directionIMG = this.rightIMG;
    }
/**
 * Set Sprites for left, right, up and down movement images. Defaults direction image to right.
 * @param left Images for left direction
 * @param right Images for right direction
 * @param up Images for up direction
 * @param down Images for down direction
 */
    public void setSprites(PImage[] left, PImage[] right, PImage[] up, PImage[] down) {
        this.leftIMG = left;
        this.rightIMG = right;
        this.downIMG = down;
        this.upIMG = up;
        this.directionIMG = right;
    }
/**
 * Movement of person. Changes xy co-ordinates according to direction and changes direction image. Also checks for wall collision and returns true if collided.
 * @param map current map instance
 * @return boolean depending on if person has collided with a wall or not.
 */
    public boolean move(Map map){
        if (!this.dead) {
            if (direc == direction.left) {
                this.directionIMG = leftIMG;
                this.x -= 32;
                if (this.wallCollisionCheck(map)){
                    this.x += 32;
                    return true;
                }
            } else if (direc == direction.right) {
                this.directionIMG = rightIMG;
                this.x += 32;
                if (this.wallCollisionCheck(map)){
                    this.x -= 32;
                    return true;
                }
            } else if (direc == direction.up) {
                this.directionIMG = upIMG;
                this.y -= 32;
                if (this.wallCollisionCheck(map)){
                    this.y += 32;
                    return true;
                }
            } else if (direc == direction.down) {
                this.directionIMG = downIMG;
                this.y += 32;
                if (this.wallCollisionCheck(map)){
                    this.y -= 32;
                    return true;
                }
            }
        }
        
        return false;
    }
/**
 * Wall collision checking algorithm
 * @param map current map instance
 * @return whether person is wanting to collide with a wall or not
 */
    public boolean wallCollisionCheck(Map map) {
        char[][] mapArray = new char[13][15];
        mapArray = map.getMap();
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 15; j++) {
    
                if (mapArray[i][j] == 'W' || mapArray[i][j] == 'B') {
                    int inv_x = 32*j;
                    int inv_y = 32*i + 48;
                    if (this.x == inv_x && this.y == inv_y) {
                        return true;
                    }
                }   
    
            }
        }
        return false;
    }

/**
 * Draws each image for the person's direction.
 * @param app PApplet required to draw
 */
    public void draw(PApplet app) {
        if (!this.dead) {
            if (this.x >= 0 && this.y >= 0){
                app.image(this.directionIMG[index], this.x, this.y);
                index++;
                if (index==4){
                    index=0;
                }
            }
     
        }            
    }
/**
 * Checks collision with bomb 
 * @param bombList a list of bombs
 * @return whether person has collided with a bomb or not
 */
    protected boolean bombCollisionCheck(ArrayList<Bomb> bombList) {
        for (Bomb bomb : bombList) {
            if (bomb.getExploded()) {
                if (this.getX() == bomb.getX() && this.getY() + 16 == bomb.getY()) {
                    this.dead();
                    return true;
                } 
    
                for (String i : bomb.getexplodeXY()) {
                    String[] arrOfStr = i.split(",", 5);
                    float x = Float.parseFloat(arrOfStr[0]);
                    float y = Float.parseFloat(arrOfStr[1]);
    
                    if (this.getX() == x && this.getY() + 16 == y) {
                        this.dead();
                        return true;
                    }
                }
            }

        }     
        return false;
    }

/**
 * Sets person to dead. 
 */
    public void dead() {
        this.dead = true;
    }
/**
 * Gets the current X co-ordinate of person
 * @return current x co-ordinate 
 */
    public int getX(){
        return this.x;
    }
/**
 * Gets the current Y co-ordinate of person
 * @return current y co-ordinate 
 */  
    public int getY(){
        return this.y;
    }
}