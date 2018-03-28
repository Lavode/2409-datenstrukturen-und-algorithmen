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

  public void compareNNSearches(int x) {
	  boolean sanityTestPassed = true;
	  Point resultListSearch;
	  Point resultTreeSearch;

	  LinkedList<Point> searchPoints = createPoints(x);

	  for (Point p : searchPoints) {
		  resultListSearch = this.listSearchNN(p);
		  resultTreeSearch = this.treeSearchNN(p);

		  boolean equalResult = resultListSearch.equals(resultTreeSearch);
		  boolean equalDistance = p.distance(resultListSearch) == p.distance(resultTreeSearch);
		  sanityTestPassed = sanityTestPassed && (equalResult || equalDistance);

		  System.out.printf("Searched for: <%d, %d>, List search result: <%d, %d>, Tree search result: <%d, %d>\n", p.x, p.y, resultListSearch.x, resultListSearch.y, resultTreeSearch.x, resultTreeSearch.y);
		  if (equalResult) {
			  System.out.println("OK: Same result");
		  } else if (equalDistance) {
			  System.out.println("OK: Different result but same distance.");
		  } else {
			  System.out.println("NOK: Differing results, differing distances!");
		  }
	  }

	  if (sanityTestPassed) {
		  System.out.println("All queries returned same or equivalent result.");
	  } else {
		  System.out.println("At least one query returned a different, inferior result.");
	  }
  }
  
  /**
   * starts creation of the kd-Tree
   */
  public void createKDTree(){
	  System.out.println("Starting to build KD Tree");
	  Timer t = new Timer();
	  t.reset();
	  //printPoints(points);
	  kdRoot = buildKDTree(points, 0);
	  System.out.printf("Tree built in %dms.\n", t.timeElapsed());
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
	  //System.out.println("Dimension: " + dimension);
	  PointComparator comp = new PointComparator(dimension);
	  Collections.sort(points, comp);
	  //System.out.println("Sorted array:");
	  //printPoints(points);

	  int median = points.size() / 2;

	  if (points.size() == 0) {
		  return null;
	  } else {
		  Point med = points.get(median);
		  //System.out.println("Median point: " + med.x + ", " + med.y);
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
	  //System.out.println("-----");
	  //System.out.println("Searching for NN of: <" + p.x + ", " + p.y + ">");
	  //printPoints(points);
	  TreeNode nn = nearestNeighbour(p, kdRoot, 0);
	  Point position = nn.getPosition();
	  //System.out.println("Got NN: <" + position.x + ", " + position.y + ">");
	  //System.out.println("-----");
	  return position;
  }

  private TreeNode nearestNeighbour(Point point, TreeNode node, int depth) {
	  int dimension = depth % 2;
	  // 0: Left, 1: Right
	  int direction;
	  TreeNode currentBest;
	  double currentBestDistance;
	  boolean checkOtherSubtree = false;

	  //System.out.println("Examining point: <" + node.getPosition().x + ", " + node.getPosition().y + ">");

	  // Decide which path to take while descending the subtree.
	  if (dimension == 0) {
		  // X-axis
		  if (point.x > node.getPosition().x) {
			  // Traverse right part
			  direction = 1;
		  } else {
			  // Traverse left part
			  direction = 0;
		  }
	  } else {
		  // Y-axis
		  if (point.y > node.getPosition().y) {
			  // Traverse right part
			  direction = 1;
		  } else {
			  // Traverse left part
			  direction = 0;
		  }
	  }

	  // Descend into the subtree with points closer to us.
	  if (direction == 0) {
		  //System.out.println("Going left");
		  if (node.getLeft() == null) {
			  //System.out.println("Reached leaf node, setting as current best.");
			  currentBest = node;
		  } else {
			  //System.out.println("Recursing deeper...");
			  currentBest = nearestNeighbour(point, node.getLeft(), depth + 1);
		  }
	  } else {
		  //System.out.println("Going right");
		  if (node.getRight() == null) {
			  //System.out.println("Reached leaf node, setting as current best.");
			  currentBest = node;
		  } else {
			  //System.out.println("Recursing deeper...");
			  currentBest = nearestNeighbour(point, node.getRight(), depth + 1);
		  }
	  }


	  currentBestDistance = point.distance(currentBest.getPosition());
	  // Replace nearest-neighbour estimate with current node if closer.
	  //System.out.println("Examining point: <" + node.getPosition().x + ", " + node.getPosition().y + ">");
	  if (point.distance(node.getPosition()) < currentBestDistance) {
		  //System.out.println("Is closer than previous current best, setting current best to it.");
		  currentBest = node;
		  currentBestDistance = point.distance(currentBest.getPosition());
	  } else {
		  //System.out.println("Is farther than current best, ignoring.");
	  }


	  // Check if other subtree might contain closer results.
	  //System.out.println("Checking if other subtree might contain closer points.");
	  if (dimension == 0) {
		  //System.out.println("Checking difference on X axis");
		  if (Math.abs(point.x - node.getPosition().x) < currentBestDistance) {
			  //System.out.println("Might be closer results in there, recursing down subtree.");
			  checkOtherSubtree = true;
		  } else {
			  //System.out.println("Split plane too far away, ignoring.");
		  }
	  } else {
		  //System.out.println("Checking difference on Y axis");
		  if (Math.abs(point.y - node.getPosition().y) < currentBestDistance) {
			  //System.out.println("Might be closer results in there, recursing down subtree.");
			  checkOtherSubtree = true;
		  } else {
			  //System.out.println("Split plane too far away, ignoring.");
		  }
	  }

	  if (checkOtherSubtree) {
		  TreeNode bestWithinSubtree = null;
		  if (direction == 0) {
			  // We've checked the left subtree previously, check the right one now.
			  //System.out.println("Checking right subtree.");
			  if (node.getRight() != null) {
				  //System.out.println("Down we go. :)");
				  bestWithinSubtree = nearestNeighbour(point, node.getRight(), depth + 1);
			  } else {
				  //System.out.println("Subtree empty, ignoring.");
			  }
		  } else {
			  // We've checked the right subtree previously, check the left one now.
			  //System.out.println("Checking left subtree.");
			  if (node.getLeft() != null) {
				  //System.out.println("Down we go. :)");
				  bestWithinSubtree = nearestNeighbour(point, node.getLeft(), depth + 1);
			  } else {
				  //System.out.println("Subtree empty, ignoring.");
			  }
		  }

		  if (bestWithinSubtree != null) {
			  double bestWithinSubtreeDistance = point.distance(bestWithinSubtree.getPosition());
			  if (bestWithinSubtreeDistance < currentBestDistance) {
				  //System.out.println("Found closer neighbour within other subtree, setting as new current best.");
				  currentBest = bestWithinSubtree;
				  currentBestDistance = bestWithinSubtreeDistance;
			  } else {
				  //System.out.println("Didn't find closer neighbour within other subtree, ignoring.");
			  }
		  }
	  }

	  return currentBest;
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

    public Point getPosition() {
	    return this.position;
    }

    public TreeNode getLeft() {
	    return this.left;
    }

    public void setLeft(TreeNode node) {
	    this.left = node;
    }

    public TreeNode getRight() {
	    return this.right;
    }

    public void setRight(TreeNode node) {
	    this.right = node;
    }
  }
}
