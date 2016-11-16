

import javax.media.opengl.*;
import com.jogamp.opengl.util.*;
import java.util.*;

public class Vivarium
{
  private Tank tank;
  //private Fish fishs[];
  private ArrayList<Fish> fishs;
  public Vivarium()
  {
    tank = new Tank( 4.0f, 4.0f, 4.0f );
    Fish f1 = new Fish();
    Fish f2 = new Fish();
    //fishs = {f1};
    fishs = new ArrayList<Fish>();
    //
    for(int i = 0; i < 3; i++){
    	fishs.add(new Fish());
    }
    
  }

  public void init( GL2 gl )
  {
    tank.init( gl );
    for(Fish fish : fishs){
    	fish.init( gl );
    }
    
  }

  public void update( GL2 gl )
  {
    tank.update( gl );
    for(Fish fish : fishs){
    	for(Fish f : fishs){
        	f.update( gl , fish);
        }
    }
  }

  public void draw( GL2 gl )
  {
    tank.draw( gl );
    for(Fish fish : fishs){
    	fish.draw( gl );
    }
  }
}
