package appLojas;

//import io.appium.java_client.MobileElement;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;


public class calculadoraTeste {
	private AndroidDriver<MobileElement> driver;

	@Before
	public void inicializarAppium() throws MalformedURLException {
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("platformName", "Android");
		desiredCapabilities.setCapability("deviceName", "emulator-5554");
		desiredCapabilities.setCapability("automationName", "uiautomator2");
		desiredCapabilities.setCapability(MobileCapabilityType.APP,
				"/Users/diego.pereira/eclipse-workspace/calculadora/src/main/resources/CTAppium_2_0.apk");
		
		driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Clicando no formulario
		driver.findElement(MobileBy.xpath("//*[@text='Formulário']")).click();
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void devePreenchercampoTxto() throws MalformedURLException {
		List<MobileElement> elementosEncontrados = driver.findElements(By.className("android.widget.TextView"));
		elementosEncontrados.get(1).click();
		MobileElement campoNome = driver.findElement(MobileBy.AccessibilityId("nome"));
		campoNome.sendKeys("Diego");
		String text = campoNome.getText();
		Assert.assertEquals("Diego", text);
	}

	@Test
	public void deveInteragirComUmCombo() throws MalformedURLException {
		/// Realizando uma ação
		driver.findElement(MobileBy.AccessibilityId("console")).click();
		driver.findElement(MobileBy.xpath("//android.widget.CheckedTextView[@text='PS4']")).click();

		// Resultado esperado
		String text = driver.findElement(MobileBy.xpath("//android.widget.Spinner/android.widget.TextView")).getText();
		Assert.assertEquals("PS4", text);
	}

	@Test
	public void deveClicarNoCheckboxESwitch() throws MalformedURLException {
		/// Verificar status dos elementos
		MobileElement checkBox = driver.findElement(By.className("android.widget.CheckBox"));
		MobileElement switc = driver.findElement(By.className("android.widget.Switch"));
		Assert.assertTrue(checkBox.getAttribute("checked").equals("false"));
		Assert.assertTrue(switc.getAttribute("checked").equals("true"));

		// Clicar nos elementos
		checkBox.click();
		switc.click();

		// Verificar status alterados
		Assert.assertFalse(checkBox.getAttribute("checked").equals("false"));
		Assert.assertFalse(switc.getAttribute("checked").equals("true"));
	}

	@Test
	public void devePreencherOFormulario() throws MalformedURLException {
		// Inputar algo no campo nome e erfificando
		MobileElement inputName = driver.findElement(MobileBy.AccessibilityId("nome"));
		inputName.sendKeys("Diego da midia");
		String outputName = inputName.getText();
		Assert.assertEquals("Diego da midia", outputName);

		/// Verificar status dos elementos check e swtc
		MobileElement checkBox = driver.findElement(MobileBy.AccessibilityId("check"));
		MobileElement switc = driver.findElement(MobileBy.AccessibilityId("switch"));
		Assert.assertTrue(checkBox.getAttribute("checked").equals("false"));
		Assert.assertTrue(switc.getAttribute("checked").equals("true"));

		// Escolhendo o console
		driver.findElement(MobileBy.AccessibilityId("console")).click();
		driver.findElement(By.xpath("//*[@text='PS4']")).click();
		MobileElement textConsole = driver.findElement(By.xpath("//android.widget.Spinner/android.widget.TextView"));
		String textPS4 = textConsole.getText();
		Assert.assertEquals("PS4", textPS4);

		// Clicar nos elementos check e switch
		checkBox.click();
		switc.click();
		Assert.assertFalse(checkBox.getAttribute("checked").equals("false"));
		Assert.assertFalse(switc.getAttribute("checked").equals("true"));

		// Clicando em salvar
		driver.findElement(By.xpath("//*[@text='SALVAR']")).click();

		// Verificar status nome alterado
		MobileElement titleAfter = driver.findElement(By.xpath("//android.widget.TextView[@index='12']"));
		String titleVerification = titleAfter.getText();
		Assert.assertEquals("Nome: Diego da midia", titleVerification);

		// Verificar status console
		MobileElement consoleAfter = driver.findElement(By.xpath("//android.widget.TextView[@index='13']"));
		String consoleVerification = consoleAfter.getText();
		Assert.assertEquals("Console: ps4", consoleVerification);

		// Verificar checkbox
		MobileElement checkBoxAfter = driver.findElement(By.xpath("//android.widget.TextView[@index='16']"));
		String chechBoxVerification = checkBoxAfter.getText();
		Assert.assertEquals("Checkbox: Marcado", chechBoxVerification);

		// Verificar switch
		MobileElement switchAfter = driver.findElement(By.xpath("//android.widget.TextView[@index='15']"));
		String switchVerification = switchAfter.getText();
		Assert.assertEquals("Switch: Off", switchVerification);
	}
}
