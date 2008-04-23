// filter.cpp : Defines the exported functions for the DLL application.
//

#include "stdafx.h"
#include "filter.h"

#include "FilterTemplate.h"

namespace filter{
	FILTER_API std::string bzip2compressor( const std::string& data )
{
	std::string encoded;

	encode<boost::iostreams::bzip2_compressor>(data, encoded);

	return encoded;
}

FILTER_API std::string bzip2decompressor( const std::string& buffer )
{
	std::string decoded;

	decode<boost::iostreams::bzip2_decompressor>(buffer, decoded);

	return decoded;
}
}