package aula.com.projeto;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CauculadoraTeste {

    Calculadora calculadora;
    @Before
    public void setUp(){
        calculadora = new Calculadora();
    }

    @Test
    public void deveSomar2Numeros(){
        int n1 = 10,n2 = 5;
        int resultado = calculadora.somar(n1, n2);
        Assertions.assertThat(resultado).isEqualTo(15);

    }
    @Test(expected = RuntimeException.class)
    public void naoDeveSomarNumerosNegativos(){
        int n1 = -10, n2 = 5;
        calculadora.somar(n1,n2);

    }
    @Test(expected = RuntimeException.class)
    public void diminuirDaCalc(){
        int n1 = -10, n2 = 5;
        calculadora.subtrair(n1,n2);
    }
    @Test(expected = RuntimeException.class)
    public void divisaoPorZero(){
        int n1 = 10, n2 = 0;
        calculadora.divisao(n1,n2);
    }

}

class Calculadora{

    int somar(int num,int num2){
        if(num < 0 || num2 < 0){
            throw new RuntimeException("nao é permitido somar numeros negativos");
        }
        return num +num2;
    }
    public int subtrair(int num, int num2){
        if(num < 0 || num2 < 0){
            throw new RuntimeException("nao é permitido numeros negativos");
        }
        return num - num2;
    }
    public int divisao(int num, int num2){
        if( num2 <= 0){
            throw new RuntimeException("nao é permitido divisao por 0 ou numero menor");
        }
        return num / num2;
    }


}
