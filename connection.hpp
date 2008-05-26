#pragma once

#include <boost/asio.hpp>
#include <boost/asio/ssl.hpp>

#include <boost/archive/binary_iarchive.hpp>
#include <boost/archive/binary_oarchive.hpp>

#include <boost/bind.hpp>
#include <boost/shared_ptr.hpp>
#include <boost/thread/mutex.hpp>
#include <boost/thread/thread.hpp>

#include <boost/iostreams/stream.hpp> 
#include <boost/iostreams/device/back_inserter.hpp>
#include <boost/iostreams/filtering_stream.hpp>
#include <boost/iostreams/filter/bzip2.hpp>

#include <iomanip>
#include <string>
#include <sstream>
#include <vector>
#include <map>

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
	typedef boost::shared_ptr<std::string> buffer_ptr;
public:
	typedef boost::function<void(const boost::system::error_code& error)> Handler;
	/// Constructor.
	connection(boost::asio::io_service& io_service, boost::asio::ssl::context& context)
		: ssl_socket(io_service, context)
	{
	}

	~connection()
	{
	}


	/// Asynchronously write a data structure to the socket.
	template <typename T/*, typename Handler*/>
	void async_write(const T& t, Handler handler)
	{
		boost::mutex::scoped_lock lck(mtx_);
		buffer_ptr outbound_header_(new std::string);
		std::string outbound_data_;

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
		*outbound_header_ = header_stream.str() + outbound_data_;
		
		boost::asio::async_write(*this, boost::asio::buffer(*outbound_header_), 
			boost::bind(&connection::handle_async_write, this, boost::asio::placeholders::error,
			outbound_header_, handler));
	}

	/// free the data buffer after the async_write complete
	//template <typename Handler>
	void handle_async_write(const boost::system::error_code& error,
		buffer_ptr dataPtr,
		Handler handler)
	{
		boost::mutex::scoped_lock lck(mtx_);
		handler(error);
	}

	/// Asynchronously read a data structure from the socket.
	template <typename T>
	void async_read(T& t, Handler handler)
	{
		buffer_ptr inbound_header_(new std::string(header_length, 0));
		// Issue a read operation to read exactly the number of bytes in a header.
		void (connection::*f)(
			const boost::system::error_code&,
			T&, buffer_ptr, Handler)
			= &connection::handle_read_header<T>;
		boost::asio::async_read(*this, 
			boost::asio::buffer(const_cast<char*>(inbound_header_->c_str ()), header_length),
			boost::bind(f,
			this, boost::asio::placeholders::error, boost::ref(t), inbound_header_, handler));
	}

	/// Handle a completed read of a message header. The handler is passed using
	/// a tuple since boost::bind seems to have trouble binding a function object
	/// created using boost::bind as a parameter.
	template <typename T>
	void handle_read_header(const boost::system::error_code& e,
		T& t, buffer_ptr headPtr, Handler handler)
	{
		if (e)
		{
			handler(e);
		}
		else
		{
			// Determine the length of the serialized data.
			std::istringstream is(*headPtr);
			std::size_t inbound_data_size = 0;
			if (!(is >> std::hex >> inbound_data_size))
			{
				// Header doesn't seem to be valid. Inform the caller.
				boost::system::error_code error(boost::asio::error::invalid_argument);
				handler(error);
				return;
			}

			// Start an asynchronous call to receive the data.
			buffer_ptr inbound_data_(new std::string(inbound_data_size, 0));
			void (connection::*f)(
				const boost::system::error_code&,
				T&, buffer_ptr, Handler)
				= &connection::handle_read_data<T>;
			boost::asio::async_read(*this, 
				boost::asio::buffer(const_cast<char*>(inbound_data_->c_str ()),inbound_data_size),
				boost::bind(f, this,
				boost::asio::placeholders::error, boost::ref(t), inbound_data_, handler));
		}
	}

	/// Handle a completed read of message data.
	template <typename T>
	void handle_read_data(const boost::system::error_code& e,
		T& t, buffer_ptr dataPtr, Handler handler)
	{
		if (e)
		{
			handler(e);
		}
		else
		{
			// Extract the data structure from the data just received.
			try
			{
				boost::iostreams::filtering_istreambuf in;

				in.push(boost::iostreams::bzip2_decompressor());
				in.push(boost::make_iterator_range(*dataPtr));

				{
					iar ia(in);
					ia >> t;
				}
			}
			catch (std::exception& e)
			{
				// Unable to decode data.
				boost::system::error_code error(boost::asio::error::invalid_argument);
				handler(error);
				return;
			}

			// Inform caller that data has been received ok.
			handler(e);
		}
	}

private:
	/// The size of a fixed length header.
	enum { header_length = 8 };

	//
	boost::mutex mtx_;

	//write_buf
	std::map<buffer_ptr, Handler> op_buf_;

};

typedef boost::shared_ptr<connection> connection_ptr;
}