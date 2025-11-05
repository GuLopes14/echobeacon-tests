package br.com.echobeacon;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TC001ListagemDeMotos {

    // Cenário 1: Acessar página de motos
    @Test
    public void acessarPaginaMotosTest() {
        // Dado: acesso à página de motos
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/motos");
        
        // Então: página carrega corretamente
        assert driver.getCurrentUrl().equals("http://localhost:8080/motos");
        assert driver.findElement(By.id("titulo-motos")).isDisplayed();
        
        driver.quit();
    }

    // Cenário 2: Verificar seção recepção existe
    @Test
    public void secaoRecepcaoTest() {
        // Dado: acesso à página de motos
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/motos");
        
        // Então: seção recepção deve estar visível
        assert driver.findElement(By.id("secao-recepcao")).isDisplayed();
        
        driver.quit();
    }

    // Cenário 3: Verificar seção pátio existe
    @Test
    public void secaoPatioTest() {
        // Dado: acesso à página de motos
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/motos");
        
        // Então: seção pátio deve estar visível
        assert driver.findElement(By.id("secao-patio")).isDisplayed();
        
        driver.quit();
    }

    // Cenário 4: Botão adicionar moto existe
    @Test
    public void botaoAdicionarMotoTest() {
        // Dado: acesso à página de motos
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/motos");
        
        // Quando: verifico botão adicionar
        assert driver.findElement(By.id("btn-adicionar-moto")).isDisplayed();
        
        // Clico no botão
        driver.findElement(By.id("btn-adicionar-moto")).click();
        
        // Então: vai para página de cadastro
        assert driver.getCurrentUrl().equals("http://localhost:8080/motos/novo");
        
        driver.quit();
    }
}
