package br.com.urubatanpacheco.ediaristas.core.enums;

public enum Icone {
    UPF_CLEANING_1("upf-cleaning-1"),
    UPF_CLEANING_2("upf-cleaning-2"),
    UPF_CLEANING_3("upf-cleaning-3");

    private String nome;

    private Icone(String nome) {
        this.nome=nome;
    }

    public String getNome() {
        return this.nome;
    }
}
