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

    /**
     * Stack size (default 10), current count of stack position
     * and the list containing the app states.
     */
    public int stackSize;
    public int stackCount;
    private final List<Image> stateList;

    /**
     * Initialize a {@code Stack} object with the provided {@code stackSize}
     * and the counter at -1.
     *
     * @param stackSize the size of the {@code Stack} to be initialized.
     * @throws IllegalArgumentException if {@code stackSize} is greater than 20
     *                                  or less than 2.
     */
    public Stack(int stackSize) {
        if (stackSize < 2 || stackSize > 20) {
            throw new IllegalArgumentException();
        }
        this.stackSize = stackSize;
        stackCount = -1;
        stateList = new ArrayList<>(stackSize);
    }

    /**
     * Push currently displayed image at the top of the {@code Stack}.
     * Remove images from the bottom of stack once counter exceeds {@code stackSize}.
     *
     * @param image the image that is being pushed to the top of the {@code Stack}.
     */
    public void push(Image image) {
        if (stackCount == stackSize - 1) {
            stateList.remove(0);
            stackCount--;
        }
        stateList.add(image);
        stackCount++;
    }

    /**
     * @return the image from the {@code Stack} when undo is pressed.
     */
    public Image popUndo() {
        if (stackCount >= 0) {
            stackCount--;
        }
        return stateList.get(stackCount);
    }

    /**
     * @return the image from the {@code Stack} when redo is pressed.
     */
    public Image popRedo() {
        if (stackCount < stateList.size() - 1) {
            stackCount++;
        }
        return stateList.get(stackCount);
    }

    /**
     * Clear the {@code Stack} from the current {@code stackCount} to the top of the
     * {@code Stack}.
     */
    public void clear() {
        if (stackCount < stateList.size()) {
            stateList.subList(stackCount + 1, stateList.size()).clear();
        }
    }
}
