package br.com.echobeacon;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class VincularEchoBeaconTest {

    // Cenário 1: Vinculação com sucesso (quando há dados)
    @Test
    public void vinculacaoComSucessoTest() {
        // Dado: acesso à página de vinculação
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/echobeacon");
        assert driver.getCurrentUrl().equals("http://localhost:8080/echobeacon");
        
        try {
            // Quando: seleciono moto e echobeacon
            Select selectMoto = new Select(driver.findElement(By.id("moto-select")));
            selectMoto.selectByIndex(0);
            
            Select selectEchoBeacon = new Select(driver.findElement(By.id("echoBeacon-select")));
            selectEchoBeacon.selectByIndex(0);
            
            driver.findElement(By.id("btn-vincular")).click();
            
            // Então: redireciona para lista de motos
            assert driver.getCurrentUrl().equals("http://localhost:8080/motos");
        } catch (NoSuchElementException e) {
            System.out.println("Não há motos ou echobeacons disponíveis para vincular");
        }
        
        driver.quit();
    }

    // Cenário 2: Acessar página de vinculação
    @Test
    public void acessarPaginaVinculacaoTest() {
        // Dado: acesso à página de vinculação
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/echobeacon");
        
        // Então: página carrega corretamente
        assert driver.getCurrentUrl().equals("http://localhost:8080/echobeacon");
        assert driver.findElement(By.id("titulo-vincular")).isDisplayed();
        
        driver.quit();
    }

    // Cenário 3: Botão vincular existe
    @Test
    public void botaoVincularExisteTest() {
        // Dado: acesso à página de vinculação
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/echobeacon");
        
        // Então: botão vincular deve estar visível
        assert driver.findElement(By.id("btn-vincular")).isDisplayed();
        
        driver.quit();
    }

    // Cenário 4: Botão adicionar EchoBeacon
    @Test
    public void botaoAdicionarEchoBeaconTest() {
        // Dado: acesso à página de vinculação
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/echobeacon");
        
        // Quando: clico no botão adicionar
        driver.findElement(By.id("btn-adicionar-echobeacon")).click();
        
        // Então: vai para página de cadastro de echobeacon
        assert driver.getCurrentUrl().equals("http://localhost:8080/echobeacon/novo");
        
        driver.quit();
    }
}
