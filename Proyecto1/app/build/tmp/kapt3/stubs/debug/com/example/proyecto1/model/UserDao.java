package com.example.proyecto1.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\n\u00a8\u0006\u000b"}, d2 = {"Lcom/example/proyecto1/model/UserDao;", "", "insertar", "", "usuario", "Lcom/example/proyecto1/model/User;", "(Lcom/example/proyecto1/model/User;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "obtenerPorCorreo", "correo", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface UserDao {
    
    @androidx.room.Insert(onConflict = 3)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertar(@org.jetbrains.annotations.NotNull()
    com.example.proyecto1.model.User usuario, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM usuarios WHERE correo = :correo")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object obtenerPorCorreo(@org.jetbrains.annotations.NotNull()
    java.lang.String correo, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.proyecto1.model.User> $completion);
}