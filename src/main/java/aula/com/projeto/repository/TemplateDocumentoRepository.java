package aula.com.projeto.repository;

import aula.com.projeto.model.TemplateDocumento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateDocumentoRepository extends JpaRepository<TemplateDocumento, Long> {
}
