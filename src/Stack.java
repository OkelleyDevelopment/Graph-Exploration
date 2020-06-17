/*
 * A Simple implementation of a Stack
 *
 * @author Nicholas O'Kelley
 * Date: June 9, 2020
 */
public class Stack {

    // Array to hold the ID's of the graph
    private int[] array;

    // an int variable to track the elements in the stack
    private int top;

    /**
     * The constructor
     */
    public Stack(){
        this.array = new int[20];
        this.top = -1;
    }

    /**
     * A method to push vertices on to
     * the stack.
     *
     * @param vertex - the new vertex to be added
     *
     * @return none
     */
    public void push(int vertex){
        this.array[++top] = vertex;
    }

    /**
     * A method to remove an element from the top.
     *
     * @param none
     *
     * @return int - the top of stack
     */
    public int pop(){
        return this.array[top--];
    }

    /**
     * A method that allows the program to see the top element.
     *
     * @param none
     *
     * @return int - the top of the stack
     */
    public int peek(){
        return this.array[top];
    }

    /**
     * A method that returns a boolean to check if the stack is empty
     *
     * @param none
     *
     * @return boolean - true or false
     */
    public boolean isEmpty(){
        return (this.top == -1);
    }
    
    /**
     * This method resets the top variable to -1 essentially,
     * clearing the stack.
     *
     * @param none
     *
     * @return none
     */
    public void clear(){
        this.top = -1;
    }

}
