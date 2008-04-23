// The following ifdef block is the standard way of creating macros which make exporting 
// from a DLL simpler. All files within this DLL are compiled with the FILTER_EXPORTS
// symbol defined on the command line. this symbol should not be defined on any project
// that uses this DLL. This way any other project whose source files include this file see 
// FILTER_API functions as being imported from a DLL, whereas this DLL sees symbols
// defined with this macro as being exported.
#pragma once

#include <string>

#ifdef FILTER_EXPORTS
#define FILTER_API __declspec(dllexport)
#else
#define FILTER_API __declspec(dllimport)
#endif

namespace filter{

FILTER_API std::string bzip2compressor(const std::string& data);
FILTER_API std::string bzip2decompressor(const std::string& buffer);

}
