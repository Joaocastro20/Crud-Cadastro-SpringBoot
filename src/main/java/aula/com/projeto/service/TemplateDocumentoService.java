package aula.com.projeto.service;

import aula.com.projeto.controller.IndexController;
import aula.com.projeto.exception.GeracaoDocumentoException;
import aula.com.projeto.model.TemplateDocumento;
import aula.com.projeto.model.User;
import aula.com.projeto.repository.TemplateDocumentoRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class TemplateDocumentoService {

    private final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    TemplateDocumentoRepository templateDocumentoRepository;

    @Autowired
    TemplateDocumentoService templateDocumentoService;

    @Autowired
    UserService userService;

    @Autowired
    GeradorPdfService geradorPdfService;

    public TemplateDocumento findById(long id) {
        return templateDocumentoRepository.findById(id).get();
    }


    @Transactional(readOnly = true)
    public byte[] buscarDocumentoPdf(long id, long idt) throws GeracaoDocumentoException, IOException {
        log.debug("PDF");
        TemplateDocumento templateDocumento = templateDocumentoService.findById(idt);
        User user = userService.findById(id);

        final String modeloTemplate = IOUtils.toString(geradorPdfService.prossesaTemplate(user,templateDocumento));


        Reader template = new StringReader(modeloTemplate);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        geradorPdfService.gerarPDF(template,stream);
        System.out.println(modeloTemplate);
        byte[] saida = stream.toByteArray();

        final byte[] pdfData = stream.toByteArray();
        System.out.println(Arrays.toString(pdfData));
        Path temp = Files.createTempFile("hello", ".pdf");
        System.out.println(temp);
        FileUtils.writeByteArrayToFile(temp.toFile(), pdfData);
        stream.close();
        return saida;
    }
}
