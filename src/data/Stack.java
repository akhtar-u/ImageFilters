package data;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Stack data structure to handle undo / redo events
 * by saving image states after transformations
 *
 * @author Usman Akhtar
 */
public class Stack {

    public int stackSize;
    private int stackCount;
    private List<Image> stateList;

    public Stack (int stackSize) {
        this.stackSize = stackSize;
        stackCount = 0;
        stateList = new ArrayList<Image>(10);
    }

    public void push(Image image){
        stateList.add(image);
        stackCount++;
    }


    public Image pop(){
        stackCount--;
        return stateList.remove(stackCount);
    }




}
