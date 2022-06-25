package com.sample

enum class TopicEnum(val value: String) {
    CCTV("cctv"),
    TRAVELING("traveling"),
    ALL("all"),
    UBIKE("ubike"),
    WATER("water"),
    STOCK("stock"),
    CMS("cms");
    //AIR("AIR"),

    companion object {
        fun parsing(topic: String) = values().firstOrNull {
            it.value == topic
        } ?: ALL
    }
}