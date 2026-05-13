package com.pichincha.simulator.questions;

import com.pichincha.core.utils.MoneyUtils;
import com.pichincha.simulator.models.AmortizationResult;
import com.pichincha.simulator.models.AmortizationRow;
import com.pichincha.simulator.ui.FinancialForm;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.*;

import org.openqa.selenium.*;

import java.math.BigDecimal;
import java.util.*;

public class AmortizationQuestion implements Question<AmortizationResult> {

    @Override
    public AmortizationResult answeredBy(Actor actor) {

        WebDriver driver = Serenity.getDriver();

        // --------------------------
        // TÍTULO Y PRODUCTO
        // --------------------------
        String modalTitle = driver.findElement(By.cssSelector(FinancialForm.MODAL_TITLE)).getText();
        String productName = "PRECISO"; // para flujo de prueba
        System.out.println("\n===== MODAL TITLE: " + modalTitle + " =====");
        System.out.println("===== PRODUCTO: " + productName + " =====\n");

        // --------------------------
        // DATOS OBTENIDOS DEL UI
        // --------------------------
        String modalText = driver.findElement(By.cssSelector(FinancialForm.MODAL)).getText();
        BigDecimal annualRate = extractAnnualRate(modalText);
        BigDecimal capitalUI = extractCapital(modalText);

        List<WebElement> rowsUI = driver.findElements(By.cssSelector(FinancialForm.TABLE_ROWS));
        List<AmortizationRow> uiRows = new ArrayList<>();
        BigDecimal monthlyFeeUI = BigDecimal.ZERO;

        for (WebElement row : rowsUI) {
            List<WebElement> cols = row.findElements(By.tagName("td"));

            int installment = Integer.parseInt(cols.get(0).getText().trim());
            BigDecimal capital = MoneyUtils.parseMoney(cols.get(2).getText());
            BigDecimal interest = MoneyUtils.parseMoney(cols.get(3).getText());
            BigDecimal insurance = MoneyUtils.parseMoney(cols.get(4).getText());
            BigDecimal fee = MoneyUtils.parseMoney(cols.get(6).getText());
            BigDecimal balance = MoneyUtils.parseMoney(cols.get(7).getText());

            monthlyFeeUI = fee;
            uiRows.add(new AmortizationRow(installment, capital, interest, insurance, fee, balance));
        }

        // --------------------------
        // DEBUG HORIZONTAL PROFESIONAL
        // --------------------------
        System.out.println("==================================================================================================================");
        System.out.printf("%-5s | %-10s | %-10s | %-10s | %-12s | %-10s%n",
                "CUOTA", "CAPITAL", "INTERES", "SEGURO", "CUOTA TOTAL", "SALDO");
        System.out.println("==================================================================================================================");

        for (AmortizationRow row : uiRows) {
            System.out.printf("%-5d | %-10.2f | %-10.2f | %-10.2f | %-12.2f | %-10.2f%n",
                    row.getInstallment(),
                    row.getCapital(),
                    row.getInterest(),
                    row.getInsurance(),
                    row.getFee(),
                    row.getBalance());
        }

        System.out.println("==================================================================================================================");
        System.out.println("CUOTA MENSUAL (UI)   : " + monthlyFeeUI);
        System.out.println("TASA ANUAL (UI)      : " + annualRate);
        System.out.println("CAPITAL APROBADO (UI): " + capitalUI);
        System.out.println("==================================================================================================================");
        System.out.println("Comparación realizada: Valores reales del UI\n");

        // --------------------------
        // DEVUELVE RESULTADO UI PARA VALIDACIÓN
        // --------------------------
        return new AmortizationResult(
                monthlyFeeUI,
                annualRate,
                capitalUI,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                uiRows
        );
    }

    // --------------------------
    // EXTRAE CAPITAL APROBADO
    // --------------------------
    private BigDecimal extractCapital(String text) {
        String[] lines = text.split("\\n");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].toLowerCase().contains("capital")) {
                return MoneyUtils.parseMoney(lines[i + 1]);
            }
        }
        return BigDecimal.ZERO;
    }

    // --------------------------
    // EXTRAE TASA EFECTIVA ANUAL
    // --------------------------
    private BigDecimal extractAnnualRate(String text) {
        String[] lines = text.split("\\n");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].toLowerCase().contains("tasa de interes efectiva anual")) {
                return MoneyUtils.parsePercentage(lines[i + 1]);
            }
        }
        return BigDecimal.ZERO;
    }

    // --------------------------
    // FACTORY
    // --------------------------
    public static AmortizationQuestion fromUI() {
        return new AmortizationQuestion();
    }
}