package br.com.echobeacon;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class TC002CadastroMoto {

    // Cenário 1: Cadastro com sucesso
    @Test
    public void cadastroComSucessoTest() {
        // Dado: acesso à página de cadastro
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/motos/novo");
        assert driver.getCurrentUrl().equals("http://localhost:8080/motos/novo");
        
        // Quando: preencho todos os campos
        driver.findElement(By.id("placa")).sendKeys("ABC" + System.currentTimeMillis() % 10000);
        driver.findElement(By.id("chassi")).sendKeys("9BWZZZ377VT00" + (System.currentTimeMillis() % 10000));
        
        Select selectModelo = new Select(driver.findElement(By.id("modelo")));
        selectModelo.selectByIndex(0);
        
        driver.findElement(By.id("problema")).sendKeys("Motor fazendo barulho");
        driver.findElement(By.id("btn-cadastrar-moto")).click();
        
        // Então: redireciona para lista de motos
        assert driver.getCurrentUrl().equals("http://localhost:8080/motos");
        
        driver.quit();
    }

    // Cenário 2: Campos vazios
    @Test
    public void camposVaziosTest() {
        // Dado: acesso à página de cadastro
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/motos/novo");
        
        // Quando: clico em cadastrar sem preencher nada
        driver.findElement(By.id("btn-cadastrar-moto")).click();
        
        // Então: exibe mensagem de validação no campo placa
        String validationMessage = driver.findElement(By.id("placa")).getDomProperty("validationMessage");
        assert validationMessage.equals("Preencha este campo.") : "Mensagem de validação incorreta";
        driver.quit();
    }

    // Cenário 3: Apenas placa preenchida
    @Test
    public void apenasPlacaTest() {
        // Dado: acesso à página de cadastro
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/motos/novo");
        
        // Quando: preencho apenas a placa
        driver.findElement(By.id("placa")).sendKeys("ABC1234");
        driver.findElement(By.id("btn-cadastrar-moto")).click();
        
        // Então: exibe mensagem de validação no campo chassi
        String validationMessage = driver.findElement(By.id("chassi")).getDomProperty("validationMessage");
        assert validationMessage.equals("Preencha este campo.") : "Mensagem de validação incorreta";
        
        driver.quit();
    }

    // Cenário 4: Placa e chassi preenchidos
    @Test
    public void placaEChassiTest() {
        // Dado: tenho acessado à página de cadastro
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/motos/novo");
        
        // Quando: preencho placa e chassi, mas falta modelo e problema
        driver.findElement(By.id("placa")).sendKeys("ABC1234");
        driver.findElement(By.id("chassi")).sendKeys("1HGBH41JXMN109186");
        driver.findElement(By.id("btn-cadastrar-moto")).click();
        
        // Então: exibe mensagem de validação no campo problema
        String validationMessage = driver.findElement(By.id("problema")).getDomProperty("validationMessage");
        assert validationMessage.equals("Preencha este campo.") : "Mensagem de validação incorreta";
        
        driver.quit();
    }
}
