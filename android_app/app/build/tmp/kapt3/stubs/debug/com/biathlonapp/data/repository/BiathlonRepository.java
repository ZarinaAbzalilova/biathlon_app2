package com.biathlonapp.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\nJ\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u0006H\u0086@\u00a2\u0006\u0002\u0010\u000eJ \u0010\u000f\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\u0011J\"\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u00062\u0006\u0010\u0013\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/biathlonapp/data/repository/BiathlonRepository;", "", "()V", "apiService", "Lcom/biathlonapp/data/api/BiathlonApiService;", "getAthleteResults", "Lcom/biathlonapp/utils/Result;", "Lcom/biathlonapp/data/model/AthleteResultsResponse;", "athleteId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAthletes", "", "Lcom/biathlonapp/data/model/Athlete;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRacePdfUrl", "raceId", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchAthletes", "query", "app_debug"})
public final class BiathlonRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.biathlonapp.data.api.BiathlonApiService apiService = null;
    
    public BiathlonRepository() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAthletes(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.biathlonapp.utils.Result<? extends java.util.List<com.biathlonapp.data.model.Athlete>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object searchAthletes(@org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.biathlonapp.utils.Result<? extends java.util.List<com.biathlonapp.data.model.Athlete>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAthleteResults(@org.jetbrains.annotations.NotNull()
    java.lang.String athleteId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.biathlonapp.utils.Result<com.biathlonapp.data.model.AthleteResultsResponse>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getRacePdfUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String raceId, @org.jetbrains.annotations.NotNull()
    java.lang.String athleteId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
}