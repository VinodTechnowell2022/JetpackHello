package com.tw.recyclerviewjetpackdemo

data class DataSource(val description:String)


fun dummyData():MutableList<DataSource>{

    val list = mutableListOf<DataSource>()
    list.add(DataSource("Item 1"))
    list.add(DataSource("Item 2"))
    list.add(DataSource("Item 3"))
    list.add(DataSource("Item 4"))
    list.add(DataSource("Item 5"))
    list.add(DataSource("Item 6"))
    list.add(DataSource("Item 7"))
    list.add(DataSource("Item 8"))
    list.add(DataSource("Item 9"))
    list.add(DataSource("Item 10"))
    list.add(DataSource("Item 11"))
    list.add(DataSource("Item 12"))
    list.add(DataSource("Item 13"))

    return list
}
