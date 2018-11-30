#ifndef GRAPH_H
#define GRAPH_H



#include <iostream>
#include <tuple>
#include <vector>
#include <list>
#include <math.h>
#include <stdio.h> 
#include <limits.h> 
#include "tsp.h"

namespace GRAPH  {
	class Graph 
	{ 
    	int size;    // No. of vertices 
  
    // Pointer to an array containing 
    // adjacency lists 
    	std::list<int> *adj; 
  
    // A recursive function used by DFS 
    	void DFSUtil(int v, bool visited[]){ 
    // Mark the current node as visited and 
    // print it 
    visited[v] = true; 
    std::cout << v << " "; 
  
    // Recur for all the vertices adjacent 
    // to this vertex 
    std::list<int>::iterator i; 
    for (i = adj[v].begin(); i != adj[v].end(); ++i) 
        if (!visited[*i]) 
            DFSUtil(*i, visited); 
}; 

	public: 
    	Graph(int _size) : size(_size) {
    		adj = new std::list<int>[_size];
    	 };   // Constructor 

    	void addNN(std::vector<std::tuple<double,double>> vec, int minDist) {
    		for(int i=0; i< size; i++){
    			for(int j=0; j< size; j++){
    				if(i!=j &&  TSPNAME::calcDistance(vec[i], vec[j]) <= minDist ){
    					addEdge(i, j);
    				}
    			}
    		}
    	};

    	int getSize() {return size;};
  
    // function to add an edge to graph 
    	void addEdge(int v, int w) {adj[v].push_back(w);} ; 
  
    // DFS traversal of the vertices 
    // reachable from v 
    	void DFS(int v){ 
    // Mark all the vertices as not visited 
    bool *visited = new bool[size]; 
    for (int i = 0; i < size; i++) 
        visited[i] = false; 
  
	
};



}; 

}


#endif