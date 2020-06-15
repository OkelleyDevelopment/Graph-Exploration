/*
 * A Simple implementation of a Stack
 *
 * @author Nicholas O'Kelley
 * Date: June 9, 2020
 */
public class Stack {

    // Array to hold the ID's of the graph
    private int[] array;

    private int top;

    public Stack(){
        this.array = new int[20];
        this.top = -1;
    }

    public void push(int vertex){
        this.array[++top] = vertex;
    }

    public int pop(){
        return this.array[top--];
    }

    public int peek(){
        return this.array[top];
    }

    public boolean isEmpty(){
        return (this.top == -1);
    }
    
    public void clear(){
        this.top = -1;
    }

}
