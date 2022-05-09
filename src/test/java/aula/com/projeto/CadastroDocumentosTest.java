package aula.com.projeto;

import aula.com.projeto.exception.DocumentoInexistente;
import aula.com.projeto.exception.DocumentoSemNome;
import aula.com.projeto.model.CadastroDocumentos;
import aula.com.projeto.model.Documentos;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class CadastroDocumentosTest {

    @Test
    @DisplayName("Deve criar o cadastro de pessoas")
    public void deveCriarCadastroDePessoas(){
        CadastroDocumentos documentos = new CadastroDocumentos();
        Documentos documentos1 = new Documentos();
        documentos1.setNome("teste");
        documentos.adicionar(documentos1);


        Assertions.assertThat(documentos.getDocumentos())
            .isNotEmpty().hasSize(1).contains(documentos1);
    }
    @Test
    public void naoDeveAdicionarDocumentoVazio(){
        CadastroDocumentos documentos = new CadastroDocumentos();
        Documentos documentos1 = new Documentos();
        org.junit.jupiter.api.Assertions.assertThrows(DocumentoSemNome.class, () ->documentos.adicionar(documentos1));
    }
    @Test
    public void deveRemoverUmaPessoa() throws DocumentoInexistente {
        CadastroDocumentos documentos = new CadastroDocumentos();
        Documentos documentos1 = new Documentos();
        documentos1.setNome("teste");
        documentos.adicionar(documentos1);

        documentos.remover(documentos1);

        Assertions.assertThat(documentos.getDocumentos()).isEmpty();
    }
    @Test(expected = DocumentoInexistente.class)
    public void deveErroDocumentoInexistente() throws DocumentoInexistente {
        CadastroDocumentos documentos = new CadastroDocumentos();
        Documentos documentos1 = new Documentos();

        documentos.remover(documentos1);

    }
}
