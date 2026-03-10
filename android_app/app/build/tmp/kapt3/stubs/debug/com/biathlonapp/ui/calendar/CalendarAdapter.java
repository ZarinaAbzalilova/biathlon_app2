package com.biathlonapp.ui.calendar;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0018B\u0019\u0012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u00a2\u0006\u0002\u0010\u0007J\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\tJ\b\u0010\r\u001a\u00020\u000eH\u0016J\u001c\u0010\u000f\u001a\u00020\u00062\n\u0010\u0010\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u000eH\u0016J\u001c\u0010\u0012\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000eH\u0016J\u0014\u0010\u0016\u001a\u00020\u00062\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00050\tR\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/biathlonapp/ui/calendar/CalendarAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/biathlonapp/ui/calendar/CalendarAdapter$CalendarViewHolder;", "onDayClick", "Lkotlin/Function1;", "Lcom/biathlonapp/data/model/CalendarDay;", "", "(Lkotlin/jvm/functions/Function1;)V", "days", "", "daysOfWeek", "", "getDayOfWeekHeader", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "submitList", "newDays", "CalendarViewHolder", "app_debug"})
public final class CalendarAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.biathlonapp.ui.calendar.CalendarAdapter.CalendarViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.biathlonapp.data.model.CalendarDay, kotlin.Unit> onDayClick = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.biathlonapp.data.model.CalendarDay> days;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> daysOfWeek = null;
    
    public CalendarAdapter(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.biathlonapp.data.model.CalendarDay, kotlin.Unit> onDayClick) {
        super();
    }
    
    public final void submitList(@org.jetbrains.annotations.NotNull()
    java.util.List<com.biathlonapp.data.model.CalendarDay> newDays) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.biathlonapp.ui.calendar.CalendarAdapter.CalendarViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.biathlonapp.ui.calendar.CalendarAdapter.CalendarViewHolder holder, int position) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getDayOfWeekHeader() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0086\u0004\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/biathlonapp/ui/calendar/CalendarAdapter$CalendarViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/biathlonapp/databinding/ItemCalendarDayBinding;", "onDayClick", "Lkotlin/Function1;", "Lcom/biathlonapp/data/model/CalendarDay;", "", "(Lcom/biathlonapp/ui/calendar/CalendarAdapter;Lcom/biathlonapp/databinding/ItemCalendarDayBinding;Lkotlin/jvm/functions/Function1;)V", "bind", "day", "app_debug"})
    public final class CalendarViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final com.biathlonapp.databinding.ItemCalendarDayBinding binding = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.jvm.functions.Function1<com.biathlonapp.data.model.CalendarDay, kotlin.Unit> onDayClick = null;
        
        public CalendarViewHolder(@org.jetbrains.annotations.NotNull()
        com.biathlonapp.databinding.ItemCalendarDayBinding binding, @org.jetbrains.annotations.NotNull()
        kotlin.jvm.functions.Function1<? super com.biathlonapp.data.model.CalendarDay, kotlin.Unit> onDayClick) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.biathlonapp.data.model.CalendarDay day) {
        }
    }
}