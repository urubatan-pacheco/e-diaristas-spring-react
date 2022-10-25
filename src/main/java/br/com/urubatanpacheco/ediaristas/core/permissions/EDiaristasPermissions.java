package br.com.urubatanpacheco.ediaristas.core.permissions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

public @interface EDiaristasPermissions {

    @PreAuthorize("hasAnyAuthority('DIARISTA','CLIENTE')")
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface isDiaristaOrCliente {}
}
