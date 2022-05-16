package aula.com.projeto.service;

import aula.com.projeto.model.TemplateDocumento;
import aula.com.projeto.repository.TemplateDocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TemplateDocumentoService {
    @Autowired
    TemplateDocumentoRepository templateDocumentoRepository;

    public TemplateDocumento findById(long id) {
        return templateDocumentoRepository.findById(id).get();
    }
}
