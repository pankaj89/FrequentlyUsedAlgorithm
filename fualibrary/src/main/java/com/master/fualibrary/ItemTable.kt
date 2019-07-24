package com.master.fualibrary

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.math.MathContext


@Entity(tableName = "ItemTable")
class ItemTable {
    @PrimaryKey
    @ColumnInfo(name = "itemId")
    var itemId: String = ""

    @ColumnInfo(name = "dataset")
    var dataSet: String = ""

    @ColumnInfo(name = "start_timestamp")
    var startTimestamp: Long = 0

    @ColumnInfo(name = "access_timestamp")
    var accessTimestamp: Long = 0

    @ColumnInfo(name = "access_count")
    var accessCount: Int= 1

    @ColumnInfo(name = "score")
    var crfScore: Float= 1.0f

    @Transient
    var calculation: BigDecimal = BigDecimal(0.0)

    override fun toString(): String {
        return "$itemId ($crfScore)"
    }
}