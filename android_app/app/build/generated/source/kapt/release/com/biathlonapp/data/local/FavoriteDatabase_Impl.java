package com.biathlonapp.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class FavoriteDatabase_Impl extends FavoriteDatabase {
  private volatile FavoriteAthleteDao _favoriteAthleteDao;

  private volatile FavoriteResultDao _favoriteResultDao;

  private volatile NewsDao _newsDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `favorite_athletes` (`athleteId` TEXT NOT NULL, `name` TEXT NOT NULL, `surname` TEXT NOT NULL, `gender` TEXT NOT NULL, `sportsRank` TEXT NOT NULL, `photoUrl` TEXT, `birthDate` TEXT, `region` TEXT, `coach` TEXT, `biography` TEXT, `lastName` TEXT, `firstName` TEXT, `regionCode` TEXT, `lastUpdated` INTEGER NOT NULL, `isFavorite` INTEGER NOT NULL, `dateAdded` INTEGER NOT NULL, PRIMARY KEY(`athleteId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `favorite_results` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `athleteId` TEXT NOT NULL, `discipline` TEXT, `date` TEXT, `nameRace` TEXT, `placeRace` TEXT, `startNumber` INTEGER, `finishPlace` INTEGER, `missCount` INTEGER, `lastUpdated` INTEGER NOT NULL, FOREIGN KEY(`athleteId`) REFERENCES `favorite_athletes`(`athleteId`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_favorite_results_athleteId` ON `favorite_results` (`athleteId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `cached_news` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `content` TEXT NOT NULL, `date` INTEGER NOT NULL, `imageUrl` TEXT, `source` TEXT NOT NULL, `vkPostId` INTEGER NOT NULL, `likesCount` INTEGER NOT NULL, `commentsCount` INTEGER NOT NULL, `repostsCount` INTEGER NOT NULL, `cachedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'b502b64475dd20e53e3a5fdca31667ed')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `favorite_athletes`");
        db.execSQL("DROP TABLE IF EXISTS `favorite_results`");
        db.execSQL("DROP TABLE IF EXISTS `cached_news`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsFavoriteAthletes = new HashMap<String, TableInfo.Column>(16);
        _columnsFavoriteAthletes.put("athleteId", new TableInfo.Column("athleteId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteAthletes.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteAthletes.put("surname", new TableInfo.Column("surname", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteAthletes.put("gender", new TableInfo.Column("gender", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteAthletes.put("sportsRank", new TableInfo.Column("sportsRank", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteAthletes.put("photoUrl", new TableInfo.Column("photoUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteAthletes.put("birthDate", new TableInfo.Column("birthDate", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteAthletes.put("region", new TableInfo.Column("region", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteAthletes.put("coach", new TableInfo.Column("coach", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteAthletes.put("biography", new TableInfo.Column("biography", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteAthletes.put("lastName", new TableInfo.Column("lastName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteAthletes.put("firstName", new TableInfo.Column("firstName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteAthletes.put("regionCode", new TableInfo.Column("regionCode", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteAthletes.put("lastUpdated", new TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteAthletes.put("isFavorite", new TableInfo.Column("isFavorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteAthletes.put("dateAdded", new TableInfo.Column("dateAdded", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFavoriteAthletes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFavoriteAthletes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFavoriteAthletes = new TableInfo("favorite_athletes", _columnsFavoriteAthletes, _foreignKeysFavoriteAthletes, _indicesFavoriteAthletes);
        final TableInfo _existingFavoriteAthletes = TableInfo.read(db, "favorite_athletes");
        if (!_infoFavoriteAthletes.equals(_existingFavoriteAthletes)) {
          return new RoomOpenHelper.ValidationResult(false, "favorite_athletes(com.biathlonapp.data.local.FavoriteAthlete).\n"
                  + " Expected:\n" + _infoFavoriteAthletes + "\n"
                  + " Found:\n" + _existingFavoriteAthletes);
        }
        final HashMap<String, TableInfo.Column> _columnsFavoriteResults = new HashMap<String, TableInfo.Column>(10);
        _columnsFavoriteResults.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteResults.put("athleteId", new TableInfo.Column("athleteId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteResults.put("discipline", new TableInfo.Column("discipline", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteResults.put("date", new TableInfo.Column("date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteResults.put("nameRace", new TableInfo.Column("nameRace", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteResults.put("placeRace", new TableInfo.Column("placeRace", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteResults.put("startNumber", new TableInfo.Column("startNumber", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteResults.put("finishPlace", new TableInfo.Column("finishPlace", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteResults.put("missCount", new TableInfo.Column("missCount", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteResults.put("lastUpdated", new TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFavoriteResults = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysFavoriteResults.add(new TableInfo.ForeignKey("favorite_athletes", "CASCADE", "NO ACTION", Arrays.asList("athleteId"), Arrays.asList("athleteId")));
        final HashSet<TableInfo.Index> _indicesFavoriteResults = new HashSet<TableInfo.Index>(1);
        _indicesFavoriteResults.add(new TableInfo.Index("index_favorite_results_athleteId", false, Arrays.asList("athleteId"), Arrays.asList("ASC")));
        final TableInfo _infoFavoriteResults = new TableInfo("favorite_results", _columnsFavoriteResults, _foreignKeysFavoriteResults, _indicesFavoriteResults);
        final TableInfo _existingFavoriteResults = TableInfo.read(db, "favorite_results");
        if (!_infoFavoriteResults.equals(_existingFavoriteResults)) {
          return new RoomOpenHelper.ValidationResult(false, "favorite_results(com.biathlonapp.data.local.FavoriteResult).\n"
                  + " Expected:\n" + _infoFavoriteResults + "\n"
                  + " Found:\n" + _existingFavoriteResults);
        }
        final HashMap<String, TableInfo.Column> _columnsCachedNews = new HashMap<String, TableInfo.Column>(11);
        _columnsCachedNews.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedNews.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedNews.put("content", new TableInfo.Column("content", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedNews.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedNews.put("imageUrl", new TableInfo.Column("imageUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedNews.put("source", new TableInfo.Column("source", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedNews.put("vkPostId", new TableInfo.Column("vkPostId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedNews.put("likesCount", new TableInfo.Column("likesCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedNews.put("commentsCount", new TableInfo.Column("commentsCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedNews.put("repostsCount", new TableInfo.Column("repostsCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedNews.put("cachedAt", new TableInfo.Column("cachedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCachedNews = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCachedNews = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCachedNews = new TableInfo("cached_news", _columnsCachedNews, _foreignKeysCachedNews, _indicesCachedNews);
        final TableInfo _existingCachedNews = TableInfo.read(db, "cached_news");
        if (!_infoCachedNews.equals(_existingCachedNews)) {
          return new RoomOpenHelper.ValidationResult(false, "cached_news(com.biathlonapp.data.local.CachedNews).\n"
                  + " Expected:\n" + _infoCachedNews + "\n"
                  + " Found:\n" + _existingCachedNews);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "b502b64475dd20e53e3a5fdca31667ed", "5ca3031e48f7e9397b2f6ac2e160cb52");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "favorite_athletes","favorite_results","cached_news");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `favorite_athletes`");
      _db.execSQL("DELETE FROM `favorite_results`");
      _db.execSQL("DELETE FROM `cached_news`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(FavoriteAthleteDao.class, FavoriteAthleteDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(FavoriteResultDao.class, FavoriteResultDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(NewsDao.class, NewsDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public FavoriteAthleteDao favoriteAthleteDao() {
    if (_favoriteAthleteDao != null) {
      return _favoriteAthleteDao;
    } else {
      synchronized(this) {
        if(_favoriteAthleteDao == null) {
          _favoriteAthleteDao = new FavoriteAthleteDao_Impl(this);
        }
        return _favoriteAthleteDao;
      }
    }
  }

  @Override
  public FavoriteResultDao favoriteResultDao() {
    if (_favoriteResultDao != null) {
      return _favoriteResultDao;
    } else {
      synchronized(this) {
        if(_favoriteResultDao == null) {
          _favoriteResultDao = new FavoriteResultDao_Impl(this);
        }
        return _favoriteResultDao;
      }
    }
  }

  @Override
  public NewsDao newsDao() {
    if (_newsDao != null) {
      return _newsDao;
    } else {
      synchronized(this) {
        if(_newsDao == null) {
          _newsDao = new NewsDao_Impl(this);
        }
        return _newsDao;
      }
    }
  }
}
