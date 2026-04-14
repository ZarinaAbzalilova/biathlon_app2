package com.biathlonapp.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0010H\u0002J$\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u001c0\u001b2\b\b\u0002\u0010\u001d\u001a\u00020\u001eH\u0086@\u00a2\u0006\u0002\u0010\u001fJ\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00160\u001cH\u0082@\u00a2\u0006\u0002\u0010!J,\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u001c0\u001b2\u0006\u0010\u0019\u001a\u00020\u00102\b\b\u0002\u0010#\u001a\u00020$H\u0082@\u00a2\u0006\u0002\u0010%J\u000e\u0010&\u001a\u00020\'H\u0082@\u00a2\u0006\u0002\u0010!J\u001c\u0010(\u001a\u00020\'2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00160\u001cH\u0082@\u00a2\u0006\u0002\u0010*R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u000b\u001a\u00020\fX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082D\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\f0\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006+"}, d2 = {"Lcom/biathlonapp/data/repository/NewsRepository;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "api", "Lcom/biathlonapp/data/api/VkApiService;", "getApi", "()Lcom/biathlonapp/data/api/VkApiService;", "api$delegate", "Lkotlin/Lazy;", "clientId", "", "clientSecret", "communities", "", "Lcom/biathlonapp/data/model/NewsSource;", "database", "Lcom/biathlonapp/data/local/FavoriteDatabase;", "newsDao", "Lcom/biathlonapp/data/local/NewsDao;", "convertVkPostToNews", "Lcom/biathlonapp/data/model/News;", "post", "Lcom/biathlonapp/data/api/VkPost;", "source", "getAllNews", "Lcom/biathlonapp/utils/Result;", "", "forceRefresh", "", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCachedNews", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getNewsFromVk", "count", "", "(Lcom/biathlonapp/data/model/NewsSource;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "refreshCacheInBackground", "", "saveNewsToCache", "news", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class NewsRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.biathlonapp.data.local.FavoriteDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    private final com.biathlonapp.data.local.NewsDao newsDao = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String clientId = "54470422";
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String clientSecret = "gOOa0qgwaAgBEsP5pQLB";
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy api$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<com.biathlonapp.data.model.NewsSource, java.lang.String> communities = null;
    
    public NewsRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final com.biathlonapp.data.api.VkApiService getApi() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAllNews(boolean forceRefresh, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.biathlonapp.utils.Result<? extends java.util.List<com.biathlonapp.data.model.News>>> $completion) {
        return null;
    }
    
    private final java.lang.Object getNewsFromVk(com.biathlonapp.data.model.NewsSource source, int count, kotlin.coroutines.Continuation<? super com.biathlonapp.utils.Result<? extends java.util.List<com.biathlonapp.data.model.News>>> $completion) {
        return null;
    }
    
    private final java.lang.Object getCachedNews(kotlin.coroutines.Continuation<? super java.util.List<com.biathlonapp.data.model.News>> $completion) {
        return null;
    }
    
    private final java.lang.Object saveNewsToCache(java.util.List<com.biathlonapp.data.model.News> news, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object refreshCacheInBackground(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final com.biathlonapp.data.model.News convertVkPostToNews(com.biathlonapp.data.api.VkPost post, com.biathlonapp.data.model.NewsSource source) {
        return null;
    }
}