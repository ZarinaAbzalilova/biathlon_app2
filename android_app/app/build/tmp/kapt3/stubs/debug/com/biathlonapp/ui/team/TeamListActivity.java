package com.biathlonapp.ui.team;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0002J\u0012\u0010\u000f\u001a\u00020\u000e2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0014J\b\u0010\u0012\u001a\u00020\u000eH\u0002J\b\u0010\u0013\u001a\u00020\u000eH\u0002J\b\u0010\u0014\u001a\u00020\u000eH\u0002J\b\u0010\u0015\u001a\u00020\u000eH\u0002J\b\u0010\u0016\u001a\u00020\u000eH\u0002J\b\u0010\u0017\u001a\u00020\u000eH\u0002J\u0010\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/biathlonapp/ui/team/TeamListActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/biathlonapp/ui/team/TeamAthleteAdapter;", "binding", "Lcom/biathlonapp/databinding/ActivityTeamListBinding;", "categoryTitle", "", "teamType", "Lcom/biathlonapp/data/model/TeamType;", "viewModel", "Lcom/biathlonapp/ui/team/TeamListViewModel;", "loadTeamAthletes", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setupObservers", "setupRecyclerView", "setupToolbar", "setupViewModel", "showAthletesList", "showEmptyState", "showError", "message", "app_debug"})
public final class TeamListActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.biathlonapp.databinding.ActivityTeamListBinding binding;
    private com.biathlonapp.ui.team.TeamListViewModel viewModel;
    private com.biathlonapp.ui.team.TeamAthleteAdapter adapter;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String categoryTitle = "";
    @org.jetbrains.annotations.Nullable()
    private com.biathlonapp.data.model.TeamType teamType;
    
    public TeamListActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupToolbar() {
    }
    
    private final void setupRecyclerView() {
    }
    
    private final void setupViewModel() {
    }
    
    private final void setupObservers() {
    }
    
    private final void loadTeamAthletes() {
    }
    
    private final void showEmptyState() {
    }
    
    private final void showAthletesList() {
    }
    
    private final void showError(java.lang.String message) {
    }
}