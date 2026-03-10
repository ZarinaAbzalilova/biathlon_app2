package com.biathlonapp.data.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\bf\u0018\u0000 \u001e2\u00020\u0001:\u0002\u001e\u001fJ\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u000bJ.\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\r0\u00032\b\b\u0003\u0010\u000e\u001a\u00020\u000f2\b\b\u0003\u0010\u0010\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0011J(\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00032\b\b\u0001\u0010\u0014\u001a\u00020\u00062\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0015J$\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\t0\u00032\b\b\u0001\u0010\u0018\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J.\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\t0\u00032\b\b\u0001\u0010\u001a\u001a\u00020\u000f2\b\b\u0001\u0010\u001b\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0011J$\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u00032\b\b\u0001\u0010\u001d\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007\u00a8\u0006 "}, d2 = {"Lcom/biathlonapp/data/api/BiathlonApiService;", "", "getAthleteResults", "Lretrofit2/Response;", "Lcom/biathlonapp/data/model/AthleteResultsResponse;", "athleteId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAthletes", "", "Lcom/biathlonapp/data/model/Athlete;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAthletesPaginated", "Lcom/biathlonapp/data/api/BiathlonApiService$PaginatedResponse;", "page", "", "perPage", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRacePdfUrl", "Lcom/biathlonapp/data/model/PdfUrlResponse;", "raceId", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRacesByDate", "Lcom/biathlonapp/data/model/RaceEvent;", "date", "getRacesByMonth", "year", "month", "searchAthletes", "query", "Companion", "PaginatedResponse", "app_debug"})
public abstract interface BiathlonApiService {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String BASE_URL = "https://biathlon-app2.onrender.com";
    @org.jetbrains.annotations.NotNull()
    public static final com.biathlonapp.data.api.BiathlonApiService.Companion Companion = null;
    
    @retrofit2.http.GET(value = "api/athletes")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAthletes(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<com.biathlonapp.data.model.Athlete>>> $completion);
    
    @retrofit2.http.GET(value = "api/athletes/search")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object searchAthletes(@retrofit2.http.Query(value = "q")
    @org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<com.biathlonapp.data.model.Athlete>>> $completion);
    
    @retrofit2.http.GET(value = "api/athletes/{athleteId}/results")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAthleteResults(@retrofit2.http.Path(value = "athleteId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String athleteId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.biathlonapp.data.model.AthleteResultsResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/athletes/paginated")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAthletesPaginated(@retrofit2.http.Query(value = "page")
    int page, @retrofit2.http.Query(value = "per_page")
    int perPage, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.biathlonapp.data.api.BiathlonApiService.PaginatedResponse<com.biathlonapp.data.model.Athlete>>> $completion);
    
    @retrofit2.http.GET(value = "races/{raceId}/pdf-url")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getRacePdfUrl(@retrofit2.http.Path(value = "raceId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String raceId, @retrofit2.http.Query(value = "athlete_id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String athleteId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.biathlonapp.data.model.PdfUrlResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/calendar/races")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getRacesByMonth(@retrofit2.http.Query(value = "year")
    int year, @retrofit2.http.Query(value = "month")
    int month, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<com.biathlonapp.data.model.RaceEvent>>> $completion);
    
    @retrofit2.http.GET(value = "api/calendar/races/by-date")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getRacesByDate(@retrofit2.http.Query(value = "date")
    @org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<com.biathlonapp.data.model.RaceEvent>>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/biathlonapp/data/api/BiathlonApiService$Companion;", "", "()V", "BASE_URL", "", "create", "Lcom/biathlonapp/data/api/BiathlonApiService;", "app_debug"})
    public static final class Companion {
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String BASE_URL = "https://biathlon-app2.onrender.com";
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.biathlonapp.data.api.BiathlonApiService create() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B3\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0004\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0012\u001a\u00020\u0004H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0004H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0004H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0004H\u00c6\u0003J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00028\u00000\tH\u00c6\u0003JG\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u00042\b\b\u0002\u0010\u0007\u001a\u00020\u00042\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tH\u00c6\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0002H\u00d6\u0003J\t\u0010\u001b\u001a\u00020\u0004H\u00d6\u0001J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000e\u00a8\u0006\u001e"}, d2 = {"Lcom/biathlonapp/data/api/BiathlonApiService$PaginatedResponse;", "T", "", "page", "", "per_page", "total", "total_pages", "data", "", "(IIIILjava/util/List;)V", "getData", "()Ljava/util/List;", "getPage", "()I", "getPer_page", "getTotal", "getTotal_pages", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
    public static final class PaginatedResponse<T extends java.lang.Object> {
        private final int page = 0;
        private final int per_page = 0;
        private final int total = 0;
        private final int total_pages = 0;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<T> data = null;
        
        public PaginatedResponse(int page, int per_page, int total, int total_pages, @org.jetbrains.annotations.NotNull()
        java.util.List<? extends T> data) {
            super();
        }
        
        public final int getPage() {
            return 0;
        }
        
        public final int getPer_page() {
            return 0;
        }
        
        public final int getTotal() {
            return 0;
        }
        
        public final int getTotal_pages() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<T> getData() {
            return null;
        }
        
        public final int component1() {
            return 0;
        }
        
        public final int component2() {
            return 0;
        }
        
        public final int component3() {
            return 0;
        }
        
        public final int component4() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<T> component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.biathlonapp.data.api.BiathlonApiService.PaginatedResponse<T> copy(int page, int per_page, int total, int total_pages, @org.jetbrains.annotations.NotNull()
        java.util.List<? extends T> data) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}