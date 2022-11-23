package br.com.urubatanpacheco.ediaristas.api.assemblers;

import java.util.List;

public interface Assembler<R> {

    void adicionarLinks(R resources);

    void adicionarLinks(List<R> collectionResources);
    
}
