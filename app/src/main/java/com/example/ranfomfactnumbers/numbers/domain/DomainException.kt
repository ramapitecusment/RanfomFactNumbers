package com.example.ranfomfactnumbers.numbers.domain


abstract class DomainException : IllegalStateException()

class NoInternetConnectionException : DomainException()

class ServiceUnavailableException : DomainException()