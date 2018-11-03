
#include "tsp.h"

#include <vector>
#include <sstream>
#include <string>
#include <tuple>
#include <iostream>
#include <fstream>
#include <iterator>
#include <cstdint>
#include <math.h>
#include <limits>
#include <list>


namespace TSPNAME{

	dotObj::dotObj(int parent, int length){
		this->parent = parent;
		this->length = length;
	}


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


	void primsEMST(std::vector<std::tuple<double,double>> vec){
		std::vector<dotObj> list;
		for(int i =0; i< vec.size(); i++){
			list.push_back(dotObj(0, calcDistance(vec[0], vec[i])));
		}

		//std::cout << vec.size();
		//printDotList(list);

		//std::vector<dotObj> mst;
		//mst.push_back(vec[0]);
		int min = std::numeric_limits<int>::max();
		int minVertex;
		for(int i=1; i< list.size(); i++ ){
			int min = std::numeric_limits<int>::max();
			int minVertex;
			for(int j =0; j< list.size(); j++){
				if(list[j].length > 0 && list[j].length < min){
					min = list[j].length;
					minVertex = j;
				}
			}

			list[minVertex].length = 0;
			list[list[minVertex].parent].children.push_back(minVertex);
			//std::cout << minVertex << std::endl;
			//std::cout << 1111 << std::endl;
			for(int j=0; j< list.size(); j++){
				if(list[j].length > calcDistance(vec[j], vec[minVertex])){
					list[j].length = calcDistance(vec[j], vec[minVertex]);
					list[j].parent = minVertex;

				}
			}
			
		}

		//std::cout << 0 << std::endl;
		dfs(list);
		//std::cout << list[0].children[0] << std::endl;
		//printer(0,list);
		//printDotList(list);
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

	void dfs(std::vector<dotObj> vec){

		if(vec.size()<=0){

		} else {

			std::list<int> linklist;

			linklist.push_front(0);

			while(!linklist.empty()){
				int first = linklist.front();
				linklist.pop_front();

				std::cout << first << std::endl;

				for(int i=0; i< vec[first].children.size(); i++){
					linklist.push_front(vec[first].children[i] );
				}
			}

		}

	}

}
