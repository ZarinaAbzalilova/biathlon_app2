package com.biathlonapp.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u001e\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001BY\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\u0007\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\rJ\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0011J\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0011J\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0011J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0007H\u00c6\u0003J\t\u0010 \u001a\u00020\u0007H\u00c6\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003Jt\u0010#\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u00072\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0007H\u00c6\u0001\u00a2\u0006\u0002\u0010$J\u0013\u0010%\u001a\u00020&2\b\u0010\'\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010(\u001a\u00020\u0003H\u00d6\u0001J\t\u0010)\u001a\u00020\u0007H\u00d6\u0001R\u0016\u0010\b\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011R\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u0016\u0010\n\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000fR\u0016\u0010\t\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000fR\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0016\u0010\u0011R\u0018\u0010\u000b\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000fR\u0018\u0010\f\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000fR\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0019\u0010\u0011\u00a8\u0006*"}, d2 = {"Lcom/biathlonapp/data/model/RaceResultItem;", "", "startNumber", "", "finishPlace", "missCount", "finishTime", "", "athleteId", "lastName", "firstName", "region", "sportsRank", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAthleteId", "()Ljava/lang/String;", "getFinishPlace", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getFinishTime", "getFirstName", "getLastName", "getMissCount", "getRegion", "getSportsRank", "getStartNumber", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcom/biathlonapp/data/model/RaceResultItem;", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class RaceResultItem {
    @com.google.gson.annotations.SerializedName(value = "start_number")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer startNumber = null;
    @com.google.gson.annotations.SerializedName(value = "finish_place")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer finishPlace = null;
    @com.google.gson.annotations.SerializedName(value = "miss_count")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer missCount = null;
    @com.google.gson.annotations.SerializedName(value = "finish_time")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String finishTime = null;
    @com.google.gson.annotations.SerializedName(value = "athlete_id")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String athleteId = null;
    @com.google.gson.annotations.SerializedName(value = "last_name")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String lastName = null;
    @com.google.gson.annotations.SerializedName(value = "first_name")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String firstName = null;
    @com.google.gson.annotations.SerializedName(value = "region")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String region = null;
    @com.google.gson.annotations.SerializedName(value = "sports_rank")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String sportsRank = null;
    
    public RaceResultItem(@org.jetbrains.annotations.Nullable()
    java.lang.Integer startNumber, @org.jetbrains.annotations.Nullable()
    java.lang.Integer finishPlace, @org.jetbrains.annotations.Nullable()
    java.lang.Integer missCount, @org.jetbrains.annotations.Nullable()
    java.lang.String finishTime, @org.jetbrains.annotations.NotNull()
    java.lang.String athleteId, @org.jetbrains.annotations.NotNull()
    java.lang.String lastName, @org.jetbrains.annotations.NotNull()
    java.lang.String firstName, @org.jetbrains.annotations.Nullable()
    java.lang.String region, @org.jetbrains.annotations.Nullable()
    java.lang.String sportsRank) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getStartNumber() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getFinishPlace() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getMissCount() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getFinishTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAthleteId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLastName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFirstName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getRegion() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSportsRank() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.biathlonapp.data.model.RaceResultItem copy(@org.jetbrains.annotations.Nullable()
    java.lang.Integer startNumber, @org.jetbrains.annotations.Nullable()
    java.lang.Integer finishPlace, @org.jetbrains.annotations.Nullable()
    java.lang.Integer missCount, @org.jetbrains.annotations.Nullable()
    java.lang.String finishTime, @org.jetbrains.annotations.NotNull()
    java.lang.String athleteId, @org.jetbrains.annotations.NotNull()
    java.lang.String lastName, @org.jetbrains.annotations.NotNull()
    java.lang.String firstName, @org.jetbrains.annotations.Nullable()
    java.lang.String region, @org.jetbrains.annotations.Nullable()
    java.lang.String sportsRank) {
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