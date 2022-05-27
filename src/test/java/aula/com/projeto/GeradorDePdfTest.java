package aula.com.projeto;

import aula.com.projeto.exception.GeracaoDocumentoException;
import aula.com.projeto.model.TemplateDocumento;
import aula.com.projeto.model.User;
import aula.com.projeto.service.GeradorPdfService;
import aula.com.projeto.service.TemplateDocumentoService;
import aula.com.projeto.service.UserService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.pdf.qrcode.WriterException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.Assert.assertArrayEquals;

@SpringBootTest
public class GeradorDePdfTest {

    private String nome = "Joao Pedro De Abreu Castro";
    private String numeroCarteira = "2658493";
    private String cpf = "062.215.987-58";
    private String data = DateFormat.getDateInstance(DateFormat.LONG).format(new Date());

    @Autowired
    private GeradorPdfService geradorPdfService;

    @Autowired
    private TemplateDocumentoService templateDocumentoService;

    @Autowired
    private UserService userService;



    @Test
    public void deveGerarTemplateII() throws GeracaoDocumentoException, IOException {
        TemplateDocumento templateDocumento = templateDocumentoService.findById(1);
        User user = userService.findById(1);
        Reader reader = geradorPdfService.prossesaTemplate(user, templateDocumento );
        System.out.println(IOUtils.toString(reader));
        assertThat(IOUtils.toString(reader).contains(cpf));
    }


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
            "O(a) colaborador(a): "+nome+"- Estagiário, Empresa Basis, lotado no Venâncio\n" +
            "Shopping, portador do RG sob o nº "+ numeroCarteira+" e CPF nº\n" + cpf+
            " se compromete a seguir a orientação da empresa,\n" +
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
        System.out.println(pdfData);
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

    @Test
    public void byteParaBase(){
        Base64 code = new Base64();
        byte[] matriz = { -56, -123, -109, -109, -106, 64, -26,
            -106, -103, -109, -124, 90 };
        byte[] base = code.encode(matriz);
        System.out.println(String.valueOf(base));
    }
    @Test
    public void whenGetBytesWithNamedCharset_thenOK()
        throws UnsupportedEncodingException {
        String inputString = "Hello World!";
        String charsetName = "IBM01140";

        byte[] byteArrray = inputString.getBytes("IBM01140");

        assertArrayEquals(
            new byte[] { -56, -123, -109, -109, -106, 64, -26,
                -106, -103, -109, -124, 90 },
            byteArrray);
    }
    @Test
    public void byteParaString() {
        Base64 code = new Base64();
        byte[] matriz = {-56, -123, -109, -109, -106, 64, -26,
            -106, -103, -109, -124, 90};
        String palavra = String.valueOf(code.decode(matriz));
        assertThat(palavra.contains("Hello World!"));
    }

    @Test
    @DisplayName("Gera QrCode com uma determinada url")
    public final void generateQRCodeImage()
        throws WriterException, IOException, com.google.zxing.WriterException {

        // String text = String.valueOf(Files.createTempFile("java.io.text","ASD"));
        String text = "asmdmasdadaw";
        //Perguntar se e pra ser aleatorio mesmo, pois aqui sera o conteudo do QRcode
        String filePath = String.valueOf(Files.createTempFile("BASIS",".png"));
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE,200,200);
        System.out.println("-----------------BIT MATRIX-------------");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Path path = FileSystems.getDefault().getPath(filePath);
        System.out.println(path);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    @Test
    public void imageParaByteArray() throws IOException {
    File image = new File("/tmp/BASIS10422564510369614420.png");
    BufferedImage original = ImageIO.read(image);
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    ImageIO.write(original,"png",stream);
    byte [] imagembyte = stream.toByteArray();
    System.out.println(Arrays.toString(imagembyte));
    }

    @Test
    public void imagemDirect() throws IOException, com.google.zxing.WriterException {
        // String text = String.valueOf(Files.createTempFile("java.io.text","ASD"));
        String text = "asmdmasdadaw";
        //Perguntar se e pra ser aleatorio mesmo, pois aqui sera o conteudo do QRcode
        String filePath = String.valueOf(Files.createTempFile("BASIS",".png"));
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE,200,200);
        System.out.println("-----------------BIT MATRIX-------------");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Path path = FileSystems.getDefault().getPath(filePath);
        System.out.println(path);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }
}
