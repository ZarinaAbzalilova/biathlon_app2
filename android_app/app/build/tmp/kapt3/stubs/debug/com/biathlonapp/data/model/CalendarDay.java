package com.biathlonapp.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BU\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\b\b\u0002\u0010\f\u001a\u00020\u0007\u0012\b\b\u0002\u0010\r\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0007H\u00c6\u0003J\u000f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00c6\u0003J\t\u0010 \u001a\u00020\u0007H\u00c6\u0003J\t\u0010!\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0007H\u00c6\u0003J_\u0010#\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\b\b\u0002\u0010\f\u001a\u00020\u00072\b\b\u0002\u0010\r\u001a\u00020\u00072\b\b\u0002\u0010\u000e\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010$\u001a\u00020\u00072\b\u0010%\u001a\u0004\u0018\u00010&H\u00d6\u0003J\t\u0010\'\u001a\u00020\u0005H\u00d6\u0001J\t\u0010(\u001a\u00020)H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\r\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0011\u0010\f\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017R\u0011\u0010\u000e\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0017R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0017\u00a8\u0006*"}, d2 = {"Lcom/biathlonapp/data/model/CalendarDay;", "Ljava/io/Serializable;", "date", "Ljava/util/Date;", "dayOfMonth", "", "isCurrentMonth", "", "hasEvent", "events", "", "Lcom/biathlonapp/data/model/RaceEvent;", "hasMaleEvent", "hasFemaleEvent", "hasMixedEvent", "(Ljava/util/Date;IZZLjava/util/List;ZZZ)V", "getDate", "()Ljava/util/Date;", "getDayOfMonth", "()I", "getEvents", "()Ljava/util/List;", "getHasEvent", "()Z", "getHasFemaleEvent", "getHasMaleEvent", "getHasMixedEvent", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "other", "", "hashCode", "toString", "", "app_debug"})
public final class CalendarDay implements java.io.Serializable {
    @org.jetbrains.annotations.NotNull()
    private final java.util.Date date = null;
    private final int dayOfMonth = 0;
    private final boolean isCurrentMonth = false;
    private final boolean hasEvent = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.biathlonapp.data.model.RaceEvent> events = null;
    private final boolean hasMaleEvent = false;
    private final boolean hasFemaleEvent = false;
    private final boolean hasMixedEvent = false;
    
    public CalendarDay(@org.jetbrains.annotations.NotNull()
    java.util.Date date, int dayOfMonth, boolean isCurrentMonth, boolean hasEvent, @org.jetbrains.annotations.NotNull()
    java.util.List<com.biathlonapp.data.model.RaceEvent> events, boolean hasMaleEvent, boolean hasFemaleEvent, boolean hasMixedEvent) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Date getDate() {
        return null;
    }
    
    public final int getDayOfMonth() {
        return 0;
    }
    
    public final boolean isCurrentMonth() {
        return false;
    }
    
    public final boolean getHasEvent() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.biathlonapp.data.model.RaceEvent> getEvents() {
        return null;
    }
    
    public final boolean getHasMaleEvent() {
        return false;
    }
    
    public final boolean getHasFemaleEvent() {
        return false;
    }
    
    public final boolean getHasMixedEvent() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Date component1() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final boolean component3() {
        return false;
    }
    
    public final boolean component4() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.biathlonapp.data.model.RaceEvent> component5() {
        return null;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final boolean component7() {
        return false;
    }
    
    public final boolean component8() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.biathlonapp.data.model.CalendarDay copy(@org.jetbrains.annotations.NotNull()
    java.util.Date date, int dayOfMonth, boolean isCurrentMonth, boolean hasEvent, @org.jetbrains.annotations.NotNull()
    java.util.List<com.biathlonapp.data.model.RaceEvent> events, boolean hasMaleEvent, boolean hasFemaleEvent, boolean hasMixedEvent) {
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