package demolition;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;
 
public class BombGuy extends Person {

    private boolean d;

/**
 * Constuctor setting x and y co-ordinates, also defaults direction of bombguy to look down
 * @param x initial x co-ordinate of bombguy
 * @param y initial y co-ordinate of bombguy
 */

    public BombGuy(int x, int y) {
        super(x, y);
        direc = direction.down;
    }
/**
 * Set sprites for bombguy
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
        this.directionIMG = down;
    }
/**
 * Checks for bomb collision and enemy collision before and after moving
 * @param bm current bombmanager
 * @param map current map 
 */
    public void tick(BombManager bm, Map map) {
        this.bombCollisionCheck(bm.getbombList(), map);
        this.enemyCollisionCheck(map);
        if (d) {
            this.move(map);
            this.bombCollisionCheck(bm.getbombList(), map);
            this.enemyCollisionCheck(map);
            d = false;
        }
    }
/**
 * Goal collision checking
 * @param map current map
 * @return boolean of whether bombguy has arrived on the goal
 */
    public boolean goalCollisionCheck(Map map) {
        if (this.x == map.getxGCoOrdinates() && (this.y + 16) == map.getyGCoOrdinates()){
            map.incLevel();
            this.bgReset(map);
            return true;
        } else {
            return false;
        }
    }
 
/**
 * Enemy collision checking
 * @param map current map 
 * @return boolean showing if bombguy has collided with enemy or not.
 */
    public boolean enemyCollisionCheck(Map map) {

        for (RedEnemy re : map.getreArray()) {
            if (re.getX() == this.x && re.getY() == this.y) {
                map.setLives(map.getLives() - 1);
                map.reset();
                this.bgReset(map);
                return true;
            }
        }

        for (YellowEnemy ye : map.getyeArray()) {
            if (ye.getX() == this.x && ye.getY() == this.y) {
                map.setLives(map.getLives() - 1);
                map.reset();
                this.bgReset(map); 
                return true;
            }
        }
        return false;

    }

/**
 * Checking if bombguy collides with a bomb
 * @param bombList List of current bombs
 * @param map current map
 * @return boolean checking if bombguy has collided with any bombs
 */

    public boolean bombCollisionCheck(ArrayList<Bomb> bombList, Map map) {
        for (Bomb bomb : bombList) {
            if (bomb.getExploded()){
                if (this.getX() == bomb.getX() && this.getY() + 16 == bomb.getY()) {
                    map.setLives(map.getLives() - 1);
                    if (map.getLives() <= 0 ) {
                        this.dead();
                        return true;
                    } else {
                        map.reset();
                        bombList.clear();
                        this.bgReset(map);
                        return true;
                    }
                    
                } 

                for (String i : bomb.getexplodeXY()) {
                    String[] arrOfStr = i.split(",", 5);
                    float x = Float.parseFloat(arrOfStr[0]);
                    float y = Float.parseFloat(arrOfStr[1]);

                    if (this.getX() == x && this.getY() + 16 == y) {
                        map.setLives(map.getLives() - 1);
                        if (map.getLives() <= 0 ) {
                            this.dead();
                        } else {
                            map.reset();
                            bombList.clear();
                            this.bgReset(map);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

/**
 * Resets all of bombguys x and y coordinates and also defaults direction back to down.
 * @param map current map
 */
    public void bgReset(Map map) {
        this.x = map.getxPCoOrdinates();
        this.y = map.getyPCoOrdinates();
        this.direc = direction.down;
        
    }

/**
 * When user presses down
 */
    public void pressDown() {
        direc = direction.down;
        d = true;
    }
/**
 * When user presses up
 */
    public void pressUp() {
        direc = direction.up;
        d = true; 
    }
/**
 * When user presses left
 */
    public void pressLeft() {
        direc = direction.left;
        d = true; 
    }
/**
 * When user presses right
 */
    public void pressRight() {
        direc = direction.right;
        d = true;
    }
 
 
 
 
}
