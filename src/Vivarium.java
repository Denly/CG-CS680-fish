

import javax.media.opengl.*;
import com.jogamp.opengl.util.*;
import java.util.*;

public class Vivarium
{
  private Tank tank;
  private ArrayList<Fish> fishs;
  private Food food;
  private int NUM_OF_FISH = 6;
  private float FOOD_SIZE = 0.2f;
  
  public Vivarium()
  {
    tank = new Tank( 4.0f, 4.0f, 4.0f );
    fishs = new ArrayList<Fish>();
    food = new Food( FOOD_SIZE );
    for(int i = 0; i < NUM_OF_FISH; i++){
    	fishs.add(new Fish());
    }
    
  }

  public void init( GL2 gl )
  {
    tank.init( gl );
    food.init( gl );
    for(Fish fish : fishs){
    	fish.init( gl );
    }
  }

  public void update( GL2 gl )
  {
    tank.update( gl );
    
    for(Fish fish : fishs){
    	if(food.eaten == false){
			food.update( gl, fish );
		}
    	for(Fish f : fishs){
    		if(f != fish)
    			f.update( gl , fish, food);
        }
    }
  }

  public void draw( GL2 gl )
  {
    tank.draw( gl );
    if(food.eaten == false)
    	food.draw( gl );
    for(Fish fish : fishs){
    	fish.draw( gl );
    }
  }
  
  public void addFood(){
	  	food.newfood();
	}
}

