package com.example.proyecto1.viewModel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b3\n\u0002\u0010\u000b\n\u0002\b\n\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010?\u001a\u0002082\u0006\u0010@\u001a\u00020\u0004H\u0002J\u0006\u0010A\u001a\u000208R+\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR/\u0010\f\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u000f\u0010\u000b\u001a\u0004\b\r\u0010\u0007\"\u0004\b\u000e\u0010\tR+\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u0013\u0010\u000b\u001a\u0004\b\u0011\u0010\u0007\"\u0004\b\u0012\u0010\tR/\u0010\u0014\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u0017\u0010\u000b\u001a\u0004\b\u0015\u0010\u0007\"\u0004\b\u0016\u0010\tR+\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u001b\u0010\u000b\u001a\u0004\b\u0019\u0010\u0007\"\u0004\b\u001a\u0010\tR/\u0010\u001c\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u001f\u0010\u000b\u001a\u0004\b\u001d\u0010\u0007\"\u0004\b\u001e\u0010\tR+\u0010 \u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b#\u0010\u000b\u001a\u0004\b!\u0010\u0007\"\u0004\b\"\u0010\tR/\u0010$\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\'\u0010\u000b\u001a\u0004\b%\u0010\u0007\"\u0004\b&\u0010\tR+\u0010(\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b+\u0010\u000b\u001a\u0004\b)\u0010\u0007\"\u0004\b*\u0010\tR/\u0010,\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b/\u0010\u000b\u001a\u0004\b-\u0010\u0007\"\u0004\b.\u0010\tR+\u00100\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b3\u0010\u000b\u001a\u0004\b1\u0010\u0007\"\u0004\b2\u0010\tR/\u00104\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b7\u0010\u000b\u001a\u0004\b5\u0010\u0007\"\u0004\b6\u0010\tR+\u00109\u001a\u0002082\u0006\u0010\u0003\u001a\u0002088F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b>\u0010\u000b\u001a\u0004\b:\u0010;\"\u0004\b<\u0010=\u00a8\u0006B"}, d2 = {"Lcom/example/proyecto1/viewModel/FormRegViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "<set-?>", "", "boleta", "getBoleta", "()Ljava/lang/String;", "setBoleta", "(Ljava/lang/String;)V", "boleta$delegate", "Landroidx/compose/runtime/MutableState;", "boletaError", "getBoletaError", "setBoletaError", "boletaError$delegate", "carrera", "getCarrera", "setCarrera", "carrera$delegate", "carreraError", "getCarreraError", "setCarreraError", "carreraError$delegate", "confirmPassword", "getConfirmPassword", "setConfirmPassword", "confirmPassword$delegate", "confirmPasswordError", "getConfirmPasswordError", "setConfirmPasswordError", "confirmPasswordError$delegate", "correo", "getCorreo", "setCorreo", "correo$delegate", "correoError", "getCorreoError", "setCorreoError", "correoError$delegate", "nombre", "getNombre", "setNombre", "nombre$delegate", "nombreError", "getNombreError", "setNombreError", "nombreError$delegate", "password", "getPassword", "setPassword", "password$delegate", "passwordError", "getPasswordError", "setPasswordError", "passwordError$delegate", "", "registroExitoso", "getRegistroExitoso", "()Z", "setRegistroExitoso", "(Z)V", "registroExitoso$delegate", "isEmailValido", "email", "validarCampos", "app_debug"})
public final class FormRegViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState nombre$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState boleta$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState correo$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState carrera$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState password$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState confirmPassword$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState nombreError$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState boletaError$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState correoError$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState carreraError$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState passwordError$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState confirmPasswordError$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState registroExitoso$delegate = null;
    
    public FormRegViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNombre() {
        return null;
    }
    
    public final void setNombre(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBoleta() {
        return null;
    }
    
    public final void setBoleta(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCorreo() {
        return null;
    }
    
    public final void setCorreo(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCarrera() {
        return null;
    }
    
    public final void setCarrera(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPassword() {
        return null;
    }
    
    public final void setPassword(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getConfirmPassword() {
        return null;
    }
    
    public final void setConfirmPassword(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getNombreError() {
        return null;
    }
    
    public final void setNombreError(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBoletaError() {
        return null;
    }
    
    public final void setBoletaError(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getCorreoError() {
        return null;
    }
    
    public final void setCorreoError(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getCarreraError() {
        return null;
    }
    
    public final void setCarreraError(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPasswordError() {
        return null;
    }
    
    public final void setPasswordError(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getConfirmPasswordError() {
        return null;
    }
    
    public final void setConfirmPasswordError(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    public final boolean getRegistroExitoso() {
        return false;
    }
    
    public final void setRegistroExitoso(boolean p0) {
    }
    
    public final boolean validarCampos() {
        return false;
    }
    
    private final boolean isEmailValido(java.lang.String email) {
        return false;
    }
}