

import ActiveRecord.Driver;
import ActiveRecord.DriverFactory;
import ActiveRecord.DriverRepository;
import ActiveRecord.Shift;
import TransactionScript.ScheduleTransactionScript;

import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Создание фабрики и репозитория
        DriverRepository repository = new DriverRepository();
        Driver driver = DriverFactory.createdriver("John Doe", true);
        repository.save(driver);

        // Создание транзакционного скрипта
        ScheduleTransactionScript transactionScript = new ScheduleTransactionScript(repository);

        // Добавление смен
        transactionScript.addShiftToDriver(driver.getId(), LocalDateTime.of(2026, 12, 14, 10, 0), LocalDateTime.of(2026, 12, 14, 12, 0));
        transactionScript.addShiftToDriver(driver.getId(), LocalDateTime.of(2026, 12, 15, 14, 0), LocalDateTime.of(2026, 12, 15, 16, 0));
        transactionScript.addShiftToDriver(2, LocalDateTime.of(2026, 12, 15, 14, 0), LocalDateTime.of(2026, 12, 15, 16, 0));

        // Проверка смен
        List<Shift> sessions = transactionScript.getShiftsForDriverOnDate(driver.getId(), LocalDateTime.of(2026, 12, 14, 0, 0));
        List<Shift> sessions2 = transactionScript.getShiftsForDriverOnDate(3, LocalDateTime.of(2026, 12, 14, 0, 0));
        System.out.println("Занятия на 2026-12-14: " + sessions);

        // Проверка доступности
        boolean isAvailable = transactionScript.checkDriverAvailability(driver.getId(), LocalDateTime.of(2026, 12, 14, 11, 0));
        System.out.println("Доступность на 2026-12-14 11:00: " + isAvailable);

    }
}