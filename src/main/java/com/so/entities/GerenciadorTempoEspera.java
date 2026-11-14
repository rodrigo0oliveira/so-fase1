package com.so.entities;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorTempoEspera {

    private final List<ProcessoGerenciado> gerenciados = new ArrayList<>();

    public void adicionarGerenciado(Processo processo, long tempo) {
        this.gerenciados.add(new ProcessoGerenciado(processo, tempo));
    }

    public void exibirStatusEspera() {
        System.out.println();
        System.out.println("============ TEMPO DE ESPERA ============");

        if (gerenciados.isEmpty()) {
            return;
        }

        long total = 0;
        for (ProcessoGerenciado g : gerenciados) {
            System.out.println("[ESPERA] " + g);
            total += g.tempoEsperaMs;
        }

        System.out.printf("[ESPERA] Tempo médio de espera: %.2f ms (%d processo(s))%n",
                (double) total / gerenciados.size(), gerenciados.size());
    }

    public static class ProcessoGerenciado {
        private final Processo processo;
        private final long tempoEsperaMs;

        public ProcessoGerenciado(Processo processo, long tempoEsperaMs) {
            this.processo = processo;
            this.tempoEsperaMs = tempoEsperaMs;
        }

        @Override
        public String toString() {
            return "ProcessoGerenciado{" +
                    "processo=" + processo.getId() +
                    ", tempoEsperaMs=" + tempoEsperaMs +
                    '}';
        }
    }
}