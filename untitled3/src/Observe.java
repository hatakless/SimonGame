import java.util.Vector;
public interface Observe
{
    public abstract void nextColorSequence(Vector<Flash> sequenceColors);
    public abstract void turnOver();
    public abstract void gameOver(boolean isGameOver);
    public abstract void updateScore(int score);
}