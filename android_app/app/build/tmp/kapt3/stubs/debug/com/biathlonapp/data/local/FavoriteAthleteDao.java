package com.biathlonapp.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0010\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001e\u0010\u0012\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u0014H\u00a7@\u00a2\u0006\u0002\u0010\u0015\u00a8\u0006\u0016"}, d2 = {"Lcom/biathlonapp/data/local/FavoriteAthleteDao;", "", "deleteFavorite", "", "athlete", "Lcom/biathlonapp/data/local/FavoriteAthlete;", "(Lcom/biathlonapp/data/local/FavoriteAthlete;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteFavoriteById", "athleteId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllFavorites", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFavoriteById", "insertFavorite", "isFavorite", "", "updateLastUpdated", "timestamp", "", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface FavoriteAthleteDao {
    
    @androidx.room.Query(value = "SELECT * FROM favorite_athletes ORDER BY dateAdded DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllFavorites(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.biathlonapp.data.local.FavoriteAthlete>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM favorite_athletes WHERE athleteId = :athleteId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getFavoriteById(@org.jetbrains.annotations.NotNull()
    java.lang.String athleteId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.biathlonapp.data.local.FavoriteAthlete> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertFavorite(@org.jetbrains.annotations.NotNull()
    com.biathlonapp.data.local.FavoriteAthlete athlete, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteFavorite(@org.jetbrains.annotations.NotNull()
    com.biathlonapp.data.local.FavoriteAthlete athlete, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM favorite_athletes WHERE athleteId = :athleteId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteFavoriteById(@org.jetbrains.annotations.NotNull()
    java.lang.String athleteId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT EXISTS(SELECT 1 FROM favorite_athletes WHERE athleteId = :athleteId)")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object isFavorite(@org.jetbrains.annotations.NotNull()
    java.lang.String athleteId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
    
    @androidx.room.Query(value = "UPDATE favorite_athletes SET lastUpdated = :timestamp WHERE athleteId = :athleteId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateLastUpdated(@org.jetbrains.annotations.NotNull()
    java.lang.String athleteId, long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}