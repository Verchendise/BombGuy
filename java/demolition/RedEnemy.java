package demolition;

import java.util.Random;
 
public class RedEnemy extends Enemy {

/**
 * Constructor for RedEnemy. Default direction set to right. Sets x and y co-ordinates.
 * @param x initial x co-ordinate
 * @param y initial y co-ordinate
 */
    public RedEnemy(int x, int y){
        super(x,y);
        direc = direction.right;
    }
/**
 * Decision algorithm for if red enemy collides to wall
 */ 
    public void decision() {
        int rnd = new Random().nextInt(4);
        if (rnd == 0) {
            direc = direction.left;
        } else if (rnd == 1) {
            direc = direction.right;
        } else if (rnd == 2) {
            direc = direction.up;
        } else if (rnd == 3) {
            direc = direction.down;
        }
    }

 
}