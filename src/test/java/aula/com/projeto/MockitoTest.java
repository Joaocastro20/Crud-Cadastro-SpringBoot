package aula.com.projeto;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {

    @Mock
    List<String> lista;

    @Test
    public void testeMockito(){
        Mockito.when(lista.size()).thenReturn(2);

        int size = 0;
        if(1 == 1){
            size = lista.size();

        }

        //Mockito.verify(lista, Mockito.times(2)).size();
        //Mockito.verify(lista, Mockito.never()).size();
        //InOrder inOrder = Mockito.inOrder(lista);
        //inOrder.verify(lista).size();


    }
}
