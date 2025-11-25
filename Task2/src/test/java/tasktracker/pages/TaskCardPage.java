package tasktracker.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TaskCardPage extends BasePage {

    private By taskTitle = By.cssSelector(".task-title, h1, h2");
    private By taskDescription = By.cssSelector(".task-description, .description");
    private By assigneeInfo = By.xpath("//*[contains(text(), 'Исполнитель:')]");
    private By boardInfo = By.xpath("//*[contains(text(), 'Доска:')]");
    private By boardLink = By.cssSelector("a[href*='board'], .board-link");

    public TaskCardPage(WebDriver driver) {
        super(driver);
    }

    public String getTaskTitle() {
        return getText(taskTitle);
    }

    public String getTaskDescription() {
        return getText(taskDescription);
    }

    public String getAssignee() {
        return getText(assigneeInfo);
    }

    public String getBoard() {
        return getText(boardInfo);
    }

    public boolean isTaskDetailsDisplayed() {
        return isElementVisible(taskTitle) &&
                isElementVisible(assigneeInfo);
    }

    public void clickBoardLink() {
        click(boardLink);
    }
}