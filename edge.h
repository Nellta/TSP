#ifndef EDGE_H
#define EDGE_H

#include <tuple>
#include <ostream>

namespace EDGE {

class Edge 
{
	public:

		Edge( std::tuple<double,double> _p1, std::tuple<double,double> _p2) : p1(_p1), p2(_p2) {};
		Edge(){};

		std::tuple<double,double> p1;
		std::tuple<double,double> p2;
};


inline bool operator == (const Edge  e1, const Edge  e2)
{
	return 	(e1.p1 == e2.p1 && e1.p2 == e2.p2) ||  (e1.p1 == e2.p2 && e1.p2 == e2.p1);
}

}


inline std::ostream &operator << (std::ostream &str, EDGE::Edge const &e)
{
	return str << "Edge " << std::get<0>(e.p1) << ":" << std::get<1>(e.p1) << ", " <<   std::get<0>(e.p2) << ":" << std::get<1>(e.p2);
}

#endif