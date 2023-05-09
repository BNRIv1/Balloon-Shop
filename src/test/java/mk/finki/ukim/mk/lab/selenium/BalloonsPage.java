package mk.finki.ukim.mk.lab.selenium;

import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class BalloonsPage extends AbstractPage{

    @FindBy(css = ".balloon-info")
    private List<WebElement> balloons;


    @FindBy(css = ".delete-product")
    private List<WebElement> deleteButtons;


    @FindBy(className = "edit-product")
    private List<WebElement> editButtons;

    @FindBy(css = ".add-product-btn")
    private List<WebElement> addBalloonButton;


    public BalloonsPage(WebDriver driver) {
        super(driver);
    }

    public static BalloonsPage to(WebDriver driver) {
        get(driver, "/products");
        return PageFactory.initElements(driver, BalloonsPage.class);
    }

    public void assertElemts(int balloonNumber, int deleteButtons, int editButtons, int addButtons) {
        Assert.assertEquals("rows do not match", balloonNumber, this.getBalloons().size());
        Assert.assertEquals("delete do not match", deleteButtons, this.getDeleteButtons().size());
        Assert.assertEquals("edit do not match", editButtons, this.getEditButtons().size());
        Assert.assertEquals("add is visible", addButtons, this.getAddBalloonButton().size());
    }

}
