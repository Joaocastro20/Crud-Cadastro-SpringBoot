package aula.com.projeto.model;

import aula.com.projeto.exception.DocumentoInexistente;
import aula.com.projeto.exception.DocumentoSemNome;
import aula.com.projeto.model.Documentos;

import java.util.ArrayList;
import java.util.List;

public class CadastroDocumentos {
    private List<Documentos> documentos;

    public CadastroDocumentos() {
        this.documentos = new ArrayList<>();
    }

    public List<Documentos> getDocumentos() {
        return this.documentos;
    }

    public void adicionar(Documentos documentos) {
        if(documentos.getNome()==null){
            throw new DocumentoSemNome();
        }
        this.documentos.add(documentos);
    }

    public void remover(Documentos documentos1) throws DocumentoInexistente {
        if(this.documentos.isEmpty()){
            throw new DocumentoInexistente();
        }
        this.documentos.remove(documentos1);
    }
}
