package aula.com.projeto.service;

import aula.com.projeto.exception.GeracaoDocumentoException;
import aula.com.projeto.model.TemplateDocumento;
import aula.com.projeto.model.User;
import com.itextpdf.text.DocumentException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import javassist.bytecode.StackMap;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
}
