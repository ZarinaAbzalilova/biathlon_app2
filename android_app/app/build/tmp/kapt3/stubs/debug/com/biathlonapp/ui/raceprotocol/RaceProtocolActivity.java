package com.biathlonapp.ui.raceprotocol;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 $2\u00020\u0001:\u0001$B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0010H\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0002J\u0010\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0012H\u0002J\u0012\u0010\u0016\u001a\u00020\f2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0014J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\fH\u0002J\b\u0010\u001c\u001a\u00020\fH\u0002J\b\u0010\u001d\u001a\u00020\fH\u0002J\b\u0010\u001e\u001a\u00020\fH\u0002J\u001e\u0010\u001f\u001a\u00020\f2\u0006\u0010 \u001a\u00020\u00122\f\u0010!\u001a\b\u0012\u0004\u0012\u00020#0\"H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/biathlonapp/ui/raceprotocol/RaceProtocolActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/biathlonapp/databinding/ActivityRaceProtocolBinding;", "regularAdapter", "Lcom/biathlonapp/ui/raceprotocol/RaceProtocolAdapter;", "relayAdapter", "Lcom/biathlonapp/ui/raceprotocol/RelayProtocolAdapter;", "viewModel", "Lcom/biathlonapp/ui/raceprotocol/RaceProtocolViewModel;", "displayRaceResults", "", "response", "Lcom/biathlonapp/data/model/RaceResultsResponse;", "displayRelayResults", "Lcom/biathlonapp/data/model/RelayResultsResponse;", "formatDate", "", "dateString", "getGenderSuffix", "gender", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSupportNavigateUp", "", "setupObservers", "setupRecyclerViews", "setupToolbar", "setupViewModel", "showTeamMembersDialog", "teamName", "members", "", "Lcom/biathlonapp/data/model/RelayTeamMember;", "Companion", "app_debug"})
public final class RaceProtocolActivity extends androidx.appcompat.app.AppCompatActivity {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_RACE_ID = "race_id";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_GENDER = "gender";
    private com.biathlonapp.databinding.ActivityRaceProtocolBinding binding;
    private com.biathlonapp.ui.raceprotocol.RaceProtocolViewModel viewModel;
    private com.biathlonapp.ui.raceprotocol.RaceProtocolAdapter regularAdapter;
    private com.biathlonapp.ui.raceprotocol.RelayProtocolAdapter relayAdapter;
    @org.jetbrains.annotations.NotNull()
    public static final com.biathlonapp.ui.raceprotocol.RaceProtocolActivity.Companion Companion = null;
    
    public RaceProtocolActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final java.lang.String getGenderSuffix(java.lang.String gender) {
        return null;
    }
    
    private final void setupViewModel() {
    }
    
    private final void setupToolbar() {
    }
    
    private final void setupRecyclerViews() {
    }
    
    private final void setupObservers() {
    }
    
    private final void displayRaceResults(com.biathlonapp.data.model.RaceResultsResponse response) {
    }
    
    private final void displayRelayResults(com.biathlonapp.data.model.RelayResultsResponse response) {
    }
    
    private final void showTeamMembersDialog(java.lang.String teamName, java.util.List<com.biathlonapp.data.model.RelayTeamMember> members) {
    }
    
    private final java.lang.String formatDate(java.lang.String dateString) {
        return null;
    }
    
    @java.lang.Override()
    public boolean onSupportNavigateUp() {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/biathlonapp/ui/raceprotocol/RaceProtocolActivity$Companion;", "", "()V", "EXTRA_GENDER", "", "EXTRA_RACE_ID", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}