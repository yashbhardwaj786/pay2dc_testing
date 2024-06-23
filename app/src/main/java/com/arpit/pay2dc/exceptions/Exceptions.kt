package com.arpit.pay2dc.exceptions

import java.io.IOException
import java.net.SocketTimeoutException

class NoInternetException(message: String) : IOException(message)
class TimeoutException(message: String) : SocketTimeoutException(message)