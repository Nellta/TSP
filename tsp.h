#ifndef TSP_H
#define TSP_H



#include <vector>
#include <sstream>
#include <tuple>
#include <iostream>
#include <cstdint> 

namespace TSPNAME{

	class dotObj {
		public:
			int length;
			int parent;
			std::vector<int> children;
			dotObj(int _parent, int _length) : parent(_parent), length(_length)  {};

	};

	void quickSort(std::vector<std::tuple<double,double>>* arr, int left, int right);

	std::vector<std::tuple<double,double>> createPoints(const std::string& filename);

	int calcDistance(std::tuple<double,double> a, std::tuple<double,double> b);

	void primsEMST(std::vector<std::tuple<double,double>> vec, int minDistance);

	void printDotList(std::vector<dotObj> list);

	//void dfs(std::vector<dotObj> vec);
	
	//
	void greedyTour(std::vector<std::tuple<double,double>> vec, int* foo);

	

}

#endif
