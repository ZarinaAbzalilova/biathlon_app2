package com.biathlonapp.ui.raceprotocol;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0019B%\u0012\u001e\u0010\u0003\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0004\u0012\u00020\b0\u0004\u00a2\u0006\u0002\u0010\tJ\b\u0010\u000f\u001a\u00020\fH\u0016J\u001c\u0010\u0010\u001a\u00020\b2\n\u0010\u0011\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0012\u001a\u00020\fH\u0016J\u001c\u0010\u0013\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\fH\u0016J\u0014\u0010\u0017\u001a\u00020\b2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0006R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R&\u0010\u0003\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0004\u0012\u00020\b0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/biathlonapp/ui/raceprotocol/RelayProtocolAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/biathlonapp/ui/raceprotocol/RelayProtocolAdapter$ViewHolder;", "onTeamClick", "Lkotlin/Function2;", "", "", "Lcom/biathlonapp/data/model/RelayTeamMember;", "", "(Lkotlin/jvm/functions/Function2;)V", "expandedPositions", "", "", "teams", "Lcom/biathlonapp/data/model/RelayTeam;", "getItemCount", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "submitList", "newTeams", "ViewHolder", "app_debug"})
public final class RelayProtocolAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.biathlonapp.ui.raceprotocol.RelayProtocolAdapter.ViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function2<java.lang.String, java.util.List<com.biathlonapp.data.model.RelayTeamMember>, kotlin.Unit> onTeamClick = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.biathlonapp.data.model.RelayTeam> teams;
    @org.jetbrains.annotations.NotNull()
    private java.util.Set<java.lang.Integer> expandedPositions;
    
    public RelayProtocolAdapter(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.util.List<com.biathlonapp.data.model.RelayTeamMember>, kotlin.Unit> onTeamClick) {
        super();
    }
    
    public final void submitList(@org.jetbrains.annotations.NotNull()
    java.util.List<com.biathlonapp.data.model.RelayTeam> newTeams) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.biathlonapp.ui.raceprotocol.RelayProtocolAdapter.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.biathlonapp.ui.raceprotocol.RelayProtocolAdapter.ViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/biathlonapp/ui/raceprotocol/RelayProtocolAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/biathlonapp/databinding/ItemRelayTeamBinding;", "(Lcom/biathlonapp/ui/raceprotocol/RelayProtocolAdapter;Lcom/biathlonapp/databinding/ItemRelayTeamBinding;)V", "bind", "", "team", "Lcom/biathlonapp/data/model/RelayTeam;", "position", "", "isExpanded", "", "createMemberView", "Landroid/view/View;", "member", "Lcom/biathlonapp/data/model/RelayTeamMember;", "app_debug"})
    public final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final com.biathlonapp.databinding.ItemRelayTeamBinding binding = null;
        
        public ViewHolder(@org.jetbrains.annotations.NotNull()
        com.biathlonapp.databinding.ItemRelayTeamBinding binding) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.biathlonapp.data.model.RelayTeam team, int position, boolean isExpanded) {
        }
        
        private final android.view.View createMemberView(com.biathlonapp.data.model.RelayTeamMember member) {
            return null;
        }
    }
}