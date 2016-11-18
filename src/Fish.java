

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
  private float SPEED = 0.005f;
  public int TYPE_OF_FISH;

  public Fish( )
  {
    angle = 0;
    angleTail = 0;
    directionTail = 1;
    loc = Point3D.randomP(-2, 2);
    dir = next_dir();
    TYPE_OF_FISH = Point3D.randomWithRangeInt(0, 1);
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

  public void update( GL2 gl , Fish f, Food food)
  {
    //tail anime
    angleTail += 0.75*directionTail;
	  if(angleTail>20 || angleTail<-20)
		  directionTail=-directionTail;
	  
	//dealing hit wall
	int hit = hitWall(loc);
	if(hit != 0){
		dir = next_dir(hit);
	}
	//collision
	if(collision(f, 1f)){
		System.out.println("collision");
		avoid(f.loc);
	}
	//chase food
	if(!food.eaten)
		chase(food.loc);
	//chase fish
	if(TYPE_OF_FISH == 1){
		chase(f.loc);
	}
	//rotate to face right direction
	rotate_by_dir();
	//update location by velocity dir
	loc = loc.plus(dir);
  }
  
  private boolean collision(Fish f, float r){
	  float dis = loc.dis(f.loc);
	  boolean hit = false;
	  if(dis < r)
		  hit = true;
	  return hit;
  }
  
  private Point3D chase(Point3D target){
	  Point3D force = loc.minus(target).normal().multiply(SPEED*0.1f);
	  dir = dir.minus(force);
	  return dir;
  }
  
  private Point3D avoid(Point3D target){
	  Point3D force = loc.minus(target).normal().multiply(SPEED*0.1f);
	  dir = dir.plus(force);
	  return dir;
  }
  
  private Point3D next_dir(){
	  Point3D r = Point3D.randomP(-SPEED, SPEED);
	  return r;
  }
  
  private void rotate_by_dir(){
	  angle = dir.angle(front);
	  axis = dir.cross_n(front);
  }
  public void changeType(){
	  TYPE_OF_FISH = 0;
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
		if(TYPE_OF_FISH == 0)
			gl.glColor3f( 0.6f, 0.6f, 0.9f); // Orange
		else if(TYPE_OF_FISH == 1) 
			gl.glColor3f( 0.8f, 0.4f, 0.4f); // Orange
	    
	        
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

