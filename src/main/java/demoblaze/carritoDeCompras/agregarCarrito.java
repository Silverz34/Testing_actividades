package demoblaze.carritoDeCompras;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;


public class agregarCarrito {
    public static void main(String[] args) {
        String PRODUCTO = "Samsung galaxy s6";
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            driver.get("https://www.demoblaze.com/");
            WebElement producto = wait.until(
                    ExpectedConditions.elementToBeClickable(By.linkText(PRODUCTO))
            );
            producto.click();
            WebElement anadir = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Add to cart']"))
            );
            anadir.click();
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();

            WebElement carrito = wait.until(
              ExpectedConditions.elementToBeClickable(By.id("cartur"))
            );
            carrito.click();

            WebElement tabla_validacion = wait.until(
              ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid"))
            );
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan( By.cssSelector("#tbodyid tr"), 0 ));

            List<WebElement> rows = driver.findElements(By.cssSelector("#tbodyid tr"));
            boolean found = false;
            for (WebElement row : rows) {
                List<WebElement> cols = row.findElements(By.tagName("td"));
                if (cols.size() > 1 && cols.get(1).getText().contains(PRODUCTO)){
                    found = true;
                    break;
                }
            }

            if(found){
                System.out.println("producto agregado al carrito" + PRODUCTO);
            }else{
                System.out.println("producto no agregado al carrito");
            }
        }finally {
            driver.quit();
        }
    }
}
