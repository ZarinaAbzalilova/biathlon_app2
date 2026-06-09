package com.biathlonapp.ui.onboarding;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u0000 !2\u00020\u0001:\u0001!B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u0012H\u0002J\u0012\u0010\u0014\u001a\u00020\u00122\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0014J\b\u0010\u0017\u001a\u00020\u0012H\u0014J\b\u0010\u0018\u001a\u00020\u0012H\u0002J\b\u0010\u0019\u001a\u00020\u0012H\u0002J\b\u0010\u001a\u001a\u00020\u0012H\u0002J\b\u0010\u001b\u001a\u00020\u0012H\u0002J\b\u0010\u001c\u001a\u00020\u0012H\u0002J\u0010\u0010\u001d\u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010 \u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020\u001fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2 = {"Lcom/biathlonapp/ui/onboarding/OnboardingActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "autoAdvanceEnabled", "", "autoAdvanceRunnable", "Ljava/lang/Runnable;", "binding", "Lcom/biathlonapp/databinding/ActivityOnboardingBinding;", "handler", "Landroid/os/Handler;", "isFirstLaunch", "onboardingCompleted", "sharedPreferences", "Landroid/content/SharedPreferences;", "viewPagerAdapter", "Lcom/biathlonapp/ui/onboarding/OnboardingPagerAdapter;", "completeOnboarding", "", "goToMainActivity", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "scheduleAutoAdvance", "setupButtons", "setupDotsIndicator", "setupViewPager", "showAuthChoiceDialog", "updateButtons", "position", "", "updateDotsIndicator", "Companion", "app_release"})
public final class OnboardingActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.biathlonapp.databinding.ActivityOnboardingBinding binding;
    private com.biathlonapp.ui.onboarding.OnboardingPagerAdapter viewPagerAdapter;
    private android.content.SharedPreferences sharedPreferences;
    @org.jetbrains.annotations.NotNull()
    private final android.os.Handler handler = null;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Runnable autoAdvanceRunnable;
    private boolean autoAdvanceEnabled = true;
    private boolean isFirstLaunch = false;
    private boolean onboardingCompleted = false;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_NAME = "onboarding_prefs";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_ONBOARDING_COMPLETED = "onboarding_completed";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_APP_FIRST_LAUNCH = "app_first_launch";
    @org.jetbrains.annotations.NotNull()
    public static final com.biathlonapp.ui.onboarding.OnboardingActivity.Companion Companion = null;
    
    public OnboardingActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupViewPager() {
    }
    
    private final void scheduleAutoAdvance() {
    }
    
    private final void setupDotsIndicator() {
    }
    
    private final void updateDotsIndicator(int position) {
    }
    
    private final void setupButtons() {
    }
    
    private final void updateButtons(int position) {
    }
    
    private final void completeOnboarding() {
    }
    
    private final void showAuthChoiceDialog() {
    }
    
    private final void goToMainActivity() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/biathlonapp/ui/onboarding/OnboardingActivity$Companion;", "", "()V", "KEY_APP_FIRST_LAUNCH", "", "KEY_ONBOARDING_COMPLETED", "PREFS_NAME", "newIntent", "Landroid/content/Intent;", "context", "Landroid/content/Context;", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.content.Intent newIntent(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}