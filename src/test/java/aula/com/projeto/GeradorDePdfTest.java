package aula.com.projeto;

import aula.com.projeto.exception.GeracaoDocumentoException;
import aula.com.projeto.model.TemplateDocumento;
import aula.com.projeto.model.User;
import aula.com.projeto.service.GeradorPdfService;
import aula.com.projeto.service.TemplateDocumentoService;
import aula.com.projeto.service.UserService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
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
    File image = new File("/tmp/BASIS13766036386056969130.png");
    BufferedImage original = ImageIO.read(image);
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    ImageIO.write(original,"png",stream);
    byte [] imagembyte = stream.toByteArray();
    System.out.println(Arrays.toString(imagembyte));
    }

    @Test
    public void imagemDirect() throws IOException, com.google.zxing.WriterException {
        String text = "asasdsadasdas";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200,200);
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        byte[] imagembyte = outputStream.toByteArray();
        System.out.println(Arrays.toString(imagembyte));

    }
    @Test
    public void byteParaImage() throws IOException {
        byte[] array = {-119, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, -56, 0, 0, 0, -56, 1, 0, 0, 0, 0, -123, 35, -109, 51, 0, 0, 0, -35, 73, 68, 65, 84, 120, 94, -19, -107, 49, 18, -123, 32, 12, 68, 67, -59, 49, -72, 41, -30, 77, 61, 6, 21, -7, -69, 65, 127, -31, 104, -19, 22, -20, 56, 12, -28, 89, -20, 24, 54, -102, -65, -55, -18, -123, -65, 22, 89, -124, 90, 68, -122, 116, -125, -110, 31, -43, 74, -29, -74, 40, 17, 110, -9, 110, 53, -5, 126, 29, 117, 72, -77, 2, -78, 117, -82, 73, -112, -32, -117, 102, 122, 87, 36, 40, -68, -72, -2, -106, 56, -69, 93, 6, -67, -97, 71, 25, 18, 41, -95, -27, 114, 79, -55, -25, 100, -118, 60, 106, 83, 34, 36, -78, -37, -14, -111, -36, -47, -10, -106, -75, 8, 84, 51, 83, 50, -116, 57, 86, 34, -68, -128, 52, -117, 87, -14, -95, 70, 6, 10, -50, 63, 6, 102, -116, 26, 113, 14, -107, -104, 43, -13, -47, 33, 83, 81, -93, 125, 41, 18, 55, 49, 113, -70, 88, 125, 72, -55, -73, -92, 96, 97, -97, -81, -67, 20, 105, 113, 7, -39, -19, -55, -43, 8, -54, 113, 25, -121, 21, 57, -62, 4, -93, -37, -74, 117, 45, -126, 101, -17, 49, -104, -89, 101, 33, 114, -90, -60, 105, 31, 92, -119, 60, 106, -111, 69, -88, 69, -76, -55, 15, 113, -107, 116, -62, -111, -41, 11, 91, 0, 0, 0, 0, 73, 69, 78, 68, -82, 66, 96, -126};
        ByteArrayInputStream bis = new ByteArrayInputStream(array);
        BufferedImage bimage = ImageIO.read(bis);
        String filePath = String.valueOf(Files.createTempFile("QR",".png"));
        Path path = FileSystems.getDefault().getPath(filePath);
        ImageIO.write(bimage,"jpg",new File(String.valueOf(path)));
        System.out.println(path);
    }
}
