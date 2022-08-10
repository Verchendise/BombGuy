package demolition;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;

public class Manager {
    private PApplet app;
    private Map map;
/**
 * Constructor for Manager. 
 * @param app PApplet app instance
 * @param map current map instance
 */
    public Manager(PApplet app, Map map) {
        this.app = app;
        this.map = map;
    }
/**
 * Decides whether game has been won or not. Displays win background if won.
 * @return boolean value depending on if won or not
 */
    public boolean win() {
        if (map.getLevel() > map.getmaxLevel()) {
            this.app.textSize(18);
            PFont font = this.app.createFont("src/main/resources/PressStart2P-Regular.ttf", 18);
            this.app.textFont(font);
            this.app.fill(255,165,0);
            this.app.rect(0,0,480,480);
            this.app.fill(0);
            this.app.text("YOU WIN", 170, 220);
            return true;
        }
        return false;
    }
/**
 * Decides if game has been lost or not. Displays lost background if lost.
 * @return boolean value depending on if lost or not
 */
    public boolean lose() {
        if (map.getLives() <= 0 || map.getTimer() <= 0) {
            this.app.textSize(18);
            PFont font = this.app.createFont("src/main/resources/PressStart2P-Regular.ttf", 18);
            this.app.textFont(font);
            this.app.fill(255,165,0);
            this.app.rect(0,0,480,480);
            this.app.fill(0);
            this.app.text("GAME OVER", 150, 220);
            return true;
        }
        return false;
        
    }
/**
 * Displays regular timer and lives icon background. 
 * @return boolean value depending on if it game hasn't been won and hasn't been lost
 */
    public boolean display() {

        if (!win() && !lose()) {
            this.app.fill(255,165,0);
            this.app.rect(0,0,480,64);
            this.app.textSize(18);
            PFont font = this.app.createFont("src/main/resources/PressStart2P-Regular.ttf", 18);
            this.app.textFont(font);
            this.app.image(this.app.loadImage("src/main/resources/icons/clock.png"), 280, 16);
            this.app.image(this.app.loadImage("src/main/resources/icons/player.png"), 130, 16);
            this.app.fill(0);
            this.app.text(map.getLives(), 170, 40);
            this.app.text(map.getTimer(), 320, 40);
            return true;
        }
        return false;
        
    }
}