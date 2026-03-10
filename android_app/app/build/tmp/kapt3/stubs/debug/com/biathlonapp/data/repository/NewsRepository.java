package com.biathlonapp.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000eH\u0002J\"\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00190\u0018H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001a\u0010\u0011J4\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00190\u00182\u0006\u0010\u0016\u001a\u00020\u000e2\b\b\u0002\u0010\u001c\u001a\u00020\u001dH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001e\u0010\u001fR\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\t\u001a\u00020\nX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082D\u00a2\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\n0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006 "}, d2 = {"Lcom/biathlonapp/data/repository/NewsRepository;", "", "()V", "api", "Lcom/biathlonapp/data/api/VkApiService;", "getApi", "()Lcom/biathlonapp/data/api/VkApiService;", "api$delegate", "Lkotlin/Lazy;", "clientId", "", "clientSecret", "communities", "", "Lcom/biathlonapp/data/model/NewsSource;", "checkCommunityId", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "convertVkPostToNews", "Lcom/biathlonapp/data/model/News;", "post", "Lcom/biathlonapp/data/api/VkPost;", "source", "getAllNews", "Lkotlin/Result;", "", "getAllNews-IoAF18A", "getNewsFromVk", "count", "", "getNewsFromVk-0E7RQCE", "(Lcom/biathlonapp/data/model/NewsSource;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class NewsRepository {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String clientId = "54470422";
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String clientSecret = "gOOa0qgwaAgBEsP5pQLB";
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy api$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<com.biathlonapp.data.model.NewsSource, java.lang.String> communities = null;
    
    public NewsRepository() {
        super();
    }
    
    private final com.biathlonapp.data.api.VkApiService getApi() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object checkCommunityId(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final com.biathlonapp.data.model.News convertVkPostToNews(com.biathlonapp.data.api.VkPost post, com.biathlonapp.data.model.NewsSource source) {
        return null;
    }
}