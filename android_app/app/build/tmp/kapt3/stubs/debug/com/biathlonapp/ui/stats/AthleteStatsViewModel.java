package com.biathlonapp.ui.stats;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010)\u001a\u00020*H\u0002J\u000e\u0010+\u001a\u00020*2\u0006\u0010,\u001a\u00020\u0007J\u000e\u0010-\u001a\u00020*2\u0006\u0010,\u001a\u00020\u0007J\u0010\u0010.\u001a\u00020*2\b\u0010/\u001a\u0004\u0018\u00010\u0007J\u0016\u00100\u001a\u00020*2\f\u00101\u001a\b\u0012\u0004\u0012\u00020\u000e0\u000bH\u0002R\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u000b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u000b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u000f\u001a\u0010\u0012\f\u0012\n \u0011*\u0004\u0018\u00010\u00100\u00100\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0012\u001a\u0010\u0012\f\u0012\n \u0011*\u0004\u0018\u00010\u00100\u00100\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000e0\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\t0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017R\u001d\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u000b0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0017R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u001eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u001f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0017R\u000e\u0010!\u001a\u00020\"X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u000b0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0017R\u0017\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00100\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0017R\u0017\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00100\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0017R\u000e\u0010\'\u001a\u00020(X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00062"}, d2 = {"Lcom/biathlonapp/ui/stats/AthleteStatsViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_athleteGender", "Landroidx/lifecycle/MutableLiveData;", "", "_athleteResults", "Lcom/biathlonapp/data/model/AthleteResultsResponse;", "_availableDisciplines", "", "_error", "_filteredResults", "Lcom/biathlonapp/ui/stats/RaceResultDisplay;", "_isFromCache", "", "kotlin.jvm.PlatformType", "_isLoading", "allResults", "athleteGender", "Landroidx/lifecycle/LiveData;", "getAthleteGender", "()Landroidx/lifecycle/LiveData;", "athleteResults", "getAthleteResults", "availableDisciplines", "getAvailableDisciplines", "currentDisciplineFilter", "disciplineMap", "", "error", "getError", "favoritesRepository", "Lcom/biathlonapp/data/repository/FavoritesRepository;", "filteredResults", "getFilteredResults", "isFromCache", "isLoading", "repository", "Lcom/biathlonapp/data/repository/BiathlonRepository;", "applyFilter", "", "loadAthleteResults", "athleteId", "refreshAthleteResults", "setDisciplineFilter", "displayDiscipline", "updateDisciplinesFromResults", "results", "app_debug"})
public final class AthleteStatsViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.biathlonapp.data.repository.BiathlonRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.biathlonapp.data.repository.FavoritesRepository favoritesRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<com.biathlonapp.data.model.AthleteResultsResponse> _athleteResults = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.biathlonapp.data.model.AthleteResultsResponse> athleteResults = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Boolean> isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.String> _error = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.String> error = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> _isFromCache = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Boolean> isFromCache = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.String> _athleteGender = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.String> athleteGender = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<java.lang.String>> _availableDisciplines = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<java.lang.String>> availableDisciplines = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.biathlonapp.ui.stats.RaceResultDisplay>> _filteredResults = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.biathlonapp.ui.stats.RaceResultDisplay>> filteredResults = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.biathlonapp.ui.stats.RaceResultDisplay> allResults;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String currentDisciplineFilter;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.String> disciplineMap = null;
    
    public AthleteStatsViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.biathlonapp.data.model.AthleteResultsResponse> getAthleteResults() {
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
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Boolean> isFromCache() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.String> getAthleteGender() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<java.lang.String>> getAvailableDisciplines() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.biathlonapp.ui.stats.RaceResultDisplay>> getFilteredResults() {
        return null;
    }
    
    public final void loadAthleteResults(@org.jetbrains.annotations.NotNull()
    java.lang.String athleteId) {
    }
    
    private final void updateDisciplinesFromResults(java.util.List<com.biathlonapp.ui.stats.RaceResultDisplay> results) {
    }
    
    public final void setDisciplineFilter(@org.jetbrains.annotations.Nullable()
    java.lang.String displayDiscipline) {
    }
    
    public final void refreshAthleteResults(@org.jetbrains.annotations.NotNull()
    java.lang.String athleteId) {
    }
    
    private final void applyFilter() {
    }
}