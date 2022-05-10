package aula.com.projeto;

import aula.com.projeto.exception.GeracaoDocumentoException;
import aula.com.projeto.service.GeradorPdfService;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GeradorDePdfTest {

    private String nome = "Joao Pedro De Abreu Castro";
    private String numeroCarteira = "2658493";
    private String cpf = "062.215.987-58";
    private String data = DateFormat.getDateInstance(DateFormat.LONG).format(new Date());

    @Autowired
    private GeradorPdfService geradorPdfService;


    @Test
    public void deveGerarPDF() throws IOException, GeracaoDocumentoException {
        final String texto = "<html><head><link href=\"classpath:/template.css\" rel=\"stylesheet\"/></head><body><header><img src=\"classpath:/basis.png\" width=\"150\" height=\"80\" /> </header>\n" +
            "<nav> <center><h4>TERMO DE RESPONSABILIDADE SOBRE O USO DE\n" +

            "PROGRAMAS LICENCIADOS</h4></center></nav><p class=\"classpath:/template.css\">Basis Tecnologia da Informação, inscrito no CNPJ sob o Nº\n" +
            "11.777.162/0001-57, determina expressamente que todos os\n" +
            "programas/produtos a serem utilizados em seus computadores\n" +
            "devem ser devidamente licenciados, sendo verdade a utilização de\n" +
            "qualquer programa/produto “pirata”.\n" +
            "</p>\n" +
            "<p>\n" +
            "O(a) colaborador(a): "+ nome +"- Estagiário, Empresa Basis, lotado no Venâncio\n" +
            "Shopping, portador do RG sob o nº "+ numeroCarteira+" e CPF nº\n" + cpf+
            "0 se compromete a seguir a orientação da empresa,\n" +
            "sob pena de responder pessoalmente, inclusive penalmente, pelo\n" +
            "uso/instalação de programas/produtos não licenciados nos\n" +
            "computadores cedidos pela empresa para uso pessoal e\n" +
            "profissional.\n" +
            "</p>" +
            "<footer class=\"classpath:/template.css\">\n" +
            "<h5><center>Brasília-DF, "+ data+".</center></h5>\n" +
            "</footer></body></html>"
            ;
        Reader template = new StringReader(texto);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        geradorPdfService.gerarPDF(template, stream);
        final byte[] pdfData = stream.toByteArray();
        Path temp = Files.createTempFile("hello", ".pdf");
        System.out.println(temp);
        FileUtils.writeByteArrayToFile(temp.toFile(), pdfData);
        assertThat(extractPdfText(pdfData)).contains("O(a) colaborador(a): ", cpf);
        stream.close();
    }

    private String extractPdfText(byte[] pdfData) throws IOException {
        try (PDDocument pdfDocument = PDDocument.load(new ByteArrayInputStream(pdfData))) {
            return new PDFTextStripper().getText(pdfDocument);
        }
    }
}
