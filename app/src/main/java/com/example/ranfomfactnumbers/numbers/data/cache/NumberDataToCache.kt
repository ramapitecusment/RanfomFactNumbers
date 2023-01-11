package com.example.ranfomfactnumbers.numbers.data.cache

import com.example.ranfomfactnumbers.numbers.data.NumberData

class NumberDataToCache : NumberData.Mapper<NumberCache> {

    override fun map(id: String, fact: String) = NumberCache(id, fact, System.currentTimeMillis())

}