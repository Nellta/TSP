#include "tsp.h"
#include <iostream>
#include <vector>
#include <fstream>
#include <cstdint>
#include <tuple>
#include <math.h>

using namespace std;
using namespace TSPNAME;


int main(int argc, char* argv[]){


	//if(argc>1){

		vector<tuple<double, double>> v = createPoints(argv[2]);

		primsEMST(v, (int) (100/std::sqrt(v.size())));


	//} else {
	//	cout << "shit don't work" << endl;
	//}

	return 0;

}