package demoblaze;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class EliminarCarrito {
    public static void main(String[] args) {
        String producto = "Nexus 6";
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.demoblaze.com");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Paso 1: Agregar
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText(producto))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@onclick, 'addToCart')]"))).click();
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();

            // Paso 2: Ir al carrito y eliminar
            wait.until(ExpectedConditions.elementToBeClickable(By.id("cartur"))).click();
            WebElement tabla = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("tbodyid")));
            List<WebElement> filas = tabla.findElements(By.tagName("tr"));

            for (WebElement fila : filas) {
                if (fila.findElements(By.tagName("td")).get(1).getText().equals(producto)) {
                    fila.findElement(By.linkText("Delete")).click();
                    break;
                }
            }

            // Paso 3: Validar que desaparece
            driver.navigate().refresh();
            // Esperar a que la tabla cargue (aunque esté vacía)
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("tbodyid")));
            boolean existe = driver.findElements(By.xpath("//td[text()='" + producto + "']")).size() > 0;

            if (!existe) {
                System.out.println("ÉXITO: El producto " + producto + " fue eliminado correctamente.");
            } else {
                System.out.println("ERROR: El producto aún permanece en el carrito.");
            }
        } finally {
            driver.quit();
        }
    }
}