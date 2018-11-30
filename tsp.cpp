
#include "tsp.h"
#include "prim.h"

#include <vector>
#include <sstream>
#include <string>
#include <tuple>
#include <fstream>
#include <iterator>
#include <cstdint>
#include <math.h>
#include <limits>
#include <list>


namespace TSPNAME{


	std::vector<std::tuple<double,double>> createPoints(const std::string &filename){
		std::ifstream in(filename);

		std::string size;
		std::getline (std::cin,size);
		int size2 = std::stoi(size);

		//std::cout << size << std::endl;
		std::vector<std::tuple<double, double>> vec = std::vector<std::tuple<double, double>>();

		
		for(int i=0; i< size2; i++){

			double a,b;
			
			std::string line;
			
			std::getline (std::cin,line);

			std::istringstream iss(line);
			iss >> a >> b;

			//std::cout << a << "  " << b << std::endl;

			
			vec.push_back(std::make_tuple(a,b));

		}

		in.close();
		
		return vec;
	}


	int calcDistance(std::tuple<double,double> a, std::tuple<double,double> b){
		return (int) std::round( std::sqrt(std::pow((std::get<0>(a) - std::get<0>(b)), 2) + std::pow((std::get<1>(a) - std::get<1>(b)), 2)));
	}


	void greedyTour(std::vector<std::tuple<double,double>> vec, int* foo){
		int used[vec.size()] = {false};
		used[0] = true;

		for(int i=1; i < vec.size(); i++){
			int best = -1;
			for(int j=0; j< vec.size(); j++){
				if(!used[j] && (best == -1 || calcDistance(vec[foo[i-1]], vec[j]) < calcDistance(vec[foo[i-1]], vec[best])  )  ){
					best = j;
				}
			}
			foo[i] = best;
			used[best] = true;
		}

	}

	


	void primsEMST(std::vector<std::tuple<double,double>> vec, int minDistance){

		PRIMNAME::Graph* graph = PRIMNAME::createGraph(vec.size());

		//std::cout << minDistance << "   min:";


		for(int i=0; i< vec.size(); i++){
			for(int j=0; j< vec.size(); j++){
				if(i!=j ){
					int len = calcDistance(vec[i],vec[j]);
					  PRIMNAME::addEdge(graph, i, j, len  );
				}
			}
		}
		
		int arr[graph->V];
		PRIMNAME::primMST(graph, arr);

		for(int i=0; i< graph->V; i++){
			std::cout << arr[i] << std::endl;
		}
	


	}


	void printDotList(std::vector<dotObj> list){
		for(int i =0; i< list.size(); i++){
			std::cout << "item: " << i << std::endl;
			for(int j=0; j< list[i].children.size(); j++){
				std::cout << list[i].children[j] << " ";
			}

			std::cout << std::endl;
		}
	}

	/**
	void dfs(std::vector<dotObj> vec){

		bool visited[vec.size()] = {false}; 

		if(vec.size()<=0){

		} else {

			std::list<int> linklist;

			linklist.push_front(0);

			while(!linklist.empty()){
				int first = linklist.front();
				linklist.pop_front();

				if(!visited[first]){
					visited[first] = true;
					std::cout << first << std::endl;
					for(int i=0; i< vec[first].children.size(); i++){
						linklist.push_front(vec[first].children[i] );
					}
				}	
			}
		}
	}
	*/






	void quickSort(std::vector<std::tuple<double,double>>* arr, int left, int right) {

      int i = left, j = right;

      std::tuple<double,double> tmp;


      double pivot =  std::get<0>(arr->at((left + right) / 2));

      /* partition */
      while (i <= j) {

            while (std::get<0>(arr->at(i)) < pivot)

                  i++;

            while (std::get<0>(arr->at(j)) > pivot)

                  j--;

            if (i <= j) {

                  tmp = arr->at(i);

                  arr->at(i) = arr->at(j);

                  arr->at(j) = tmp;

                  i++;

                  j--;

            }

      };
      /* recursion */

      if (left < j)

            quickSort(arr, left, j);

      if (i < right)

            quickSort(arr, i, right);
    }


}
