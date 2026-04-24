package demoblaze;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class PersistenciCarrito {
    public static void main(String[] args) {
        String producto = "Iphone 6 32gb";
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.demoblaze.com");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Agregar producto
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText(producto))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@onclick, 'addToCart')]"))).click();
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();

            // Ir al carrito
            wait.until(ExpectedConditions.elementToBeClickable(By.id("cartur"))).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("tbodyid")));

            // Refrescar página
            System.out.println("Refrescando la página para validar persistencia...");
            driver.navigate().refresh();

            // Validar que sigue ahí
            WebElement item = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//td[text()='" + producto + "']")
            ));

            if (item.isDisplayed()) {
                System.out.println("ÉXITO: El producto persiste después de refrescar.");
            }
        } catch (Exception e) {
            System.out.println("ERROR: El producto se perdió al refrescar o hubo un problema: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}