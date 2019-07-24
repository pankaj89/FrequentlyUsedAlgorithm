package com.master.fualibrary

import android.content.Context

class FrequentlyUsedAlgorithm(private val context: Context, private val dataset: String) {

    val WEIGHT = 0.9f

    private val itemDao = FUADatabase.getAppDataBase(context).itemDao()

    fun access(itemId: String) {
        itemDao.increaseWeight(dataset, WEIGHT)
        val item = itemDao.getItem(dataset, itemId)
        if (item != null) {
            itemDao.updateCurrent(dataset, itemId, System.currentTimeMillis())
        } else {
            val item = ItemTable().apply {
                this.itemId = itemId
                this.dataSet = dataset
                this.startTimestamp = System.currentTimeMillis() - 1
                this.accessTimestamp = System.currentTimeMillis()
                this.accessCount = 1
                this.crfScore = 1f
            }
            itemDao.addItem(item)
        }
    }

    fun getFrequentlyUsedList(): ArrayList<String> {
        val list = itemDao.getAllList(dataset)
        val sortedList = list.sortedWith(compareByDescending { it.crfScore }).map { it.itemId }
        return ArrayList(sortedList)
    }

    fun getMostlyUsedList(): ArrayList<String> {
        val list = itemDao.getMostlyUsedList(dataset).map { it.itemId }
        return ArrayList(list)
    }

    fun getRecentlyUsedList(): ArrayList<String> {
        val list = itemDao.getRecentUsedList(dataset).map { it.itemId }
        return ArrayList(list)
    }
}
