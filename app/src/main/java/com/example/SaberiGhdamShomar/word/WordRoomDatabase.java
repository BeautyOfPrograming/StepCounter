package com.example.SaberiGhdamShomar.word;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {Word.class}, version = 2, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {


    public abstract WordDoa wordDao();

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
//        @Override
////        public void onOpen(@NonNull SupportSQLiteDatabase db) {
////            super.onOpen(db);
////
////            // If you want to keep data through app restarts,
////
////        }


        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                WordDoa dao = INSTANCE.wordDao();
//                dao.deleteAll();

                Word word = new Word("84","0,1,32",60+"",000.3+"");
                dao.insert(word);
                word = new Word("40","0,1,00",36+"",0000+"");
                dao.insert(word);
            });
        }
    };
    private static volatile WordRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .addMigrations(MIGRATION_1_3)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static final Migration MIGRATION_1_3 = new Migration(1, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE word_table ADD COLUMN distance TEXT  NOT NULL DEFAULT ''");

        }
    };
}
