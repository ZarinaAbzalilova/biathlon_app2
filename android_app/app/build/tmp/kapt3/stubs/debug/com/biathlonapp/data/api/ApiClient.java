package com.biathlonapp.data.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/biathlonapp/data/api/ApiClient;", "", "()V", "TIMEOUT_SECONDS", "", "apiService", "Lcom/biathlonapp/data/api/BiathlonApiService;", "getApiService", "()Lcom/biathlonapp/data/api/BiathlonApiService;", "apiService$delegate", "Lkotlin/Lazy;", "loggingInterceptor", "Lokhttp3/logging/HttpLoggingInterceptor;", "okHttpClient", "Lokhttp3/OkHttpClient;", "app_debug"})
public final class ApiClient {
    private static final long TIMEOUT_SECONDS = 30L;
    @org.jetbrains.annotations.NotNull()
    private static final okhttp3.logging.HttpLoggingInterceptor loggingInterceptor = null;
    @org.jetbrains.annotations.NotNull()
    private static final okhttp3.OkHttpClient okHttpClient = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.Lazy apiService$delegate = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.biathlonapp.data.api.ApiClient INSTANCE = null;
    
    private ApiClient() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.biathlonapp.data.api.BiathlonApiService getApiService() {
        return null;
    }
}