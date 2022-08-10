package demolition;

public class YellowEnemy extends Enemy {
/**
 * Constructor for YellowEnemy. Sets default direction to right. Sets x and y co-ordinates.
 * @param x initial x co-ordinate
 * @param y initial y co-ordinate
 */ 
    public YellowEnemy(int x, int y) {
        super(x, y);
        direc = direction.right;
    }
/**
 * Decision algorithm for if yellow enemy collides to wall
 */ 
    public void decision() {
        if (this.direc == direction.left) {
            direc = direction.up;
        } else if (this.direc == direction.up) {
            direc = direction.right;
        } else if (this.direc == direction.right) {
            direc = direction.down;
        } else if (this.direc == direction.down) {
            direc = direction.left;
        }
    }
 
}

