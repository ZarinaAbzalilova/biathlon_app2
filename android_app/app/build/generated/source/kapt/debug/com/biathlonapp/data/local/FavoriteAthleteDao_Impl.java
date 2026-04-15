package com.biathlonapp.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class FavoriteAthleteDao_Impl implements FavoriteAthleteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FavoriteAthlete> __insertionAdapterOfFavoriteAthlete;

  private final EntityDeletionOrUpdateAdapter<FavoriteAthlete> __deletionAdapterOfFavoriteAthlete;

  private final SharedSQLiteStatement __preparedStmtOfDeleteFavoriteById;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLastUpdated;

  public FavoriteAthleteDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFavoriteAthlete = new EntityInsertionAdapter<FavoriteAthlete>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `favorite_athletes` (`athleteId`,`name`,`surname`,`gender`,`sportsRank`,`photoUrl`,`birthDate`,`region`,`coach`,`biography`,`lastName`,`firstName`,`regionCode`,`lastUpdated`,`isFavorite`,`dateAdded`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FavoriteAthlete entity) {
        if (entity.getAthleteId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getAthleteId());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getSurname() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSurname());
        }
        if (entity.getGender() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getGender());
        }
        if (entity.getSportsRank() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getSportsRank());
        }
        if (entity.getPhotoUrl() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPhotoUrl());
        }
        if (entity.getBirthDate() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getBirthDate());
        }
        if (entity.getRegion() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getRegion());
        }
        if (entity.getCoach() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getCoach());
        }
        if (entity.getBiography() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getBiography());
        }
        if (entity.getLastName() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getLastName());
        }
        if (entity.getFirstName() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getFirstName());
        }
        if (entity.getRegionCode() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getRegionCode());
        }
        statement.bindLong(14, entity.getLastUpdated());
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(15, _tmp);
        statement.bindLong(16, entity.getDateAdded());
      }
    };
    this.__deletionAdapterOfFavoriteAthlete = new EntityDeletionOrUpdateAdapter<FavoriteAthlete>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `favorite_athletes` WHERE `athleteId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FavoriteAthlete entity) {
        if (entity.getAthleteId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getAthleteId());
        }
      }
    };
    this.__preparedStmtOfDeleteFavoriteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM favorite_athletes WHERE athleteId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateLastUpdated = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE favorite_athletes SET lastUpdated = ? WHERE athleteId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertFavorite(final FavoriteAthlete athlete,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfFavoriteAthlete.insert(athlete);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteFavorite(final FavoriteAthlete athlete,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfFavoriteAthlete.handle(athlete);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteFavoriteById(final String athleteId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteFavoriteById.acquire();
        int _argIndex = 1;
        if (athleteId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, athleteId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteFavoriteById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateLastUpdated(final String athleteId, final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateLastUpdated.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 2;
        if (athleteId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, athleteId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateLastUpdated.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllFavorites(final Continuation<? super List<FavoriteAthlete>> $completion) {
    final String _sql = "SELECT * FROM favorite_athletes ORDER BY dateAdded DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<FavoriteAthlete>>() {
      @Override
      @NonNull
      public List<FavoriteAthlete> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAthleteId = CursorUtil.getColumnIndexOrThrow(_cursor, "athleteId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSurname = CursorUtil.getColumnIndexOrThrow(_cursor, "surname");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfSportsRank = CursorUtil.getColumnIndexOrThrow(_cursor, "sportsRank");
          final int _cursorIndexOfPhotoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUrl");
          final int _cursorIndexOfBirthDate = CursorUtil.getColumnIndexOrThrow(_cursor, "birthDate");
          final int _cursorIndexOfRegion = CursorUtil.getColumnIndexOrThrow(_cursor, "region");
          final int _cursorIndexOfCoach = CursorUtil.getColumnIndexOrThrow(_cursor, "coach");
          final int _cursorIndexOfBiography = CursorUtil.getColumnIndexOrThrow(_cursor, "biography");
          final int _cursorIndexOfLastName = CursorUtil.getColumnIndexOrThrow(_cursor, "lastName");
          final int _cursorIndexOfFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "firstName");
          final int _cursorIndexOfRegionCode = CursorUtil.getColumnIndexOrThrow(_cursor, "regionCode");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfDateAdded = CursorUtil.getColumnIndexOrThrow(_cursor, "dateAdded");
          final List<FavoriteAthlete> _result = new ArrayList<FavoriteAthlete>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FavoriteAthlete _item;
            final String _tmpAthleteId;
            if (_cursor.isNull(_cursorIndexOfAthleteId)) {
              _tmpAthleteId = null;
            } else {
              _tmpAthleteId = _cursor.getString(_cursorIndexOfAthleteId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpSurname;
            if (_cursor.isNull(_cursorIndexOfSurname)) {
              _tmpSurname = null;
            } else {
              _tmpSurname = _cursor.getString(_cursorIndexOfSurname);
            }
            final String _tmpGender;
            if (_cursor.isNull(_cursorIndexOfGender)) {
              _tmpGender = null;
            } else {
              _tmpGender = _cursor.getString(_cursorIndexOfGender);
            }
            final String _tmpSportsRank;
            if (_cursor.isNull(_cursorIndexOfSportsRank)) {
              _tmpSportsRank = null;
            } else {
              _tmpSportsRank = _cursor.getString(_cursorIndexOfSportsRank);
            }
            final String _tmpPhotoUrl;
            if (_cursor.isNull(_cursorIndexOfPhotoUrl)) {
              _tmpPhotoUrl = null;
            } else {
              _tmpPhotoUrl = _cursor.getString(_cursorIndexOfPhotoUrl);
            }
            final String _tmpBirthDate;
            if (_cursor.isNull(_cursorIndexOfBirthDate)) {
              _tmpBirthDate = null;
            } else {
              _tmpBirthDate = _cursor.getString(_cursorIndexOfBirthDate);
            }
            final String _tmpRegion;
            if (_cursor.isNull(_cursorIndexOfRegion)) {
              _tmpRegion = null;
            } else {
              _tmpRegion = _cursor.getString(_cursorIndexOfRegion);
            }
            final String _tmpCoach;
            if (_cursor.isNull(_cursorIndexOfCoach)) {
              _tmpCoach = null;
            } else {
              _tmpCoach = _cursor.getString(_cursorIndexOfCoach);
            }
            final String _tmpBiography;
            if (_cursor.isNull(_cursorIndexOfBiography)) {
              _tmpBiography = null;
            } else {
              _tmpBiography = _cursor.getString(_cursorIndexOfBiography);
            }
            final String _tmpLastName;
            if (_cursor.isNull(_cursorIndexOfLastName)) {
              _tmpLastName = null;
            } else {
              _tmpLastName = _cursor.getString(_cursorIndexOfLastName);
            }
            final String _tmpFirstName;
            if (_cursor.isNull(_cursorIndexOfFirstName)) {
              _tmpFirstName = null;
            } else {
              _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
            }
            final String _tmpRegionCode;
            if (_cursor.isNull(_cursorIndexOfRegionCode)) {
              _tmpRegionCode = null;
            } else {
              _tmpRegionCode = _cursor.getString(_cursorIndexOfRegionCode);
            }
            final long _tmpLastUpdated;
            _tmpLastUpdated = _cursor.getLong(_cursorIndexOfLastUpdated);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final long _tmpDateAdded;
            _tmpDateAdded = _cursor.getLong(_cursorIndexOfDateAdded);
            _item = new FavoriteAthlete(_tmpAthleteId,_tmpName,_tmpSurname,_tmpGender,_tmpSportsRank,_tmpPhotoUrl,_tmpBirthDate,_tmpRegion,_tmpCoach,_tmpBiography,_tmpLastName,_tmpFirstName,_tmpRegionCode,_tmpLastUpdated,_tmpIsFavorite,_tmpDateAdded);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getFavoriteById(final String athleteId,
      final Continuation<? super FavoriteAthlete> $completion) {
    final String _sql = "SELECT * FROM favorite_athletes WHERE athleteId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (athleteId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, athleteId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<FavoriteAthlete>() {
      @Override
      @Nullable
      public FavoriteAthlete call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAthleteId = CursorUtil.getColumnIndexOrThrow(_cursor, "athleteId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSurname = CursorUtil.getColumnIndexOrThrow(_cursor, "surname");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfSportsRank = CursorUtil.getColumnIndexOrThrow(_cursor, "sportsRank");
          final int _cursorIndexOfPhotoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUrl");
          final int _cursorIndexOfBirthDate = CursorUtil.getColumnIndexOrThrow(_cursor, "birthDate");
          final int _cursorIndexOfRegion = CursorUtil.getColumnIndexOrThrow(_cursor, "region");
          final int _cursorIndexOfCoach = CursorUtil.getColumnIndexOrThrow(_cursor, "coach");
          final int _cursorIndexOfBiography = CursorUtil.getColumnIndexOrThrow(_cursor, "biography");
          final int _cursorIndexOfLastName = CursorUtil.getColumnIndexOrThrow(_cursor, "lastName");
          final int _cursorIndexOfFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "firstName");
          final int _cursorIndexOfRegionCode = CursorUtil.getColumnIndexOrThrow(_cursor, "regionCode");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfDateAdded = CursorUtil.getColumnIndexOrThrow(_cursor, "dateAdded");
          final FavoriteAthlete _result;
          if (_cursor.moveToFirst()) {
            final String _tmpAthleteId;
            if (_cursor.isNull(_cursorIndexOfAthleteId)) {
              _tmpAthleteId = null;
            } else {
              _tmpAthleteId = _cursor.getString(_cursorIndexOfAthleteId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpSurname;
            if (_cursor.isNull(_cursorIndexOfSurname)) {
              _tmpSurname = null;
            } else {
              _tmpSurname = _cursor.getString(_cursorIndexOfSurname);
            }
            final String _tmpGender;
            if (_cursor.isNull(_cursorIndexOfGender)) {
              _tmpGender = null;
            } else {
              _tmpGender = _cursor.getString(_cursorIndexOfGender);
            }
            final String _tmpSportsRank;
            if (_cursor.isNull(_cursorIndexOfSportsRank)) {
              _tmpSportsRank = null;
            } else {
              _tmpSportsRank = _cursor.getString(_cursorIndexOfSportsRank);
            }
            final String _tmpPhotoUrl;
            if (_cursor.isNull(_cursorIndexOfPhotoUrl)) {
              _tmpPhotoUrl = null;
            } else {
              _tmpPhotoUrl = _cursor.getString(_cursorIndexOfPhotoUrl);
            }
            final String _tmpBirthDate;
            if (_cursor.isNull(_cursorIndexOfBirthDate)) {
              _tmpBirthDate = null;
            } else {
              _tmpBirthDate = _cursor.getString(_cursorIndexOfBirthDate);
            }
            final String _tmpRegion;
            if (_cursor.isNull(_cursorIndexOfRegion)) {
              _tmpRegion = null;
            } else {
              _tmpRegion = _cursor.getString(_cursorIndexOfRegion);
            }
            final String _tmpCoach;
            if (_cursor.isNull(_cursorIndexOfCoach)) {
              _tmpCoach = null;
            } else {
              _tmpCoach = _cursor.getString(_cursorIndexOfCoach);
            }
            final String _tmpBiography;
            if (_cursor.isNull(_cursorIndexOfBiography)) {
              _tmpBiography = null;
            } else {
              _tmpBiography = _cursor.getString(_cursorIndexOfBiography);
            }
            final String _tmpLastName;
            if (_cursor.isNull(_cursorIndexOfLastName)) {
              _tmpLastName = null;
            } else {
              _tmpLastName = _cursor.getString(_cursorIndexOfLastName);
            }
            final String _tmpFirstName;
            if (_cursor.isNull(_cursorIndexOfFirstName)) {
              _tmpFirstName = null;
            } else {
              _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
            }
            final String _tmpRegionCode;
            if (_cursor.isNull(_cursorIndexOfRegionCode)) {
              _tmpRegionCode = null;
            } else {
              _tmpRegionCode = _cursor.getString(_cursorIndexOfRegionCode);
            }
            final long _tmpLastUpdated;
            _tmpLastUpdated = _cursor.getLong(_cursorIndexOfLastUpdated);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final long _tmpDateAdded;
            _tmpDateAdded = _cursor.getLong(_cursorIndexOfDateAdded);
            _result = new FavoriteAthlete(_tmpAthleteId,_tmpName,_tmpSurname,_tmpGender,_tmpSportsRank,_tmpPhotoUrl,_tmpBirthDate,_tmpRegion,_tmpCoach,_tmpBiography,_tmpLastName,_tmpFirstName,_tmpRegionCode,_tmpLastUpdated,_tmpIsFavorite,_tmpDateAdded);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object isFavorite(final String athleteId,
      final Continuation<? super Boolean> $completion) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM favorite_athletes WHERE athleteId = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (athleteId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, athleteId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Boolean>() {
      @Override
      @NonNull
      public Boolean call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Boolean _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp == null ? null : _tmp != 0;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
