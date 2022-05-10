package aula.com.projeto.service;

import aula.com.projeto.exception.GeracaoDocumentoException;
import com.itextpdf.text.DocumentException;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Service
public class GeradorPdfService {

    private final ResourceLoader resourceLoader;

    public GeradorPdfService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
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
    }
