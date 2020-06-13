
public class Vertex {

    // A unique id for the vertex 
    private int ID;

    // Color for the vertex
    private String color;

    // Base default color
    public final String BASE_COLOR = "White";

    public Vertex(int ID){
        this.ID = ID;
        this.color = BASE_COLOR;
    }

    public int getID(){
        return this.ID;
    }

    public void setID(int id){
        this.ID = id;
    }

    public void setColor(String color){
        this.color = color;
    }

    public String getColor(){
        return this.color;
    }

    @Override
    public String toString(){
        return "Vertex ID: " + this.ID;
    }

    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        } 

        if (object == null){
            return false;
        }

    	Vertex other = (Vertex) object;
		if (ID != other.ID) {
			return false;
		}
		if (color == null) {
			if (other.color != null) {
				return false;
			}
		} else if (!color.equals(other.color)) {
			return false;
		}
		return true;
    }
}
