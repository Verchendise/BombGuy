package demolition;

public class Enemy extends Person {
/**
 * Constructor for Enemy
 * @param x initial x co-ordinate of enemy
 * @param y initial y co-ordinate of enemy
 */
    public Enemy(int x, int y) {
        super(x, y);
    }
/**
 * Checking for bomb collision before and after moving
 * @param bm current bombmanager instance
 * @param map current map instance
 */
    public void tick(BombManager bm, Map map){
        if(!this.dead) {
            boolean t;
            bombCollisionCheck(bm.getbombList());
            t = this.move(map);
            bombCollisionCheck(bm.getbombList());
            if (t) {
                this.decision();
                this.move(map);
                bombCollisionCheck(bm.getbombList());
            }
        }
    }
/**
 * Decision made if collision with wall
 */
    protected void decision() {};
 
}