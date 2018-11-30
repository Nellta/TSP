import java.util.*;
import java.lang.Math;

public class Tsp{

	int calcDistance(Point a, Point b){
		if( a.x == b.x && b.y == a.y){
			return 0;
		}
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

						int orig = calcDistance(points.get(i-1), points.get(i)) + calcDistance(points.get(j-1), points.get(j)) +
							calcDistance(points.get(k-1), points.get(k));

						
						if(calcDistance(points.get(j), points.get(i-1)) + calcDistance(points.get(k), points.get(i)) +
							calcDistance(points.get(j-1), points.get(k-1)) < orig ){
							int[] tmp = swap(list, i-1, k );
							int[] tmp2 = swap(tmp, j, k-1);
							for(int l = 0; l< list.length ; l++) list[l] = tmp2[l];
							swapped = true;
						}
						else if(calcDistance(points.get(i), points.get(j)) + calcDistance(points.get(k-1), points.get(i-1)) +
							calcDistance(points.get(k), points.get(j-1)) < orig ){
							int[] tmp = swap(list, i-1, k );
							int[] tmp2 = swap(tmp, i, j-1);
							for(int l = 0; l< list.length ; l++) list[l] = tmp2[l];
							swapped = true;
						}
						else if(calcDistance(points.get(i-1), points.get(j-1)) + calcDistance(points.get(i), points.get(k-1)) +
							calcDistance(points.get(j), points.get(k)) < orig ){
							int[] tmp = swap(list, i, j-1 );
							int[] tmp2 = swap(tmp, j, k-1);
							for(int l = 0; l< list.length ; l++) list[l] = tmp2[l];
							swapped = true;
						}
						else if(calcDistance(points.get(i), points.get(k-1)) + calcDistance(points.get(j), points.get(i-1)) +
							calcDistance(points.get(k), points.get(j-1)) < orig ){
							int[] tmp = swap(list, i-1, k );
							int[] tmp2 = swap(tmp, i, j-1);
							int[] tmp3 = swap(tmp2, j, k-1);
							for(int l = 0; l< list.length ; l++) list[l] = tmp3[l];
							swapped = true;
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

		if(points.size()>0) points.get(0).priority = -1;

		ArrayList<List<Integer>> mst = new ArrayList<List<Integer>>();
		for(int i=0; i< points.size(); i++) mst.add(new ArrayList<Integer>());

		for(int i=1 ; i< points.size(); i++){
			int min = Integer.MAX_VALUE;
			int minvertex = -1;

			for(int j=0; j< points.size(); j++){
				if (points.get(j).priority >=0 && points.get(j).priority < min){
					min = points.get(j).priority;
					minvertex = j;
				}
			}

			points.get(minvertex).priority = -1;
			mst.get( points.get(minvertex).parent).add(minvertex);

			for(int k=0; k< points.size(); k++){
				if(points.get(k).priority > calcDistance(points.get(minvertex), points.get(k)) ){
					points.get(k).priority = calcDistance(points.get(minvertex), points.get(k));
					points.get(k).parent = minvertex;
				}
			}


		}

		/**
		for(int i=0; i< mst.size(); i++){
			for(int j=0; j < mst.get(i).size(); j++){
				System.out.print(mst.get(i).get(j) + " ");
			}
			System.out.println("");
		}*/


		return dfs(mst);
	}

	public int[] dfs(ArrayList<List<Integer>> mst){

		if(mst.size()==0) return null;

		Stack<Integer> stack = new Stack<Integer>();
		stack.push(0);
		int[] list = new int[mst.size()];
		int counter =0;

		while(!stack.isEmpty()){
			int index = stack.pop();
			list[counter] = index;
			for(int i=0; i< mst.get(index).size();i++ ){
				stack.push(mst.get(index).get(i));
			}

			counter++;
		}

		
		return list;
	}

	public int[] reverse(int[] list, int i, int j){
		return null;
	}


	public static void main(String[] args) {
		Kattio io = new Kattio(System.in,System.out);
		Tsp tsp = new Tsp();
		

		ArrayList<Point> points = tsp.createPoints(io);

		int[] newList = tsp.nnTour(points);

		if(newList == null) System.exit(1);

		int[] newList2 = tsp.threeOpt(points, tsp.twoOpt(points, newList));

		if(tsp.totalDist(points, newList) > tsp.totalDist(points, newList2)){
			for(int i=0; i< newList2.length; i++) System.out.println(newList2[i]);
		} else {
			for(int i=0; i< newList.length; i++) System.out.println(newList[i]);
		}

		
		
		io.close();
	}

}