package aula.com.projeto.service;

import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.core.io.ResourceLoader;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextUserAgent;
import org.xhtmlrenderer.util.XRLog;

import java.io.IOException;
import java.io.InputStream;

public class ClassPathAwareUserAgent extends ITextUserAgent {

    private ResourceLoader resourceLoader;

    public ClassPathAwareUserAgent(ITextOutputDevice outputDevice, SharedContext sharedContext, ResourceLoader resourceLoader) {
        super(outputDevice);
        this.resourceLoader = resourceLoader;
        setSharedContext(sharedContext);
    }
    @Override
    protected InputStream resolveAndOpenStream(String uri) {
        InputStream retorno;
        if (!StringUtils.isBlank(uri) && uri.startsWith("classpath:")) {
            retorno = carrwgaUriDoClasspath(uri);
        } else {
            retorno = super.resolveAndOpenStream(uri);
        }
        return retorno;
    }

    private InputStream carrwgaUriDoClasspath(String uri) {
        try {
            return resourceLoader.getResource(uri).getInputStream();
        } catch (IOException e) {
            XRLog.exception("Erro ao carregar a uri: " + uri, e);
        }
        return null;
    }
}
