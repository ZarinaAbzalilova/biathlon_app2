package com.biathlonapp.ui.calendar;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J$\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\f0\u0007H\u0002J\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\bJ\u000e\u0010 \u001a\u00020\u001e2\u0006\u0010\u001a\u001a\u00020\u001bJ\u000e\u0010!\u001a\u00020\u001e2\u0006\u0010\u001a\u001a\u00020\u001bR\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\f0\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012R\u001d\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00070\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0012R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2 = {"Lcom/biathlonapp/ui/calendar/CalendarViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/biathlonapp/data/repository/CalendarRepository;", "(Lcom/biathlonapp/data/repository/CalendarRepository;)V", "_calendarDays", "Landroidx/lifecycle/MutableLiveData;", "", "Lcom/biathlonapp/data/model/CalendarDay;", "_error", "", "_eventsForSelectedDay", "Lcom/biathlonapp/data/model/RaceEvent;", "_isLoading", "", "calendarDays", "Landroidx/lifecycle/LiveData;", "getCalendarDays", "()Landroidx/lifecycle/LiveData;", "currentMonthEvents", "error", "getError", "eventsForSelectedDay", "getEventsForSelectedDay", "isLoading", "generateCalendarDays", "calendar", "Ljava/util/Calendar;", "events", "loadEventsForDay", "", "day", "loadMonthEvents", "updateCalendarDays", "app_debug"})
public final class CalendarViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.biathlonapp.data.repository.CalendarRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.biathlonapp.data.model.CalendarDay>> _calendarDays = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.biathlonapp.data.model.CalendarDay>> calendarDays = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.biathlonapp.data.model.RaceEvent>> _eventsForSelectedDay = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.biathlonapp.data.model.RaceEvent>> eventsForSelectedDay = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Boolean> isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.String> _error = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.String> error = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.biathlonapp.data.model.RaceEvent> currentMonthEvents;
    
    public CalendarViewModel(@org.jetbrains.annotations.NotNull()
    com.biathlonapp.data.repository.CalendarRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.biathlonapp.data.model.CalendarDay>> getCalendarDays() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.biathlonapp.data.model.RaceEvent>> getEventsForSelectedDay() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Boolean> isLoading() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.String> getError() {
        return null;
    }
    
    public final void loadMonthEvents(@org.jetbrains.annotations.NotNull()
    java.util.Calendar calendar) {
    }
    
    public final void updateCalendarDays(@org.jetbrains.annotations.NotNull()
    java.util.Calendar calendar) {
    }
    
    public final void loadEventsForDay(@org.jetbrains.annotations.NotNull()
    com.biathlonapp.data.model.CalendarDay day) {
    }
    
    private final java.util.List<com.biathlonapp.data.model.CalendarDay> generateCalendarDays(java.util.Calendar calendar, java.util.List<com.biathlonapp.data.model.RaceEvent> events) {
        return null;
    }
}