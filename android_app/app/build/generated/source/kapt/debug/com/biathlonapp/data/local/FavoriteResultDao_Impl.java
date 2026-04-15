package com.biathlonapp.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
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
public final class FavoriteResultDao_Impl implements FavoriteResultDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FavoriteResult> __insertionAdapterOfFavoriteResult;

  private final SharedSQLiteStatement __preparedStmtOfDeleteResultsForAthlete;

  public FavoriteResultDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFavoriteResult = new EntityInsertionAdapter<FavoriteResult>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `favorite_results` (`id`,`athleteId`,`discipline`,`date`,`nameRace`,`placeRace`,`startNumber`,`finishPlace`,`missCount`,`lastUpdated`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FavoriteResult entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getAthleteId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getAthleteId());
        }
        if (entity.getDiscipline() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDiscipline());
        }
        if (entity.getDate() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDate());
        }
        if (entity.getNameRace() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getNameRace());
        }
        if (entity.getPlaceRace() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPlaceRace());
        }
        if (entity.getStartNumber() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getStartNumber());
        }
        if (entity.getFinishPlace() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getFinishPlace());
        }
        if (entity.getMissCount() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getMissCount());
        }
        statement.bindLong(10, entity.getLastUpdated());
      }
    };
    this.__preparedStmtOfDeleteResultsForAthlete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM favorite_results WHERE athleteId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertResult(final FavoriteResult result,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfFavoriteResult.insert(result);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAllResults(final List<FavoriteResult> results,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfFavoriteResult.insert(results);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteResultsForAthlete(final String athleteId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteResultsForAthlete.acquire();
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
          __preparedStmtOfDeleteResultsForAthlete.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getResultsForAthlete(final String athleteId,
      final Continuation<? super List<FavoriteResult>> $completion) {
    final String _sql = "SELECT * FROM favorite_results WHERE athleteId = ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (athleteId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, athleteId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<FavoriteResult>>() {
      @Override
      @NonNull
      public List<FavoriteResult> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAthleteId = CursorUtil.getColumnIndexOrThrow(_cursor, "athleteId");
          final int _cursorIndexOfDiscipline = CursorUtil.getColumnIndexOrThrow(_cursor, "discipline");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfNameRace = CursorUtil.getColumnIndexOrThrow(_cursor, "nameRace");
          final int _cursorIndexOfPlaceRace = CursorUtil.getColumnIndexOrThrow(_cursor, "placeRace");
          final int _cursorIndexOfStartNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "startNumber");
          final int _cursorIndexOfFinishPlace = CursorUtil.getColumnIndexOrThrow(_cursor, "finishPlace");
          final int _cursorIndexOfMissCount = CursorUtil.getColumnIndexOrThrow(_cursor, "missCount");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final List<FavoriteResult> _result = new ArrayList<FavoriteResult>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FavoriteResult _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpAthleteId;
            if (_cursor.isNull(_cursorIndexOfAthleteId)) {
              _tmpAthleteId = null;
            } else {
              _tmpAthleteId = _cursor.getString(_cursorIndexOfAthleteId);
            }
            final String _tmpDiscipline;
            if (_cursor.isNull(_cursorIndexOfDiscipline)) {
              _tmpDiscipline = null;
            } else {
              _tmpDiscipline = _cursor.getString(_cursorIndexOfDiscipline);
            }
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpNameRace;
            if (_cursor.isNull(_cursorIndexOfNameRace)) {
              _tmpNameRace = null;
            } else {
              _tmpNameRace = _cursor.getString(_cursorIndexOfNameRace);
            }
            final String _tmpPlaceRace;
            if (_cursor.isNull(_cursorIndexOfPlaceRace)) {
              _tmpPlaceRace = null;
            } else {
              _tmpPlaceRace = _cursor.getString(_cursorIndexOfPlaceRace);
            }
            final Integer _tmpStartNumber;
            if (_cursor.isNull(_cursorIndexOfStartNumber)) {
              _tmpStartNumber = null;
            } else {
              _tmpStartNumber = _cursor.getInt(_cursorIndexOfStartNumber);
            }
            final Integer _tmpFinishPlace;
            if (_cursor.isNull(_cursorIndexOfFinishPlace)) {
              _tmpFinishPlace = null;
            } else {
              _tmpFinishPlace = _cursor.getInt(_cursorIndexOfFinishPlace);
            }
            final Integer _tmpMissCount;
            if (_cursor.isNull(_cursorIndexOfMissCount)) {
              _tmpMissCount = null;
            } else {
              _tmpMissCount = _cursor.getInt(_cursorIndexOfMissCount);
            }
            final long _tmpLastUpdated;
            _tmpLastUpdated = _cursor.getLong(_cursorIndexOfLastUpdated);
            _item = new FavoriteResult(_tmpId,_tmpAthleteId,_tmpDiscipline,_tmpDate,_tmpNameRace,_tmpPlaceRace,_tmpStartNumber,_tmpFinishPlace,_tmpMissCount,_tmpLastUpdated);
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
  public Object hasResults(final String athleteId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM favorite_results WHERE athleteId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (athleteId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, athleteId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
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
