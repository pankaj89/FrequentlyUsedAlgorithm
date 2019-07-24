package com.master.fualibrary;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {ItemTable.class}, version = 1, exportSchema = false)
public abstract class FUADatabase extends RoomDatabase {

    public abstract ItemDao itemDao();

    private static FUADatabase appDatabase;

    public static FUADatabase getAppDataBase(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context, FUADatabase.class, "FUADatabase")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabase;
    }
}
