/**
 * Point3D.java - a three-dimensional point with float values
 * 
 * History:
 * 
 * 18 February 2011
 * 
 * - made members private and added accessors
 * 
 * - added documentation
 * 
 * - added toString() method
 * 
 * (Jeffrey Finkelstein <jeffrey.finkelstein@gmail.com>)
 * 
 * 30 January 2008
 * 
 * - created
 * 
 * (Tai-Peng Tian <tiantp@gmail.com>)
 */


/**
 * A three-dimensional point with float values.
 * 
 * @author Tai-Peng Tian <tiantp@gmail.com>
 * @author Jeffrey Finkelstein <jeffrey.finkelstein@gmail.com>
 * @since Spring 2008
 */
public class Point3D {
  /** The origin, (0, 0, 0). */
  public static final Point3D ORIGIN = new Point3D(0, 0, 0);
  /** The x component of this point. */
  public float x;
  /** The y component of this point. */
  public float y;
  /** The z component of this point. */
  public float z;

  /**
   * Instantiates this point with the three specified components.
   * 
   * @param x
   *          The x component of this point.
   * @param y
   *          The y component of this point.
   * @param z
   *          The z component of this point.
   */
  public Point3D(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  static public Point3D randomP(float min, float max){
	    float x = randomWithRange(min, max);
	    float y = randomWithRange(min, max);
	    float z = randomWithRange(min, max);
	    return new Point3D(x, y, z);
  }

  static private float randomWithRange(float min, float max){
	 float range = Math.abs(max - min);     
     return (float)(Math.random() * range) + (min <= max ? min : max);
  }
  
  public Point3D plus(Point3D p) {
	  Point3D r = new Point3D(this.x + p.x(), this.y + p.y(), this.z + p.z());
	    return r;
  }
  
  public Point3D normal(){
	  float len = this.dis(); 
	  return new Point3D(this.x/len, this.y/len, this.z/len);
  }
  public float dot(Point3D p){
	  return this.x * p.x + this.y * p.y + this.z * p.z;
  }
  
  public float angle(Point3D p){
	  Point3D p_n = p.normal();
	  Point3D this_n = this.normal();
	  
	  float angle = (float)Math.toDegrees( Math.acos(this_n.dot(p_n)) );
	  System.out.println(angle);
	  if(this.cross_n(p_n).dot(this.cross(p)) < 0){
		  angle = -angle;
	  }
	  
	  return angle; 
  }
  
  public Point3D cross(Point3D p){
	  float x = this.y * p.z - this.z * p.y;
	  float y = this.z * p.x - this.x * p.z;
	  float z = this.x * p.y - this.y * p.x;
	  Point3D r = new Point3D(x, y, z);
	  return r;
  } 
  
  public Point3D cross_n(Point3D p){
	  return cross(p).normal();
  }
  
  public float dis(){
	return dis(new Point3D(0, 0, 0));  
  }
  
  public float dis(Point3D p){
	  double x = Math.pow(this.x, 2);
	  double y = Math.pow(this.y, 2);
	  double z = Math.pow(this.z, 2);
	  
	  float len = (float)Math.sqrt( x+y+z );
	  return len;
  }
  /**
   * Returns the String representation of this object.
   * 
   * @return The String representation of this object.
   */
  @Override
  public String toString() {
    return "Point[" + this.x + ", " + this.y + ", " + this.z + "]";
  }

  /**
   * Gets the x component of this point.
   * 
   * @return The x component of this point.
   */
  public float x() {
    return this.x;
  }

  /**
   * Gets the y component of this point.
   * 
   * @return The y component of this point.
   */
  public float y() {
    return this.y;
  }

  /**
   * Gets the z component of this point.
   * 
   * @return The z component of this point.
   */
  public float z() {
    return this.z;
  }
}
