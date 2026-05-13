package com.pichincha.simulator.tasks.comparison;

import com.pichincha.core.utils.JsonReader;
import com.pichincha.simulator.constants.Environment;
import com.pichincha.simulator.models.AmortizationResult;
import com.pichincha.simulator.models.CreditSimulationData;
import com.pichincha.simulator.models.comparison.ProductComparisonResult;
import com.pichincha.simulator.models.comparison.ProductSimulationSnapshot;
import com.pichincha.simulator.questions.AmortizationQuestion;
import com.pichincha.simulator.tasks.EnterFinancialInfo;
import com.pichincha.simulator.tasks.SelectCreditType;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CompareCreditProducts implements Task {

    private static final int TIMEOUT = 30;

    private final String firstScenarioId;
    private final String secondScenarioId;

    public CompareCreditProducts(String firstScenarioId, String secondScenarioId) {
        this.firstScenarioId = firstScenarioId;
        this.secondScenarioId = secondScenarioId;
    }

    public static CompareCreditProducts between(String firstScenarioId, String secondScenarioId) {
        return Tasks.instrumented(
                CompareCreditProducts.class,
                firstScenarioId,
                secondScenarioId
        );
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        System.out.println("\n================================================================================");
        System.out.println("FLUJO 5 - COMPARACIÓN DE PRODUCTOS FINANCIEROS");
        System.out.println("Producto base      : " + firstScenarioId);
        System.out.println("Producto comparado : " + secondScenarioId);
        System.out.println("================================================================================");

        ProductSimulationSnapshot firstProduct = simulateAndCapture(actor, firstScenarioId, false);
        ProductSimulationSnapshot secondProduct = simulateAndCapture(actor, secondScenarioId, true);

        ProductComparisonResult comparisonResult = new ProductComparisonResult(
                firstProduct,
                secondProduct
        );

        actor.remember(ProductComparisonResult.MEMORY_KEY, comparisonResult);
    }

    private <T extends Actor> ProductSimulationSnapshot simulateAndCapture(T actor,
                                                                           String scenarioId,
                                                                           boolean reopenSimulator) {

        if (reopenSimulator) {
            reopenSimulatorInsideIframe();
        }

        CreditSimulationData creditData = JsonReader.getById(scenarioId);

        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println("Ejecutando simulación para comparación: " + scenarioId);
        System.out.println("Tipo de crédito : " + creditData.getTipoCredito());
        System.out.println("Monto préstamo  : " + creditData.getMontoPrestamo());
        System.out.println("Monto vivienda  : " + creditData.getMontoVivienda());
        System.out.println("Plazo           : " + creditData.getTerm());
        System.out.println("--------------------------------------------------------------------------------");

        actor.attemptsTo(
                SelectCreditType.from(creditData),
                EnterFinancialInfo.from(creditData)
        );

        AmortizationResult result = actor.asksFor(
                AmortizationQuestion.fromUI()
        );

        ProductSimulationSnapshot snapshot = new ProductSimulationSnapshot(
                scenarioId,
                creditData.getTipoCredito(),
                creditData.getMontoPrestamo(),
                creditData.getTerm(),
                result
        );

        printProductSnapshot(snapshot);

        return snapshot;
    }

    private void reopenSimulatorInsideIframe() {

        WebDriver driver = Serenity.getDriver();

        driver.switchTo().defaultContent();
        driver.get(Environment.BASE_URL);

        WebDriverWait wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(TIMEOUT)
        );

        WebElement iframe = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("iframe")
                )
        );

        driver.switchTo().frame(iframe);

        WebElement dropdown = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("pichincha-dropdown")
                )
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior:'instant', block:'nearest', inline:'nearest'});",
                dropdown
        );
    }

    private void printProductSnapshot(ProductSimulationSnapshot snapshot) {
        System.out.println("\nResumen capturado del producto: " + snapshot.getScenarioId());
        System.out.println("Tipo de crédito mostrado      : " + snapshot.getProductType());
        System.out.println("Base amortización tabla       : " + snapshot.getAmortizationBase());
        System.out.println("Capital mostrado UI           : " + snapshot.getCapitalShownByUi());
        System.out.println("Cuota mensual UI              : " + snapshot.getMonthlyFee());
        System.out.println("Tasa anual UI                 : " + snapshot.getAnnualRate());
        System.out.println("Número de cuotas tabla        : " + snapshot.getInstallments());
    }
}
