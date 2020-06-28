import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;

/**
 *
 * @author Vinicius Zorzanelli
 */
public class InstagramAPI {

    private ChromeDriver driver;
    private String user;
    private String password;
    
    public InstagramAPI(String user, String password) {
        System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
        driver = new ChromeDriver();
        this.user = user;
        this.password = password;
    }

    public void openPage(String url){
        waitPageLoad(2000);
        driver.get(url);
        waitPageLoad(2000);
    }

    public void login(){
        openPage("https://www.instagram.com/accounts/login/");

        WebElement usernameInput = driver.findElementByXPath("//*[@id=\"react-root\"]/section/main/div/article/div/div[1]/div/form/div[2]/div/label/input");
        WebElement passwordInput = driver.findElementByXPath("//*[@id=\"react-root\"]/section/main/div/article/div/div[1]/div/form/div[3]/div/label/input");

        usernameInput.sendKeys(user);
        passwordInput.sendKeys(password);

        WebElement loginButton = driver.findElementByXPath("//*[@id=\"react-root\"]/section/main/div/article/div/div[1]/div/form/div[4]/button");
        loginButton.click();
        waitPageLoad(2000);

        WebElement notNowButton = driver.findElementByXPath("//*[@id=\"react-root\"]/section/main/div/div/div/div/button");
        if(notNowButton != null && notNowButton.getText().equals("Agora não")){
            notNowButton.click();
            waitPageLoad(2000);
        }

        notNowButton = driver.findElementByXPath("/html/body/div[4]/div/div/div/div[3]/button[2]");
        if(notNowButton != null && notNowButton.getText().equals("Agora não")){
            notNowButton.click();
            waitPageLoad(2000);
        }
    }

    private void waitPageLoad(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void comment(ArrayList<String> users){

        WebElement postButton = driver.findElementByXPath("//*[@id=\"react-root\"]/section/main/div/div[1]/article/div[2]/section[3]/div/form/button");

        for (String user: users) {
            try{
                user = user.trim().toLowerCase();
                waitPageLoad(2000);
                Actions actions = new Actions(driver);
                actions.moveToElement(driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/div[1]/article/div[2]/section[3]/div/form/textarea")));
                actions.click();
                actions.sendKeys("@" + user);
                waitPageLoad(2000);
                actions.build().perform();
                postButton.click();
                waitPageLoad(2000);
            }catch (Exception e){
                waitPageLoad(12000);
            }
        }
    }


    //=====[ GETTER AND SETTERS ]=====

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

