package com.master.fualibrary;

import androidx.room.*;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addItem(ItemTable itemTable);

    @Update
    void updateItem(ItemTable itemTable);

    @Query("SELECT * FROM ItemTable where dataset=:dataset AND itemId LIKE  :itemId ")
    ItemTable getItem(String dataset, String itemId);

    @Query("SELECT * FROM ItemTable where dataset=:dataset")
    List<ItemTable> getAllList(String dataset);

    @Query("SELECT * FROM ItemTable where dataset=:dataset ORDER BY access_count DESC")
    List<ItemTable> getMostlyUsedList(String dataset);

    @Query("SELECT * FROM ItemTable where dataset=:dataset ORDER BY access_timestamp DESC")
    List<ItemTable> getRecentUsedList(String dataset);

    @Query("Update ItemTable SET score = score * :weight where dataset=:dataset")
    void increaseWeight(String dataset, float weight);

    @Query("Update ItemTable SET score = score + 1, access_count = access_count + 1, access_timestamp=:currentTimestamp where dataset=:dataset AND itemId LIKE  :itemId")
    void updateCurrent(String dataset, String itemId, long currentTimestamp);
}
