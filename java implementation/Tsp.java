import java.util.*;
import java.lang.Math;

public class Tsp{

	int calcDistance(Point a, Point b){
		return (int) Math.round( Math.sqrt( Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y,2) )  );
	}

	int findClosest(ArrayList<Point> list, int match, boolean[] visited){
		int best = -1;
		int dist = Integer.MAX_VALUE;
		for(int i=0; i< list.size(); i++){
			
			if(visited[i] == false && i!=match){
				int tmp = calcDistance(list.get(match), list.get(i));
				if( dist > tmp){
					best = i;
					dist = tmp;

				}
			}
		}

		return best;
	}


	ArrayList<Point> createPoints(Kattio io){
		

		int size = io.getInt();


		ArrayList<Point> list = new ArrayList<Point>();

		for(int i=0; i< size; i++){
			
			list.add(new Point(io.getLine()));
		}


		return list;
	}

	public int[] nnTour(ArrayList<Point> list){
		boolean[] visited = new boolean[list.size()];

		int counter = visited.length -1;
		int current = 0;
		int[] newList = new int[list.size()];

		if(list.size()>0){
			newList[0] = 0;
			visited[current] = true;
			int i=1;
			while(counter>0){
				current = findClosest(list, current, visited);
				visited[current] = true;
				newList[i] = current;
				i++;
				counter--;
			}

		}

		return newList;
	}

	public int[] swap(int[] list , int i, int j){
		int[] newList = new int[list.length];
		for(int k=0; k< list.length; k++){
			newList[k] = list[k];
		}

		int dec =0;
		for(int c = i; c<=j ; c++){
			newList[c] = list[j-dec];
			dec++;
		}

		return newList;
	}

	public int totalDist(ArrayList<Point> points , int[] list){
		int sum =0;
		for(int i=0; i< list.length -1 ; i++){
			sum+= calcDistance( points.get(list[i]), points.get(list[i+1])  );
		}
		return sum;
	}



	public int[] twoOpt(ArrayList<Point> points, int[] list){

		boolean swapped = true;
		int best = totalDist(points, list);
		int[] tentative = new int[list.length];
		for(int i=0; i< list.length; i++) tentative[i] = list[i];


		while(swapped){
			swapped = false;

			for(int i=1; i< list.length-2; i++ ){
				for(int j=i+1; j< list.length-1; j++){
					if( (calcDistance(points.get(list[i]), points.get(list[i-1])) + calcDistance(points.get(list[j]), points.get(list[j+1]))) 
					> ( calcDistance(points.get(list[i]), points.get(list[j+1])) + calcDistance(points.get(list[i-1]), points.get(list[j])))){
						
						int[] newList = swap(list, i,j);
						int tmpDist = totalDist(points, newList);
						if( best > tmpDist ){
							swapped = true;
							if( totalDist(points, tentative) > totalDist(points, newList) ){
								for(int k=0; k< list.length; k++){
									tentative[k] = newList[k];
								}
								best = tmpDist;
							}
						}
						
					}
				}
				for(int k=0; k< list.length; k++){
					list[k] = tentative[k];
				}
			}
		}

		return list;
		//for(int i=0; i<list.length; i++) System.out.println(list[i]);


	}

	public int[] threeOpt(ArrayList<Point> points, int[] list){
		boolean swapped = false;
		int best = totalDist(points, list);
		while(swapped){
			swapped = false;
			for(int i=1; i< list.length - 3; i++){
				for(int j=i+2; j< list.length-2; j++){
					for(int k=j+2; k< list.length-1; k++){

						int[] dists = new int[7];
						int[][] tmps = new int[7][1];

						tmps[0] = swap(list, i-1,j);
						dists[0]= totalDist(points, tmps[0]);

						tmps[1]= swap(list, j,k-1 );
						dists[1] = totalDist(points, tmps[1]);

						tmps[2] = swap(tmps[1], i, j-1 );
						dists[2]= totalDist(points, tmps[2]);

						tmps[3] = swap(list,  i  ,k-1);
						dists[3] = totalDist(points, tmps[3]);

						tmps[4] = swap(tmps[3]  , i , j-1 );
						dists[4] = totalDist(points, tmps[4]);

						tmps[5] = swap( tmps[3]  , j , k-1 );
						dists[5] = totalDist(points, tmps[5]);


						tmps[6] = swap( tmps[4]  , j , k-1 );
						dists[6] = totalDist(points, tmps[6]);


						int smallest = 0;
						for(int f =1; f< dists.length; f++){
							if( dists[smallest] > dists[i]){
								smallest = i;
							}
						}


						if(dists[smallest] < best){
							best = dists[smallest];
							swapped = true;
							for(int l=0; l< list.length; l++) list[l] = tmps[smallest][i] ;
						}
					}
				}
			}

		}


		return list;
	}

	//create a minimum spannin tree with prim's algorithm, then do dfs to get tsp path.
	public int[] prim(ArrayList<Point> points){

		
		//for each vertex set prio = d(v,root), parent = root.
		for(int i =0; i< points.size(); i++ ){
			points.get(i).priority = calcDistance(points.get(i), points.get(0));
			points.get(i).parent = 0;
		}

		ArrayList<Tuple<Point,Point>> mst = new ArrayList<Tuple<Point,Point>>();

		for(int i=1 ; i< points.size(); i++){
			int min = Integer.MAX_VALUE;
			int minvertex = -1;

			for(int j=0; j< points.size(); j++){
				if (points.get(i).priority >0 && points.get(i).priority < min){
					min = points.get(i).priority;
					minvertex = i;
				}
			}

			points.get(minvertex).priority = 0;




		}

		return null;
	}


	public static void main(String[] args) {
		Kattio io = new Kattio(System.in,System.out);
		Tsp tsp = new Tsp();
		

		ArrayList<Point> points = tsp.createPoints(io);

		int[] newList = tsp.nnTour(points);

		int[] newList2 = tsp.threeOpt(points, newList);

		if(tsp.totalDist(points, newList) > tsp.totalDist(points, newList2)){
			for(int i=0; i< newList2.length; i++) System.out.println(newList2[i]);
		} else {
			for(int i=0; i< newList.length; i++) System.out.println(newList[i]);
		}

		
		
		io.close();
	}

}