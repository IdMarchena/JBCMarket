package com.afk.backend.model.entity.enm;

import lombok.Getter;

@Getter
public enum OfertasLaborales {
    OfertasLaborales1(1);

    private final int numero;

    OfertasLaborales(int numero) {
        this.numero = numero;
    }

}
