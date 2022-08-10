package demolition;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

public class Bomb {
  
    PImage[] bombIMG;
    PImage[] explosionIMG;
    int xBomb;
    int yBomb;
    int i = 0;
    int time;
    boolean explode = false;
    ArrayList<String> explodeXY = new ArrayList<String>();

    public Bomb(PImage[] bombIMG, PImage[] explosionIMG, int x, int y, int time) {
        this.bombIMG = bombIMG;
        this.explosionIMG = explosionIMG;
        this.xBomb = x;
        this.yBomb = y;
        this.time = time;
    }
/**
 * Sets index for image
 * @param i index of the current frame image is displaying
 */
    public void setI(int i){
        this.i = i;
    }
/**
 * Gets index for image
 * @return current i index of the image
 */
    public int getI(){
        return this.i;
    }
/**
 * Gets X co ordinate for bomb
 * @return current x co-ordinate of the bomb
 */
    public int getX(){
        return this.xBomb;
    }
/**
 * Gets Y co ordinate for bomb
 * @return current y co-ordinate of the bomb
 */
    public int getY(){
        return this.yBomb;
    }
/**
 * Sets time for bomb
 * @param time time to set to
 */
    public void setTime(int time){
        this.time = time;
    }
/**
 * Gets time for bomb
 * @return time current time
 */
    public int getTime(){
        return this.time;
    }
/**
 * Sets bomb to be exploded
 */
    public void setExploded(){
        this.explode = true;
    }
/**
 * Gets whether bomb has exploded or not
 * @return boolean of whether the bomb has exploded
 */
    public boolean getExploded(){
        return this.explode;
    }
/**
 * Gets all possible XY exploding co ordinates
 * @return List of all possible x,y co-ordinates for that bomb
 */
    public ArrayList<String> getexplodeXY(){
        return this.explodeXY;
    }

/**
 * Draws bomb image
 * @param app PApplet app required to draw
 */
    public void draw(PApplet app) {
        app.image(this.bombIMG[this.getI()], this.getX(), this.getY());
    }
/**
 * Draws the explosion images
 * @param app PApplet required to draw
 */
    public void explodeDraw(PApplet app) {
        
        PImage img = explosionIMG[0];
        app.image(img , this.getX(), this.getY());//center explode

        boolean previousExplode = false;
        for (String i : explodeXY) {

            String[] arrOfStr = i.split(",", 5);

            float x = Float.parseFloat(arrOfStr[0]);
            float y = Float.parseFloat(arrOfStr[1]);

            if (y - this.getY() != 0 ){
                img = explosionIMG[6];
            } else {
                img = explosionIMG[5];
            }

                
            app.image(img, x, y);
            
        }
    }
/**
 * Explosion logic, by checking every possible co ordinate of explosion and determining if explosion can occur.
 * @param map Map required to access locations of the map
 */
    public void explode(Map map) {
        ArrayList<String> possibleXY = new ArrayList<String>();

        possibleXY.add(String.valueOf(this.getX())  + "," + String.valueOf(this.getY() - 32)); // above 1
        possibleXY.add(String.valueOf(this.getX())  + "," + String.valueOf(this.getY() - 64)); // 2
        

        possibleXY.add(String.valueOf(this.getX())  + "," + String.valueOf(this.getY() + 32)); // below 3
        possibleXY.add(String.valueOf(this.getX())  + "," + String.valueOf(this.getY() + 64)); // 4
        
        possibleXY.add(String.valueOf(this.getX() - 32)  + "," + String.valueOf(this.getY())); // left 5
        possibleXY.add(String.valueOf(this.getX() - 64)  + "," + String.valueOf(this.getY())); // 6
        

        possibleXY.add(String.valueOf(this.getX() + 32)  + "," + String.valueOf(this.getY())); // right 7
        possibleXY.add(String.valueOf(this.getX() + 64)  + "," + String.valueOf(this.getY())); // 8

        int counter = 0;
        boolean previousExplode = false;
        for (String i : possibleXY) {
            counter++;
            boolean explode = false;


            char[][] mapArray = new char[13][15];
            mapArray = map.getMap();
            String[] arrOfStr = i.split(",", 5);

            if (previousExplode || counter == 1 || counter == 3 || counter == 5 || counter == 7) { 

                int possibleX = (Integer.parseInt(arrOfStr[1]) - 64) / 32;
                int possibleY = Integer.parseInt(arrOfStr[0]) / 32;

                if (mapArray[possibleX][possibleY] == ' ' || mapArray[possibleX][possibleY] == 'G'|| mapArray[possibleX][possibleY] == 'R' || mapArray[possibleX][possibleY] == 'Y' || mapArray[possibleX][possibleY] == 'P') {
                    previousExplode = true;
                    explodeXY.add(i);
                } else if (mapArray[possibleX][possibleY] == 'W') {
                    previousExplode  = false;
                } else if (mapArray[possibleX][possibleY] == 'B') {
                    previousExplode = false;
                    explodeXY.add(i);
                    map.setMap(possibleX, possibleY, ' ');
                }
            }
        }

    }
}