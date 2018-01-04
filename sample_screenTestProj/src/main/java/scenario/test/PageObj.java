package scenario.test;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;

import com.codeborne.selenide.WebDriverRunner;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public abstract class PageObj {

    public PageObj() {
        super();
    }

    /**
     * @param label
     * @throws Exception
     */
    public void screenshot(String label) throws Exception {
        Screenshot shot = new AShot()
                          .shootingStrategy(ShootingStrategies.viewportPasting(100))
                          .takeScreenshot(WebDriverRunner.getWebDriver());
        File f = new File("build/reports/tests/scenario/test/" + SwaggerEditorPage.class.getSimpleName() + "_" + label + ".png") ;
        f.mkdirs();
        ImageIO.write(shot.getImage(), "png",f);
    }

    public void _elementScreenshot(String elementCss, String label) throws Exception {
        // FIXED AShotだと、以下エラー。デフォルトjqueryを使うようなので、CoordsProviderの指定が必要
        // 指定しても、windows版 chromeではちゃんと取れない。docker上ではok
        // また、全体スクリーンショットの場合、指定すると全画面のスクリーンショットが取れない
        // org.openqa.selenium.WebDriverException: unknown error: $ is not defined
        // => https://github.com/yandex-qatools/ashot/issues/68
        // use aShot.coordsProvider(new WebDriverCoordsProvider()) if you don't have jQuery on your page.
        // WebDriverCoordsProvider not uses jQuery for position calculating.
        Screenshot shot = new AShot().coordsProvider(new WebDriverCoordsProvider())
                                      .shootingStrategy(ShootingStrategies.viewportPasting(100))
                .takeScreenshot(WebDriverRunner.getWebDriver(),
                                By.cssSelector(elementCss).findElement(WebDriverRunner.getWebDriver()));
        BufferedImage image = shot.getImage();

        File file = new File("build/reports/tests/scenario/test/" + SwaggerEditorPage.class.getSimpleName() + "_" + label + ".png") ;
        file.mkdirs();
        ImageIO.write(image, "png",file);
    }

    public abstract void click(String arg1);
    public abstract void checkDialog(String arg1);
    public abstract void input(String arg1, String arg2);
    public abstract void elementScreenshot(String elementCss, String label) throws Exception;

}