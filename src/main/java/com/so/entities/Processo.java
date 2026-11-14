package com.so.entities;

import java.util.concurrent.atomic.AtomicInteger;

import static com.so.SimuladorCPU.SLEEPMS;

public class Processo {

    private static final AtomicInteger integerGenerator = new AtomicInteger(0);

    private final int id;
    private int prioridade;
    private int quantidadeInstrucoes;
    private int etapaAtual;
    private long tempoEspera;

    public Processo(int quantidadeInstrucoes, int prioridade) {
        this(gerarId(), quantidadeInstrucoes, prioridade);
    }

    public Processo(int id, int quantidadeInstrucoes, int prioridade) {
        this.id = id;
        this.quantidadeInstrucoes = quantidadeInstrucoes;
        this.prioridade = prioridade;
        this.etapaAtual = 0;
        this.tempoEspera = 0;
    }

    private static int gerarId() {
        return integerGenerator.incrementAndGet();
    }

    public int getId() {
        return id;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public int getQuantidadeInstrucoes() {
        return quantidadeInstrucoes;
    }

    public void setQuantidadeInstrucoes(int quantidadeInstrucoes) {
        this.quantidadeInstrucoes = quantidadeInstrucoes;
    }

    public int getEtapaAtual() {
        return etapaAtual;
    }

    public void setEtapaAtual(int etapaAtual) {
        this.etapaAtual = etapaAtual;
    }

    public long getTempoEspera() {
        return tempoEspera;
    }

    public void setTempoEspera(long tempoEspera) {
        this.tempoEspera = tempoEspera;
    }

    public boolean terminou() {
        return quantidadeInstrucoes <= 0;
    }

    public void executar() throws InterruptedException {
        executar(this.quantidadeInstrucoes);
    }

    public void executar(int quantum) throws InterruptedException {
        for (int i = 0; i < quantum && !terminou(); i++) {
            quantidadeInstrucoes--;
            etapaAtual++;

            System.out.printf(
                    "Processo: %d | Total Instruções: %d | Prioridade: %d | Executando etapa %d, restam %d etapa(s)%n",
                    id, quantidadeInstrucoes + etapaAtual, prioridade, etapaAtual, quantidadeInstrucoes);

            Thread.sleep(SLEEPMS);
        }
    }

    @Override
    public String toString() {
        return "id=" + id;
    }
}