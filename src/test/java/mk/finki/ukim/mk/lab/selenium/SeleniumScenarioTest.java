package mk.finki.ukim.mk.lab.selenium;


import mk.finki.ukim.mk.lab.model.Manufacturer;
import mk.finki.ukim.mk.lab.model.Role;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.service.BalloonService;
import mk.finki.ukim.mk.lab.service.ManufacturerService;
import mk.finki.ukim.mk.lab.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumScenarioTest {

    @Autowired
    UserService userService;

    @Autowired
    ManufacturerService manufacturerService;

    @Autowired
    BalloonService balloonService;

    private static boolean dataInitialized = false;

    private HtmlUnitDriver driver;

    private static Manufacturer m1;
    private static Manufacturer m2;
    private static User regularUser;
    private static User adminUser;

    private static String user = "user";
    private static String admin = "admin";

    @BeforeEach
    private void setup() {
        this.driver = new HtmlUnitDriver(true);
        initData();
    }

    @AfterEach
    public void destroy() {
        if (this.driver != null) {
            this.driver.close();
        }
    }

    private void initData() {
        if (!dataInitialized) {

            m1 = manufacturerService.save("m1", "m1", "m1").get();
            m2 = manufacturerService.save("m2", "m2", "m2").get();


            regularUser = userService.register(user, user, user, user, user, LocalDate.now(), Role.ROLE_USER);
            adminUser = userService.register(admin, admin, admin, admin, admin, LocalDate.now(), Role.ROLE_ADMIN);
            dataInitialized = true;
        }
    }

    @Test
    public void testScenario() throws Exception {
        BalloonsPage balloonsPage = BalloonsPage.to(this.driver);
        balloonsPage.assertElemts(0, 0, 0, 0);
        LoginPage loginPage = LoginPage.openLogin(this.driver);

        balloonsPage = LoginPage.doLogin(this.driver, loginPage, adminUser.getUsername(), admin);
        balloonsPage.assertElemts(0, 0, 0, 1);

        balloonsPage = AddOrEditBalloon.addBalloon(this.driver, "test1", "test1", m1.getName());
        balloonsPage.assertElemts(1, 1, 1, 1);

        balloonsPage = AddOrEditBalloon.addBalloon(this.driver, "test2", "test2", m2.getName());
        balloonsPage.assertElemts(2, 2, 2,1);

        balloonsPage.getDeleteButtons().get(1).click();

        balloonsPage.assertElemts(1, 1, 1, 1);

        balloonsPage = AddOrEditBalloon.editBalloon(this.driver,  balloonsPage.getEditButtons().get(0), "test3", "test3", m2.getName());
        balloonsPage.assertElemts(1, 1, 1, 1);

        loginPage = LoginPage.logout(this.driver);
        balloonsPage = LoginPage.doLogin(this.driver, loginPage, regularUser.getUsername(), user);
        balloonsPage.assertElemts(1, 0, 0, 0);

    }


}
