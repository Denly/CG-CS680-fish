

import javax.media.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.gl2.GLUT;

import java.util.*;

public class Fish
{
  private int body;
  private int tail1;
  private int tail2;
  private float angle;
  private Point3D axis;
  private float angleTail;
  private float directionTail;
  public Point3D loc;
  private Point3D dir;
  private Point3D front = new Point3D(0, 0, -1);
  private int BOUND = 2;
  private float SPEED = 0.002f;
  

  public Fish( )
  {
    angle = 0;
    angleTail = 0;
    directionTail = 1;
    loc = Point3D.randomP(-2, 2);
    dir = next_dir();
  }

  public void init( GL2 gl )
  {

	GLUT glut = new GLUT();
	
	/* body */
    body = gl.glGenLists(1);
    gl.glNewList( body, GL2.GL_COMPILE );
    	gl.glPushMatrix();
    		gl.glTranslated(0, 0, 0.2*1.3f);		
    		gl.glScalef(0.3f, 0.9f, 1.3f);	
	    	glut.glutSolidSphere(0.2, 36, 18); 
	    gl.glPopMatrix();
    gl.glEndList();
    
    /* tail1 */
    tail1 = gl.glGenLists(1);
    gl.glNewList( tail1, GL2.GL_COMPILE );
	    gl.glPushMatrix();
	        gl.glTranslated(0, 0, -0.2*0.3f);
			gl.glScalef(0.2f, 0.5f, 0.3f);		
			glut.glutSolidSphere(0.2, 36, 18); 
		gl.glPopMatrix();
    gl.glEndList();
    
    /* tail2 */
    tail2 = gl.glGenLists(1);
    gl.glNewList( tail2, GL2.GL_COMPILE );
	    gl.glPushMatrix();
	        gl.glTranslated(0, 0, -0.2*0.3f*3);
			gl.glScalef(0.2f, 0.5f, 0.3f);		
			glut.glutSolidSphere(0.2, 36, 18); 
		gl.glPopMatrix();
    gl.glEndList();


    
  }

  public void update( GL2 gl , Fish f)
  {
    //angle += 5;
    
    angleTail += 0.75*directionTail;
	  if(angleTail>20 || angleTail<-20)
		  directionTail=-directionTail;
	  
	loc = loc.plus(dir);
	int hit = hitWall(loc);
	if(hit != 0){
		dir = next_dir(hit);
	}
	
	if(collision(f, 0.5f)){
		System.out.println("collision");
		next_dir();
	}

  }
  
  private boolean collision(Fish f, float r){
	  float dis = loc.dis(f.loc);
	  if(dis < r)
		  return true;
	  else
		  return false;
  }
  
  private Point3D next_dir(){
	  Point3D r = Point3D.randomP(-SPEED, SPEED);
	  angle = r.angle(front);
	  axis = r.cross_n(front);
	  
	  return r;
  }
  
  private Point3D next_dir(int wall){
	  Point3D new_dir = Point3D.randomP(-SPEED, SPEED);
	  if(wall == 1)
		  new_dir.x = -SPEED;
	  if(wall == 2)
		  new_dir.y = -SPEED;
	  if(wall == 3)
		  new_dir.z = -SPEED;
	  if(wall == -1)
		  new_dir.x = SPEED;
	  if(wall == -2)
		  new_dir.y = SPEED;
	  if(wall == -3)
		  new_dir.z = SPEED;
	  
	  angle = new_dir.angle(front);
	  axis = new_dir.cross_n(front);
	  
	  return new_dir;
  }
  
  public int hitWall(Point3D p){
	  int r = 0;
	  if(p.x() >= BOUND)
		  r = 1;
	  if(p.y() >= BOUND)
		  r = 2;
	  if(p.z() >= BOUND)
		  r = 3;
	  if(p.x() <= -BOUND)
		  r = -1;
	  if(p.y() <= -BOUND)
		  r = -2;
	  if(p.z() <= -BOUND)
		  r = -3;		
	  
	  return r;
  }

  public void draw( GL2 gl )
  {
	/* draw fish object with updates */
    gl.glPushMatrix();
    gl.glPushAttrib( GL2.GL_CURRENT_BIT );
    	gl.glTranslated( (float)loc.x(), (float)loc.y(), (float)loc.z());
		gl.glRotatef( angle, axis.x, axis.y, axis.z );
	    gl.glColor3f( 0.6f, 0.6f, 0.9f); // Orange
	    
	        
		gl.glPushMatrix();
			gl.glCallList( body );
			gl.glPushMatrix();
			
				// the following rotation is applied to both tail 1 and tail 2
				gl.glRotated(angleTail, 0, 1, 0);
				gl.glCallList( tail1 );
				// T out
				gl.glTranslated(0, 0, -0.2*0.3f*2);
				// This rotation is only applied to tail 2
				gl.glRotated(angleTail, 0, 1, 0);
				// T in
				gl.glTranslated(0, 0, 0.2*0.3f*2);
				gl.glCallList( tail2 );
				
			gl.glPopMatrix();
		gl.glPopMatrix();
	    	    
    gl.glPopAttrib();
    gl.glPopMatrix();
  }
}
