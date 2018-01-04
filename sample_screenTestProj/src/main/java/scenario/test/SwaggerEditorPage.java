package scenario.test;

import static com.codeborne.selenide.Selenide.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.codeborne.selenide.conditions.Text;

public class SwaggerEditorPage extends PageObj {

    /**
     * @param url
     */
    public SwaggerEditorPage(String url) {
        open(url);
    }

    /**
     * @param label
     * @throws Exception
     */
    public void elementScreenshot(String label, String elementName) throws Exception {
        String elementCss = null;
        if ("pane1".equals(elementName)) {
            elementCss = "#swagger-editor > div > div:nth-child(2) > section > div > div.Pane.vertical.Pane1";
        } else if ("pane2".equals(elementName)) {
            elementCss = "#swagger-editor > div > div:nth-child(2) > section > div > div.Pane.vertical.Pane2";
        } else {
            throw new RuntimeException("No Such Element");
        }
        _elementScreenshot(elementCss, label);
    }

    /**
     * @param label
     */
    public void click(String label) {
        String elementCss = null;
        if ("User:".equals(label)) {
            elementCss = "#swagger-editor > div > div:nth-child(1) > div.topbar > div > div.topbar-specmgr-info > div:nth-child(4) > span";
        } else if ("Add User".equals(label)) {
            elementCss = "#swagger-editor > div > div:nth-child(1) > div.topbar > div > div.topbar-specmgr-info > div:nth-child(4) > div > ul > li:nth-child(1) > button";
        } else if ("Create".equals(label)) {
            elementCss = "#swagger-editor > div > div:nth-child(1) > div.topbar > div > div.topbar-specmgr-info > span:nth-child(5) > div.swagger-ui > div > div > div > div > div > div.modal-ux-content > div.topbar-popup-button-area > button";
        } else if ("Change User".equals(label)) {
            elementCss = "#swagger-editor > div > div:nth-child(1) > div.topbar > div > div.topbar-specmgr-info > div:nth-child(4) > div > ul > li:nth-child(3) > button";
        } else {
            throw new RuntimeException("No Such Label");
        }
        $(By.cssSelector(elementCss)).click();
    }

    /**
     * @param label
     * @param value
     */
    public void input(String label, String value) {
        if ("id:".equals(label)) {
            $(By.cssSelector("#swagger-editor > div > div:nth-child(1) > div.topbar > div > div.topbar-specmgr-info > span:nth-child(5) > div.swagger-ui > div > div > div > div > div > div.modal-ux-content > div.parameters-col_description > div:nth-child(2) > section > input[type=\"text\"]")).setValue(value);
        } else if ("email:".equals(label)) {
            $(By.cssSelector("#swagger-editor > div > div:nth-child(1) > div.topbar > div > div.topbar-specmgr-info > span:nth-child(5) > div.swagger-ui > div > div > div > div > div > div.modal-ux-content > div.parameters-col_description > div:nth-child(3) > section > input[type=\"text\"]")).setValue(value);
        } else if ("ace-editor".equals(label)) {
            // 不細工。もうちょっとうまく細工を...
            for (String val : value.split("\\\\n")) {
                $(By.cssSelector( "#ace-editor > textarea")).sendKeys(val);
                $(By.cssSelector( "#ace-editor > textarea")).sendKeys(Keys.RETURN);
            }
            // FIXME ace エディタへの入力は sendKeyでないとうまくいかない..
            // Element should be visible {#ace-editor > textarea}
            // https://stackoverflow.com/questions/30972119/how-to-automate-the-ace-editor-send-keys-using-webdriver
            // executeJavaScript("arguments[0].value = arguments[1];", $(By.cssSelector("#ace-editor > textarea")).toWebElement(), value);
        } else {
            throw new RuntimeException("No Such Label");
        }
    }

    /**
     * @param title
     */
    public void checkDialog(String title) {
        if ("Add User".equals(title)) {
            $(By.cssSelector("#swagger-editor > div > div:nth-child(1) > div.topbar > div > div.topbar-specmgr-info > span:nth-child(5) > div.swagger-ui > div > div > div > div > div > div.modal-ux-header > h3")).shouldHave(new Text(title));
        } else {
            throw new RuntimeException("No Such Label");
        }
    }
}
