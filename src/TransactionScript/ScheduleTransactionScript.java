package TransactionScript;

import ActiveRecord.Driver;
import ActiveRecord.DriverRepository;
import ActiveRecord.Shift;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class ScheduleTransactionScript {
    private final DriverRepository repository;

    public ScheduleTransactionScript(DriverRepository repository) {
        this.repository = repository;
    }

    public void addShiftToDriver(int driverId, LocalDateTime start, LocalDateTime end) {
        Driver driver = repository.findById(driverId);
        if (driver == null) {
            System.err.println("Водитель с ID " + driverId + " не найден.");
            return;
        }
        try {
            System.out.println("Начало транзакции добавления смены.");
            driver.addShift(new Shift(start, end));
            repository.save(driver);
            System.out.println("Транзакция успешно завершена: Смена добавлена.");
        } catch (Exception e) {
            System.err.println("Ошибка транзакции: " + e.getMessage());
        }
    }

    public List<Shift> getShiftsForDriverOnDate(int driverId, LocalDateTime date) {
        Driver driver = repository.findById(driverId);
        if (driver == null) {
            System.err.println("Водитель с ID " + driverId + " не найден.");
            return Collections.emptyList();
        }
        System.out.println("Получение списка смен для водителя на дату: " + date.toLocalDate());
        return driver.getShiftsForDate(date);
    }

    public boolean checkDriverAvailability(int driverId, LocalDateTime dateTime) {
        Driver driver = repository.findById(driverId);
        if (driver == null) {
            System.err.println("Водитель с ID " + driverId + " не найден.");
            return false;
        }
        System.out.println("Проверка доступности водителя на момент времени: " + dateTime);
        return driver.isAvailableAt(dateTime);
    }
}
