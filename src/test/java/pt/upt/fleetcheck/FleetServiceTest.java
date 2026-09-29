package pt.upt.fleetcheck;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FleetServiceTest {

    @Test
    void testNeedsService() {
        FleetService service = new FleetService();

        // Record Vehicle: id, model, mileageKm, lastServiceKm, serviceIntervalKm
        Vehicle v1 = new Vehicle("1", "Truck", 120000, 100000, 15000); // 20.000 km desde a última revisão (> 15.000)
        Vehicle v2 = new Vehicle("2", "Car", 50000, 45000, 15000);   // 5.000 km desde a última revisão (< 15.000)

        assertTrue(service.needsService(v1), "Vehicle with kms since service > interval should need service");
        assertFalse(service.needsService(v2), "Vehicle with kms since service < interval should not need service");
    }

    @Test
    void testAverageMileage() {
        FleetService service = new FleetService();
        List<Vehicle> vehicles = List.of(
                new Vehicle("1", "Car", 10000, 0, 15000),
                new Vehicle("2", "Car", 30000, 0, 15000)
        );

        double avg = service.averageMileage(vehicles);
        assertEquals(20000.0, avg, 0.001, "Average mileage calculation should be accurate");
    }

    @Test
    void testBoundaryCondition() {
        FleetService service = new FleetService();
        // A diferença exata é 15000 km (igual ao intervalo de revisão)
        Vehicle vBoundary = new Vehicle("3", "Car", 30000, 15000, 15000);

        // Se atingiu o limite exato do intervalo, deve necessitar de revisão (true)
        assertTrue(service.needsService(vBoundary), "Vehicle reaching exact service interval should need service");
    }
}