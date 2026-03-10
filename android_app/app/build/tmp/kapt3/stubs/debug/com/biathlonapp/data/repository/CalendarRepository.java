package com.biathlonapp.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\"\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ*\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/biathlonapp/data/repository/CalendarRepository;", "", "apiService", "Lcom/biathlonapp/data/api/BiathlonApiService;", "(Lcom/biathlonapp/data/api/BiathlonApiService;)V", "dateFormat", "Ljava/text/SimpleDateFormat;", "getRacesForDate", "Lcom/biathlonapp/utils/Result;", "", "Lcom/biathlonapp/data/model/RaceEvent;", "date", "Ljava/util/Date;", "(Ljava/util/Date;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRacesForMonth", "year", "", "month", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class CalendarRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.biathlonapp.data.api.BiathlonApiService apiService = null;
    @org.jetbrains.annotations.NotNull()
    private final java.text.SimpleDateFormat dateFormat = null;
    
    public CalendarRepository(@org.jetbrains.annotations.NotNull()
    com.biathlonapp.data.api.BiathlonApiService apiService) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getRacesForMonth(int year, int month, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.biathlonapp.utils.Result<? extends java.util.List<com.biathlonapp.data.model.RaceEvent>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getRacesForDate(@org.jetbrains.annotations.NotNull()
    java.util.Date date, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.biathlonapp.utils.Result<? extends java.util.List<com.biathlonapp.data.model.RaceEvent>>> $completion) {
        return null;
    }
}