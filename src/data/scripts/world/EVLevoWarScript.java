package data.scripts.world;

import com.fs.starfarer.api.EveryFrameScript;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * RETIRED. This used to send Confederate and Rebel patrols through Levo; the user removed that
 * feature. The class is kept only so saves made while it was active still load: those saves contain
 * an instance of it, so the fields keep their old names. It does nothing and reports itself done,
 * so the game drops it from the save. It can be deleted once no such saves are in use.
 */
public class EVLevoWarScript implements EveryFrameScript {

    private final List confedFleets = new ArrayList();
    private final List rebelFleets = new ArrayList();
    private float daysToNext = 0f;
    private final Random random = new Random();

    public boolean isDone() { return true; }
    public boolean runWhilePaused() { return false; }
    public void advance(float amount) { }
}
