package com.biathlonapp.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\u0011\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\tH\u00c6\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003JY\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010!\u001a\u00020\"H\u00d6\u0001J\t\u0010#\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u001e\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\u0007\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000eR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000e\u00a8\u0006$"}, d2 = {"Lcom/biathlonapp/data/model/RaceInfoFull;", "", "raceId", "", "nameRace", "discipline", "date", "placeRace", "pdfUrls", "", "Lcom/biathlonapp/data/model/PdfUrlInfo;", "gender", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;)V", "getDate", "()Ljava/lang/String;", "getDiscipline", "getGender", "getNameRace", "getPdfUrls", "()Ljava/util/List;", "getPlaceRace", "getRaceId", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class RaceInfoFull {
    @com.google.gson.annotations.SerializedName(value = "race_id")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String raceId = null;
    @com.google.gson.annotations.SerializedName(value = "name_race")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String nameRace = null;
    @com.google.gson.annotations.SerializedName(value = "discipline")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String discipline = null;
    @com.google.gson.annotations.SerializedName(value = "date")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String date = null;
    @com.google.gson.annotations.SerializedName(value = "place_race")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String placeRace = null;
    @com.google.gson.annotations.SerializedName(value = "pdf_urls")
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<com.biathlonapp.data.model.PdfUrlInfo> pdfUrls = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String gender = null;
    
    public RaceInfoFull(@org.jetbrains.annotations.NotNull()
    java.lang.String raceId, @org.jetbrains.annotations.NotNull()
    java.lang.String nameRace, @org.jetbrains.annotations.NotNull()
    java.lang.String discipline, @org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.NotNull()
    java.lang.String placeRace, @org.jetbrains.annotations.Nullable()
    java.util.List<com.biathlonapp.data.model.PdfUrlInfo> pdfUrls, @org.jetbrains.annotations.Nullable()
    java.lang.String gender) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRaceId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNameRace() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDiscipline() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPlaceRace() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.biathlonapp.data.model.PdfUrlInfo> getPdfUrls() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getGender() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.biathlonapp.data.model.PdfUrlInfo> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.biathlonapp.data.model.RaceInfoFull copy(@org.jetbrains.annotations.NotNull()
    java.lang.String raceId, @org.jetbrains.annotations.NotNull()
    java.lang.String nameRace, @org.jetbrains.annotations.NotNull()
    java.lang.String discipline, @org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.NotNull()
    java.lang.String placeRace, @org.jetbrains.annotations.Nullable()
    java.util.List<com.biathlonapp.data.model.PdfUrlInfo> pdfUrls, @org.jetbrains.annotations.Nullable()
    java.lang.String gender) {
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