

import javax.media.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.gl2.GLUT;

import java.util.*;

public class Food
{
  private int teapot_object;
  private float scale;
  private float angle;
  public boolean eaten;
  public Point3D loc;

  public Food( float scale_)
  {
    scale = scale_;
    angle = 0;
    eaten = false;
    loc = new Point3D(0,0,0);
  }

  public void init( GL2 gl )
  {

    teapot_object = gl.glGenLists(1);
    gl.glNewList( teapot_object, GL2.GL_COMPILE );
	    // create the teapot triangles 
	    GLUT glut = new GLUT();
	    glut.glutSolidCube(scale); 
    gl.glEndList();

  }
  
  private boolean collision(Fish f, float r){
	  float dis = loc.dis(f.loc);
	  
	  if(dis < r)
		  return true;
	  else
		  return false;
  }

  public void update( GL gl , Fish f)
  {
    angle += 5;
    if(collision(f, 1f)){
    	eaten = true;
    	System.out.println("Food: "+ eaten);
    }else{
    	System.out.println("Not eaten~~~: ");
    }
    	
  }

  public void draw( GL2 gl )
  {
    gl.glPushMatrix();
    gl.glPushAttrib( GL2.GL_CURRENT_BIT );
    gl.glRotatef( angle, 0.0f, 1.0f, 0.0f );
    gl.glColor3f( 0.85f, 0.55f, 0.20f); // Orange
    gl.glCallList( teapot_object );
    gl.glPopAttrib();
    gl.glPopMatrix();
  }
}
