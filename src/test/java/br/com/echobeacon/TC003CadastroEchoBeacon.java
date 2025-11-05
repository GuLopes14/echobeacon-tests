package br.com.echobeacon;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class TC003CadastroEchoBeacon {

    // Cenário 1: Cadastro com sucesso
    @Test
    public void cadastroComSucessoTest() {
        // Dado: acesso à página de cadastro
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/echobeacon/novo");
        assert driver.getCurrentUrl().equals("http://localhost:8080/echobeacon/novo");
        
        // Quando: preencho os campos
        driver.findElement(By.id("numeroIdentificacao")).sendKeys(String.valueOf(System.currentTimeMillis() % 100000));
        
        Select selectStatus = new Select(driver.findElement(By.id("statusConexao")));
        selectStatus.selectByIndex(0);
        
        driver.findElement(By.id("btn-cadastrar-echobeacon")).click();
        
        // Então: redireciona para vinculação
        assert driver.getCurrentUrl().equals("http://localhost:8080/echobeacon");
        
        driver.quit();
    }

    // Cenário 2: Número de identificação vazio
    @Test
    public void numeroVazioTest() {
        // Dado: acesso à página de cadastro
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/echobeacon/novo");
        
        // Quando: clico em cadastrar sem preencher o número
        Select selectStatus = new Select(driver.findElement(By.id("statusConexao")));
        selectStatus.selectByIndex(0);
        
        driver.findElement(By.id("btn-cadastrar-echobeacon")).click();
        
        // Então: permanece na mesma página
        assert driver.getCurrentUrl().equals("http://localhost:8080/echobeacon/novo");
        
        driver.quit();
    }

    // Cenário 3: Número duplicado
    @Test
    public void numeroDuplicadoTest() {
        // Dado: cadastro primeiro echobeacon
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/echobeacon/novo");
        
        String numeroDuplicado = "10000";
        driver.findElement(By.id("numeroIdentificacao")).sendKeys(numeroDuplicado);
        
        Select selectStatus = new Select(driver.findElement(By.id("statusConexao")));
        selectStatus.selectByIndex(0);
        
        driver.findElement(By.id("btn-cadastrar-echobeacon")).click();
        
        // Quando: tento cadastrar novamente com mesmo número
        driver.get("http://localhost:8080/echobeacon/novo");
        driver.findElement(By.id("numeroIdentificacao")).sendKeys(numeroDuplicado);
        
        Select selectStatus2 = new Select(driver.findElement(By.id("statusConexao")));
        selectStatus2.selectByIndex(0);
        
        driver.findElement(By.id("btn-cadastrar-echobeacon")).click();
        
        // Então: sistema não deve dar erro 500
        assert !driver.getTitle().contains("Error");
        
        driver.quit();
    }
}
