package testdata;

import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public interface TestInterface {

    Map<UUID, Supplier<Object>> dataGenerator();
}
