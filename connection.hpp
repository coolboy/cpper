#pragma once

#include <boost/asio.hpp>
#include <boost/asio/ssl.hpp>

#include <boost/archive/binary_iarchive.hpp>
#include <boost/archive/binary_oarchive.hpp>

#include <boost/bind.hpp>
#include <boost/array.hpp>
#include <boost/shared_ptr.hpp>
#include <boost/tuple/tuple.hpp>

#include <boost/iostreams/stream.hpp> 
#include <boost/iostreams/device/back_inserter.hpp>
#include <boost/iostreams/filtering_stream.hpp>
#include <boost/iostreams/filter/bzip2.hpp>

#include <iomanip>
#include <string>
#include <sstream>
#include <vector>

namespace network
{

typedef boost::asio::ssl::stream<boost::asio::ip::tcp::socket> ssl_socket;
/// The connection class provides serialization primitives on top of a socket.
/**
* Each message sent using this class consists of:
* @li An 8-byte header containing the length of the serialized data in
* hexadecimal.
* @li The serialized data.
*/
class connection : public ssl_socket
{
	typedef boost::archive::binary_oarchive oar;
	typedef boost::archive::binary_iarchive iar;
public:
	/// Constructor.
	connection(boost::asio::io_service& io_service, boost::asio::ssl::context& context)
		: ssl_socket(io_service, context)
	{
	}

	~connection()
	{
	}


	/// Asynchronously write a data structure to the socket.
	template <typename T, typename Handler>
	void async_write(const T& t, Handler handler)
	{
		outbound_data_.clear ();

		boost::iostreams::filtering_ostreambuf out;

		out.push(boost::iostreams::bzip2_compressor());
		out.push(boost::iostreams::back_inserter(outbound_data_));

		{
			oar oa(out);
			oa << t;
		}// archive have to be closed before out.pop()

		out.pop ();

		// Format the header.
		std::ostringstream header_stream;
		header_stream << std::setw(header_length)
			<< std::hex << outbound_data_.size();
		if (!header_stream || header_stream.str().size() != header_length)
		{
			// Something went wrong, inform the caller.
			boost::system::error_code error(boost::asio::error::invalid_argument);
			io_service().post(boost::bind(handler, error));
			return;
		}
		outbound_header_ = header_stream.str();

		// Write the serialized data to the socket. We use "gather-write" to send
		// both the header and the data in a single write operation.
		boost::array<boost::asio::const_buffer, 2> buffers = {
			boost::asio::buffer(outbound_header_),
			boost::asio::buffer(outbound_data_)
		};
		boost::asio::async_write(*this, buffers, handler);
	}

	/// Asynchronously read a data structure from the socket.
	template <typename T, typename Handler>
	void async_read(T& t, Handler handler)
	{
		// Issue a read operation to read exactly the number of bytes in a header.
		void (connection::*f)(
			const boost::system::error_code&,
			T&, boost::tuple<Handler>)
			= &connection::handle_read_header<T, Handler>;
		boost::asio::async_read(*this, boost::asio::buffer(inbound_header_),
			boost::bind(f,
			this, boost::asio::placeholders::error, boost::ref(t),
			boost::make_tuple(handler)));
	}

	/// Handle a completed read of a message header. The handler is passed using
	/// a tuple since boost::bind seems to have trouble binding a function object
	/// created using boost::bind as a parameter.
	template <typename T, typename Handler>
	void handle_read_header(const boost::system::error_code& e,
		T& t, boost::tuple<Handler> handler)
	{
		if (e)
		{
			boost::get<0>(handler)(e);
		}
		else
		{
			// Determine the length of the serialized data.
			std::istringstream is(std::string(inbound_header_, header_length));
			std::size_t inbound_data_size = 0;
			if (!(is >> std::hex >> inbound_data_size))
			{
				// Header doesn't seem to be valid. Inform the caller.
				boost::system::error_code error(boost::asio::error::invalid_argument);
				boost::get<0>(handler)(error);
				return;
			}

			// Start an asynchronous call to receive the data.
			inbound_data_.resize(inbound_data_size);
			void (connection::*f)(
				const boost::system::error_code&,
				T&, boost::tuple<Handler>)
				= &connection::handle_read_data<T, Handler>;
			boost::asio::async_read(*this, 
				boost::asio::buffer(const_cast<char*>(inbound_data_.c_str ()),inbound_data_size),
				boost::bind(f, this,
				boost::asio::placeholders::error, boost::ref(t), handler));
		}
	}

	/// Handle a completed read of message data.
	template <typename T, typename Handler>
	void handle_read_data(const boost::system::error_code& e,
		T& t, boost::tuple<Handler> handler)
	{
		if (e)
		{
			boost::get<0>(handler)(e);
		}
		else
		{
			// Extract the data structure from the data just received.
			try
			{
				boost::iostreams::filtering_istreambuf in;

				in.push(boost::iostreams::bzip2_decompressor());
				in.push(boost::make_iterator_range(inbound_data_));

				{
					iar ia(in);
					ia >> t;
				}
			}
			catch (std::exception& e)
			{
				// Unable to decode data.
				boost::system::error_code error(boost::asio::error::invalid_argument);
				boost::get<0>(handler)(error);
				return;
			}

			// Inform caller that data has been received ok.
			boost::get<0>(handler)(e);
		}
	}

private:
	/// The size of a fixed length header.
	enum { header_length = 8 };

	/// Holds an outbound header.
	std::string outbound_header_;

	/// Holds the outbound data.
	std::string outbound_data_;

	/// Holds an inbound header.
	char inbound_header_[header_length];

	/// Holds the inbound data.
	std::string inbound_data_;
};

typedef boost::shared_ptr<connection> connection_ptr;
}