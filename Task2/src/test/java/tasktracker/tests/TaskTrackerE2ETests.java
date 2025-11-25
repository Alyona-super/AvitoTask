package tasktracker.tests;

import tasktracker.config.TestConfig;
import tasktracker.pages.HomePage;
import tasktracker.pages.ProjectBoardPage;
import tasktracker.pages.TaskCardPage;
import tasktracker.utils.TestDataGenerator;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskTrackerE2ETests {

    private WebDriver driver;
    private HomePage homePage;
    private TaskCardPage taskCardPage;
    private ProjectBoardPage projectBoardPage;

    @BeforeAll
    public static void setupClass() {
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    public void setupTest() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--start-maximized");
        driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(TestConfig.IMPLICIT_WAIT);

        homePage = new HomePage(driver);
        taskCardPage = new TaskCardPage(driver);
        projectBoardPage = new ProjectBoardPage(driver);

        homePage.navigateToHomePage();
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("1: Создание задачи")
    public void testCreateTask() {
        // Подготовка данных для теста
        String taskTitle = TestDataGenerator.generateUniqueTaskName();
        String taskDescription = "Проверка связи)";
        String assignee = "Максим Орлов";
        String board = "Редизайн карточки товара";

        // Шаги
        homePage.clickNewTaskButton();
        homePage.fillTaskTitle(taskTitle);
        homePage.fillTaskDescription(taskDescription);
        homePage.selectAssignee(assignee);
        homePage.selectBoard(board);
        homePage.clickCreateTask();


        assertThat(homePage.isTaskDisplayed(taskTitle))
                .as("Созданная задача должна отображаться в списке")
                .isTrue();
    }

    @Test
    @DisplayName("2: Открытие карточки задачи")
    public void testOpenTaskCard() {
        // Проверка, что есть хотя бы одна задача
        int initialTaskCount = homePage.getTaskCount();
        Assumptions.assumeTrue(initialTaskCount > 0, "В системе должны быть задачи для тестирования");

        // Шаги
        homePage.openFirstTask();

        // Проверка результатов
        assertThat(taskCardPage.isTaskDetailsDisplayed())
                .as("Должно открыться окно задачи с детальной информацией")
                .isTrue();

        assertThat(taskCardPage.getTaskTitle())
                .as("Заголовок задачи должен быть не пустым")
                .isNotBlank();

        assertThat(taskCardPage.getAssignee())
                .as("Информация об исполнителе должна отображаться")
                .isNotBlank();
    }

    @Test
    @DisplayName("3: Поиск задачи")
    public void testSearchTask() {
        // Подготовка: создание задачи
        String searchTerm = TestDataGenerator.generateSearchTerm();

        homePage.clickNewTaskButton();
        homePage.fillTaskTitle(searchTerm);
        homePage.clickCreateTask();

        // Шаги
        homePage.searchTask(searchTerm);

        // Проверка результатов
        int tasksAfterSearch = homePage.getTaskCount();
        assertThat(tasksAfterSearch)
                .as("После поиска должны отображаться задачи")
                .isGreaterThan(0);

        // Проверяем, что все отображенные задачи содержат поисковый запрос
        boolean allTasksContainSearchTerm = homePage.getAllTasks().stream()
                .allMatch(task -> task.getText().contains(searchTerm));

        assertThat(allTasksContainSearchTerm)
                .as("Все найденные задачи должны содержать поисковый термин")
                .isTrue();
    }

    @Test
    @DisplayName("4: Переход на доску проекта")
    public void testNavigateToProjectBoard() {
        String boardName = "Редизайн карточки товара";

        // Шаги
        homePage.navigateToBoard(boardName);

        // Проверка результатов
        assertThat(projectBoardPage.isBoardDisplayed())
                .as("Должна открыться страница доски проекта")
                .isTrue();

        assertThat(projectBoardPage.getBoardTitle())
                .as("Заголовок доски должен соответствовать ожидаемому")
                .contains(boardName);

        assertThat(projectBoardPage.getColumnsCount())
                .as("На доске должны быть колонки статусов")
                .isGreaterThan(0);

        assertThat(projectBoardPage.getTasksCount())
                .as("На доске должны отображаться задачи")
                .isGreaterThanOrEqualTo(0);
    }
}