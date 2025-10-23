package com.example.proyecto1.model;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class userDao_Impl implements userDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  public userDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `usuarios` (`id`,`boleta`,`nombre`,`correo`,`carrera`,`contraseña`,`tipo`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final User entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getBoleta() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getBoleta());
        }
        if (entity.getNombre() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getNombre());
        }
        if (entity.getCorreo() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getCorreo());
        }
        if (entity.getCarrera() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getCarrera());
        }
        if (entity.getContraseña() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getContraseña());
        }
        if (entity.getTipo() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getTipo());
        }
      }
    };
  }

  @Override
  public Object insertar(final User usuario, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUser.insert(usuario);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object obtenerPorCorreo(final String correo,
      final Continuation<? super User> $completion) {
    final String _sql = "SELECT * FROM usuarios WHERE correo = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (correo == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, correo);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBoleta = CursorUtil.getColumnIndexOrThrow(_cursor, "boleta");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfCorreo = CursorUtil.getColumnIndexOrThrow(_cursor, "correo");
          final int _cursorIndexOfCarrera = CursorUtil.getColumnIndexOrThrow(_cursor, "carrera");
          final int _cursorIndexOfContraseA = CursorUtil.getColumnIndexOrThrow(_cursor, "contraseña");
          final int _cursorIndexOfTipo = CursorUtil.getColumnIndexOrThrow(_cursor, "tipo");
          final User _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpBoleta;
            if (_cursor.isNull(_cursorIndexOfBoleta)) {
              _tmpBoleta = null;
            } else {
              _tmpBoleta = _cursor.getString(_cursorIndexOfBoleta);
            }
            final String _tmpNombre;
            if (_cursor.isNull(_cursorIndexOfNombre)) {
              _tmpNombre = null;
            } else {
              _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            }
            final String _tmpCorreo;
            if (_cursor.isNull(_cursorIndexOfCorreo)) {
              _tmpCorreo = null;
            } else {
              _tmpCorreo = _cursor.getString(_cursorIndexOfCorreo);
            }
            final String _tmpCarrera;
            if (_cursor.isNull(_cursorIndexOfCarrera)) {
              _tmpCarrera = null;
            } else {
              _tmpCarrera = _cursor.getString(_cursorIndexOfCarrera);
            }
            final String _tmpContraseña;
            if (_cursor.isNull(_cursorIndexOfContraseA)) {
              _tmpContraseña = null;
            } else {
              _tmpContraseña = _cursor.getString(_cursorIndexOfContraseA);
            }
            final String _tmpTipo;
            if (_cursor.isNull(_cursorIndexOfTipo)) {
              _tmpTipo = null;
            } else {
              _tmpTipo = _cursor.getString(_cursorIndexOfTipo);
            }
            _result = new User(_tmpId,_tmpBoleta,_tmpNombre,_tmpCorreo,_tmpCarrera,_tmpContraseña,_tmpTipo);
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
