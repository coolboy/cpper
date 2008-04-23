#pragma once

#include <boost/iostreams/device/back_inserter.hpp>
#include <boost/iostreams/filtering_stream.hpp>
#include <boost/iostreams/copy.hpp>

template <typename FILTER, typename BUFTYPE, typename DATATYPE>
void
encode
(
 const DATATYPE& data,
 BUFTYPE&       buffer
 )
{
	boost::iostreams::filtering_ostreambuf out;

	out.push(FILTER());
	out.push(boost::iostreams::back_inserter(buffer));

	//out << data;

	boost::iostreams::copy(boost::make_iterator_range(data), out);
}

template <typename FILTER, typename BUFTYPE, typename DATATYPE>
void
decode
(
 const BUFTYPE& buffer,
 DATATYPE&       data
 )
{
	boost::iostreams::filtering_istreambuf in;

	in.push(FILTER());
	in.push(boost::make_iterator_range(buffer));

	//in >> data;

	//in.set_auto_close (false);
	//in.pop();
	//in.push(boost::make_iterator_range(buffer));

	boost::iostreams::copy(in, boost::iostreams::back_inserter(data));
}
