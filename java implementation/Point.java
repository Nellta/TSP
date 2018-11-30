public class Point{
	
	public double x;
	public double y;

	public int priority;
	public int parent;

	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}

	public Point(String line){
		String[] split = line.split(" ");
		x =Double.parseDouble( split[0]);
		y =Double.parseDouble( split[1]);
	}


	public void printPoints(){
		System.out.println( x + " " + y);
	}

}