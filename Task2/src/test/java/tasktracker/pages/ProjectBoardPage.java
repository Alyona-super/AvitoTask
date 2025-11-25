package tasktracker.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProjectBoardPage extends BasePage {

    private By boardTitle = By.cssSelector("h1, h2");
    private By boardColumns = By.cssSelector(".board-column, .column, [class*='column']");
    private By boardTasks = By.cssSelector(".task-item, .board-task");

    public ProjectBoardPage(WebDriver driver) {
        super(driver);
    }

    public String getBoardTitle() {
        return getText(boardTitle);
    }

    public int getColumnsCount() {
        return driver.findElements(boardColumns).size();
    }

    public int getTasksCount() {
        return driver.findElements(boardTasks).size();
    }

    public boolean isBoardDisplayed() {
        return isElementVisible(boardTitle) &&
                getColumnsCount() > 0;
    }
}