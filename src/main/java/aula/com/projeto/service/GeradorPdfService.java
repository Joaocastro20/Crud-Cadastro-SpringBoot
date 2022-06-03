package aula.com.projeto.service;

import aula.com.projeto.exception.GeracaoDocumentoException;
import aula.com.projeto.model.TemplateDocumento;
import aula.com.projeto.model.User;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.DocumentException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class GeradorPdfService {

    private final ResourceLoader resourceLoader;
    private final Configuration configuration;

    public GeradorPdfService(ResourceLoader resourceLoader, Configuration configuration) {
        this.resourceLoader = resourceLoader;
        this.configuration = configuration;
    }

    public void gerarPDF(Reader template, OutputStream saida) throws GeracaoDocumentoException {
        ITextRenderer renderer = criaRenderer();
        try {
            renderer.setDocument(IOUtils.toByteArray(template, StandardCharsets.UTF_8));
            renderer.layout();
            renderer.createPDF(saida);
        } catch (IOException | DocumentException e) {
            throw new GeracaoDocumentoException("Erro na geração do PDF", e);
        }
}

    private ITextRenderer criaRenderer() {
        final ITextRenderer renderer = new ITextRenderer();
        final SharedContext sharedContext = renderer.getSharedContext();
        sharedContext.setUserAgentCallback(
            new ClassPathAwareUserAgent(renderer.getOutputDevice(), sharedContext, resourceLoader));
        return renderer;
    }

    public Reader prossesaTemplate(User user, TemplateDocumento templateDocumento) throws GeracaoDocumentoException {
        try {
            Template template = new Template(templateDocumento.getNome(), templateDocumento.getModelo(), configuration);
            Writer writer = new StringWriter();
            Map<String, Object> root = new HashMap<>();
            root.put("user", user);
            template.process(root, writer);
            return new StringReader(writer.toString());
        }catch (IOException | TemplateException e){
            throw new GeracaoDocumentoException(String.format("Erro ao gerar o modelo %s",templateDocumento.getNome()),e);
        }
    }

    public String geraQRCode(String text) throws WriterException, IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200,200);
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        byte[] imagembyte = outputStream.toByteArray();
        return Base64.encodeBase64String(imagembyte);

    }
}
