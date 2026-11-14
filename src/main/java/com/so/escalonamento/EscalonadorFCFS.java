package com.so.escalonamento;

import com.so.entities.Processo;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EscalonadorFCFS implements Escalonador {

    private final Queue<Processo> processos;

    public EscalonadorFCFS(List<Processo> processosList) {
        this.processos = this.ordenarProcessos(processosList);
    }

    @Override
    public Queue<Processo> ordenarProcessos(List<Processo> processos) {
        return new LinkedList<>(processos);
    }

    @Override
    public Processo next() {
        return this.processos.poll();
    }

    @Override
    public void put(Processo processo) {

    }
}
