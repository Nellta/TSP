#include "tsp.h"
#include <iostream>
#include <vector>
#include <fstream>
#include <cstdint>
#include <tuple>

using namespace std;
using namespace TSPNAME;


int main(int argc, char* argv[]){


	if(argc>1){

		vector<tuple<double, double>> v = createPoints(argv[2]);
		int foo[v.size()] = {0};
		greedyTour(v, foo);
		for(int i=0; i< v.size(); i++){
			//cout << get<0>(v[i]) << "  " << get<1>(v[i])<< endl;
			cout << foo[i] << endl;
		}

		cout << "ans is " << endl;
		primsEMST(v);

		cout << "all dist from 0" << endl;
		for(int i=1; i< v.size(); i++){
			cout << calcDistance(v[0], v[i]) << endl;
		}

	} else {
		cout << "shit don't work" << endl;
	}
	


}