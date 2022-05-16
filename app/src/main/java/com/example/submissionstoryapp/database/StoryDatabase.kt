package com.example.submissionstoryapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.submissionstoryapp.data.ConverterList
import com.example.submissionstoryapp.data.StoriesResponseItem

@Database(
    entities = [StoriesResponseItem::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ConverterList::class)
abstract class StoryDatabase: RoomDatabase() {

    companion object{
        @Volatile
        private var INSTANCE : StoryDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): StoryDatabase{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    StoryDatabase::class.java, "story_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}