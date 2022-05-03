package aula.com.projeto;

import aula.com.projeto.controller.UserController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

@SpringBootTest
public class BuscarTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveBuscarDados() throws Exception {
        URI uri = new URI("/user");
        String content = "{\"name\":\"teste\" , \"email\":\"teste@gemail.com\"}";
        mockMvc.perform(MockMvcRequestBuilders
            .post(uri)
            .content(content)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().is(200));
    }

}
