package com.sample

data class PageSearchParams(val topic: String, val filterName: String? = null, val count: Int = 10)