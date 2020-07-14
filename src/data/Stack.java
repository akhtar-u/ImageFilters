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
    public int stackCount;
    private List<Image> stateList;

    public Stack (int stackSize) {
        this.stackSize = stackSize;
        stackCount = -1;
        stateList = new ArrayList<Image>(10);
    }

    public void push(Image image){
        if(stackCount == stackSize - 1){
            stateList.remove(0);
            stackCount--;
        }
        stateList.add(image);
        stackCount++;
    }

    public Image popUndo(){
        stackCount--;
        return stateList.get(stackCount);
    }

    public Image popRedo(){
        stackCount++;
        return stateList.get(stackCount);
    }




}
