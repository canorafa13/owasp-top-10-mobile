package com.owasp.top.mobile.demo.utils


class ObjectNullException(message: String? = "Objeto nulo"): Exception(message)

class ApiErrorException(message: String): Exception(message)