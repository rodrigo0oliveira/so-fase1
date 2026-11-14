package com.so.escalonamento;

import com.so.entities.Processo;

import java.util.List;
import java.util.Queue;

public interface Escalonador {

    Queue<Processo> ordenarProcessos(List<Processo> processos);

    Processo next();

    void put(Processo processo);
}
