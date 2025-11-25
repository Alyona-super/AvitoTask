package tasktracker.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;

import java.util.List;

public class HomePage extends BasePage {

    private By newTaskButton = By.xpath("//button[contains(text(), 'создать задачу')]");
    private By taskTitleInput = By.xpath("//input[@placeholder='Название']");
    private By taskDescriptionInput = By.xpath("//textarea[@placeholder='Описание']");
    private By assigneeDropdown = By.xpath("//div[contains(text(), 'Исполнитель')]");
    private By boardDropdown = By.xpath("//div[contains(text(), 'Доска')]");
    private By createTaskButton = By.xpath("//button[contains(text(), 'создать')]");
    private By searchInput = By.xpath("//input[contains(@placeholder, 'Поиск')]");
    private By taskItems = By.cssSelector(".task-item, [class*='task'], .issue-item");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void navigateToHomePage() {
        driver.get("https://avito-tech-internship-psi.vercel.app/issues");
    }

    public void clickNewTaskButton() {
        click(newTaskButton);
    }

    public void fillTaskTitle(String title) {
        type(taskTitleInput, title);
    }

    public void fillTaskDescription(String description) {
        type(taskDescriptionInput, description);
    }

    public void selectAssignee(String assigneeName) {
        click(assigneeDropdown);
        By assigneeOption = By.xpath(String.format("//div[contains(text(), '%s')]", assigneeName));
        click(assigneeOption);
    }

    public void selectBoard(String boardName) {
        click(boardDropdown);
        By boardOption = By.xpath(String.format("//div[contains(text(), '%s')]", boardName));
        click(boardOption);
    }

    public void clickCreateTask() {
        click(createTaskButton);
    }

    public void searchTask(String searchTerm) {
        type(searchInput, searchTerm);
        searchInput.findElement(driver).sendKeys(Keys.ENTER);
    }

    public void openFirstTask() {
        if (getTaskCount() > 0) {
            click(taskItems);
        }
    }

    public int getTaskCount() {
        return driver.findElements(taskItems).size();
    }

    public boolean isTaskDisplayed(String taskTitle) {
        By taskLocator = By.xpath(String.format("//*[contains(text(), '%s')]", taskTitle));
        return isElementVisible(taskLocator);
    }


    public List<WebElement> getAllTasks() {
        return driver.findElements(taskItems);
    }

    public void navigateToBoard(String boardName) {
        By boardLink = By.xpath(String.format("//a[contains(text(), '%s')]", boardName));
        click(boardLink);
    }
}