package demoblaze.carritoDeCompras;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class multiplesProductos {
    public static void main(String[] args) {
        String[] productos = {"Samsung galaxy s6", "Nokia lumia 1520"};
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.demoblaze.com");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            for (String producto : productos) {
                seleccionarElemento(producto, wait);
                agregarAlCarrito(wait, driver);
                // Regresar al inicio para el siguiente producto
                wait.until(ExpectedConditions.elementToBeClickable(By.id("nava"))).click();
            }

            irACarrito(wait);
            for (String producto : productos) {
                validarCompra(producto, wait);
            }
        } finally {
            driver.quit();
        }
    }

    private static void seleccionarElemento(String producto, WebDriverWait wait) {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText(producto))).click();
    }

    private static void agregarAlCarrito(WebDriverWait wait, WebDriver driver) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@onclick, 'addToCart')]"))).click();
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    private static void irACarrito(WebDriverWait wait) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("cartur"))).click();
    }

    private static boolean validarCompra (String producto, WebDriverWait wait) {
        WebElement tabla = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("tbodyid")));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("td")));
        List<WebElement> filas = tabla.findElements(By.tagName("tr"));
        for (WebElement fila : filas) {
            if (fila.findElements(By.tagName("td")).get(1).getText().equals(producto)) {
                System.out.println("VALIDACIÓN EXITOSA: " + producto + " está en el carrito.");
                return true;
            }
        }
        System.out.println("FALLO: " + producto + " no se encontró.");
        return false;
    }
}