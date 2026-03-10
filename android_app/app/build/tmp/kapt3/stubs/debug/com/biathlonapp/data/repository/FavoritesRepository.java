package com.biathlonapp.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0012J\u0016\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010\u0017J\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0019H\u0086@\u00a2\u0006\u0002\u0010\u001aJ\u0012\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00190\u001cJ\u001c\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00192\u0006\u0010\u0015\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010\u0017J\u0018\u0010\u001f\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010\u0017J\u0016\u0010 \u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010\u0017J\u0016\u0010!\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010\u0017J\u0016\u0010\"\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010\u0017J$\u0010#\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\u0019H\u0086@\u00a2\u0006\u0002\u0010&J\u0016\u0010\'\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006("}, d2 = {"Lcom/biathlonapp/data/repository/FavoritesRepository;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "db", "Lcom/biathlonapp/data/local/FavoriteDatabase;", "favoriteAthleteDao", "Lcom/biathlonapp/data/local/FavoriteAthleteDao;", "favoriteResultDao", "Lcom/biathlonapp/data/local/FavoriteResultDao;", "addToFavorites", "", "favoriteAthlete", "Lcom/biathlonapp/data/local/FavoriteAthlete;", "(Lcom/biathlonapp/data/local/FavoriteAthlete;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "athlete", "Lcom/biathlonapp/data/model/Athlete;", "(Lcom/biathlonapp/data/model/Athlete;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteResultsForAthlete", "", "athleteId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllFavorites", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllFavoritesFlow", "Lkotlinx/coroutines/flow/Flow;", "getCachedResults", "Lcom/biathlonapp/data/local/FavoriteResult;", "getFavoriteById", "hasCachedResults", "isFavorite", "removeFromFavorites", "saveAthleteResults", "results", "Lcom/biathlonapp/data/model/RaceResult;", "(Ljava/lang/String;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateFavoriteAthlete", "app_debug"})
public final class FavoritesRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.biathlonapp.data.local.FavoriteDatabase db = null;
    @org.jetbrains.annotations.NotNull()
    private final com.biathlonapp.data.local.FavoriteAthleteDao favoriteAthleteDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.biathlonapp.data.local.FavoriteResultDao favoriteResultDao = null;
    
    public FavoritesRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAllFavorites(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.biathlonapp.data.local.FavoriteAthlete>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.biathlonapp.data.local.FavoriteAthlete>> getAllFavoritesFlow() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addToFavorites(@org.jetbrains.annotations.NotNull()
    com.biathlonapp.data.model.Athlete athlete, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addToFavorites(@org.jetbrains.annotations.NotNull()
    com.biathlonapp.data.local.FavoriteAthlete favoriteAthlete, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object removeFromFavorites(@org.jetbrains.annotations.NotNull()
    java.lang.String athleteId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object isFavorite(@org.jetbrains.annotations.NotNull()
    java.lang.String athleteId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateFavoriteAthlete(@org.jetbrains.annotations.NotNull()
    com.biathlonapp.data.model.Athlete athlete, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getFavoriteById(@org.jetbrains.annotations.NotNull()
    java.lang.String athleteId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.biathlonapp.data.local.FavoriteAthlete> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveAthleteResults(@org.jetbrains.annotations.NotNull()
    java.lang.String athleteId, @org.jetbrains.annotations.NotNull()
    java.util.List<com.biathlonapp.data.model.RaceResult> results, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getCachedResults(@org.jetbrains.annotations.NotNull()
    java.lang.String athleteId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.biathlonapp.data.local.FavoriteResult>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object hasCachedResults(@org.jetbrains.annotations.NotNull()
    java.lang.String athleteId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteResultsForAthlete(@org.jetbrains.annotations.NotNull()
    java.lang.String athleteId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}