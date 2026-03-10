package com.biathlonapp.data.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0003\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\t\u00a8\u0006\n"}, d2 = {"Lcom/biathlonapp/data/api/VkApiService;", "", "getWallPosts", "Lcom/biathlonapp/data/api/VkResponse;", "Lcom/biathlonapp/data/api/VkWallResponseData;", "ownerId", "", "count", "", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface VkApiService {
    
    @retrofit2.http.GET(value = "method/wall.get")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getWallPosts(@retrofit2.http.Query(value = "owner_id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String ownerId, @retrofit2.http.Query(value = "count")
    int count, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.biathlonapp.data.api.VkResponse<com.biathlonapp.data.api.VkWallResponseData>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}