import java.awt.Component;

import java.awt.Point;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.RenderingHints;
import java.awt.Color;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Collections;

/**
 * Implements the operations on a KD-Tree and
 * functions to visualize the KD-Tree
 */
public class KDTreeVisualization extends Component{

  LinkedList<Point> points;  // List of points
  TreeNode kdRoot;           // The kd-Tree
  Image img;                 // Image to display points
  int w, h;                  // Width an height of image
  Graphics2D gi;             // Graphics object to draw points
  int n;                     // Number of points
  
  /**
   * Initializes the points.
   * 
   * @param w width of window.
   * @param h height of window.
   * @param n number of points.
   */
  public KDTreeVisualization(int w, int h, int n) {
    
    this.w=w;
    this.h=h;
    this.n=n;
    
    this.kdRoot = null;
  }
  
  /**
   * Initializes the image
   */
  public void init(){
    img = createImage(w, h);
    gi = (Graphics2D)img.getGraphics();
    gi.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
  }
  
  /**
   * create and show a set of randomly generated points
   */
  public void initPoints(){
	  points = this.createPoints(n);
	  this.visualizePoints();
  }
  
  /**
   * Initialize points by distributing them randomly.
   */
  public LinkedList<Point> createPoints(int size){
    LinkedList<Point> p = new LinkedList<Point>();
    for(int i=0; i< size; i++) {
      p.add(new Point(Math.round((float)Math.random()*w)-1,Math.round((float)Math.random()*h)-1));
    }
    return p;
  }
  
  /**
   * Searches the nearest neighbor for x points
   * @param x number of points to search
   * @param mode data structure to use (0: list, 1: kd-tree)
   */
  public void searchNN(int x, int mode){
    
	  LinkedList<Point> searchPoints = createPoints(x);
	  Timer t = new Timer();
	  t.reset();
	  Iterator<Point> it = searchPoints.iterator();
	  while(it.hasNext())
	  {
		  Point p = it.next();
		  Point q;
      
		  switch(mode){
		  	case 0: 
		  		q=this.listSearchNN(p);
		  		break;
		  	case 1:
		  		q=this.treeSearchNN(p);
		  }
	  }
	  System.out.printf("Number of points searched: %d, Time: %dms\n", x, t.timeElapsed());
  }
  
  /**
   * starts creation of the kd-Tree
   */
  public void createKDTree(){
	  System.out.println("Starting to build KD Tree");
	  printPoints(points);
	  kdRoot = buildKDTree(points, 0);
  }

  private void printPoints(List<Point> points) {
	  System.out.print("[");
	  for (Point p : points) {
		  System.out.print("<" + p.x + ", " + p.y + ">");
	  }
	  System.out.println("]");
  }

  public TreeNode buildKDTree(List<Point> points, int depth) {
	  // 0 => x, 1 => y
	  int dimension = depth % 2;
	  System.out.println("Dimension: " + dimension);
	  PointComparator comp = new PointComparator(dimension);
	  Collections.sort(points, comp);
	  System.out.println("Sorted array:");
	  printPoints(points);

	  int median = points.size() / 2;

	  if (points.size() == 0) {
		  return null;
	  } else {
		  Point med = points.get(median);
		  System.out.println("Median point: " + med.x + ", " + med.y);
		  // subList(a, b) returns from a inclusive, to b exclusive.
		  return new TreeNode(
		  	med,	
		  	buildKDTree(points.subList(0, median), depth + 1),
		  	buildKDTree(points.subList(median + 1, points.size()), depth + 1)
		  );
	  }

  }
    
  /**
   * searches the nearest neighbor of a point in a 
   * list of points
   * @param p the point for which to search
   * @return the nearest neighbor of p
   */
  private Point listSearchNN(Point p){
	  // Arguably a tad ugly
	  double closestDistance = Integer.MAX_VALUE;
	  Point closest = null;

	  for (Point neighbour : points) {
		  if (neighbour == p) {
			  continue;
		  }

		  if (closest == null) {
			  closest = neighbour;
			  closestDistance = p.distance(closest);
		  }

		  double neighbourDistance = p.distance(neighbour);
		  if (neighbourDistance < closestDistance) {
			  closest = neighbour;
			  closestDistance = neighbourDistance;
		  }
	  }

	  return closest;
  }

  /**
   * searches the nearest neighbor of a point in a kd-tree
   * @param p the point for which to search
   * @return the nearest neighbor of p
   */
  private Point treeSearchNN(Point p){
    //to be implemented
    return p;
  }
  
  /**
   * Visualizes the points in the list
   */
  public void visualizePoints(){
    gi.clearRect(0, 0, w, h);
    
    Iterator<Point> it = points.iterator();
    while(it.hasNext())
    {
      Point p = it.next();
      gi.fillOval(p.x-2, p.y-2,5,5);
    }
    this.repaint();
  }
  
  /**
   * Visualizes the order of the points in the list
   *
   */
  public void visualizeList(){
    gi.clearRect(0, 0, w, h);
    
    Point old= new Point(0,0);
    Iterator<Point> it = points.iterator();
    if(it.hasNext()){
      old=it.next();
      gi.setColor(Color.RED);
      gi.fillOval(old.x-2, old.y-2,5,5);
    }
    while(it.hasNext())
    {
      Point p = it.next();
      gi.setColor(Color.BLACK);
      gi.fillOval(p.x-2, p.y-2,5,5);
      gi.setColor(Color.BLUE);
      gi.drawLine(old.x, old.y,p.x,p.y);
      old = p;
    }
    this.repaint();
  }
  
  /**
   * starter for the kd-tree visualization
   */
  public void visualizeTree(){
	  gi.clearRect(0, 0, w, h);
      visualize(this.kdRoot, 0, 0, w, 0, h);
      this.repaint();
  }
  
  /**
   * Visualizes the kd-tree
   * @param n TreeNode
   * @param depth depth in the tree
   * @param left left border of the sub-image
   * @param right right border of the sub-image
   * @param top top border of the sub-image
   * @param bottom bottom border of the sub-image
   */
  private void visualize(TreeNode n, int depth, int left, int right, int top, int bottom){
	  if(n != null){
		  int axis = depth%2;
		  if(axis == 0){
			  gi.fillOval(n.position.x-2, n.position.y-2, 5, 5);
			  gi.drawLine(n.position.x, top, n.position.x, bottom);
			  visualize(n.left, depth+1, left, n.position.x, top, bottom);
			  visualize(n.right, depth+1, n.position.x, right, top, bottom); 
		  }else {
			  gi.fillOval(n.position.x-2, n.position.y-2, 5, 5);
			  gi.drawLine(left, n.position.y, right, n.position.y);
			  visualize(n.left, depth+1, left,right , top,n.position.y);
			  visualize(n.right, depth+1, left ,right , n.position.y, bottom);
		  }
	  }
  }
  
  
  /**
   * Paint the image
   */
    public void paint(Graphics g)
    { 
      g.drawImage(img, 0, 0, null);
    }
    
  public Dimension getPreferredSize() {
    return new Dimension(w, h);
  }

  /**
   * Node in the kd-Tree
   */
  private class TreeNode
  {
    private TreeNode left, right;    // Pointers to left and right child
    private Point position;          // Position of the Point
    
    TreeNode(Point point)
    {
      this.position = point;
    }

    TreeNode(Point point, TreeNode left, TreeNode right) {
      this.position = point;
      this.left = left;
      this.right = right;
    }

    public void setLeft(TreeNode node) {
	    this.left = node;
    }

    public void setRight(TreeNode node) {
	    this.right = node;
    }
  }
}
