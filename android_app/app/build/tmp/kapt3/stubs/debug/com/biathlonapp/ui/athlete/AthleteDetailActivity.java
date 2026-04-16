package com.biathlonapp.ui.athlete;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 22\u00020\u0001:\u00012B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u0006H\u0002J\u0010\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u0010H\u0002J\u0012\u0010\u001d\u001a\u00020\u00062\b\u0010\u001e\u001a\u0004\u0018\u00010\u0006H\u0002J\u0012\u0010\u001f\u001a\u00020\u00062\b\u0010 \u001a\u0004\u0018\u00010\u0006H\u0002J\n\u0010!\u001a\u0004\u0018\u00010\u0010H\u0002J\u0010\u0010\"\u001a\u00020\u00182\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010#\u001a\u00020\u00182\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\b\u0010$\u001a\u00020\u0018H\u0002J\u0012\u0010%\u001a\u00020\u00182\b\u0010&\u001a\u0004\u0018\u00010\'H\u0014J\b\u0010(\u001a\u00020\u000eH\u0016J\b\u0010)\u001a\u00020\u0018H\u0002J\u001a\u0010*\u001a\u00020\u00182\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010+\u001a\u0004\u0018\u00010\u0006H\u0002J\b\u0010,\u001a\u00020\u0018H\u0002J\u0010\u0010-\u001a\u00020\u00182\u0006\u0010.\u001a\u00020\u0006H\u0002J\b\u0010/\u001a\u00020\u0018H\u0002J\u0010\u00100\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u0010H\u0002J\b\u00101\u001a\u00020\u0018H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0013\u0010\u0014\u00a8\u00063"}, d2 = {"Lcom/biathlonapp/ui/athlete/AthleteDetailActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "apiService", "Lcom/biathlonapp/data/api/BiathlonApiService;", "athleteId", "", "authRepository", "Lcom/biathlonapp/data/repository/AuthRepository;", "binding", "Lcom/biathlonapp/databinding/ActivityAthleteDetailBinding;", "favoritesRepository", "Lcom/biathlonapp/data/repository/FavoritesRepository;", "isFavorite", "", "selectedAthlete", "Lcom/biathlonapp/data/model/Athlete;", "viewModel", "Lcom/biathlonapp/ui/stats/AthleteStatsViewModel;", "getViewModel", "()Lcom/biathlonapp/ui/stats/AthleteStatsViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "checkIfFavorite", "", "createMinimalAthlete", "id", "displayAthleteInfo", "athlete", "formatBirthDate", "rawDate", "formatGender", "gender", "getAthleteFromIntent", "loadAthleteData", "loadFromCache", "observeViewModel", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSupportNavigateUp", "setupFavoriteButton", "setupStatsButton", "athleteGender", "setupToolbar", "showMessage", "message", "toggleFavorite", "updateFavoriteAthleteData", "updateFavoriteButton", "Companion", "app_debug"})
public final class AthleteDetailActivity extends androidx.appcompat.app.AppCompatActivity {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_ATHLETE_ID = "athlete_id";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_ATHLETE = "extra_athlete";
    private com.biathlonapp.databinding.ActivityAthleteDetailBinding binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    private com.biathlonapp.data.repository.FavoritesRepository favoritesRepository;
    private com.biathlonapp.data.repository.AuthRepository authRepository;
    private com.biathlonapp.data.api.BiathlonApiService apiService;
    @org.jetbrains.annotations.Nullable()
    private com.biathlonapp.data.model.Athlete selectedAthlete;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String athleteId;
    private boolean isFavorite = false;
    @org.jetbrains.annotations.NotNull()
    public static final com.biathlonapp.ui.athlete.AthleteDetailActivity.Companion Companion = null;
    
    public AthleteDetailActivity() {
        super();
    }
    
    private final com.biathlonapp.ui.stats.AthleteStatsViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void loadFromCache(java.lang.String athleteId) {
    }
    
    private final void setupFavoriteButton() {
    }
    
    private final void setupToolbar() {
    }
    
    private final com.biathlonapp.data.model.Athlete getAthleteFromIntent() {
        return null;
    }
    
    private final void loadAthleteData(java.lang.String athleteId) {
    }
    
    private final void setupStatsButton(java.lang.String athleteId, java.lang.String athleteGender) {
    }
    
    private final void checkIfFavorite(java.lang.String athleteId) {
    }
    
    private final void toggleFavorite() {
    }
    
    private final com.biathlonapp.data.model.Athlete createMinimalAthlete(java.lang.String id) {
        return null;
    }
    
    private final void updateFavoriteButton() {
    }
    
    private final void observeViewModel() {
    }
    
    private final void updateFavoriteAthleteData(com.biathlonapp.data.model.Athlete athlete) {
    }
    
    private final void displayAthleteInfo(com.biathlonapp.data.model.Athlete athlete) {
    }
    
    private final java.lang.String formatGender(java.lang.String gender) {
        return null;
    }
    
    private final java.lang.String formatBirthDate(java.lang.String rawDate) {
        return null;
    }
    
    private final void showMessage(java.lang.String message) {
    }
    
    @java.lang.Override()
    public boolean onSupportNavigateUp() {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/biathlonapp/ui/athlete/AthleteDetailActivity$Companion;", "", "()V", "EXTRA_ATHLETE", "", "EXTRA_ATHLETE_ID", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}